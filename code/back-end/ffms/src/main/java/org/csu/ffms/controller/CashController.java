package org.csu.ffms.controller;


import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.CashService;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/cash/")
public class CashController {
    @Autowired
    CashService cashService;
    @Autowired
    DisburseController disburseController;
    @Autowired
    Disburse disburse;
    @Autowired
    Income income;
    @Autowired
    DisburseService disburseService;
    @Autowired
    IncomeService incomeService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    //家庭或个人现金总额
    @UserLoginToken
    @RequestMapping(value="total",method = RequestMethod.GET)
    public String totalCash(String userId) {
        int out = cashService.totalDisbursement(userId);
        int out_family = cashService.totalDisbursementByFamily(userId);
        int in = cashService.totalIncome(userId);
        int in_family = cashService.totalIncomeByFamily(userId);

        int total = out + in ;
        int total_family = out_family + in_family;
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("个人现金",total);
        json.put("家庭现金",total_family);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    //按周结算家庭组的现金总额，返回时间为该周最后一天
    @UserLoginToken
    @RequestMapping(value="totalbyweek",method = RequestMethod.GET)
    public String totalByWeek(String userId) {
        int[] disburse_total = new int[7];
        int[] income_total = new int[7];
        int[] total = new int[7];
        Date[] dates = new Date[8];

        disburse.setUserId(userId);
        income.setUserId(userId);
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        dates[0] = date;
        disburse.setTime(date);
        income.setTime(date);

        for (int i = 0; i <7;i++){
            disburse_total[i] = disburseService.totalFamilyDisbursementByWeek(disburse);
            income_total[i] = incomeService.totalFamilyIncomeByWeek(income);
            total[i] = disburse_total[i] + income_total[i];

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -7);
            java.util.Date yesterday =  c.getTime();//这是昨天
            date = new java.sql.Date(yesterday.getTime());
            dates[i+1] = date;
            disburse.setTime(date);
            income.setTime(date);
        }


        JSONObject json = new JSONObject();
        json.put("status", 0);
        String[][] ret = new String[7][2];
        for(int i = 0;i<7;i++){
            ret[i][0] = dates[i].toString();
            ret[i][1] = String.valueOf(total[i]);
        }
        json.put("data",ret);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

}
