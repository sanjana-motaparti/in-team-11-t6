-- ============================================
-- TICKET-ADV010: VWAP per instrument per day
-- ============================================

WITH vwap_cte AS (
  SELECT 
    trade_id,
    instrument_id,
    trade_date,
    quantity,
    price,
    notional,
    SUM(price * quantity) OVER (
      PARTITION BY instrument_id, trade_date
    ) / NULLIF(SUM(quantity) OVER (
      PARTITION BY instrument_id, trade_date
    ), 0) AS vwap
  FROM trades
  WHERE trade_date = CURRENT_DATE - INTERVAL '1 day'
)
SELECT 
  trade_id,
  instrument_id,
  trade_date,
  quantity,
  price,
  notional,
  vwap
FROM vwap_cte
ORDER BY instrument_id, trade_id;

-- ============================================
-- TICKET-ADV011: Trade lifecycle rollup (Recursive CTE)
-- ============================================

WITH RECURSIVE trade_lifecycle AS (
  SELECT 
    trade_id,
    1 AS step,
    'EXECUTED' AS stage_name,
    executed_at AS event_at,
    status AS event_status
  FROM trades
  
  UNION ALL
  
  SELECT 
    tl.trade_id,
    tl.step + 1,
    CASE tl.step
      WHEN 1 THEN 'CONFIRMED'
      WHEN 2 THEN 'SETTLED'
      WHEN 3 THEN 'RECONCILED'
      WHEN 4 THEN 'RESOLVED'
    END,
    CURRENT_TIMESTAMP,
    CASE tl.step
      WHEN 1 THEN 'pending'
      WHEN 2 THEN 'pending'
      WHEN 3 THEN 'matched'
      WHEN 4 THEN 'closed'
    END
  FROM trade_lifecycle tl
  WHERE tl.step < 5
)
SELECT 
  trade_id,
  step,
  stage_name,
  event_at,
  event_status
FROM trade_lifecycle
ORDER BY trade_id, step
LIMIT 100;