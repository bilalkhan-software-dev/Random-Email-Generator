package com.randomEmailGenerator.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectForLogging {

    @Around("execution(* com.randomEmailGenerator.controller..*(..))")
    public Object loggerForController(ProceedingJoinPoint pjp) throws Throwable {
        return getObject(pjp);
    }

    @Around("execution(* com.randomEmailGenerator.services..*(..))")
    public Object loggerForService(ProceedingJoinPoint pjp) throws Throwable {
        return getObject(pjp);
    }

    private Object getObject(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {} :: {}()", className, methodName);
        Object proceed = pjp.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("End :: {} :: {}() duration: {}ms", className, methodName, endTime);
        return proceed;
    }
}
