package com.example.demo.service;

import com.example.demo.domain.entity.Repair;
import com.example.demo.domain.vo.CreateRepairRequest;
import com.example.demo.domain.vo.HandRepairRequest;
import com.example.demo.domain.vo.RateRepairRequest;
import com.example.demo.domain.vo.RepairItem;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;
@Service
public interface RepairService {
    List<RepairItem> getAllRepairRequests() throws SQLException;
    boolean createRepairRequestRequest(CreateRepairRequest createRepairRequest);
    Repair findById(int Id) throws SQLException;
    List<RepairItem> findRepairList(int creatorId) throws SQLException;
    boolean handRepair(HandRepairRequest handRepairRequest) ;
    boolean rateRepair(RateRepairRequest rateRepairRequest);

}
