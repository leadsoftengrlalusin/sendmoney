package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.AuthRequest;
import com.gxchange.sendmoney.dto.AuthResponse;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.repository.UserRepository;
import com.gxchange.sendmoney.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        User user = userRepo.findByPhoneNumber(authRequest.getPhoneNumber());
        if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getPhoneNumber());
            return new AuthResponse(token);
        } else {
            throw new RuntimeException("Invalid phone number or password");
        }
    }
}
