package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/income/")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @UserLoginToken
    @PostMapping("new")
    public String newIncome(Income income) {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        income.setTime(date);
        incomeService.newIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",income);
        return JSONObject.toJSONString(json);
    }

    @UserLoginToken
    @DeleteMapping("delete")
    public String deleteIncome(int incomeId){
        incomeService.deleteIncome(incomeId);
        JSONObject json = new JSONObject();
        json.put("status",0);
        return JSONObject.toJSONString(json);
    }

    @UserLoginToken
    @PutMapping("update")
    public String updateIncome(Income income){
        incomeService.updateIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",income);
        return JSONObject.toJSONString(json);
    }

    @UserLoginToken
    @GetMapping("query")
    public String findIncomeList(Income income){
        List<Income> incomeList=incomeService.findIncomeList(income);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",incomeList);
        return JSONObject.toJSONString(json);
    }

}
