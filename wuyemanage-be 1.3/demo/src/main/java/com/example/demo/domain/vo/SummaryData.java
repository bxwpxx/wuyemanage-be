package com.example.demo.domain.vo;

import java.util.List;

public class SummaryData {
    private int totalOwners;          // 业主总数
    private List<Integer> ownersPerBuilding; // 每栋楼的业主数(1-5号楼)
    private int totalRepairs;              // 报修总数
    private int publicAreaRepairs;         // 室外报修数
    private List<Integer> indoorRepairsPerBuilding;  // 1-5号楼室内报修数
    private int processedRepairs;          // 已处理报修数
    private double averageProcessingHours; // 平均处理时长(小时)
    private int ratedRepairs;              // 已评价报修数
    private List<Integer> repairsByRating; // 评分1-5的报修数

    // 新增的水电费相关字段
    private double totalWaterFee;          // 总水费
    private double totalElectricityFee;    // 总电费
    private double paymentRate;            // 缴费率(百分比)
    private double averagePaymentDays;     // 平均缴费天数(已缴费账单从账单日到缴费日的平均天数)

    // 构造函数
    public SummaryData() {
    }

    // 原有getter和setter...

    // 新增的getter和setter
    public double getTotalWaterFee() {
        return totalWaterFee;
    }

    public void setTotalWaterFee(double totalWaterFee) {
        this.totalWaterFee = totalWaterFee;
    }

    public double getTotalElectricityFee() {
        return totalElectricityFee;
    }

    public void setTotalElectricityFee(double totalElectricityFee) {
        this.totalElectricityFee = totalElectricityFee;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    public double getAveragePaymentDays() {
        return averagePaymentDays;
    }

    public void setAveragePaymentDays(double averagePaymentDays) {
        this.averagePaymentDays = averagePaymentDays;
    }

    @Override
    public String toString() {
        return "SummaryData{" +
                "totalOwners=" + totalOwners +
                ", ownersPerBuilding=" + ownersPerBuilding +
                ", totalRepairs=" + totalRepairs +
                ", publicAreaRepairs=" + publicAreaRepairs +
                ", indoorRepairsPerBuilding=" + indoorRepairsPerBuilding +
                ", processedRepairs=" + processedRepairs +
                ", averageProcessingHours=" + averageProcessingHours +
                ", ratedRepairs=" + ratedRepairs +
                ", repairsByRating=" + repairsByRating +
                ", totalWaterFee=" + totalWaterFee +
                ", totalElectricityFee=" + totalElectricityFee +
                ", paymentRate=" + paymentRate +
                ", averagePaymentDays=" + averagePaymentDays +
                '}';
    }

    public int getTotalOwners() {
        return totalOwners;
    }

    public void setTotalOwners(int totalOwners) {
        this.totalOwners = totalOwners;
    }

    public List<Integer> getOwnersPerBuilding() {
        return ownersPerBuilding;
    }

    public void setOwnersPerBuilding(List<Integer> ownersPerBuilding) {
        this.ownersPerBuilding = ownersPerBuilding;
    }

    public int getTotalRepairs() {
        return totalRepairs;
    }

    public void setTotalRepairs(int totalRepairs) {
        this.totalRepairs = totalRepairs;
    }

    public int getPublicAreaRepairs() {
        return publicAreaRepairs;
    }

    public void setPublicAreaRepairs(int publicAreaRepairs) {
        this.publicAreaRepairs = publicAreaRepairs;
    }

    public List<Integer> getIndoorRepairsPerBuilding() {
        return indoorRepairsPerBuilding;
    }

    public void setIndoorRepairsPerBuilding(List<Integer> indoorRepairsPerBuilding) {
        this.indoorRepairsPerBuilding = indoorRepairsPerBuilding;
    }

    public int getProcessedRepairs() {
        return processedRepairs;
    }

    public void setProcessedRepairs(int processedRepairs) {
        this.processedRepairs = processedRepairs;
    }

    public double getAverageProcessingHours() {
        return averageProcessingHours;
    }

    public void setAverageProcessingHours(double averageProcessingHours) {
        this.averageProcessingHours = averageProcessingHours;
    }

    public int getRatedRepairs() {
        return ratedRepairs;
    }

    public void setRatedRepairs(int ratedRepairs) {
        this.ratedRepairs = ratedRepairs;
    }

    public List<Integer> getRepairsByRating() {
        return repairsByRating;
    }

    public void setRepairsByRating(List<Integer> repairsByRating) {
        this.repairsByRating = repairsByRating;
    }
}