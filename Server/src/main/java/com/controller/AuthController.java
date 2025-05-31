package com.controller;

import com.domain.vo.LoginRequest;
import com.domain.vo.LoginResponse;
import com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest credentials) throws SQLException {
        return authService.authenticate(credentials.getUserID(), credentials.getPassword());
    }
}