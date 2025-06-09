package com.example.demo.domain.vo;

public class CreateAnnouncementRequest {
    private String title;              // 公告标题
    private String content;            // 公告内容
    private Integer uploaderId;

    public CreateAnnouncementRequest(String title, String content, Integer uploaderId) {
        this.title = title;
        this.content = content;
        this.uploaderId = uploaderId;
    }

    public CreateAnnouncementRequest() {
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

    @Override
    public String toString() {
        return "CreateAnnouncementRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", uploaderId=" + uploaderId +
                '}';
    }
}
