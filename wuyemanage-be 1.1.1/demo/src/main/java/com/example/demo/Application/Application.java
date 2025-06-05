package com.example.demo.Application;  // 替换为您的实际包名

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用主启动类
 *
 * 核心注解说明：
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 *   - 开启自动配置、组件扫描和配置类功能
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(Application.class, args);

        // 可选：添加启动成功提示（生产环境建议移除）
        System.out.println("✅ 应用启动成功！访问 http://localhost:8085");
    }
}