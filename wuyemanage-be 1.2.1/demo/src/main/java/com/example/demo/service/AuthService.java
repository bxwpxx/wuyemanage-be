package com.example.demo.service;

import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.*;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface AuthService {
    public OwnerSignResponse authorizeOwner(OwnerSignRequest ownerSignRequest)throws SQLException;

    PropertySignResponse authorizeProperty(PropertySignRequest propertySignRequest)throws SQLException;

    LoginResponse authenticate(String username, String password) throws SQLException;
}
