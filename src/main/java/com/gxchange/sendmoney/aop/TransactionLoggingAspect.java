package com.gxchange.sendmoney.aop;

import com.gxchange.sendmoney.dto.SendMoneyRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class TransactionLoggingAspect {

    @Pointcut("execution(* com.gxchange.sendmoney.controller.TransactionController.sendMoney(..))")
    public void sendMoneyPointcut() {}

    @Before("sendMoneyPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof SendMoneyRequest request) {
            String timestamp = String.format("🔵 [BEFORE] Send money request at %s", LocalDateTime.now());
            String sender = String.format("Sender: %s", request.getSender());
            String receiver = String.format("Receiver: %s", request.getReceiver());
            String amount = String.format("Amount: %s", request.getAmount());

            System.out.println(timestamp);
            System.out.println(sender);
            System.out.println(receiver);
            System.out.println(amount);
        }
    }

    @AfterReturning(pointcut = "sendMoneyPointcut()", returning = "result")
    public void logAfterReturning(Object result) {
        String success = String.format("🟢 [AFTER RETURNING] Send money success: %s", result);
        System.out.println(success);
    }

    @AfterThrowing(pointcut = "sendMoneyPointcut()", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        String error = String.format("🔴 [AFTER THROWING] Send money failed with exception: %s", ex.getMessage());
        System.out.println(error);
    }
}
