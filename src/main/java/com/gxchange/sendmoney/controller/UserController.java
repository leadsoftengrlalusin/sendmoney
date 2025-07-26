package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.UserDto;
import com.gxchange.sendmoney.model.Balance;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.model.Role;
import com.gxchange.sendmoney.repository.BalanceRepository;
import com.gxchange.sendmoney.repository.UserRepository;
import com.gxchange.sendmoney.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BalanceRepository balanceRepo;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (userRepo.existsByPhoneNumber(userDto.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone number already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(User.Status.ACTIVE);
        user.setRoles(Collections.singleton(roleRepo.findByName("ROLE_UNVERIFIED")));

        User savedUser = userRepo.save(user);

        // Initialize balance
        Balance balance = new Balance(savedUser);
        balanceRepo.save(balance);

        return ResponseEntity.ok("User registered successfully");
    }

}

