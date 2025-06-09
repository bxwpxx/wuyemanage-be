package com.example.demo.domain.vo;

public class HandRepairRequest {
    private Integer id;
    private Integer handlerId;

    public HandRepairRequest(Integer id, Integer handlerId) {
        this.id = id;
        this.handlerId = handlerId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    @Override
    public String toString() {
        return "HandRepairRequest{" +
                "id=" + id +
                ", handlerId=" + handlerId +
                '}';
    }
}
