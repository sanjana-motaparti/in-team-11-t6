package com.dbtraining.reconx.observability;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class MdcFilter implements Filter {

  private static final String HDR_CORRELATION = "X-Correlation-Id";
  private static final String HDR_TRADE_REF = "X-Trade-Ref";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;

    try {
      // Step 4: Read X-Correlation-Id header, default to UUID if not present
      String correlationId = httpRequest.getHeader(HDR_CORRELATION);
      if (correlationId == null || correlationId.isBlank()) {
        correlationId = UUID.randomUUID().toString();
      }
      MDC.put("correlationId", correlationId);

      // Step 5: Read X-Trade-Ref header (may be null, only MDC.put if non-null)
      String tradeRef = httpRequest.getHeader(HDR_TRADE_REF);
      if (tradeRef != null && !tradeRef.isBlank()) {
        MDC.put("tradeRef", tradeRef);
      }

      // Step 6: Wrap chain.doFilter in try/finally; call MDC.clear() in the finally
      chain.doFilter(request, response);

    } finally {
      MDC.clear();
    }
  }
}
