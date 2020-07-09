package org.csu.ffms.service;

import org.csu.ffms.domain.Fund;
import org.csu.ffms.persistence.FundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Service
public class FundService {
    @Autowired
    private FundMapper fundMapper;

    public Fund getFundByFundCode(String code,String userid){
        return fundMapper.getFundByFundCode(code,userid);
    }

    public List<Fund> getFundByUserId(String userid){
        return fundMapper.getFundByUserId(userid);
    }

    public void insertFund(Fund fund){
        fundMapper.insertFund(fund);
    }

    public void deleteFund(Fund fund){
        fundMapper.deleteFund(fund);
    }

    public void updateFund(Fund fund){
        fundMapper.updateFund(fund);
    }


}