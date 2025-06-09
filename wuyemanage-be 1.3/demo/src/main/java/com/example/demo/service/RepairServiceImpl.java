package com.example.demo.service;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.dao.OwnerDAO;
import com.example.demo.dao.PropertyDAO;
import com.example.demo.dao.RepairDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Repair;
import com.example.demo.domain.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService {
    private final RepairDAO repairDAO;
    private final OwnerDAO ownerDAO;

    public RepairServiceImpl(RepairDAO repairDAO,OwnerDAO ownerDAO) {
        this.repairDAO = repairDAO;
        this.ownerDAO=ownerDAO;
    }

    public List<RepairItem> getAllRepairRequests() throws SQLException {
        return repairDAO.getAllRepairs().stream()
                .map(RepairItem::new)
                .collect(Collectors.toList());
    }
    public boolean createRepairRequestRequest(CreateRepairRequest createRepairRequest)
    {
        try {
            Repair repair =new Repair(createRepairRequest);
            if(repair.getLocationType().equals(Repair.LocationType.INDOOR))
            {
                Owner owner =ownerDAO.getOwnerByOwnerId(repair.getCreatorId());
                repair.setSpecificLocation(owner.getBuildingNumber()+"-"+owner.getDoorNumber());
            }
            repairDAO.create(repair);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }
    public Repair findById(int Id) throws SQLException {
        return repairDAO.findById(Id);
    }
    public List<RepairItem> findRepairList(int creatorId) throws SQLException
    {
        return repairDAO.findByCreatorId(creatorId).stream()
                .map(RepairItem::new)
                .collect(Collectors.toList());
    }
    public boolean handRepair(HandRepairRequest handRepairRequest)
    {
        try {
            Repair repair=repairDAO.findById(handRepairRequest.getId());
            repair.setHandlerId(handRepairRequest.getHandlerId());
            repair.setHandledAt(LocalDateTime.now());
            repair.setStatus(Repair.Status.PROCESSED);
            repairDAO.update(repair);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }
    public boolean rateRepair(RateRepairRequest rateRepairRequest)
    {
        try {
            Repair repair=repairDAO.findById(rateRepairRequest.getId());
            repair.setRating(rateRepairRequest.getRating());
            repair.setRatedAt(LocalDateTime.now());
            repair.setStatus(Repair.Status.RATED);
            repairDAO.update(repair);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        RepairDAO repairDAO=null;
        OwnerDAO ownerDAO=null;
        try {

            connection = DatabaseConnectionPool.getConnection();
            // 创建 DAO 实例
            repairDAO=new RepairDAO();
            ownerDAO=new OwnerDAO();


        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        RepairService repairService=new RepairServiceImpl(repairDAO,ownerDAO);
        for (RepairItem repairItem:repairService.findRepairList(1))
        {
            System.out.println(repairItem);
            System.out.println(repairService.findById(repairItem.getId()));
        }

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
