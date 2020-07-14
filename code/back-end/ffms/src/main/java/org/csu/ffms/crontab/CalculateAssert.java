package org.csu.ffms.crontab;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.controller.FundController;
import org.csu.ffms.controller.StockController;
import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.AccountAssert;
import org.csu.ffms.domain.Stock;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.service.StockService;
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

    @Scheduled(cron = "0/5 * * * * ?")
    public void print(){
        System.out.println(new Date());
    }

    @Scheduled(cron="0 0 4 ? * 3 ")
    public void updateAssert(){
        System.out.println(new Date());
        System.out.println("updateAssert");
//        List<Account> accountList=accountService.getAllAccount();
//        for (Account account :accountList) {
//            AccountAssert accountAssert=new AccountAssert();
//            Map<String,String> map=new HashMap<>();
//            map.put("userid",account.getUserid());
//            map.put("queryid",account.getUserid());
//            JSONObject stockJson=stockController.getTotal(map);
//            accountAssert.setStock(new BigDecimal(stockJson.get("total").toString()));
//            JSONObject fundJson = fundController.getTotal(map);
//            accountAssert.setFund(new BigDecimal(fundJson.get("total").toString()));
//
//        }
    }


}
