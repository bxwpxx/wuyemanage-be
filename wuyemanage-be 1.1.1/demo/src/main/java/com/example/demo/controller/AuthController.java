package com.example.demo.controller;

import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.LoginRequest;
import com.example.demo.domain.vo.LoginResponse;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController(value = "as")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/PropertySign")
    public String PropertySign(@RequestBody Property credentials) throws SQLException {
        return authService.authorizeProperty(credentials);
    }
    @PostMapping("/OwnerSign")
    public String OwnerSign(@RequestBody Owner credentials) throws SQLException {
        return authService.authorizeOwner(credentials);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest credentials) throws SQLException {
        return authService.authenticate(credentials.getUserID(), credentials.getPassword());
    }

}