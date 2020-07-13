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

import java.sql.Connection;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
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

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @UserLoginToken
    @RequestMapping(value="insert",method = RequestMethod.POST)
    public String newDisburse(@RequestBody Disburse disburse) {
        disburseService.newDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburse);
        return JSONObject.toJSONString(json);
    }

    //删除支出款项
    @UserLoginToken
    @RequestMapping(value="delete",method = RequestMethod.DELETE)
    public String deleteDisburse(@RequestParam("disburseid") int disburseId){
        disburseService.deleteDisburse(disburseId);
        JSONObject json = new JSONObject();
        json.put("status",0);
        return JSONObject.toJSONString(json);

    }

    //更新支出款项
    @UserLoginToken
    @RequestMapping(value="update",method = RequestMethod.PUT)
    public String updateDisburse(@RequestBody Disburse disburse){
        disburseService.updateDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburse);
        return JSONObject.toJSONString(json);
    }

    //可以通过userId，type，time对家庭支出表进行筛选，并排序
    @UserLoginToken
    @RequestMapping(value="query",method = RequestMethod.GET)
    public String findDisburseList(@RequestBody Disburse disburse){
        List<Disburse> disburseList=disburseService.findDisburseList(disburse);
        disburseService.sortByDate(disburseList);

        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburseList);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //通过userid查找该用户所在家庭组的支出列表，并排序
    @UserLoginToken
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
        json.put("status",0);
        json.put("data",familyList);
        json.put("familyName",familyName);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //统计家庭组在某天的总支出
    @UserLoginToken
    @GetMapping("total_paid")
    public String total_paid(String userid){
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        int[][] total = new int[7][4];//二维数组7行代表7天的数据，第0列表示日期，第1列表示支出，第2列表示收入，第3列表示差值

        //从当前日期起，循环七天，求每一天家庭组的支出总和
        for(int i = 0;i<7;i++) {
            disburse.setTime(date);
            disburse.setUserId(userid);
            income.setUserId(userid);
            income.setTime(date);

            total[i][2] = incomeService.totalFamilyIncome(income);
            total[i][1] = disburseService.totalFamilyDisbursement(disburse);
            total[i][3] = total[i][1]+total[i][2];
            total[i][0] = i;

            //时间类型的转换，将date的前一天赋值给date
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            java.util.Date yesterday =  c.getTime();//这是昨天
            date = new java.sql.Date(yesterday.getTime());
        }

        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",total);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }


}
