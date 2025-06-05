package com.example.demo.domain.entity;

import java.time.LocalDateTime;

public class User {
    private String userID;
    private String username;
    private String hashPassword;
    private String email;
    private String role;
    private String phoneNumber;
    private LocalDateTime createdAt;

    // 构造函数
    public User(String userID, String username, String hashPassword, String email, String role, String phoneNumber, LocalDateTime createdAt) {
        this.userID = userID;
        this.username = username;
        this.hashPassword = hashPassword;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public User() {
    }

    public User(String userID, String username, String hashPassword, String email, String role, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.hashPassword = hashPassword;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.createdAt = LocalDateTime.now();
    }

    public User(String userID,String username, String hashPassword, String email, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.hashPassword = hashPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters 和 Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
