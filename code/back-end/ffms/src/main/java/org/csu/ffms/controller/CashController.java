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

@ResponseBody
@RestController
@RequestMapping("/cash/")
public class CashController {
    @Autowired
    CashService cashService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
/*
    @UserLoginToken*/
    @RequestMapping(value="total",method = RequestMethod.GET)
    public String totalCash(String userId) {
        System.out.println("hhhhhhhhhhhhh");
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

}
