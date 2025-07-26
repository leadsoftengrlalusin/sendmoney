package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.model.Balance;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<?> getBalance(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Balance balance = user.getBalance();
        if (balance == null) {
            return ResponseEntity.ok("Balance not initialized");
        }

        return ResponseEntity.ok("Current Balance: " + balance.getAmount());
    }
}
