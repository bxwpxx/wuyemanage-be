package com.example.demo.domain.vo;

public class RateRepairRequest {
    private Integer id;
    private Integer rating;

    public RateRepairRequest(Integer id, Integer rating) {
        this.id = id;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "RateRepairRequest{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }
}
