package com.example.demo.domain.entity;

import java.time.LocalDateTime;

public class Announcement {
    private Integer announcementId;    // 公告ID
    private String title;              // 公告标题
    private String content;            // 公告内容
    private Integer uploaderId;         // 上传人ID

    private String uploaderName;        // 上传人姓名
    private LocalDateTime uploadTime;           // 上传时间



    public Announcement() {
    }

    public Announcement(Integer announcementId, String title, String content, Integer uploaderId, LocalDateTime uploadTime) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.uploaderId = uploaderId;
        this.uploadTime = uploadTime;
    }

    public Announcement(Integer announcementId, String title, String content, Integer uploaderId, String uploaderName, LocalDateTime uploadTime) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.uploaderId = uploaderId;
        this.uploaderName = uploaderName;
        this.uploadTime = uploadTime;
    }

    // Getter 和 Setter 方法
    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "announcementId=" + announcementId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", uploaderId=" + uploaderId +
                ", uploaderName='" + uploaderName + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
