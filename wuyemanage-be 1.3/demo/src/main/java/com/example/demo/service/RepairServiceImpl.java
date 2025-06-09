package com.example.demo.service;

import com.example.demo.dao.RepairDAO;
import com.example.demo.domain.entity.Repair;
import com.example.demo.domain.vo.CreateRepairRequest;
import com.example.demo.domain.vo.HandRepairRequest;
import com.example.demo.domain.vo.RateRepairRequest;
import com.example.demo.domain.vo.RepairItem;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService {
    private final RepairDAO repairDAO;

    public RepairServiceImpl(RepairDAO repairDAO) {
        this.repairDAO = repairDAO;
    }

    public List<RepairItem> getAllRepairRequests() throws SQLException {
        return repairDAO.getAllRepairRequests().stream()
                .map(RepairItem::new)
                .collect(Collectors.toList());
    }
    public boolean createRepairRequestRequest(CreateRepairRequest createRepairRequest)
    {
        try {
            Repair repair =new Repair(createRepairRequest);
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

}
