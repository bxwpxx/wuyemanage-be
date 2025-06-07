package com.example.demo.domain.vo;

public class OwnerSignResponse {
    private boolean success;
    private String message;
    private String userid;

    public OwnerSignResponse(boolean success, String message, String userid) {
        this.success = success;
        this.message = message;
        this.userid = userid;
    }

    public OwnerSignResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
