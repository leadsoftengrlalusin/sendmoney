package com.gxchange.sendmoney.repository;

import com.gxchange.sendmoney.model.Balance;
import com.gxchange.sendmoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findByUser(User user);
}
