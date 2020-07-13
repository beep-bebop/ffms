package org.csu.ffms.service;

import org.csu.ffms.persistence.CashMapper;
import org.csu.ffms.persistence.DisburseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashService {
    @Autowired
    CashMapper cashMapper;

    public int totalDisbursement(String userId){
        return cashMapper.totalDisbursement(userId);
    }

    public int totalDisbursementByFamily(String userId){
        return cashMapper.totalDisbursementByFamily(userId);
    }

    public int totalIncome(String userId){
        return cashMapper.totalIncome(userId);
    }

    public int totalIncomeByFamily(String userId){
        return cashMapper.totalIncomeByFamily(userId);
    }


}
