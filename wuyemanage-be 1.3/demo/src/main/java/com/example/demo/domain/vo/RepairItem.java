package com.example.demo.domain.vo;

import com.example.demo.domain.entity.Repair;

import java.time.LocalDateTime;

public class RepairItem {
    private Integer id;
    private String description;
    private Repair.LocationType locationType;
    private String specificLocation;
    private Integer creatorId;
    private LocalDateTime createdAt;
    private Repair.Status status;
    private Integer handlerId;
    private LocalDateTime handledAt;
    private Integer rating;
    private LocalDateTime ratedAt;

    public RepairItem(Integer id, String description, Repair.LocationType locationType, String specificLocation, Integer creatorId, LocalDateTime createdAt, Repair.Status status, Integer handlerId, LocalDateTime handledAt, Integer rating, LocalDateTime ratedAt) {
        this.id = id;
        this.description = description;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.status = status;
        this.handlerId = handlerId;
        this.handledAt = handledAt;
        this.rating = rating;
        this.ratedAt = ratedAt;
    }
    public RepairItem(Repair repair) {
        this.id = repair.getId();
        this.description = repair.getDescription();
        this.locationType = repair.getLocationType();
        this.specificLocation = repair.getSpecificLocation();
        this.creatorId = repair.getCreatorId();
        this.createdAt = repair.getCreatedAt();
        this.status = repair.getStatus();
        this.handlerId = repair.getHandlerId();
        this.handledAt = repair.getHandledAt();
        this.rating = repair.getRating();
        this.ratedAt = repair.getRatedAt();
    }

    public Integer getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Repair.Status getStatus() {
        return status;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public LocalDateTime getHandledAt() {
        return handledAt;
    }

    public Integer getRating() {
        return rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    @Override
    public String toString() {
        return "RepairItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", locationType=" + locationType +
                ", specificLocation='" + specificLocation + '\'' +
                ", creatorId=" + creatorId +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", handlerId=" + handlerId +
                ", handledAt=" + handledAt +
                ", rating=" + rating +
                ", ratedAt=" + ratedAt +
                '}';
    }
}
