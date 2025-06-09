package com.example.demo.service;

import com.example.demo.domain.entity.UtilityBills;
import com.example.demo.domain.vo.AddBillRequest;
import com.example.demo.domain.vo.QueryBillRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

@Service
public interface UtilityBillsService {
    boolean addBillRequest(AddBillRequest addBillRequest) throws SQLException;
    List<UtilityBills> queryBillRequest(QueryBillRequest queryBillRequest) throws SQLException;
    boolean payBill(int billId);
}
