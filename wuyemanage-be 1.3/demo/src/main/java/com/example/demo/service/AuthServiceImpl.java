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
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    @Override
    public OwnerSignResponse authorizeOwner(OwnerSignRequest ownerSignRequest) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getConnection();
            connection.setAutoCommit(false); // 开始事务

            // 创建 Owner 对象
            Owner owner = new Owner(ownerSignRequest.getBuildingNumber(),
                    ownerSignRequest.getDoorNumber());

            // 插入 Owner 记录
            int ownerID = ownerDAO.insertOwner(owner);
            String userID = "OWN" + ownerID;

            // 创建 User 对象
            User user = new User(
                    userID,
                    ownerSignRequest.getUsername(),
                    PasswordUtil.hashPassword(ownerSignRequest.getPassword()),
                    ownerSignRequest.getEmail(),
                    "OWNER",
                    ownerSignRequest.getPhoneNumber()
            );

            // 更新 Owner 信息
            owner.setUser(user);
            owner.setOwnerID(ownerID);
            owner.setUserID(userID);

            // 插入 User 记录
            userDAO.insertUser(user);

            // 更新 Owner 记录
            ownerDAO.updateOwner(owner);

            connection.commit(); // 提交事务
            return new OwnerSignResponse(true, "OwnerSign successful", userID);

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // 回滚事务
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new OwnerSignResponse(false, "Database error: " + e.getMessage());

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback(); // 回滚事务
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new OwnerSignResponse(false, "Error: " + e.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // 恢复自动提交
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PropertySignResponse authorizeProperty(PropertySignRequest propertySignRequest) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getConnection();
            connection.setAutoCommit(false); // 开始事务

            // 插入Property记录并获取ID
            int propertyID = propertyDAO.insertProperty();
            String userID = "PRO" + propertyID;

            // 创建Property和User对象
            Property property = new Property(propertyID, userID);
            User user = new User(
                    userID,
                    propertySignRequest.getUsername(),
                    PasswordUtil.hashPassword(propertySignRequest.getPassword()),
                    propertySignRequest.getEmail(),
                    "PROPERTY",
                    propertySignRequest.getPhoneNumber()
            );

            // 设置关联关系
            property.setUser(user);

            // 插入User记录
            userDAO.insertUser(user);

            // 更新Property记录
            propertyDAO.updateProperty(property);

            connection.commit(); // 提交事务
            return new PropertySignResponse(true, "Property authorization successful",userID);

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // 回滚事务
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new PropertySignResponse(false, "Database error: " + e.getMessage());

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback(); // 回滚事务
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new PropertySignResponse(false, "Error: " + e.getMessage(), null);

        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // 恢复自动提交
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    @Override
    public boolean amendUser(UserUpdateRequest userUpdateRequest) {
        try {
            // 1. 参数基础校验
            if (userUpdateRequest == null || userUpdateRequest.getUserID() == null) {
                return false;
            }

            // 2. 获取用户对象
            User user = userDAO.getUserById(userUpdateRequest.getUserID());
            if (user == null) {
                return false;
            }

            // 3. 选择性更新字段（仅更新非空字段）
            if (!isEmpty(userUpdateRequest.getUsername())) {
                user.setUsername(userUpdateRequest.getUsername());
            }

            if (!isEmpty(userUpdateRequest.getPassword())) {
                user.setHashPassword(PasswordUtil.hashPassword(userUpdateRequest.getPassword()));
            }

            if (!isEmpty(userUpdateRequest.getEmail())) {
                user.setEmail(userUpdateRequest.getEmail());
            }

            if (!isEmpty(userUpdateRequest.getPhone())) {
                // 可选：添加手机号格式校验
                user.setPhoneNumber(userUpdateRequest.getPhone());
            }

            // 4. 执行更新
            userDAO.updateUser(user);
            return true;

        } catch (SQLException e) {
            // 记录错误日志
            System.err.println("用户更新失败: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // 处理其他异常（如密码加密失败）
            System.err.println("系统错误: " + e.getMessage());
            return false;
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
        UserUpdateRequest userUpdateRequest=new UserUpdateRequest("OWN2","12346768",null,null,null);
        authServiceImpl.amendUser(userUpdateRequest);
        System.out.println(authServiceImpl.authenticate("0","123456"));
        System.out.println(authServiceImpl.authenticate("OWN1","123456"));
        System.out.println(authServiceImpl.authenticate("PRO1","123456"));
        System.out.println(authServiceImpl.authenticate("OWN2","123456"));
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