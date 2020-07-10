package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.input.DataFormat;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@ResponseBody
@RestController
@RequestMapping("/disbursement/")
public class DisburseController {

    @Autowired
    DisburseService disburseService;
    @Autowired
    Disburse disburse;
    @Autowired
    IncomeService incomeService;
    @Autowired
    Income income;

    //新建支出款项
    @PostMapping("new")
    public String newDisburse(Disburse disburse) {
        /*long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        disburse.setTime(date);*/
        disburseService.newDisburse(disburse);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",disburse);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //删除支出款项
    @DeleteMapping("delete")
    public String deleteDisburse(int disburseId){
        disburseService.deleteDisburse(disburseId);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //更新支出款项
    @PutMapping("update")
    public String updateDisburse(Disburse disburse){
        disburseService.updateDisburse(disburse);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",disburse);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //可以通过userId，type，time对家庭支出表进行筛选，并排序
    @GetMapping(value="query",produces = "application/Json;charset=UTF-8")
    public String findDisburseList(Disburse disburse){
        List<Disburse> disburseList=disburseService.findDisburseList(disburse);
        disburseService.sortByDate(disburseList);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",disburseList);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //通过userid查找该用户所在家庭组的支出列表，并排序
    @GetMapping(value="familyList",produces = "application/Json;charset=UTF-8")
    public String findDisburseByFamily(String userid){
        List<String> familyMemberList=disburseService.findFamilyMember(userid);
        List<Disburse> familyList = new ArrayList<>();
        String familyName = disburseService.findFamily(userid);

        for(int i=0;i<familyMemberList.size();i++){
            Disburse disburse = new Disburse();
            disburse.setUserId(familyMemberList.get(i));
            familyList=disburseService.findDisburseList(disburse);
        }
        disburseService.sortByDate(familyList);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",familyList);
        json.put("familyName",familyName);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //统计家庭组在某天的总支出
    @GetMapping("total_paid")
    public String total_paid(String userid){
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        int[] out = new int[7];
        int[] in  = new int[7];
        int[] netting = new int[7];//净额结算

        //从当前日期起，循环七天，求每一天家庭组的支出总和
        for(int i = 0;i<7;i++) {
            disburse.setTime(date);
            disburse.setUserId(userid);
            income.setUserId(userid);
            income.setTime(date);

            in[i] = incomeService.totalFamilyIncome(income);
            out[i] = disburseService.totalFamilyDisbursement(disburse);
            netting[i] = in[i]+out[i];

            //时间类型的转换，将date的前一天赋值给date
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            java.util.Date yesterday =  c.getTime();//这是昨天
            date = new java.sql.Date(yesterday.getTime());

        }

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("total_paid_out",out);
        json.put("total_paid_in",in);
        json.put("netting",netting);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }



}
