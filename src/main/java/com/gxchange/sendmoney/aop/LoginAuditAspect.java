package com.gxchange.sendmoney.aop;

import com.gxchange.sendmoney.dto.AuthRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoginAuditAspect {

    @Pointcut("execution(* com.gxchange.sendmoney.controller.AuthController.login(..))")
    public void loginPointcut() {}

    @Before("loginPointcut()")
    public void beforeLogin(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof AuthRequest auth) {
            System.out.printf("🔵 [LOGIN ATTEMPT] %s at %s%n", auth.getPhoneNumber(), LocalDateTime.now());
        }
    }

    @AfterReturning(pointcut = "loginPointcut()", returning = "response")
    public void afterLoginSuccess(Object response) {
        System.out.printf("🟢 [LOGIN SUCCESS] Token issued at %s%n", LocalDateTime.now());
    }

    @AfterThrowing(pointcut = "loginPointcut()", throwing = "ex")
    public void afterLoginFailure(Exception ex) {
        System.out.printf("🔴 [LOGIN FAILURE] Reason: %s%n", ex.getMessage());
    }
}
