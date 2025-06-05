package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OwnerDAO {
    private final Connection connection;

    public OwnerDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertOwner(Owner owner) throws SQLException {
        String sql = "INSERT INTO Owner (UserID, BuildingNumber, DoorNumber) VALUES (NULL, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, owner.getBuildingNumber());
            statement.setInt(2, owner.getDoorNumber());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // 返回自增的 OwnerID
                } else {
                    throw new SQLException("Creating owner failed, no ID obtained.");
                }
            }
        }
    }
    public Owner getOwnerByOwnerId(int ownerID) throws SQLException {
        String sql = "SELECT * FROM Owner WHERE OwnerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ownerID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Owner(
                        resultSet.getInt("OwnerID"),
                        resultSet.getString("UserID"),
                        resultSet.getInt("BuildingNumber"),
                        resultSet.getInt("DoorNumber")
                );
            }
            return null;
        }
    }

    public Owner getOwnerByUserID(String userID) throws SQLException {
        String sql = "SELECT * FROM Owner WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Owner(
                        resultSet.getInt("OwnerID"),
                        resultSet.getString("UserID"),
                        resultSet.getInt("BuildingNumber"),
                        resultSet.getInt("DoorNumber")
                );
            }
            return null;
        }
    }
    public void addOwner(Owner owner) throws SQLException {
        String sql = "INSERT INTO Owner (OwnerID, UserID, BuildingNumber, DoorNumber) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, owner.getOwnerID());
            statement.setString(2, owner.getUserID());
            statement.setInt(3, owner.getBuildingNumber());
            statement.setInt(4, owner.getDoorNumber());
            statement.executeUpdate();
        }
    }

    public List<Owner> getAllOwners() throws SQLException {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM Owner";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                owners.add(new Owner(
                        resultSet.getInt("OwnerID"),
                        resultSet.getString("UserID"),
                        resultSet.getInt("BuildingNumber"),
                        resultSet.getInt("DoorNumber")
                ));
            }
        }
        return owners;
    }

    public void updateOwner(Owner owner) throws SQLException {
        String sql = "UPDATE Owner SET UserID = ?, BuildingNumber = ?, DoorNumber = ? WHERE OwnerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, owner.getUserID());
            statement.setInt(2, owner.getBuildingNumber());
            statement.setInt(3, owner.getDoorNumber());
            statement.setInt(4, owner.getOwnerID());
            statement.executeUpdate();
        }
    }

    public void deleteOwner(int ownerID) throws SQLException {
        String sql = "DELETE FROM Owner WHERE OwnerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ownerID);
            statement.executeUpdate();
        }
    }
    public static void main(String[] args) {

        Connection connection = null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 Owner 实例
            OwnerDAO ownerDAO = new OwnerDAO();

            // 获取所有用户
            List<Owner> owners = ownerDAO.getAllOwners();

            // 输出所有用户信息
            for (Owner owner : owners) {
                System.out.println(owner);
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