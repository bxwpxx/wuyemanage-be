package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PropertyDAO {
    private final Connection connection;

    public PropertyDAO()
    {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertProperty() throws SQLException {
        String sql = "INSERT INTO Property (UserID) VALUES (NULL)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                throw new SQLException("Creating property failed, no ID obtained.");
            }
        }
    }
    public Property getPropertyByPropertyID(int propertyID) throws SQLException {
        String sql = "SELECT * FROM Property WHERE PropertyID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, propertyID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Property(
                        resultSet.getInt("PropertyID"),
                        resultSet.getString("UserID")
                );
            }
            return null;
        }
    }
    public Property getPropertyByUserID(String userID) throws SQLException {
        String sql = "SELECT * FROM Property WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Property(
                        resultSet.getInt("PropertyID"),
                        resultSet.getString("UserID")
                );
            }
            return null;
        }
    }
    public void addProperty(Property property) throws SQLException {
        String sql = "INSERT INTO Property (PropertyID, UserID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, property.getPropertyID());
            statement.setString(2, property.getUserID());
            statement.executeUpdate();
        }
    }

    public List<Property> getAllProperties() throws SQLException {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM Property";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                properties.add(new Property(
                        resultSet.getInt("PropertyID"),
                        resultSet.getString("UserID")
                ));
            }
        }
        return properties;
    }

    public void updateProperty(Property property) throws SQLException {
        String sql = "UPDATE Property SET UserID = ? WHERE PropertyID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, property.getUserID());
            statement.setInt(2, property.getPropertyID());
            statement.executeUpdate();
        }
    }

    public void deleteProperty(int propertyID) throws SQLException {
        String sql = "DELETE FROM Property WHERE PropertyID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, propertyID);
            statement.executeUpdate();
        }
    }
    public static void main(String[] args) {


        Connection connection = null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 Property 实例
            PropertyDAO propertyDAO = new PropertyDAO();


            // 获取所有用户
            List<Property> propertys = propertyDAO.getAllProperties();

            // 输出所有用户信息
            for (Property property : propertys) {
                System.out.println(property);
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
