package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.SendMoneyRequest;
import com.gxchange.sendmoney.model.*;
import com.gxchange.sendmoney.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private BalanceRepository balanceRepo;

    @PostMapping("/send")
    public ResponseEntity<?> sendMoney(@RequestBody SendMoneyRequest request) {
        if (request.getSender().equals(request.getReceiver())) {
            return ResponseEntity.badRequest().body("Sender and receiver cannot be the same.");
        }

        User sender = userRepo.findByPhoneNumber(request.getSender());
        User receiver = userRepo.findByPhoneNumber(request.getReceiver());

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Sender or receiver not found.");
        }

        Balance senderBalance = balanceRepo.findByUser(sender);
        Balance receiverBalance = balanceRepo.findByUser(receiver);

        BigDecimal amount = request.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        if (senderBalance.getAmount().compareTo(amount) < 0) {
            return ResponseEntity.badRequest().body("Insufficient balance.");
        }

        // Deduct from sender
        senderBalance.setAmount(senderBalance.getAmount().subtract(amount));

        // Add to receiver
        receiverBalance.setAmount(receiverBalance.getAmount().add(amount));

        // Save balances
        balanceRepo.save(senderBalance);
        balanceRepo.save(receiverBalance);

        // Record transaction
        Transaction tx = new Transaction();
        tx.setSender(sender);
        tx.setReceiver(receiver);
        tx.setAmount(amount);
        tx.setTransactionDate(LocalDateTime.now());

        transactionRepo.save(tx);

        return ResponseEntity.ok("Transaction successful.");
    }
}
