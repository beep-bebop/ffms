package org.csu.ffms.crontab;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.controller.FundController;
import org.csu.ffms.controller.StockController;
import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.AccountAssert;
import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Stock;
import org.csu.ffms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/10
 * @描述
 **/
@Component
public class CalculateAssert {
    @Autowired
    AccountService accountService;
    @Autowired
    StockController stockController;
    @Autowired
    FundController fundController;
    @Autowired
    IncomeService incomeService;
    @Autowired
    DisburseService disburseService;
    @Autowired
    AccountAssertService accountAssertService;

    //？如何测试
    @Scheduled(cron="0 0 12 ? * 1 ")
    public void updateAssert(){
        List<Account> accountList=accountService.getAllAccount();
        for (Account account :accountList) {
            AccountAssert accountAssert=new AccountAssert();
            Map<String,String> map=new HashMap<>();
            map.put("userid",account.getUserid());
            map.put("queryid",account.getUserid());
            JSONObject stockJson=stockController.getTotal(map);
            JSONObject fundJson = fundController.getTotal(map);
            accountAssert.setUserid(account.getUserid());
            accountAssert.setTime(new Date());
            accountAssert.setStock(new BigDecimal(stockJson.get("total").toString()));
            accountAssert.setFund(new BigDecimal(fundJson.get("total").toString()));
            accountAssert.setCash(new BigDecimal(incomeService.totalWeekIncome(account.getUserid())+disburseService.totalWeekDisburse(account.getUserid())));
            accountAssertService.insertAssert(accountAssert);
        }

    }


}
