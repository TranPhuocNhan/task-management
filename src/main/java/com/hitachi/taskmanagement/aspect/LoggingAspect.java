package com.hitachi.taskmanagement.aspect;


import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.hitachi.taskmanagement.service.impl.*.*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer() && @annotation(loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable{
        Long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String username = getCurrentUsername();

        log.info("=== Start: {}, {} - User: {}", className, methodName, username);
        log.info("=== Parameters: {}", Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            Long executionTime = System.currentTimeMillis() - startTime;

            log.info("=== End: {}, {} - User: {} - Execution Time: {}ms", className, methodName, username, executionTime);
            log.info("=== Result: {}", result);

            return result;
        } catch (Exception e) {
            log.error("=== Exception in {}, {} - User: {} - Error: {}", className, methodName, username, e.getMessage());

            throw e;
        }
    }

    @AfterThrowing(pointcut = "@annotation(loggable) && serviceLayer()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Loggable loggable, Exception e) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String username = getCurrentUsername();

        log.error("=== Exception in {}, {} - User: {} - Error: {}", className, methodName, username, e.getMessage());
        log.error("=== StackTrace: {}", Arrays.toString(e.getStackTrace()));
    }

    private String getCurrentUsername() { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "anonymous";
    }
}
