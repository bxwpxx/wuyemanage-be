package com.example.demo.service;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.dao.OwnerDAO;
import com.example.demo.dao.RepairDAO;
import com.example.demo.dao.UtilityBillsDAO;
import com.example.demo.domain.vo.SummaryData;
import com.example.demo.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {

    private final OwnerDAO ownerDAO;
    private final RepairDAO repairDAO;
    private final UtilityBillsDAO utilityBillsDAO;

    @Autowired
    public SummaryServiceImpl(OwnerDAO ownerDAO, RepairDAO repairDAO, UtilityBillsDAO utilityBillsDAO) {
        this.ownerDAO = ownerDAO;
        this.repairDAO = repairDAO;
        this.utilityBillsDAO = utilityBillsDAO;
    }

    @Override
    public SummaryData getSummaryData() {
        try {
            SummaryData summaryData = new SummaryData();
            summaryData.setTotalOwners(ownerDAO.getTotalOwnersCount());
            summaryData.setOwnersPerBuilding(ownerDAO.getOwnersCountPerBuilding());

            // 报修相关数据
            summaryData.setTotalRepairs(repairDAO.getTotalRepairsCount());
            summaryData.setPublicAreaRepairs(repairDAO.getPublicAreaRepairsCount());
            summaryData.setIndoorRepairsPerBuilding(repairDAO.getIndoorRepairsCountPerBuilding());
            summaryData.setProcessedRepairs(repairDAO.getProcessedRepairsCount());
            summaryData.setAverageProcessingHours(repairDAO.getAverageProcessingHours());
            summaryData.setRatedRepairs(repairDAO.getRatedRepairsCount());
            summaryData.setRepairsByRating(repairDAO.getRepairsCountByRating());

            // 水电费相关数据
            summaryData.setTotalWaterFee(utilityBillsDAO.getTotalWaterFee());
            summaryData.setTotalElectricityFee(utilityBillsDAO.getTotalElectricityFee());
            summaryData.setPaymentRate(utilityBillsDAO.getPaymentRate());
            summaryData.setAveragePaymentDays(utilityBillsDAO.getAveragePaymentDays());

            return summaryData;
        } catch (SQLException e) {
            throw new RuntimeException("获取汇总数据失败", e);
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 DAO 实例
            OwnerDAO ownerDAO = new OwnerDAO();
            RepairDAO repairDAO = new RepairDAO();
            UtilityBillsDAO utilityBillsDAO = new UtilityBillsDAO();
            SummaryServiceImpl summaryService = new SummaryServiceImpl(ownerDAO, repairDAO, utilityBillsDAO);

            // 获取汇总数据
            SummaryData summaryData = summaryService.getSummaryData();

            // 打印业主数据
            System.out.println("业主总数: " + summaryData.getTotalOwners());
            List<Integer> ownersPerBuilding = summaryData.getOwnersPerBuilding();
            for (int i = 0; i < ownersPerBuilding.size(); i++) {
                System.out.println((i + 1) + "号楼业主数: " + ownersPerBuilding.get(i));
            }

            // 打印报修数据
            System.out.println("\n报修总数: " + summaryData.getTotalRepairs());
            System.out.println("室外报修数: " + summaryData.getPublicAreaRepairs());

            List<Integer> indoorRepairs = summaryData.getIndoorRepairsPerBuilding();
            for (int i = 0; i < indoorRepairs.size(); i++) {
                System.out.println((i + 1) + "号楼室内报修数: " + indoorRepairs.get(i));
            }

            System.out.println("已处理报修数: " + summaryData.getProcessedRepairs());
            System.out.println("平均处理时长(天): " + summaryData.getAverageProcessingHours()/24);
            System.out.println("已评价报修数: " + summaryData.getRatedRepairs());

            List<Integer> repairsByRating = summaryData.getRepairsByRating();
            for (int i = 0; i < repairsByRating.size(); i++) {
                System.out.println("评分" + (i + 1) + "的报修数: " + repairsByRating.get(i));
            }

            // 打印水电费数据
            System.out.println("\n总水费: " + summaryData.getTotalWaterFee());
            System.out.println("总电费: " + summaryData.getTotalElectricityFee());
            System.out.println("缴费率: " + summaryData.getPaymentRate() + "%");
            System.out.println("平均缴费天数: " + summaryData.getAveragePaymentDays());

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        } finally {
            // 关闭连接
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("关闭连接失败: " + e.getMessage());
                }
            }
            // 关闭连接池
            DatabaseConnectionPool.closePool();
        }
    }
}