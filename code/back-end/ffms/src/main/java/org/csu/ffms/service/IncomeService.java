package org.csu.ffms.service;

import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Income;
import org.csu.ffms.persistence.DisburseMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeMapper incomeMapper;
    @Autowired
    DisburseMapper disburseMapper;

    public void newIncome(Income income){
        incomeMapper.newIncome(income);
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

    public void sortByDate(List<Income> incomeList) {
        Collections.sort(incomeList, new Comparator<Income>() {
            public int compare(Income o1, Income o2) {
                //按照CityModel的city_code字段进行降序排列
                if (o1.getTime().before(o2.getTime())) {
                    return -1;
                }
                if (!o1.getTime().after(o2.getTime())) {
                    return 1;
                }
                return 0;
            }
        });
    }

    //单用户某天的收入总额
    public void totalIncome(Income income){
        incomeMapper.totalIncome(income);
    }

    //家庭组某天的收入总额
    public int totalFamilyIncome(Income income){
        List<String> memberList = disburseMapper.findFamilyMember(income.getUserId());
        int in = 0;
        Date date = income.getTime();
        for(int i =0;i<memberList.size();i++){
            Income inco = new Income();
            inco.setTime(date);
            inco.setUserId(memberList.get(i));
            in = in+incomeMapper.totalIncome(inco);
        }
        return in;
    }



}
