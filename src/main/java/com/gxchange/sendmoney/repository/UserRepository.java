package com.gxchange.sendmoney.repository;

import com.gxchange.sendmoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
