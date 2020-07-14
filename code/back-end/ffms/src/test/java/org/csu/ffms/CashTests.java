package org.csu.ffms;

import org.csu.ffms.controller.CashController;
import org.csu.ffms.persistence.CashMapper;
import org.csu.ffms.service.CashService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class CashTests {
@Autowired
CashController cashController;
@Autowired
CashService cashService;

    @Test
    void totalCash(){
        String userId = "S1";
        int out = cashService.totalDisbursement(userId);
        int out1 = cashService.totalDisbursementByFamily(userId);
        int in = cashService.totalIncome(userId);
        int in1 = cashService.totalIncomeByFamily(userId);
        System.out.println("out = "+out +",out1 = "+out1+",in = "+in+",in1 = "+in1);

    }

    @Test
    void totalByWeek(){
        cashController.totalByWeek("S1");
    }


}
