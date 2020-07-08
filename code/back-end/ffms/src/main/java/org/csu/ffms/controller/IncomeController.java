package org.csu.ffms.controller;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income/")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @PostMapping("new")
    public void newIncome(Income income) {
        System.out.println("333333");
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        income.setTime(date);
        incomeService.newIncome(income);
        System.out.println("444444");
    }

    @DeleteMapping("delete")
    public void deleteIncome(int incomeId){
        incomeService.deleteIncome(incomeId);
    }

    @PutMapping("update")
    public void updateIncome(Income income){
        incomeService.updateIncome(income);
    }

    @GetMapping("query")
    public void findIncomeList(Income income){
        List<Income> incomeList=incomeService.findIncomeList(income);
        for(int i =0 ;i<incomeList.size();i++) {
            System.out.println(incomeList.get(i).toString());
        }
    }

}
