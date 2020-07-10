package org.csu.ffms;

import org.csu.ffms.controller.IncomeController;
import org.csu.ffms.domain.Income;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IncomeTests {
    @Autowired
    Income income;
    @Autowired
    IncomeController incomeController;

    @Test
    public void findIncomeList(){
        income.setUserId("11111");
        income.setType("salary");
        incomeController.findIncomeList(income);
    }

}
