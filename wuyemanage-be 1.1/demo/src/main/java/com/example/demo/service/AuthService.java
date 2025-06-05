package com.example.demo.service;

import com.example.demo.domain.vo.LoginResponse;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface AuthService {
    LoginResponse authenticate(String username, String password) throws SQLException;
}
