package com.example.demo.domain.entity;

public class Property {
    private int propertyID;
    private String userID;
    private User user=new User();

    // 构造函数
    public Property(int propertyID, User user) {
        this.propertyID = propertyID;
        this.userID = user.getUserID();
        this.user=user;
    }
    public Property(int propertyID, String userID) {
        this.propertyID = propertyID;
        this.userID = userID;
        this.user.setUserID(userID);
    }

    public Property(int propertyID, String userID, User user) {
        this.propertyID = propertyID;
        this.userID = userID;
        this.user = user;
    }

    // Getters 和 Setters
    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyID=" + propertyID +
                ", userID='" + userID + '\'' +
                ", user=" + user +
                '}';
    }
}
