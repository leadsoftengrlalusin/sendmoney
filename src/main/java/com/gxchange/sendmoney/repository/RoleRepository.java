package com.gxchange.sendmoney.repository;

import com.gxchange.sendmoney.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
