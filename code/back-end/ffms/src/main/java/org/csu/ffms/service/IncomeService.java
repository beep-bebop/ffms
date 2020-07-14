package org.csu.ffms.service;

import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Income;
import org.csu.ffms.persistence.DisburseMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public int totalIncome(Income income){
        return incomeMapper.totalIncome(income);
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

    //单用户本周的收入总额
    public int totalWeekIncome(String userid){
        Income income=new Income();
        income.setUserId(userid);
//        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        income.setTime(calendar.getTime());
        int total = totalIncome(income);
        for (int i=0 ; i<6 ; ++i){
            calendar.set(Calendar.HOUR_OF_DAY,-24);
            income.setTime(calendar.getTime());
            total=total+totalIncome(income);
        }
        return total;
    }

}
