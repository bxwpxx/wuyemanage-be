package com.example.demo.domain.vo;

import com.example.demo.domain.entity.Repair;

public class CreateRepairRequest {
    private String description;
    private String imagePath;
    private Repair.LocationType locationType;
    private String specificLocation;
    private Integer creatorId;

    public CreateRepairRequest(String description, String imagePath, Repair.LocationType locationType, String specificLocation, Integer creatorId) {
        this.description = description;
        this.imagePath = imagePath;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
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
                ", imagePath='" + imagePath + '\'' +
                ", locationType=" + locationType +
                ", specificLocation='" + specificLocation + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }
}
