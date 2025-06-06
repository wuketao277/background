package com.hello.background.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 日志切片，在控制器方法执行前后记录日志，通过traceId串联一个请求的所有调用方法。
 * @author wuketao
 * @date 2024/6/4
 * @Description
 */
@Component
@Aspect
public class LogAspect {
    // 线程本地变量，用于记录traceId
    public static ThreadLocal<String> tlUUID = new ThreadLocal<>();

    @Pointcut("execution(* com.hello.background.controller.*.*(..))")
    public void logPointCut() {
    }

    /**
     * 环绕切记
     * @param joinPoint
     * @return
     */
    @Around("logPointCut()")
    public Object aroundLogPointCut(ProceedingJoinPoint joinPoint) {
        tlUUID.set(UUID.randomUUID().toString());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println(tlUUID.get() + " " + className + " " + methodName + "开始：" + new Date());
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable ex) {
        }
        System.out.println(tlUUID.get() + " " + className + " " + methodName + "结束：" + new Date());
        return proceed;
    }
}
