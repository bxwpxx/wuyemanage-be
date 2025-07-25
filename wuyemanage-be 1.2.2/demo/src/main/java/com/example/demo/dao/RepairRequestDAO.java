package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.RepairRequest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class RepairRequestDAO {
    private final Connection connection;

    public RepairRequestDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database connection", e);
        }
    }

    // 创建报修记录
    public int create(RepairRequest request) throws SQLException {
        String sql = "INSERT INTO repair_requests (description, image_path, location_type, specific_location, " +
                "creator_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, request.getDescription());
            stmt.setString(2, request.getImagePath());
            stmt.setString(3, request.getLocationType().toString());
            stmt.setString(4, request.getSpecificLocation());
            stmt.setInt(5, request.getCreatorId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建报修记录失败，没有行被影响");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建报修记录失败，无法获取ID");
                }
            }
        }
    }

    // 根据ID查询报修记录
    public RepairRequest findById(int id) throws SQLException {
        String sql = "SELECT * FROM repair_requests WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRepairRequest(rs);
                }
                return null;
            }
        }
    }

    // 更新报修记录
    public boolean update(RepairRequest request) throws SQLException {
        String sql = "UPDATE repair_requests SET description = ?, image_path = ?, location_type = ?, " +
                "specific_location = ?, status = ?, handler_id = ?, handled_at = ?, " +
                "rating = ?, rated_at = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, request.getDescription());
            stmt.setString(2, request.getImagePath());
            stmt.setString(3, request.getLocationType().toString());
            stmt.setString(4, request.getSpecificLocation());
            stmt.setString(5, request.getStatus().toString());
            setNullableInt(stmt, 6, request.getHandlerId());
            setNullableTimestamp(stmt, 7, request.getHandledAt());
            setNullableInt(stmt, 8, request.getRating());
            setNullableTimestamp(stmt, 9, request.getRatedAt());
            stmt.setInt(10, request.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // 删除报修记录
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM repair_requests WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 查询所有报修记录
    public List<RepairRequest> getAllRepairRequests() throws SQLException {
        String sql = "SELECT * FROM repair_requests";
        List<RepairRequest> requests = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                requests.add(mapResultSetToRepairRequest(rs));
            }
        }
        return requests;
    }

    // 根据状态查询报修记录
    public List<RepairRequest> findByStatus(RepairRequest.Status status) throws SQLException {
        String sql = "SELECT * FROM repair_requests WHERE status = ?";
        List<RepairRequest> requests = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRepairRequest(rs));
                }
            }
        }
        return requests;
    }

    // 根据创建人ID查询报修记录
    public List<RepairRequest> findByCreatorId(int creatorId) throws SQLException {
        String sql = "SELECT * FROM repair_requests WHERE creator_id = ?";
        List<RepairRequest> requests = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, creatorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRepairRequest(rs));
                }
            }
        }
        return requests;
    }

    // 根据处理人ID查询报修记录
    public List<RepairRequest> findByHandlerId(int handlerId) throws SQLException {
        String sql = "SELECT * FROM repair_requests WHERE handler_id = ?";
        List<RepairRequest> requests = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, handlerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToRepairRequest(rs));
                }
            }
        }
        return requests;
    }

    // 将ResultSet映射为RepairRequest对象
    private RepairRequest mapResultSetToRepairRequest(ResultSet rs) throws SQLException {
        RepairRequest request = new RepairRequest();
        request.setId(rs.getInt("id"));
        request.setDescription(rs.getString("description"));
        request.setImagePath(rs.getString("image_path"));
        request.setLocationType(RepairRequest.LocationType.fromValue(rs.getString("location_type")));
        request.setSpecificLocation(rs.getString("specific_location"));
        request.setCreatorId(rs.getInt("creator_id"));
        request.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        request.setStatus(RepairRequest.Status.fromValue(rs.getString("status")));

        int handlerId = rs.getInt("handler_id");
        if (!rs.wasNull()) {
            request.setHandlerId(handlerId);
        }

        Timestamp handledAt = rs.getTimestamp("handled_at");
        if (handledAt != null) {
            request.setHandledAt(handledAt.toLocalDateTime());
        }

        int rating = rs.getInt("rating");
        if (!rs.wasNull()) {
            request.setRating(rating);
        }

        Timestamp ratedAt = rs.getTimestamp("rated_at");
        if (ratedAt != null) {
            request.setRatedAt(ratedAt.toLocalDateTime());
        }

        return request;
    }

    // 辅助方法：设置可为null的整数参数
    private void setNullableInt(PreparedStatement stmt, int index, Integer value) throws SQLException {
        if (value != null) {
            stmt.setInt(index, value);
        } else {
            stmt.setNull(index, Types.INTEGER);
        }
    }

    // 辅助方法：设置可为null的时间戳参数
    private void setNullableTimestamp(PreparedStatement stmt, int index, LocalDateTime value) throws SQLException {
        if (value != null) {
            stmt.setTimestamp(index, Timestamp.valueOf(value));
        } else {
            stmt.setNull(index, Types.TIMESTAMP);
        }
    }

    // 关闭连接
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getConnection();
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            // 测试获取所有公告
            List<RepairRequest> repairRequests = repairRequestDAO.findByStatus(RepairRequest.Status.PENDING);
            for (RepairRequest repairRequest : repairRequests) {
                System.out.println(repairRequest);
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