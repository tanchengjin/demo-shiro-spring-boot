package com.tanchengjin.demo.shiro.controller;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @GetMapping("/register")
    public String registerView() {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(String username, String password) {
        return "/auth/register";
    }

    @GetMapping("/login")
    public String loginView() {
        return "/auth/login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        usernamePasswordToken.setUsername(username);
        usernamePasswordToken.setPassword(password.toCharArray());
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return "redirect:/home/index";
    }
}
