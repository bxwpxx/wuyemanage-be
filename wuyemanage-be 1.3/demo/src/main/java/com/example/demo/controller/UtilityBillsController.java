package com.example.demo.controller;

import com.example.demo.domain.entity.UtilityBills;
import com.example.demo.domain.vo.AddBillRequest;
import com.example.demo.domain.vo.QueryBillRequest;
import com.example.demo.domain.vo.RateRepairRequest;
import com.example.demo.service.RepairService;
import com.example.demo.service.UtilityBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
public class UtilityBillsController {
    private final UtilityBillsService utilityBillsService;

    @Autowired
    public  UtilityBillsController(UtilityBillsService utilityBillsService) {
        this.utilityBillsService = utilityBillsService;
    }
    @PostMapping("/addBillRequest")
    public boolean addBillRequest(@RequestBody AddBillRequest addBillRequest) throws SQLException {
        return utilityBillsService.addBillRequest(addBillRequest);
    }
    @PostMapping("/queryBill")
    public List<UtilityBills> queryBillRequest(@RequestBody QueryBillRequest queryBillRequest) throws SQLException {
        return utilityBillsService.queryBillRequest(queryBillRequest);
    }
    @PostMapping("/payBill")
    public boolean payBill(@RequestBody Integer billId)
    {
        return utilityBillsService.payBill(billId);
    }
}
