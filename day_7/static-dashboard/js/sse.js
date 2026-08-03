// TICKET-ADV104 — EventSource subscription to /api/v1/trades/stream
// TICKET-ADV105 — prepend-and-animate with XSS-safe rendering + 50-entry DOM cap
(function () {
  'use strict';

  const fmtQty   = new Intl.NumberFormat('en-US');
  const fmtPrice = new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 4,
  });

  /** Always escape server-provided strings before inserting into innerHTML. */
  function escapeHtml(s) {
    return String(s)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#39;');
  }

  // ES6 class wrapping the SSE connection + feed-rendering state.
  class TradeFeed {
    constructor(feedEl, statusEl, streamUrl, maxEntries) {
      this.feedEl = feedEl;
      this.statusEl = statusEl;
      this.streamUrl = streamUrl || '/api/v1/trades/stream';
      this.maxEntries = maxEntries || 50;
      this.sse = null;
    }

    updateBadge(text) {
      if (this.statusEl) this.statusEl.textContent = text;
    }

    // TICKET-ADV105 — prepend one trade card
    prependTradeRow(trade) {
      const statusMap = { MATCHED: 'matched', BREAK: 'break', UNMATCHED: 'break' };
      const mod = statusMap[trade.status] || 'pending';

      const el = document.createElement('article');
      // trade-card--new triggers the combined slide-in + fade-in entrance;
      // stripped after 500 ms once the CSS animation finishes.
      el.className = 'trade-card trade-card--' + mod + ' trade-card--new';
      el.innerHTML =
        '<strong>' + escapeHtml(trade.tradeRef) + '</strong> ' +
        '<span>' + escapeHtml(trade.symbol) + '</span> ' +
        '<span>qty=' + fmtQty.format(trade.qty != null ? trade.qty : (trade.quantity || 0)) + '</span> ' +
        '<span>price=' + fmtPrice.format(trade.price || 0) + '</span> ' +
        '<span>[' + escapeHtml(trade.status) + ']</span>';

      this.feedEl.prepend(el);

      setTimeout(() => el.classList.remove('trade-card--new'), 500);

      // Cap the feed so the DOM stays bounded after a long session.
      while (this.feedEl.children.length > this.maxEntries) {
        this.feedEl.lastElementChild.remove();
      }
    }

    // TICKET-ADV104 — EventSource connection
    connect() {
      this.sse = new EventSource(this.streamUrl);

      this.sse.onopen = () => this.updateBadge('Live');

      this.sse.onmessage = (e) => {
        try {
          this.prependTradeRow(JSON.parse(e.data));
        } catch (_) {
          // malformed JSON from server — ignore silently
        }
      };

      // IMPORTANT: do NOT reconnect manually inside onerror.
      // EventSource auto-reconnects on its own with exponential backoff;
      // calling connect() again here would flood the dev server with
      // cascading connection attempts.
      this.sse.onerror = () => this.updateBadge('Reconnecting…');
    }

    close() {
      if (this.sse) this.sse.close();
    }

    // Demo fallback — fires a few hardcoded trades via setTimeout so the
    // page still demonstrates the animated feed without a live backend.
    // Remove this call once your backend SSE endpoint is live.
    runDemo(demoTrades) {
      demoTrades.forEach((trade, i) => {
        setTimeout(() => this.prependTradeRow(trade), 500 * (i + 1));
      });
    }
  }

  const FEED_EL = document.getElementById('trade-feed');
  if (!FEED_EL) return; // guard: script may load on pages without the feed
  const STATUS_EL = document.getElementById('sse-status');

  const feed = new TradeFeed(FEED_EL, STATUS_EL);

  // Clean up when the user navigates away.
  window.addEventListener('beforeunload', () => feed.close());

  feed.runDemo([
    { tradeRef: 'EQU-20260603-0001', symbol: 'SAP.DE',  qty: 1000,    price: 125.50, status: 'MATCHED' },
    { tradeRef: 'FX-20260603-0001',  symbol: 'EUR/USD', qty: 1000000, price: 1.0852, status: 'PENDING' },
    { tradeRef: 'EQU-20260603-0002', symbol: 'AAPL',    qty: 500,     price: 178.20, status: 'BREAK'   },
  ]);

  feed.connect();
})();