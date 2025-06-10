package com.dailyword.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Facade, Usecase 패키지 적용
    @Pointcut("execution(* com.dailyword.*.facade..*(..)) || execution(* com.dailyword.*.usecase..*(..))")
    public void applicationPackagePointcut() {}

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        long start = System.currentTimeMillis();
        log.info("[START] {}.{} args={}", className, methodName, Arrays.toString(args));

        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("[END] {}.{} result={} ({}ms)", className, methodName, result, duration);
        }
    }
}
