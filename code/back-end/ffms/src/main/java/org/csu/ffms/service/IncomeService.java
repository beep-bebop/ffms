package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Income;
import org.csu.ffms.persistence.DisburseMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IncomeService {
    @Autowired
    IncomeMapper incomeMapper;
    @Autowired
    DisburseMapper disburseMapper;

    //新增收入款项
    public void newIncome(Income income){
        incomeMapper.newIncome(income);
    }

    //删除收入款项
    public void deleteIncome(int incomeId){
        incomeMapper.deleteIncome(incomeId);
    }

    //更新收入款项
    public Income updateIncome(Income income){
        incomeMapper.updateIncome(income);
        return income;
    }

    //查找某用户的收入列表
    public List<Income> findIncomeList(Income income){
        return incomeMapper.findIncomeList(income);
    }

    //对收入列表依据time进行排序
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
            System.out.println("11111111111in = "+in);
        }
        return in;
    }

    //家庭组一周的收入总额，income中time为该周的最后一天
    public int totalFamilyIncomeByWeek(Income income){
        int in = 0,sum = 0;
        Date date = new Date();
        for(int i = 0;i< 7;i++){
            in = totalFamilyIncome(income);
            System.out.println("in =" + in);
            sum = sum + in;

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            java.util.Date yesterday =  c.getTime();//这是昨天
            date = new java.sql.Date(yesterday.getTime());
            income.setTime(date);
            System.out.println("第"+i+"天："+sum);
        }
        return sum;
    }

    //家庭组一周内某一类型的收入总额
    public int totalIncomeByTypeAndWeek(Income income){
        return incomeMapper.totalIncomeByTypeAndWeek(income);
    }

}
