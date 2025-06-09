package com.example.demo.domain.vo;

import com.example.demo.domain.entity.Repair;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CreateRepairRequest {
    private String description;
    private String image;
    private Repair.LocationType locationType;
    private String specificLocation;
    private Integer creatorId;

    public CreateRepairRequest(String description, String image, Repair.LocationType locationType, String specificLocation, Integer creatorId) {
        this.description = description;
        this.image = image;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
    }

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
                ", imagePath='" + image + '\'' +
                ", locationType=" + locationType +
                ", specificLocation='" + specificLocation + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }

    public byte[] getImage() {
        return Base64.getDecoder().decode(image);
    }
}
