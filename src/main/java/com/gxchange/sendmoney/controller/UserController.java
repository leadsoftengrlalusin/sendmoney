package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.UserDto;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.model.Role;
import com.gxchange.sendmoney.repository.UserRepository;
import com.gxchange.sendmoney.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public String register(@RequestBody UserDto userDto) {
        Role unverified = roleRepo.findByName("ROLE_UNVERIFIED");

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setStatus(User.Status.ACTIVE);
        user.setRoles(Collections.singleton(unverified));

        userRepo.save(user);
        return "User registered with UNVERIFIED role";
    }
}

