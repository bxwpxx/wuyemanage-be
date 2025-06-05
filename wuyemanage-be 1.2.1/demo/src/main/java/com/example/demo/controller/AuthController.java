package com.example.demo.controller;

import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.*;
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
    public PropertySignResponse PropertySign(@RequestBody PropertySignRequest credentials) throws SQLException {
        return authService.authorizeProperty(credentials);
    }
    @PostMapping("/OwnerSign")
    public OwnerSignResponse OwnerSign(@RequestBody OwnerSignRequest credentials) throws SQLException {
        return authService.authorizeOwner(credentials);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest credentials) throws SQLException {
        return authService.authenticate(credentials.getUserID(), credentials.getPassword());
    }

}