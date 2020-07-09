package org.csu.ffms.controller;

import org.csu.ffms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;
    
    /**
     *@描述 
     *@参数  无
     *@返回值  用户家庭组的所有股票
     *@创建人  李振豪
     *@创建时间  2020/7/8
     *@修改人和其它信息
     */
    @GetMapping("/all")
    public String getAllStock(Model model){
//        Account account= (Account)model.getAttribute("account");
//        String familyid= account.getFamilyid();
//        List<Stock> stocklist=stockService.getStockByFamilyId(familyid);
//        JSONObject json = new JSONObject();
//        json.put("status",200);
//        json.put("data",stocklist);
//        System.out.println(JSONObject.toJSONString(json));
//        return JSONObject.toJSONString(json);
        return "";
    }


}
