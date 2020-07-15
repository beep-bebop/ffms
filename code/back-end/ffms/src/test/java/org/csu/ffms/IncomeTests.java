package org.csu.ffms;

import org.csu.ffms.controller.IncomeController;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class IncomeTests {
    @Autowired
    Income income;
    @Autowired
    IncomeController incomeController;

    @Autowired
    IncomeService incomeService;

    @Test
    public void findIncomeList(){
        incomeController.findIncomeByFamily("S1");
    }


    @Test
    public void testWeekTotal() {
        income.setUserId("S1");
        System.out.println(incomeService.findIncomeList(income));
    }

    @Test
    public void totalIncomeByTypeAndWeek(){
        incomeController.totalIncomeByTypeAndWeek("S1");
    }

}
