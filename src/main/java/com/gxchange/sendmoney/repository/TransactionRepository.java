package com.gxchange.sendmoney.repository;

import com.gxchange.sendmoney.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
