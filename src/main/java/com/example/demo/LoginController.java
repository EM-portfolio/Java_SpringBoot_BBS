package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class LoginController {

    @Autowired
    UsersRepository repos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    /* 初期データ作成 ユーザ:demo パスワード:demo */
    @PostConstruct
    public void init() {
        Users user1 = new Users();
        user1.setUsername("demo");
        user1.setPassword(passwordEncoder.encode("demo"));
        repos.saveAndFlush(user1); 
    }
}
