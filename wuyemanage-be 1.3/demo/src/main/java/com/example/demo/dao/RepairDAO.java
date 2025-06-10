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
    // 在 RepairDAO 类中添加以下方法

    /**
     * 获取报修总数
     */
    public int getTotalRepairsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM repair";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    /**
     * 获取室外公共区域报修数
     */
    public int getPublicAreaRepairsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM repair WHERE location_type = 'public_area'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    /**
     * 获取1-5号楼各楼栋的室内报修数
     * 返回包含5个元素的List，分别对应1-5号楼的报修数
     */
    public List<Integer> getIndoorRepairsCountPerBuilding() throws SQLException {
        List<Integer> counts = new ArrayList<>(5);
        // 初始化所有楼栋计数为0
        for (int i = 0; i < 5; i++) {
            counts.add(0);
        }

        // 使用正则表达式提取楼号（假设地址格式为"楼号-门牌号"）
        String sql = "SELECT " +
                "SUM(CASE WHEN specific_location REGEXP '^1-' THEN 1 ELSE 0 END) as building1, " +
                "SUM(CASE WHEN specific_location REGEXP '^2-' THEN 1 ELSE 0 END) as building2, " +
                "SUM(CASE WHEN specific_location REGEXP '^3-' THEN 1 ELSE 0 END) as building3, " +
                "SUM(CASE WHEN specific_location REGEXP '^4-' THEN 1 ELSE 0 END) as building4, " +
                "SUM(CASE WHEN specific_location REGEXP '^5-' THEN 1 ELSE 0 END) as building5 " +
                "FROM repair WHERE location_type = 'indoor'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                counts.set(0, rs.getInt("building1"));
                counts.set(1, rs.getInt("building2"));
                counts.set(2, rs.getInt("building3"));
                counts.set(3, rs.getInt("building4"));
                counts.set(4, rs.getInt("building5"));
            }
        }
        return counts;
    }

    /**
     * 获取已处理的报修数
     */
    public int getProcessedRepairsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM repair WHERE status IN ('processed', 'rated')";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    /**
     * 获取平均处理时长（小时），只统计已处理的报修
     */
    public double getAverageProcessingHours() throws SQLException {
        String sql = "SELECT AVG(TIMESTAMPDIFF(HOUR, created_at, handled_at)) as avg_hours " +
                "FROM repair WHERE handled_at IS NOT NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_hours");
            }
            return 0.0;
        }
    }

    /**
     * 获取已评价的报修数
     */
    public int getRatedRepairsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM repair WHERE status = 'rated'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    /**
     * 获取各评分(1-5)的报修数
     * 返回包含5个元素的List，分别对应评分1-5的报修数
     */
    public List<Integer> getRepairsCountByRating() throws SQLException {
        List<Integer> counts = new ArrayList<>(5);
        // 初始化所有评分计数为0
        for (int i = 0; i < 5; i++) {
            counts.add(0);
        }

        String sql = "SELECT " +
                "SUM(CASE WHEN rating = 1 THEN 1 ELSE 0 END) as rating1, " +
                "SUM(CASE WHEN rating = 2 THEN 1 ELSE 0 END) as rating2, " +
                "SUM(CASE WHEN rating = 3 THEN 1 ELSE 0 END) as rating3, " +
                "SUM(CASE WHEN rating = 4 THEN 1 ELSE 0 END) as rating4, " +
                "SUM(CASE WHEN rating = 5 THEN 1 ELSE 0 END) as rating5 " +
                "FROM repair WHERE rating IS NOT NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                counts.set(0, rs.getInt("rating1"));
                counts.set(1, rs.getInt("rating2"));
                counts.set(2, rs.getInt("rating3"));
                counts.set(3, rs.getInt("rating4"));
                counts.set(4, rs.getInt("rating5"));
            }
        }
        return counts;
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