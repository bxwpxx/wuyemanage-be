package com.example.demo.dao;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.domain.entity.UtilityBills;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UtilityBillsDAO {
    private final Connection connection;

    public UtilityBillsDAO() {
        try {
            this.connection = DatabaseConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 插入水电费账单
    public void insertUtilityBill(UtilityBills bill) throws SQLException {
        String sql = "INSERT INTO UtilityBills (BuildingNumber, DoorNumber, bill_month, water_fee, electricity_fee) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bill.getBuildingNumber());
            statement.setInt(2, bill.getDoorNumber());
            statement.setDate(3, Date.valueOf(bill.getBillMonth()));
            statement.setBigDecimal(4, bill.getWaterFee());
            statement.setBigDecimal(5, bill.getElectricityFee());
            statement.executeUpdate();
        }
    }

    // 根据ID获取账单
    public UtilityBills getUtilityBillById(int billId) throws SQLException {
        String sql = "SELECT * FROM UtilityBills WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractUtilityBillFromResultSet(resultSet);
            }
            return null;
        }
    }

    // 获取所有账单
    public List<UtilityBills> getAllUtilityBills() throws SQLException {
        List<UtilityBills> bills = new ArrayList<>();
        String sql = "SELECT * FROM UtilityBills";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                bills.add(extractUtilityBillFromResultSet(resultSet));
            }
        }
        return bills;
    }

    // 根据楼号和门牌号查询账单
    public List<UtilityBills> getBillsByBuildingAndDoor(int buildingNumber, int doorNumber) throws SQLException {
        List<UtilityBills> bills = new ArrayList<>();
        String sql = "SELECT * FROM UtilityBills WHERE BuildingNumber = ? AND DoorNumber = ? ORDER BY bill_month DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buildingNumber);
            statement.setInt(2, doorNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bills.add(extractUtilityBillFromResultSet(resultSet));
            }
        }
        return bills;
    }

    // 更新账单信息
    public void updateUtilityBill(UtilityBills bill) throws SQLException {
        String sql = "UPDATE UtilityBills SET BuildingNumber = ?, DoorNumber = ?, bill_month = ?, " +
                "water_fee = ?, electricity_fee = ?, is_paid = ?, paid_date = ? WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bill.getBuildingNumber());
            statement.setInt(2, bill.getDoorNumber());
            statement.setDate(3, Date.valueOf(bill.getBillMonth()));
            statement.setBigDecimal(4, bill.getWaterFee());
            statement.setBigDecimal(5, bill.getElectricityFee());
            statement.setBoolean(6, bill.getIsPaid());
            if (bill.getPaidDate() != null) {
                statement.setDate(7, Date.valueOf(bill.getPaidDate()));
            } else {
                statement.setNull(7, Types.DATE);
            }
            statement.setInt(8, bill.getBillId());
            statement.executeUpdate();
        }
    }

    // 删除账单
    public void deleteUtilityBill(int billId) throws SQLException {
        String sql = "DELETE FROM UtilityBills WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }

    // 更新支付状态
    public void updatePaymentStatus(int billId, boolean isPaid, LocalDate paidDate) throws SQLException {
        String sql = "UPDATE UtilityBills SET is_paid = ?, paid_date = ? WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, isPaid);
            if (paidDate != null) {
                statement.setDate(2, Date.valueOf(paidDate));
            } else {
                statement.setNull(2, Types.DATE);
            }
            statement.setInt(3, billId);
            statement.executeUpdate();
        }
    }

    // 从ResultSet提取UtilityBills对象
    private UtilityBills extractUtilityBillFromResultSet(ResultSet resultSet) throws SQLException {
        UtilityBills bill = new UtilityBills();
        bill.setBillId(resultSet.getInt("bill_id"));
        bill.setBuildingNumber(resultSet.getInt("BuildingNumber"));
        bill.setDoorNumber(resultSet.getInt("DoorNumber"));
        bill.setBillMonth(resultSet.getDate("bill_month").toLocalDate());
        bill.setWaterFee(resultSet.getBigDecimal("water_fee"));
        bill.setElectricityFee(resultSet.getBigDecimal("electricity_fee"));
        bill.setTotalFee(resultSet.getBigDecimal("total_fee"));
        bill.setIsPaid(resultSet.getBoolean("is_paid"));
        Date paidDate = resultSet.getDate("paid_date");
        bill.setPaidDate(paidDate != null ? paidDate.toLocalDate() : null);
        return bill;
    }
    // 在UtilityBillsDAO类中添加以下方法

    // 计算总水费
    public double getTotalWaterFee() throws SQLException {
        String sql = "SELECT SUM(water_fee) AS total_water_fee FROM UtilityBills";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("total_water_fee");
            }
            return 0.0;
        }
    }

    // 计算总电费
    public double getTotalElectricityFee() throws SQLException {
        String sql = "SELECT SUM(electricity_fee) AS total_electricity_fee FROM UtilityBills";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("total_electricity_fee");
            }
            return 0.0;
        }
    }

    // 计算缴费率
    public double getPaymentRate() throws SQLException {
        String sql = "SELECT COUNT(*) AS total, SUM(CASE WHEN is_paid = TRUE THEN 1 ELSE 0 END) AS paid " +
                "FROM UtilityBills";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                int paid = resultSet.getInt("paid");
                return total == 0 ? 0.0 : (paid * 100.0 / total);
            }
            return 0.0;
        }
    }

    // 计算平均缴费天数
    public double getAveragePaymentDays() throws SQLException {
        String sql = "SELECT AVG(DATEDIFF(paid_date, bill_month)) AS avg_days " +
                "FROM UtilityBills WHERE is_paid = TRUE";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("avg_days");
            }
            return 0.0;
        }
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 UtilityBillsDAO 实例
            UtilityBillsDAO utilityBillsDAO = new UtilityBillsDAO();

            // 获取所有账单
            List<UtilityBills> bills = utilityBillsDAO.getBillsByBuildingAndDoor(1,101);

            // 输出所有账单信息
            for (UtilityBills bill : bills) {
                System.out.println(bill);
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