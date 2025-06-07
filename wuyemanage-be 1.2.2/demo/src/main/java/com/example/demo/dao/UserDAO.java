package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.User;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO User (UserID, Username, HashPassword, Email, Role, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getHashPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getPhoneNumber());
            statement.executeUpdate();
        }
    }
    public User getUserById(String userID) throws SQLException {
        String sql = "SELECT * FROM User WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("UserID"),
                        resultSet.getString("Username"),
                        resultSet.getString("HashPassword"),
                        resultSet.getString("Email"),
                        resultSet.getString("Role"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                );
            }
            return null;
        }
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO User (UserID, Username, HashPassword, Email, Role, PhoneNumber, CreatedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getHashPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getPhoneNumber());
            statement.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
            statement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("UserID"),
                        resultSet.getString("Username"),
                        resultSet.getString("HashPassword"),
                        resultSet.getString("Email"),
                        resultSet.getString("Role"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getTimestamp("CreatedAt").toLocalDateTime()
                ));
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET Username = ?, HashPassword = ?, Email = ?, Role = ?, PhoneNumber = ? WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getUserID());
            statement.executeUpdate();
        }
    }

    public void deleteUser(String userID) throws SQLException {
        String sql = "DELETE FROM User WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            statement.executeUpdate();
        }
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 UserDAO 实例
            UserDAO userDAO = new UserDAO();
            // 获取所有用户
            List<User> users = userDAO.getAllUsers();

            // 输出所有用户信息
            for (User user : users) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        } finally {
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
}
