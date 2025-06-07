package com.example.demo.domain.vo;

public class UserUpdateRequest {
    private String userID;       // 必传，用户唯一标识
    private String username;
    private String password;  // 可选
    private String email;        // 可选
    private String phone;        // 可选

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String userID, String phone, String email, String password, String username) {
        this.userID = userID;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "userId='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
