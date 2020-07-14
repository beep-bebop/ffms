package org.csu.ffms.service;

import org.csu.ffms.domain.AccountAssert;
import org.csu.ffms.persistence.AccountAssertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/13
 * @描述
 **/
@Service
public class AccountAssertService {
    @Autowired
    AccountAssertMapper accountAssertMapper;

    public void insertAssert(AccountAssert accountAssert){
        accountAssertMapper.insertAssert(accountAssert);
    }

    public List<AccountAssert> getAssertByUserid(String userid){
        return  accountAssertMapper.getAssertByUserid(userid);
    }

    public AccountAssert getLastAssertByUserid(String userid){
        return accountAssertMapper.getLastAssertByUserid(userid);
    }

}
