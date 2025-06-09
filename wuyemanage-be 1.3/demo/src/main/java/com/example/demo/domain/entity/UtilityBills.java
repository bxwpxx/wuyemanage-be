package com.example.demo.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UtilityBills {
    private int billId;
    private int buildingNumber;
    private int doorNumber;
    private LocalDate billMonth;
    private BigDecimal waterFee;
    private BigDecimal electricityFee;
    private BigDecimal totalFee;
    private boolean isPaid;
    private LocalDate paidDate;

    // 构造方法
    public UtilityBills() {
    }

    // Getter 和 Setter 方法
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public LocalDate getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(LocalDate billMonth) {
        this.billMonth = billMonth;
    }

    public BigDecimal getWaterFee() {
        return waterFee;
    }

    public void setWaterFee(BigDecimal waterFee) {
        this.waterFee = waterFee;
    }

    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        this.electricityFee = electricityFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    @Override
    public String toString() {
        return "UtilityBills{" +
                "billId=" + billId +
                ", buildingNumber=" + buildingNumber +
                ", doorNumber=" + doorNumber +
                ", billMonth=" + billMonth +
                ", waterFee=" + waterFee +
                ", electricityFee=" + electricityFee +
                ", totalFee=" + totalFee +
                ", isPaid=" + isPaid +
                ", paidDate=" + paidDate +
                '}';
    }
}