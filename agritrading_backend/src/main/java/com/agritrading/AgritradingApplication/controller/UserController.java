package com.agritrading.AgritradingApplication.controller;


import com.agritrading.AgritradingApplication.dto.LoginDTO;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.RegisterRequest;
import com.agritrading.AgritradingApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/register")
    public  Users register(@RequestBody RegisterRequest user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public LoginDTO login(@RequestBody Users user) {
        Users user1 = userRepo.findByUsername(user.getUsername());
        String token =  userService.verify(user);
        return LoginDTO.builder()
                .token(token)
                .role(user1.getRole())
                .username(user.getUsername())
                .build();

    }
}
