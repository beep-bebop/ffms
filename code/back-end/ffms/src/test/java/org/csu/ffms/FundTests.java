package org.csu.ffms;

import com.alibaba.fastjson.JSONArray;
import org.csu.ffms.controller.FundController;
import org.csu.ffms.domain.Fund;
import org.csu.ffms.service.FundService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class FundTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    FundService fundService;

    @Test
    public void testGetFundByFundCode(){
        System.out.println("get userid:1 and code:1 fund");
        Fund fund=fundService.getFundByFundCode("1","1");
        System.out.println(fund);
    }

    @Test
    public void testGetFundByUserId(){
        System.out.println("get userid:1 all funds");
        List<Fund> fundList=fundService.getFundByUserId("1");
        for (Fund fund:fundList
        ) {
            System.out.println(fund.toString());
        }
    }

    @Test
    public void testInsertFund(){
        System.out.println("before insert");
        testGetFundByUserId();

        System.out.println("insert fund code:4 ,userid :1");
        Fund fund=new Fund("4","1","fourth",5,100);
        fundService.insertFund(fund);
        System.out.println("get fund by code:4 ,userid :1");

        System.out.println("after insert");
        testGetFundByUserId();
    }
    @Test
    public void testDeleteFund(){
        System.out.println("before delete");
        testGetFundByUserId();

        System.out.println("delete fund code:4 ,userid :1");
        Fund fund=new Fund("4","1","fourth",5,100);
        fundService.deleteFund(fund);

        System.out.println("after delete");
        testGetFundByUserId();
    }
    @Test
    public void testUpdateFund(){
        System.out.println("before update"+fundService.getFundByFundCode("1","1").toString());
        System.out.println("update fund code:1 ,userid :1");
        Fund fund=new Fund("1","1","first++fourth",55,10000);
        fundService.updateFund(fund);
        System.out.println("after update"+fundService.getFundByFundCode("1","1").toString());
    }

    @Autowired
    FundController fundController;

    @Test
    public void testGetAPIJSON(){
        Map<String,String> map=new HashMap<>();
        map.put("userid","1");
        System.out.println(fundController.getTotal(map));
        map.put("queryid","1");
        System.out.println(fundController.getTotal(map));

    }
}