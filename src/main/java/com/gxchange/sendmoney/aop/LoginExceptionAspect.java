package com.gxchange.sendmoney.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginExceptionAspect {

    @Around("execution(* com.gxchange.sendmoney.controller.AuthController.login(..))")
    public Object handleLoginExceptions(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (RuntimeException ex) {
            System.out.printf("⚠️ [EXCEPTION WRAPPED] %s%n", ex.getMessage());
            return ResponseEntity.badRequest().body(String.format("Login failed: %s", ex.getMessage()));
        }
    }
}
