package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.Repair;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepairDAO {
    private final Connection connection;

    public RepairDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database connection", e);
        }
    }

    // 创建报修记录
    public int create(Repair request) throws SQLException {
        String sql = "INSERT INTO repair (description, image, location_type, specific_location, " +
                "creator_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, request.getDescription());
            stmt.setBytes(2, request.getImage()); // 修改为setBytes处理二进制数据
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
    public Repair findById(int id) throws SQLException {
        String sql = "SELECT * FROM repair WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRepair(rs);
                }
                return null;
            }
        }
    }

    // 更新报修记录
    public boolean update(Repair request) throws SQLException {
        String sql = "UPDATE repair SET description = ?, image = ?, location_type = ?, " +
                "specific_location = ?, status = ?, handler_id = ?, handled_at = ?, " +
                "rating = ?, rated_at = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, request.getDescription());
            stmt.setBytes(2, request.getImage()); // 修改为setBytes处理二进制数据
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
        String sql = "DELETE FROM repair WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 查询所有报修记录
    public List<Repair> getAllRepairs() throws SQLException {
        String sql = "SELECT * FROM repair ORDER BY created_at DESC";
        List<Repair> repairs = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                repairs.add(mapResultSetToRepair(rs));
            }
        }
        return repairs;
    }

    // 根据状态查询报修记录
    public List<Repair> findByStatus(Repair.Status status) throws SQLException {
        String sql = "SELECT * FROM repair WHERE status = ? ORDER BY created_at DESC";
        List<Repair> repairs = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    repairs.add(mapResultSetToRepair(rs));
                }
            }
        }
        return repairs;
    }

    // 根据创建人ID查询报修记录
    public List<Repair> findByCreatorId(int creatorId) throws SQLException {
        String sql = "SELECT * FROM repair WHERE creator_id = ? ORDER BY created_at DESC";
        List<Repair> repairs = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, creatorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    repairs.add(mapResultSetToRepair(rs));
                }
            }
        }
        return repairs;
    }

    // 根据处理人ID查询报修记录
    public List<Repair> findByHandlerId(int handlerId) throws SQLException {
        String sql = "SELECT * FROM repair WHERE handler_id = ? ORDER BY created_at DESC"; // 添加了排序
        List<Repair> repairs = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, handlerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    repairs.add(mapResultSetToRepair(rs));
                }
            }
        }
        return repairs;
    }

    // 将ResultSet映射为Repair对象
    private Repair mapResultSetToRepair(ResultSet rs) throws SQLException {
        Repair repair = new Repair();
        repair.setId(rs.getInt("id"));
        repair.setDescription(rs.getString("description"));
        repair.setImage(rs.getBytes("image")); // 修改为getBytes获取二进制数据
        repair.setLocationType(Repair.LocationType.fromValue(rs.getString("location_type")));
        repair.setSpecificLocation(rs.getString("specific_location"));
        repair.setCreatorId(rs.getInt("creator_id"));
        repair.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        repair.setStatus(Repair.Status.fromValue(rs.getString("status")));

        int handlerId = rs.getInt("handler_id");
        if (!rs.wasNull()) {
            repair.setHandlerId(handlerId);
        }

        Timestamp handledAt = rs.getTimestamp("handled_at");
        if (handledAt != null) {
            repair.setHandledAt(handledAt.toLocalDateTime());
        }

        int rating = rs.getInt("rating");
        if (!rs.wasNull()) {
            repair.setRating(rating);
        }

        Timestamp ratedAt = rs.getTimestamp("rated_at");
        if (ratedAt != null) {
            repair.setRatedAt(ratedAt.toLocalDateTime());
        }

        return repair;
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
            RepairDAO repairDAO = new RepairDAO();
            // 测试获取所有报修记录
            List<Repair> repairs = repairDAO.getAllRepairs();
            for (Repair repair : repairs) {
                System.out.println(repair);
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