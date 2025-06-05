package com.example.demo.service;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.dao.OwnerDAO;
import com.example.demo.dao.PropertyDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.vo.*;
import com.example.demo.config.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final PropertyDAO propertyDAO;
    private final OwnerDAO ownerDAO;

    @Autowired
    public AuthServiceImpl(UserDAO userDAO,
                           PropertyDAO propertyDAO,
                           OwnerDAO ownerDAO) {
        this.userDAO = userDAO;
        this.propertyDAO = propertyDAO;
        this.ownerDAO = ownerDAO;
    }
    @Override
    public OwnerSignResponse authorizeOwner(OwnerSignRequest ownerSignRequest)throws SQLException
    {
        Owner owner=new Owner(ownerSignRequest.getBuildingNumber(),ownerSignRequest.getDoorNumber());
        int ownerID=ownerDAO.insertOwner(owner);
        String userID="OWN"+ownerID;
        User user=new User(userID,ownerSignRequest.getUsername(),PasswordUtil.hashPassword(ownerSignRequest.getPassWord()),ownerSignRequest.getEmail(),ownerSignRequest.getPhoneNumber());
        owner.setUser(user);
        owner.setOwnerID(ownerID);
        owner.setUserID(userID);
        userDAO.insertUser(user);
        ownerDAO.updateOwner(owner);
        return new OwnerSignResponse(true,"OwnerSign",userID);
    }

    @Override
    public PropertySignResponse authorizeProperty(PropertySignRequest propertySignRequest)throws SQLException
    {

        int propertyID=propertyDAO.insertProperty();
        String userID="PRO"+propertyID;
        Property property=new Property(propertyID,userID);
        User user=new User(userID,propertySignRequest.getUsername(),PasswordUtil.hashPassword(propertySignRequest.getPassWord()),propertySignRequest.getEmail(),propertySignRequest.getPhoneNumber());
        property.setUser(user);
        userDAO.insertUser(user);
        propertyDAO.updateProperty(property);
        return new PropertySignResponse(true,"PropertySign",userID);
    }
    @Override
    public LoginResponse authenticate(String userid, String password) throws SQLException {
        // 1. 查找用户
        User user = userDAO.getUserById(userid);
        if (user == null) {
            return new LoginResponse(false, "User not found");
        }
        // 2. 验证密码
        if (!PasswordUtil.verifyPassword(password, user.getHashPassword())) {
            return new LoginResponse(false, "Invalid credentials");
        }
        // 3. 根据角色获取详细信息
        switch (user.getRole().toUpperCase()) {
            case "PROPERTY":
                Property property = propertyDAO.getPropertyByUserID(user.getUserID());
                property.setUser(user);
                return new LoginResponse(true, "PROPERTY", property);

            case "OWNER":
                Owner owner = ownerDAO.getOwnerByUserID(user.getUserID());
                owner.setUser(user);
                return new LoginResponse(true, "OWNER", owner);

            case "ADMIN":
                // 管理员不需要额外信息
                return new LoginResponse(true, "ADMIN", user);

            default:
                return new LoginResponse(false, "Unknown user role");
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        UserDAO userDAO =null;
        PropertyDAO propertyDAO=null;
        OwnerDAO ownerDAO=null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 DAO 实例
            userDAO = new UserDAO();
            propertyDAO=new PropertyDAO();
            ownerDAO=new OwnerDAO();


        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        AuthServiceImpl authServiceImpl=new AuthServiceImpl(userDAO,propertyDAO,ownerDAO);
        System.out.println(authServiceImpl.authenticate("0","123456"));
        System.out.println(authServiceImpl.authenticate("OWN1","123456"));
        System.out.println(authServiceImpl.authenticate("PRO1","123456"));
        System.out.println(authServiceImpl.authenticate("0","23456"));
        System.out.println(authServiceImpl.authenticate("1","123456"));

        // 关闭连接
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("关闭连接失败: " + e.getMessage());
            }
        }
        // 关闭连接池
        DatabaseConnectionPool.closePool();
    }
}