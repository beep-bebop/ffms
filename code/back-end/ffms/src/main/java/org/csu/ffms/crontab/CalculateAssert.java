package org.csu.ffms.crontab;

import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.Stock;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/10
 * @描述
 **/
//@Component
//@EnableScheduling
public class CalculateAssert {
//    @Autowired
//    AccountService accountService;
//    @Autowired
//    StockService stockService;
//
//    @Scheduled(cron="0 0 12  L * ?")
//    public void updateAssert(){
//        List<Account> accountList=accountService.getAllAccount();
//        for (Account account :accountList) {
//            List<Stock> stockList=stockService.getStockByUserId(account.getUserid());
//
//        }
//    }
}
