package com.example.demo.domain.vo;


import java.math.BigDecimal;
import java.time.YearMonth;


public class AddBillRequest {

    private Integer buildingNumber;


    private Integer doorNumber;


    private YearMonth yearMonth;


    private BigDecimal waterFee;


    private BigDecimal electricityFee;

    public AddBillRequest() {
    }

    public AddBillRequest(Integer buildingNumber, Integer doorNumber, YearMonth yearMonth, BigDecimal waterFee, BigDecimal electricityFee) {
        this.buildingNumber = buildingNumber;
        this.doorNumber = doorNumber;
        this.yearMonth = yearMonth;
        this.waterFee = waterFee;
        this.electricityFee = electricityFee;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public BigDecimal getWaterFee() {
        return waterFee;
    }

    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    @Override
    public String toString() {
        return "AddBillRequest{" +
                "buildingNumber=" + buildingNumber +
                ", doorNumber=" + doorNumber +
                ", yearMonth=" + yearMonth +
                ", waterFee=" + waterFee +
                ", electricityFee=" + electricityFee +
                '}';
    }
}