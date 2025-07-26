package com.gxchange.sendmoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {

    @GetMapping("/terms")
    public String terms(Model model) {
        model.addAttribute("para1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam at quam sed erat iaculis tincidunt.");
        model.addAttribute("para2", "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.");
        model.addAttribute("para3", "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae.");
        return "terms";
    }
}
