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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/income/")
public class IncomeController {
    @Autowired
    IncomeService incomeService;
    @Autowired
    DisburseService disburseService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    //新增收入款项
    @UserLoginToken
    @RequestMapping(value="new",method = RequestMethod.POST)
    public String newIncome(@RequestBody Income income) {
        incomeService.newIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",income);
        return JSONObject.toJSONString(json);
    }

    //删除收入款项
    @UserLoginToken
    @DeleteMapping("delete")
    public String deleteIncome(@RequestBody Map<String,String>map){

        int incomeId=Integer.parseInt(map.get("incomeId"));
        incomeService.deleteIncome(incomeId);
        JSONObject json = new JSONObject();
        json.put("status",0);
        return JSONObject.toJSONString(json);
    }

    //更新收入款项
    @UserLoginToken
    @PutMapping("update")
    public String updateIncome(@RequestBody Income income){
        incomeService.updateIncome(income);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",income);
        return JSONObject.toJSONString(json);
    }

    //可根据userId，date，type属性筛选收入列表，并排序
    @UserLoginToken
    @GetMapping("query")
    public String findIncomeList(@RequestBody Income income){
        List<Income> incomeList=incomeService.findIncomeList(income);
        JSONObject json = new JSONObject();
        json.put("status", 0);
        json.put("data", incomeList);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //通过userId查询改家庭组的所有收入列表，并排序
    @UserLoginToken
    @GetMapping(value = "familyList", produces = "application/Json;charset=UTF-8")
    public String findIncomeByFamily(String userid) {
        List<String> familyMemberList = disburseService.findFamilyMember(userid);
        List<Income> familyList = new ArrayList<>();
        String familyName = disburseService.findFamily(userid);

        for (int i = 0; i < familyMemberList.size(); i++) {
            Income income = new Income();
            income.setUserId(familyMemberList.get(i));
            familyList = incomeService.findIncomeList(income);
        }
        incomeService.sortByDate(familyList);

        JSONObject json = new JSONObject();
        json.put("data", familyList);
        json.put("familyName", familyName);
        json.put("status", 0);
        return JSONObject.toJSONString(json);
    }

    //查询各个类型的总收入，类型有“工资”，“其他”
    @UserLoginToken
    @RequestMapping(value="type",method = RequestMethod.GET)
    public String totalIncomeByTypeAndWeek(String userId){
        List<Income> incomeList = new ArrayList<>();
        Income income1 = new Income();
        Income income2 = new Income();

        income1.setUserId(userId);
        income1.setType("工资");
        int out1 = incomeService.totalIncomeByTypeAndWeek(income1);
        income1.setIncome(out1);
        incomeList.add(income1);

        income2.setUserId(userId);
        income2.setType("其他");
        int out2 = incomeService.totalIncomeByTypeAndWeek(income2);
        income2.setIncome(out2);
        incomeList.add(income2);

        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",incomeList);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

}
