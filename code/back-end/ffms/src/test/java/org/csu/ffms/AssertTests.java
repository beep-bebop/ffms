package org.csu.ffms;

import org.csu.ffms.crontab.CalculateAssert;
import org.csu.ffms.domain.AccountAssert;
import org.csu.ffms.service.AccountAssertService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/13
 * @描述
 **/
@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class AssertTests {
    @Autowired
    AccountAssertService accountAssertService;

    @Test
    public void getAccount() throws ParseException {
        AccountAssert accountAssert=new AccountAssert();
        accountAssert.setUserid("1");
        accountAssert.setTime(new Date());
        accountAssert.setCash(new BigDecimal(123));
        accountAssert.setFund(new BigDecimal(123));
        accountAssert.setStock(new BigDecimal(123));

        accountAssertService.insertAssert(accountAssert);
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        accountAssert.setTime(dateFormat1.parse("2009-06-01"));
        accountAssertService.insertAssert(accountAssert);

        System.out.println(accountAssertService.getLastAssertByUserid("1").toString());

    }

    @Autowired
    CalculateAssert calculateAssert;

    @Test
    public void test(){
        calculateAssert.updateAssert();
    }
}
