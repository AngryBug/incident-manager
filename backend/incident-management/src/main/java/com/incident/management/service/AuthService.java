package com.incident.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.incident.management.entity.UserEntity;
import com.incident.management.repository.UserEntityRepository;
import com.incident.management.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    
    //private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    public UserEntity register(UserEntity user) {
    	  	
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntityRepository.save(user);
    }

    public String login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return jwtUtil.generateToken(email);
    }
}

