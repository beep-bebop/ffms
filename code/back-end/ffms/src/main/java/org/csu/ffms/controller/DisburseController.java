package org.csu.ffms.controller;

import org.csu.ffms.domain.AppResult;
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
    public AppResult<Disburse> newDisburse(Disburse disburse) {
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        disburse.setTime(date);
        AppResult<Disburse> appResult = new AppResult<>();
        disburseService.newDisburse(disburse);
        appResult.setStatus(200);
        appResult.setData(disburse);
        return appResult;
    }

    @DeleteMapping("delete")
    public AppResult<String> deleteDisburse(int disburseId){
        disburseService.deleteDisburse(disburseId);
        AppResult<String> appResult = new AppResult<>();
        appResult.setStatus(200);
        appResult.setData("true");
        return appResult;

    }

    @PutMapping("update")
    public AppResult<Disburse> updateDisburse(Disburse disburse){
        disburseService.updateDisburse(disburse);
        AppResult<Disburse> appResult = new AppResult<>();
        appResult.setStatus(200);
        appResult.setData(disburse);
        return appResult;
    }

    @GetMapping(value="query",produces = "application/Json;charset=UTF-8")
    public AppResult<List<Disburse>> findDisburseList(Disburse disburse){
        AppResult<List<Disburse>> appResult = new AppResult<>();
        List<Disburse> disburseList=disburseService.findDisburseList(disburse);
        appResult.setStatus(200);
        appResult.setData(disburseList);
        return appResult;
    }


}
