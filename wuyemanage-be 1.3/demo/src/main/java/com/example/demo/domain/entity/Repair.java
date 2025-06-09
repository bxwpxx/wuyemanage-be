package com.example.demo.domain.entity;

import com.example.demo.domain.vo.CreateRepairRequest;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDateTime;

public class Repair {
    private Integer id;
    private String description;
    private byte[] image; // 修改为字节数组存储二进制数据
    private LocationType locationType;
    private String specificLocation;
    private Integer creatorId;
    private LocalDateTime createdAt;
    private Status status;
    private Integer handlerId;
    private LocalDateTime handledAt;
    private Integer rating;
    private LocalDateTime ratedAt;

    // 枚举定义地点类型
    public enum LocationType {
        INDOOR("indoor"),
        PUBLIC_AREA("public_area");

        private final String value;

        LocationType(String value) {
            this.value = value;
        }
        @JsonValue
        public String getValue() {
            return value;
        }

        public static LocationType fromValue(String value) {
            for (LocationType type : values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown location type: " + value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    // 状态枚举
    public enum Status {
        PENDING("pending"),
        PROCESSED("processed"),
        RATED("rated");

        private final String value;

        Status(String value) {
            this.value = value;
        }
        @JsonValue
        public String getValue() {
            return value;
        }

        public static Status fromValue(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Status value cannot be null");
            }
            for (Status status : Status.values()) {
                if (status.getValue().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status value: " + value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    // 构造方法
    public Repair() {
    }

    public Repair(CreateRepairRequest createRepairRequest) {
        if (createRepairRequest == null) {
            throw new IllegalArgumentException("CreateRepairRequest cannot be null");
        }

        this.description = createRepairRequest.getDescription();
        this.image = createRepairRequest.getImage(); // 修改为获取字节数组
        this.locationType = createRepairRequest.getLocationType();
        this.specificLocation = createRepairRequest.getSpecificLocation();
        this.creatorId = createRepairRequest.getCreatorId();
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;

        // 其他字段保持默认值或null
        this.handlerId = null;
        this.handledAt = null;
        this.rating = null;
        this.ratedAt = null;
    }

    public Repair(String description, LocationType locationType, String specificLocation, Integer creatorId) {
        this.description = description;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Integer handlerId) {
        this.handlerId = handlerId;
    }

    public LocalDateTime getHandledAt() {
        return handledAt;
    }

    public void setHandledAt(LocalDateTime handledAt) {
        this.handledAt = handledAt;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("评分必须在1-5之间");
        }
        this.rating = rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(LocalDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", image=" + (image != null ? "[binary data]" : "null") + // 避免直接打印二进制数据
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