package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.domain.Income;
import org.csu.ffms.domain.Income;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/income/")
public class IncomeController {
    @Autowired
    IncomeService incomeService;
    @Autowired
    DisburseService disburseService;

    @PostMapping("new")
    public String newIncome(Income income) {
        incomeService.newIncome(income);
        
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",income);
        System.out.println(JSONObject.toJSONString(json));
        
        return JSONObject.toJSONString(json);
    }

    @DeleteMapping("delete")
    public String deleteIncome(int incomeId){
        incomeService.deleteIncome(incomeId);
       
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        System.out.println(JSONObject.toJSONString(json));
        
        return JSONObject.toJSONString(json);
    }

    @PutMapping("update")
    public String updateIncome(Income income){

        incomeService.updateIncome(income);
       
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",income);
        System.out.println(JSONObject.toJSONString(json));
       
        return JSONObject.toJSONString(json);
    }

    @GetMapping("query")
    public String findIncomeList(Income income){
        List<Income> incomeList=incomeService.findIncomeList(income);
        incomeService.sortByDate(incomeList);
        
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",incomeList);
        System.out.println(JSONObject.toJSONString(json));
       
        return JSONObject.toJSONString(json);
    }

    @GetMapping(value="familyList",produces = "application/Json;charset=UTF-8")
    public String findIncomeByFamily(String userid){
        List<String> familyMemberList=disburseService.findFamilyMember(userid);
        List<Income> familyList = new ArrayList<>();
        String familyName = disburseService.findFamily(userid);

        for(int i=0;i<familyMemberList.size();i++){
            Income income = new Income();
            income.setUserId(familyMemberList.get(i));
            familyList=incomeService.findIncomeList(income);
        }
        incomeService.sortByDate(familyList);

        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",familyList);
        json.put("familyName",familyName);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

}
