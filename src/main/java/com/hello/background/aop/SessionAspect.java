package com.hello.background.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wuketao
 * @date 2024/6/4
 * @Description
 */
@Component
@Aspect
public class SessionAspect {
    @Pointcut("execution(* com.hello.background.controller.*.*(..))")
    public void sessionPointCut() {
    }

    /**
     * 环绕切片
     *
     * @param joinPoint
     * @return
     */
    @Around("sessionPointCut()")
    public Object aroundLogPointCut(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String classMethodName = className + "." + methodName;
        boolean loginFlag = false;
        List<String> excludeURLList = Arrays.asList("SecurityController.login", "SecurityController.checkLogin"
                , "SecurityController.checkVersion", "UserController.findSelf", "CommentController.downloadInterviews");
        if (!excludeURLList.contains(classMethodName)) {
            // 检查是否有登陆
            loginFlag = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .map(x -> x.getRequest().getSession(false))
                    .map(y -> y.getAttribute("user"))
                    .map(z -> null != z).orElse(false);
        } else {
            // 不需要检查的方法，直接赋值true
            loginFlag = true;
        }
        Object proceed = null;
        if (loginFlag) {
            try {
                proceed = joinPoint.proceed();
            } catch (Throwable ex) {
            }
        } else {

            proceed = "未登录";
        }
        return proceed;
    }
}
