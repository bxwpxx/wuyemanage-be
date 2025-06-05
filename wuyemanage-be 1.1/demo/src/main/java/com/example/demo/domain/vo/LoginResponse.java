package com.example.demo.domain.vo;

import com.example.demo.domain.entity.User;

public class LoginResponse {
    private boolean success;
    private String message;
    private String role;
    private String userDetails; // 根据角色动态返回的数据

    // 构造方法
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String role, Object userDetails) {
        this.success = success;
        this.role = role;
        this.userDetails =  userDetails.toString();
        this.message = "Login successful";
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public Object getUserDetails() { return userDetails; }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", role='" + role + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }
}
