package com.wiken.example1.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/signup")
    public String signup(Model model) {
        return "user/signup";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "user/loginForm";
    }
}
