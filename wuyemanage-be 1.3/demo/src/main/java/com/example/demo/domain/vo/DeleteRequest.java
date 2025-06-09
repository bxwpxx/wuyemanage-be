package com.example.demo.domain.vo;

public class DeleteRequest {
    public DeleteRequest() {
    }

    public int getId() {
        return id;
    }

    private int id;

    public DeleteRequest(int id) {
        this.id = id;
    }
}
