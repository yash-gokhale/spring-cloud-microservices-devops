package org.example.orderservice.controller;


import org.example.orderservice.Repository.UserRepository;
import org.example.orderservice.model.User;
import org.example.orderservice.security.JwtUtil;
import org.example.orderservice.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";

    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User login){

        UserDetails user = userDetailsService.loadUserByUsername(login.getUsername());

        if(!encoder.matches(login.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

}
