package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.persistence.DisburseMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeMapper incomeMapper;

    public void newIncome(Income income){
        System.out.println("111111");
        incomeMapper.newIncome(income);
        System.out.println("222222");
    }

    public void deleteIncome(int incomeId){
        incomeMapper.deleteIncome(incomeId);
    }

    public Income updateIncome(Income income){
        incomeMapper.updateIncome(income);
        return income;
    }

    public List<Income> findIncomeList(Income income){
        return incomeMapper.findIncomeList(income);
    }


}
