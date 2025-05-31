package com.domain.entity;

public class Owner {
    private int ownerID;
    private String userID;
    private int buildingNumber;
    private int doorNumber;
    private User user=new User();

    // 构造函数
    public Owner(int ownerID, int buildingNumber, int doorNumber,User user) {
        this.ownerID = ownerID;
        this.userID = user.getUserID();
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.user=user;
    }
    public Owner(int ownerID,String userID, int buildingNumber, int doorNumber) {
        this.ownerID = ownerID;
        this.userID = userID;
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.user.setUserID(userID);
    }

    public Owner(int ownerID, String userID, int buildingNumber, int doorNumber, User user) {
        this.ownerID = ownerID;
        this.userID = userID;
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.user = user;
    }

    // Getters 和 Setters
    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerID=" + ownerID +
                ", userID='" + userID + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", doorNumber=" + doorNumber +
                ", user=" + user +
                '}';
    }
}
