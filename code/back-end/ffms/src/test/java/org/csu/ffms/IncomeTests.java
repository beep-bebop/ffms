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
        income.setUserId("11111");
        income.setType("salary");
        incomeController.findIncomeList(income);
    }

<<<<<<< HEAD

    @Test
    public void testWeekTotal(){
       System.out.println(incomeService.totalWeekIncome("1"));
=======
    @Test
    public void totalIncomeByTypeAndWeek(){
        incomeController.totalIncomeByTypeAndWeek("S1");
>>>>>>> daed8738c68f319566be5a20d7da9926468ce990
    }

}
