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

    //家庭一周内的现金总额
    @UserLoginToken
    @RequestMapping(value="totalbyweek",method = RequestMethod.GET)
    public String totalByWeek(String userId) {
        disburse.setUserId(userId);
        income.setUserId(userId);
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        disburse.setTime(date);
        income.setTime(date);
        int disburse_total = disburseService.totalFamilyDisbursementByWeek(disburse);
        int income_total = incomeService.totalFamilyIncomeByWeek(income);
        int total = disburse_total + income_total;

        JSONObject json = new JSONObject();
        json.put("status", 0);
        json.put("现金总额",total);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

}
