package com.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/PropertyManagement?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "040195sld";

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);

        // 连接池配置
        config.setMaximumPoolSize(10); // 最大连接数
        config.setMinimumIdle(5);     // 最小空闲连接
        config.setIdleTimeout(600000); // 空闲连接超时时间(毫秒)
        config.setConnectionTimeout(30000); // 连接超时时间
        config.setMaxLifetime(1800000); // 连接最大生命周期
        config.setPoolName("PropertyManagementPool"); // 连接池名称

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("成功获取连接: " + conn);
            // 使用连接执行数据库操作...
        } catch (SQLException e) {
            System.err.println("获取连接失败: " + e.getMessage());
        } finally {
            closePool();
        }
    }
}