package com.example.demo.domain.vo;

public class LoginRequest {
    private String userID;
    private String password;

    // 无参构造方法
    public LoginRequest() {
    }

    // 全参构造方法
    public LoginRequest(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    // Getter 和 Setter 方法
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}