package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.persistence.DisburseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

    //单用户某天的支出总额
    public void totalDisbursement(Disburse disburse){
        disburseMapper.totalDisbursement(disburse);
    }

    //家庭组某天的支出总额
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


}
