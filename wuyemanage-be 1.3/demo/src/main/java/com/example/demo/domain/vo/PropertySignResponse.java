package com.example.demo.domain.vo;

public class PropertySignResponse {
    private boolean success;
    private String message;
    private String userid;

    public PropertySignResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public PropertySignResponse(boolean success, String message, String userid) {
        this.success = success;
        this.message = message;
        this.userid = userid;
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

    @Override
    public String toString() {
        return "PropertySignResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
