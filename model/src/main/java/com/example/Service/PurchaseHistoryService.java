package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.PurchaseHistoryRepositoy;
import com.example.entity.PurchaseHistory;
import com.example.entity.Users;

@Service
public class PurchaseHistoryService {
    @Autowired
    private PurchaseHistoryRepositoy historyRepositoy;

    @Autowired
    private UserByAdminService adminService;
    
    public List<PurchaseHistory> getPurchaseHistoryById(String UserId){
        Users user = adminService.findById(UserId);
        return historyRepositoy.findByUser(user);
    }   
}
