package com.gxchange.sendmoney.aop;

import com.gxchange.sendmoney.dto.AuthRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginNotificationAspect {

    private static final ThreadLocal<String> lastUser = new ThreadLocal<>();

    @Pointcut("execution(* com.gxchange.sendmoney.controller.AuthController.login(..))")
    public void loginPointcut() {}

    @Before("loginPointcut()")
    public void captureUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof AuthRequest auth) {
            lastUser.set(auth.getPhoneNumber());
        }
    }

    @AfterReturning("loginPointcut()")
    public void notifyOnLogin() {
        System.out.printf("📩 [NOTIFY] Welcome back, user: %s%n", lastUser.get());
        lastUser.remove();
    }
}
