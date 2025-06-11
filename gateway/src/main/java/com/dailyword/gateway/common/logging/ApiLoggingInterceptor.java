package com.dailyword.gateway.common.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String MDC_KEY = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_KEY, traceId);
        request.setAttribute(TRACE_ID_HEADER, traceId); // 이후 RestTemplate 호출 시 사용 가능

        log.info("[API_ACCESS_LOG] method={} path={} traceId={}", request.getMethod(), request.getRequestURI(), traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("[API_RESPONSE] path={} status={} traceId={}", request.getRequestURI(), response.getStatus(), MDC.get(MDC_KEY));
        MDC.clear();
    }
}