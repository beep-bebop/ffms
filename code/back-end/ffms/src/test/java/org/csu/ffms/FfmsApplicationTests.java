package org.csu.ffms;

import org.csu.ffms.controller.DisburseController;
import org.csu.ffms.controller.IncomeController;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class FfmsApplicationTests {


    @Test
    void contextLoads1() {
        disburseController.deleteDisburse(2);
    }

    @Test
    void contextLoads2() {
        disburse.setDisburseId(1);
        disburse.setAmount_paid(500);
        disburse.setDescription("购物");
        disburse.setType("shopping");
        disburse.setUserId("11111");
        disburseController.updateDisburse(disburse);

    }

    @Test
    void contextLoads3() {
        disburse.setUserId("11111");
        disburseController.findDisburseList(disburse);

    }


    @Test
    void contextLoads01() {
        income.setIncome(100);
        income.setDescription("游戏");
        income.setType("fund");
        income.setUserId("1111111");
        incomeController.newIncome(income);
    }

    @Autowired
    IncomeService incomeService;
    @Test
    void contextLoads11() {
        incomeService.deleteIncome(1);
    }

    @Test
    void contextLoads21() {
        income.setIncomeId(3);
        income.setIncome(500);
        income.setDescription("购物");
        incomeController.updateIncome(income);

    }

    @Test
    void contextLoads31() {
        income.setType("salary");
        incomeController.findIncomeList(income);
    }
}
