package com.example.demo.service;

import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.LoginResponse;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface AuthService {
    String authorizeOwner(Owner owner)throws SQLException;

    String authorizeProperty(Property property)throws SQLException;

    LoginResponse authenticate(String username, String password) throws SQLException;
}
