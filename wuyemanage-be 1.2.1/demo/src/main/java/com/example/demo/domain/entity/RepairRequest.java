package com.example.demo.domain.entity;

import java.time.LocalDateTime;

public class RepairRequest {
    private Integer id;
    private String description;
    private String imagePath;
    private LocationType locationType;
    private String specificLocation;
    private Integer creatorId;
    private LocalDateTime createdAt;
    private Status status;  // 从Boolean改为枚举类型
    private Integer handlerId;
    private LocalDateTime handledAt;
    private Integer rating;
    private LocalDateTime ratedAt;

    // 枚举定义地点类型
    public enum LocationType {
        INDOOR(0, "indoor"),      // 数字代码0，字符串代码"indoor"
        PUBLIC_AREA(1, "public_area"); // 数字代码1，字符串代码"public_area"

        private final int numericCode;
        private final String stringCode;

        LocationType(int numericCode, String stringCode) {
            this.numericCode = numericCode;
            this.stringCode = stringCode;
        }

        public int getNumericCode() {
            return numericCode;
        }

        public String getStringCode() {
            return stringCode;
        }

        // 支持从数字或字符串转换
        public static LocationType fromCode(Object code) {
            if (code instanceof Integer) {
                return fromNumericCode((Integer) code);
            } else if (code instanceof String) {
                return fromStringCode((String) code);
            }
            throw new IllegalArgumentException("Code must be Integer or String");
        }

        // 从数字代码转换
        public static LocationType fromNumericCode(int code) {
            for (LocationType type : values()) {
                if (type.numericCode == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown numeric code: " + code);
        }

        // 从字符串代码转换
        public static LocationType fromStringCode(String code) {
            for (LocationType type : values()) {
                if (type.stringCode.equalsIgnoreCase(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown string code: " + code);
        }

        @Override
        public String toString() {
            return stringCode;
        }
    }

    // 新增状态枚举
    public enum Status {
        PENDING(0,"pending"),
        PROCESSED(1, "processed"),
        RATED(3, "rated");

        private final int numericCode;
        private final String value;

        Status(int numericCode, String value) {
            this.numericCode = numericCode;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Status fromValue(String value) {
            for (Status status : values()) {
                if (status.value.equalsIgnoreCase(value)) {
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
    public RepairRequest() {
    }

    public RepairRequest(String description, LocationType locationType, String specificLocation, Integer creatorId) {
        this.description = description;
        this.locationType = locationType;
        this.specificLocation = specificLocation;
        this.creatorId = creatorId;
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;  // 默认值改为PENDING
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        return "RepairRequest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
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