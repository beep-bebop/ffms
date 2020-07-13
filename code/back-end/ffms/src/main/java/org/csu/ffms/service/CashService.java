package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.persistence.CashMapper;
import org.csu.ffms.persistence.DisburseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashService {

    @Autowired
    CashMapper cashMapper;

    //用户总支出
    public int totalDisbursement(String userId){
        return cashMapper.totalDisbursement(userId);
    }

    //家庭总支出
    public int totalDisbursementByFamily(String userId){
        return cashMapper.totalDisbursementByFamily(userId);
    }

    //用户总收入
    public int totalIncome(String userId){
        return cashMapper.totalIncome(userId);
    }

    //家庭总收入
    public int totalIncomeByFamily(String userId){
        return cashMapper.totalIncomeByFamily(userId);
    }


}
