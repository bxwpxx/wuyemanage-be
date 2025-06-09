package com.example.demo.service;

import com.example.demo.dao.UtilityBillsDAO;
import com.example.demo.domain.entity.UtilityBills;
import com.example.demo.domain.vo.AddBillRequest;
import com.example.demo.domain.vo.QueryBillRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UtilityBillsServiceImpl implements UtilityBillsService{
    private final UtilityBillsDAO utilityBillsDAO;

    public UtilityBillsServiceImpl(UtilityBillsDAO utilityBillsDAO) {
        this.utilityBillsDAO = utilityBillsDAO;
    }

    public boolean addBillRequest(AddBillRequest addBillRequest) throws SQLException
    {
        try {
            UtilityBills utilityBills=new UtilityBills();
            utilityBills.setBuildingNumber(addBillRequest.getBuildingNumber());
            utilityBills.setDoorNumber(addBillRequest.getDoorNumber());
            utilityBills.setWaterFee(addBillRequest.getWaterFee());
            utilityBills.setElectricityFee(addBillRequest.getElectricityFee());
            utilityBills.setBillMonth(addBillRequest.getYearMonth().atDay(1));
            utilityBillsDAO.insertUtilityBill(utilityBills);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }
    public List<UtilityBills> queryBillRequest(@RequestBody QueryBillRequest queryBillRequest) throws SQLException
    {

        return  utilityBillsDAO.getBillsByBuildingAndDoor(queryBillRequest.getBuildingNumber(),queryBillRequest.getDoorNumber()) ;
    }
    public  boolean payBill(int billId)
    {
        try {
            UtilityBills utilityBills=utilityBillsDAO.getUtilityBillById(billId);
            utilityBills.setIsPaid(true);
            utilityBills.setPaidDate(LocalDate.from(LocalDateTime.now()));
            utilityBillsDAO.updateUtilityBill(utilityBills);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }
}
