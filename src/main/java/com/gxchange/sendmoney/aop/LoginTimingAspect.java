package com.gxchange.sendmoney.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginTimingAspect {

    @Around("execution(* com.gxchange.sendmoney.controller.AuthController.login(..))")
    public Object timeLoginExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        System.out.printf("⏱️ [LOGIN DURATION] Took %d ms%n", end - start);
        return result;
    }
}
