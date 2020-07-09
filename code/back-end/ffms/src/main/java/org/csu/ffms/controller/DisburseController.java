package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.service.DisburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ResponseBody
@RestController
@RequestMapping("/disbursement/")
public class DisburseController {

    @Autowired
    DisburseService disburseService;

    @PostMapping("new")
    public String newDisburse(Disburse disburse) {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        disburse.setTime(date);
        disburseService.newDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",disburse);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    @DeleteMapping("delete")
    public String deleteDisburse(int disburseId){
        disburseService.deleteDisburse(disburseId);
        JSONObject json = new JSONObject();
        json.put("status",200);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);

    }

    @PutMapping("update")
    public String updateDisburse(Disburse disburse){
        disburseService.updateDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",disburse);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }

    @GetMapping(value="query",produces = "application/Json;charset=UTF-8")
    public String findDisburseList(Disburse disburse){
        List<Disburse> disburseList=disburseService.findDisburseList(disburse);
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("data",disburseList);
        System.out.println(JSONObject.toJSONString(json));
        return JSONObject.toJSONString(json);
    }


}
