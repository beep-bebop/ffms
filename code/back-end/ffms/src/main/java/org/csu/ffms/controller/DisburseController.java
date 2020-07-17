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

@CrossOrigin
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
        List<Disburse> disburseList=disburseService.findDisburseListByUser(disburse);
        disburseService.sortByDate(disburseList);

        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburseList);
        System.out.println(JSONObject.toJSONString(json));

        return JSONObject.toJSONString(json);
    }

    //通过userid查找该用户所在家庭组的支出列表，并排序
    //@UserLoginToken
    @GetMapping(value="familyList",produces = "application/Json;charset=UTF-8")
    public String findDisburseByFamily(String userid){
        List<Disburse> familyList = new ArrayList<>();
        String familyName = disburseService.findFamily(userid);
        disburse.setUserId(userid);
        familyList = disburseService.findDisburseList(disburse);

        /*for(int i=0;i<familyMemberList.size();i++){
            Disburse disburse = new Disburse();
            disburse.setUserId(familyMemberList.get(i));
            familyList=disburseService.findDisburseList(disburse);
        }*/
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

    //查询各个类型的总支出，类型有“餐饮”，“游玩”，“逛街”，“学习”，“其他”
    @UserLoginToken
    @RequestMapping(value="type",method = RequestMethod.GET)
    public String totalDisburseByTypeAndWeek(String userId){
        List<Disburse> disburseList = new ArrayList<>();
        Disburse disburse1 = new Disburse();
        Disburse disburse2 = new Disburse();
        Disburse disburse3 = new Disburse();
        Disburse disburse4 = new Disburse();
        Disburse disburse5 = new Disburse();

        disburse1.setUserId(userId);
        disburse1.setType("餐饮");
        int out1 = disburseService.totalDisburseByTypeAndWeek(disburse1);
        disburse1.setAmount_paid(out1);
        disburseList.add(disburse1);

        disburse2.setUserId(userId);
        disburse2.setType("逛街");
        int out2 = disburseService.totalDisburseByTypeAndWeek(disburse2);
        disburse2.setAmount_paid(out2);
        disburseList.add(disburse2);

        disburse3.setUserId(userId);
        disburse3.setType("游玩");
        int out3 = disburseService.totalDisburseByTypeAndWeek(disburse3);
        disburse3.setAmount_paid(out3);
        disburseList.add(disburse3);

        disburse4.setUserId(userId);
        disburse4.setType("学习");
        int out4 = disburseService.totalDisburseByTypeAndWeek(disburse4);
        disburse4.setAmount_paid(out4);
        disburseList.add(disburse4);

        disburse5.setUserId(userId);
        disburse5.setType("其他");
        int out5 = disburseService.totalDisburseByTypeAndWeek(disburse5);
        disburse5.setAmount_paid(out5);
        disburseList.add(disburse5);

        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburseList);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }


}
