package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Income;
import org.csu.ffms.persistence.DisburseMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.csu.ffms.persistence.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.text.DateFormat;
import java.text.SimpleDateFormat;
=======
>>>>>>> daed8738c68f319566be5a20d7da9926468ce990
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

<<<<<<< HEAD
    //单用户某天的收入总额
=======
    //单用户某天的收入总额，income中time为这一天
>>>>>>> daed8738c68f319566be5a20d7da9926468ce990
    public int totalIncome(Income income){
        return incomeMapper.totalIncome(income);
    }

    //家庭组某天的收入总额，income中time为这一天
    public int totalFamilyIncome(Income income){
        List<String> memberList = disburseMapper.findFamilyMember(income.getUserId());
        int in = 0;
        Date date = income.getTime();
        for(int i =0;i<memberList.size();i++) {
            Income inco = new Income();
            inco.setTime(date);
            inco.setUserId(memberList.get(i));
            in = in + incomeMapper.totalIncome(inco);
        }
        return in;
    }

<<<<<<< HEAD
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
=======
    //家庭组一周的收入总额，income中time为该周的最后一天
    public int totalFamilyIncomeByWeek(Income income){
        int in = 0,sum = 0;
        Date date = income.getTime();
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
>>>>>>> daed8738c68f319566be5a20d7da9926468ce990
    }

}
