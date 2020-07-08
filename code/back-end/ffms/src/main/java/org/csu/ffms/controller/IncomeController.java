package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income/")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @PostMapping("new")
    public String newIncome(Income income) {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        income.setTime(date);
        incomeService.newIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",income);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    @DeleteMapping("delete")
    public String deleteIncome(int incomeId){
        incomeService.deleteIncome(incomeId);
        JSONObject json = new JSONObject();
        json.put("status",200);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    @PutMapping("update")
    public String updateIncome(Income income){
        incomeService.updateIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",income);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    @GetMapping("query")
    public String findIncomeList(Income income){
        List<Income> incomeList=incomeService.findIncomeList(income);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",incomeList);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

}
