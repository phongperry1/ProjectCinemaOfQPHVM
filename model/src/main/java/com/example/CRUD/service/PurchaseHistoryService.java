package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.PurchaseHistoryRepositoy;
import com.example.mo.PurchaseHistory;
import com.example.mo.Users;

@Service
public class PurchaseHistoryService {
    @Autowired
    private PurchaseHistoryRepositoy historyRepositoy;

    @Autowired
    private UserByAdminService adminService;
    
    public List<PurchaseHistory> getPurchaseHistoryById(Integer UserId){
        Users user = adminService.findById(UserId);
        return historyRepositoy.findByUser(user);
    }   
}