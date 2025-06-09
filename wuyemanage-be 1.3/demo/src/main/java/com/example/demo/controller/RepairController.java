package com.example.demo.controller;

import com.example.demo.domain.entity.Repair;
import com.example.demo.domain.vo.CreateRepairRequest;
import com.example.demo.domain.vo.HandRepairRequest;
import com.example.demo.domain.vo.RateRepairRequest;
import com.example.demo.domain.vo.RepairItem;
import com.example.demo.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
public class RepairController {
    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }
    @PostMapping("/getAllRepairList")
    public List<RepairItem> getAllRepairRequests() throws SQLException {
        return repairService.getAllRepairRequests();
    }
    @PostMapping("/createRepair")
    public boolean createRepair(@RequestBody CreateRepairRequest createRepairRequest) throws SQLException {
        return repairService.createRepairRequestRequest(createRepairRequest);
    }
    @PostMapping("/findRepairById")
    public Repair findRepairById(@RequestBody int Id) throws SQLException {
        return repairService.findById(Id);
    }
    @PostMapping("/findRepairListByCreatorId")
    public List<RepairItem> findRepairList(@RequestBody int creatorId) throws SQLException {
        return repairService.getAllRepairRequests();
    }
    @PostMapping("/handRepair")
    public boolean handRepair(@RequestBody HandRepairRequest handRepairRequest) throws SQLException {
        return repairService.handRepair(handRepairRequest);
    }
    @PostMapping("/rateRepair")
    public boolean rateRepair(@RequestBody RateRepairRequest rateRepairRequest) throws SQLException {
        return repairService.rateRepair(rateRepairRequest);
    }
}
