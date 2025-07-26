package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verify")
public class PublicVerifyController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{phone}")
    public String verifyUserByPhone(@PathVariable String phone, Model model) {
        User user = userRepository.findByPhoneNumber(phone);
        if (user == null) {
            model.addAttribute("maskedName", "User not found.");
        } else {
            model.addAttribute("maskedName", maskName(user.getName()));
        }
        return "verify";
    }

    private String maskName(String fullName) {
        String[] parts = fullName.split(" ");
        StringBuilder masked = new StringBuilder();
        for (String part : parts) {
            if (!masked.isEmpty()) masked.append(" ");
            masked.append(maskWord(part));
        }
        return masked.toString();
    }

    private String maskWord(String word) {
        if (word.length() <= 1) return "*";
        StringBuilder sb = new StringBuilder();
        sb.append(word.charAt(0));
        for (int i = 1; i < word.length() - 1; i++) {
            sb.append("*");
        }
        sb.append(word.charAt(word.length() - 1));
        return sb.toString();
    }
}
