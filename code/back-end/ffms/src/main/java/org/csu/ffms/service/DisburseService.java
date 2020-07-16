package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.persistence.DisburseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DisburseService {
    @Autowired
    DisburseMapper disburseMapper;

    public void newDisburse(Disburse disburse){
        disburseMapper.newDisburse(disburse);
    }

    public void deleteDisburse(int disburseId){
        disburseMapper.deleteDisburse(disburseId);
    }

    public Disburse updateDisburse(Disburse disburse){
        disburseMapper.updateDisburse(disburse);
        return disburse;
    }

    //查找某用户的支出列表
    public List<Disburse> findDisburseListByUser(Disburse disburse){return disburseMapper.findDisburseListByUser(disburse);}

    //查找某用户家庭组的支出列表
    public List<Disburse> findDisburseList(Disburse disburse){
        return disburseMapper.findDisburseList(disburse);
    }

    //查找某用户家庭组的其他用户
    public List<String> findFamilyMember(String userId){
        return disburseMapper.findFamilyMember(userId);
    }

    //通过userId查找家庭组名称
    public String findFamily(String userId){
        return disburseMapper.findFamily(userId);
    }

    //对列表中元素按照时间排序
    public void sortByDate(List<Disburse> disburseList) {
        Collections.sort(disburseList, new Comparator<Disburse>() {
            public int compare(Disburse o1, Disburse o2) {
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

    //单用户某天的支出总额，disburse中time为这一天
    public int totalDisbursement(Disburse disburse){
        return disburseMapper.totalDisbursement(disburse);
    }

    //家庭组某天的支出总额,disburse中time为这一天
    public int totalFamilyDisbursement(Disburse disburse){
        List<String> memberList = disburseMapper.findFamilyMember(disburse.getUserId());
        int out = 0;
        Date date = disburse.getTime();
        for(int i =0;i<memberList.size();i++){
            Disburse dis = new Disburse();
            dis.setTime(date);
            dis.setUserId(memberList.get(i));
            out = out+disburseMapper.totalDisbursement(dis);
        }
       return out;
    }

    //家庭组一周的总支出，disburse中的time为这一周的最后一天
    public int totalFamilyDisbursementByWeek(Disburse disburse){
        int out1 = 0,sum = 0;
        Date date = disburse.getTime();
        for(int i = 0;i< 7;i++){
            out1 = totalFamilyDisbursement(disburse);
            System.out.println("out =" + out1);
            sum = sum + out1;

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            java.util.Date yesterday =  c.getTime();//这是昨天
            date = new java.sql.Date(yesterday.getTime());
            disburse.setTime(date);
            System.out.println("第"+i+"天："+sum);
        }
        return sum;
    }

    //家庭组一周内某一类型的总支出，disburse中的type为该类型
    public int totalDisburseByTypeAndWeek(Disburse disburse){
        return disburseMapper.totalDisburseByTypeAndWeek(disburse);
    }

    //单用户一周的支出总额
    public int totalWeekDisburse(String userid) {
        Disburse disburse = new Disburse();
        disburse.setUserId(userid);
        Calendar calendar = Calendar.getInstance();
        disburse.setTime(calendar.getTime());
        int total = totalDisbursement(disburse);
        for (int i = 0; i < 6; ++i) {
            calendar.set(Calendar.HOUR_OF_DAY, -24);
            disburse.setTime(calendar.getTime());
            total = total + totalDisbursement(disburse);
        }
        return total;
    }
}
