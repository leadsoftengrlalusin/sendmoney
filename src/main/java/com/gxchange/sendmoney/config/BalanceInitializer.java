package com.gxchange.sendmoney.config;

import com.gxchange.sendmoney.model.Balance;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.repository.BalanceRepository;
import com.gxchange.sendmoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BalanceInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BalanceRepository balanceRepo;

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = userRepo.findAll();

        for (User user : users) {
            if (user.getBalance() == null) {
                Balance balance = new Balance();
                balance.setUser(user);
                balance.setAmount(BigDecimal.ZERO);
                balanceRepo.save(balance);
            }
        }
    }
}

