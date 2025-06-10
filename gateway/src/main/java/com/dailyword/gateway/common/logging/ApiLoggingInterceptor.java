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

    private static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        request.setAttribute(TRACE_ID, traceId);
        log.info("[API_ACCESS_LOG] method={} path={} traceId={}", request.getMethod(), request.getRequestURI(), traceId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String traceId = (String) request.getAttribute(TRACE_ID);

        log.info("[API_RESPONSE] path={} status={} traceId={}", request.getRequestURI(), response.getStatus(), traceId);

        MDC.clear(); // 반드시 clear (메모리 누수 방지)
    }
}