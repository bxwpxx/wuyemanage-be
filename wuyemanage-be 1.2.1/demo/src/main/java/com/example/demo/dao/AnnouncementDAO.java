package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.Announcement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnnouncementDAO {
    private final Connection connection;

    public AnnouncementDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 插入公告
    public int insertAnnouncement(Announcement announcement) throws SQLException {
        String sql = "INSERT INTO announcement (title, content, uploader_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, announcement.getTitle());
            statement.setString(2, announcement.getContent());
            statement.setInt(3, announcement.getUploaderId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("创建公告失败，没有行受影响");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // 返回自增ID
                } else {
                    throw new SQLException("创建公告失败，未获取到ID");
                }
            }
        }
    }

    // 根据ID获取公告
    public Announcement getAnnouncementById(int announcementId) throws SQLException {
        String sql = "SELECT * FROM announcement WHERE announcement_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, announcementId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Announcement(
                        resultSet.getInt("announcement_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getInt("uploader_id"),
                        resultSet.getTimestamp("upload_time").toLocalDateTime()
                );
            }
            return null;
        }
    }

    // 获取所有公告（按时间倒序）
    public List<Announcement> getAllAnnouncements() throws SQLException {
        List<Announcement> announcements = new ArrayList<>();
        String sql = "SELECT * FROM announcement ORDER BY upload_time DESC";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                announcements.add(new Announcement(
                        resultSet.getInt("announcement_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getInt("uploader_id"),
                        resultSet.getTimestamp("upload_time").toLocalDateTime()
                ));
            }
        }
        return announcements;
    }

    // 更新公告
    public void updateAnnouncement(Announcement announcement) throws SQLException {
        String sql = "UPDATE announcement SET title = ?, content = ? WHERE announcement_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, announcement.getTitle());
            statement.setString(2, announcement.getContent());
            statement.setInt(3, announcement.getAnnouncementId());
            statement.executeUpdate();
        }
    }

    // 删除公告
    public void deleteAnnouncement(int announcementId) throws SQLException {
        String sql = "DELETE FROM announcement WHERE announcement_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, announcementId);
            statement.executeUpdate();
        }
    }

    // 测试方法
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getConnection();
            AnnouncementDAO announcementDAO = new AnnouncementDAO();
            // 测试获取所有公告
            List<Announcement> announcements = announcementDAO.getAllAnnouncements();
            for (Announcement announcement : announcements) {
                System.out.println(announcement);
            }

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("关闭连接失败: " + e.getMessage());
                }
            }
            DatabaseConnectionPool.closePool();
        }
    }
}