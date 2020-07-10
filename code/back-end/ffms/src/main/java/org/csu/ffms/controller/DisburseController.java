package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Disburse;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.DisburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@ResponseBody
@RestController
@RequestMapping("/disbursement/")
public class DisburseController {

    @Autowired
    DisburseService disburseService;

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
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        disburse.setTime(date);
        disburseService.newDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburse);
        return JSONObject.toJSONString(json);
    }

    @UserLoginToken
    @RequestMapping(value="delete",method = RequestMethod.DELETE)
    public String deleteDisburse(@RequestParam("disburseid") int disburseId){
        disburseService.deleteDisburse(disburseId);
        JSONObject json = new JSONObject();
        json.put("status",0);
        return JSONObject.toJSONString(json);

    }

    @UserLoginToken
    @RequestMapping(value="update",method = RequestMethod.PUT)
    public String updateDisburse(@RequestBody Disburse disburse){
        disburseService.updateDisburse(disburse);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburse);
        return JSONObject.toJSONString(json);
    }

    @UserLoginToken
    @RequestMapping(value="query",method = RequestMethod.GET)
    public String findDisburseList(@RequestBody Disburse disburse){
        List<Disburse> disburseList=disburseService.findDisburseList(disburse);
        JSONObject json = new JSONObject();
        json.put("status",0);
        json.put("data",disburseList);
        return JSONObject.toJSONString(json);
    }


}
