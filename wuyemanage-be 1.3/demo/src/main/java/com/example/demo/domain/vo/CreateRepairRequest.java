package com.example.demo.domain.vo;

import com.example.demo.domain.entity.Repair;

public class CreateRepairRequest {
    private String description;
    private byte[] image;  // 改为byte[]类型
    private Repair.LocationType locationType;
    private String specificLocation;
    private Integer creatorId;

    public CreateRepairRequest(String description, byte[] image,
                               Repair.LocationType locationType,
                               String specificLocation, Integer creatorId) {
        this.description = description;
        this.image = image;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
    }

    // 移除Base64相关代码，直接使用byte[]
    public byte[] getImage() {
        return image;
    }

    // 其他getter方法保持不变
    public String getDescription() {
        return description;
    }

    public Repair.LocationType getLocationType() {
        return locationType;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public String toString() {
        return "CreateRepairRequest{" +
                "description='" + description + '\'' +
                ", imageSize=" + (image != null ? image.length + " bytes" : "null") +
                ", locationType=" + locationType +
                ", specificLocation='" + specificLocation + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }
}