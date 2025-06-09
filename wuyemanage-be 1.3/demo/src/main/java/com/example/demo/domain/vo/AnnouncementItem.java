package com.example.demo.domain.vo;



import com.example.demo.domain.entity.Announcement;

import java.time.LocalDateTime;

/**
 * 用于表格显示的公告项（不含正文内容）
 */
public class AnnouncementItem {
    private Integer announcementId;
    private String title;
    private Integer uploaderId;
    private LocalDateTime uploadTime;
    private String uploaderName;

    // 从Announcement实体转换的构造方法
    public AnnouncementItem(Announcement announcement) {
        this.announcementId = announcement.getAnnouncementId();
        this.title = announcement.getTitle();
        this.uploaderId = announcement.getUploaderId();
        this.uploadTime = announcement.getUploadTime();
        this.uploaderName=announcement.getUploaderName();
    }

    public AnnouncementItem() {
    }

    public AnnouncementItem(Integer announcementId, String title, Integer uploaderId, LocalDateTime uploadTime, String uploaderName) {
        this.announcementId = announcementId;
        this.title = title;
        this.uploaderId = uploaderId;
        this.uploadTime = uploadTime;
        this.uploaderName = uploaderName;
    }

    // Getter方法
    public Integer getAnnouncementId() {
        return announcementId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
    public String getUploaderName() {
        return uploaderName;
    }


    // 格式化时间显示（可选）
    public String getFormattedTime() {
        return uploadTime.toString(); // 可根据需要调整格式
    }

    @Override
    public String toString() {
        return "AnnouncementItem{" +
                "announcementId=" + announcementId +
                ", title='" + title + '\'' +
                ", uploaderId=" + uploaderId +
                ", uploadTime=" + uploadTime +
                ", uploaderName='" + uploaderName + '\'' +
                '}';
    }
}
