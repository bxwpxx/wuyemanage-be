package com.service;

import com.domain.vo.LoginResponse;

import java.sql.SQLException;

public interface AuthService {
    LoginResponse authenticate(String username, String password) throws SQLException;
}
