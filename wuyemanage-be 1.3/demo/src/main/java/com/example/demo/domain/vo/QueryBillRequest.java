package com.example.demo.domain.vo;

public class QueryBillRequest {
    private Integer buildingNumber;
    private Integer doorNumber;

    public QueryBillRequest(Integer buildingNumber, Integer doorNumber) {
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
    }

    public QueryBillRequest() {
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    @Override
    public String toString() {
        return "QueryBillParam{" +
                "buildingNumber=" + buildingNumber +
                ", doorNumber=" + doorNumber +
                '}';
    }
}
