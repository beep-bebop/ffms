package org.csu.ffms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.*;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    StockService stockService;

    @Autowired
    AccountService accountService;

    @Autowired
    DisburseService disburseService;

    @Autowired
    IncomeService incomeService;

    /**
     *@描述 返回该用户所拥有的股票的具体信息
     *@参数  userid：用户id，code：股票代码
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/stockInfo")
    public String getStock(@RequestBody Map<String,String> map){
        String userid=map.get("userid");
        String code=map.get("code");
        JSONObject jsonObject = new JSONObject();
        try{
            Stock stock = stockService.getStockByStockCode(code,userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",stock);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/stock/stockInfo]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 该user的所有股票
     *@参数  userid
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/ownStock")
    public String getAllStock(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        JSONObject jsonObject = new JSONObject();
        try{
            List<Stock> stockList =stockService.getStockByUserId(userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",stockList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/stock/ownStock] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 获取该用户家庭中的所有股票
     *@参数  familyid：家庭id
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/familyStocks")
    public String getFamilyStocks(@RequestBody Map<String,String>map){
        String familyid=map.get("familyid");

        JSONObject jsonObject = new JSONObject();
        try{
            List<Account> accountList= accountService.getAllAccountByFamilyid(familyid);
            List<Stock> stockList=new ArrayList<>();
            for (Account account : accountList) {
                System.out.println(account.getUserid());
                List<Stock> stocks = stockService.getStockByUserId(account.getUserid());
                for (Stock stock : stocks) {
                    stockList.add(stock);
                }
            }
            jsonObject.put("status_code",0);
            jsonObject.put("data",stockList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/stock/familyStocks] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 将指定股票插入表中，表示该用户新购了股票
     *@参数  stock：股票对象
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @PostMapping("/insertStock")
    public String insertStock(@RequestBody Stock stock){
        JSONObject jsonObject = new JSONObject();
        try{
            stockService.insertStock(stock);
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/stock/insertStock] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 删除指定股票，表示该用户股票的抛出
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @DeleteMapping("/deleteStock")
    public String deleteStock(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String code=map.get("code");
        JSONObject jsonObject = new JSONObject();
        try{
            stockService.deleteStock(stockService.getStockByStockCode(code, userid));
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/stock/deleteStock] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 更新股票信息，表示该用户持有股份的变化
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    //@UserLoginToken
    @PutMapping("/updateStock")
    public String updateStock(@RequestBody Map<String,String> map){
        String userid=map.get("userid");
        String code=map.get("stockid");
        int quantity = Integer.parseInt(map.get("amount"));
        BigDecimal price=new BigDecimal(map.get("price"));

        JSONObject json = new JSONObject();

        if(stockService.getStockAPIInfoByCode(code)==null){
            json.put("status_code",-2);
            json.put("msg","stock id is error");
            return JSONObject.toJSONString(json);
        }

        //查看用户之前是否已购该股票
        Stock stock=stockService.getStockByStockCode(code,userid);
        //stock为空说明用户之前没有购买该股票，不为空说明该用户之前已购该股票
        if(stock==null){
            //数量小于0，说明是卖出股票,大于0，说明是买入股票
            if(quantity<0){
                json.put("status_code",-2);
                json.put("msg","error , quantity<0 , the stock not exist in table,you can't sold it.");
            }
            else{
                //插入股票信息
                stock = new Stock();
                stock.setUserid(userid);
                stock.setCode(code);
                String name=null;
                name=(String)stockService.getStockAPIInfoByCode(code).get("name");
                stock.setName(name);
                stock.setQuantity(quantity);
                stockService.insertStock(stock);
                //新增支出信息
                Disburse disburse = new Disburse();
                disburse.setAmount_paid(price.multiply(new BigDecimal(quantity)).intValue());
                disburse.setUserId(userid);
                disburse.setTime(new Date());
                disburse.setDescription("购入股票");
                disburse.setType("股票");
                disburseService.newDisburse(disburse);
                json.put("status_code",0);
                json.put("msg","success buy stock");
            }
        }
        else{
            //数量小于0，说明是卖出股票,大于0，说明是买入股票
            if(quantity<0){
                Income income = new Income();
                income.setUserId(userid);
                income.setTime(new Date());
                income.setDescription("卖出股票");
                income.setType("股票");

                if ((stock.getQuantity()+quantity)<=0){
                    income.setIncome(price.multiply(new BigDecimal(stock.getQuantity())).floatValue());
                    stockService.deleteStock(stock);
                    json.put("msg","success sell stock,you can only sell stock quantity you have ");
                }
                else{
                    income.setIncome(price.multiply(new BigDecimal(-quantity)).floatValue());
                    stock.setQuantity(stock.getQuantity()+quantity);
                    stockService.updateStock(stock);
                    json.put("msg","success sell stock");
                }
                incomeService.newIncome(income);
                json.put("status_code",0);
            }
            else{
                stock.setQuantity(stock.getQuantity()+quantity);
                stockService.updateStock(stock);

                Disburse disburse = new Disburse();
                disburse.setAmount_paid(price.multiply(new BigDecimal(quantity)).intValue());
                disburse.setUserId(userid);
                disburse.setTime(new Date());
                disburse.setDescription("购入股票");
                disburse.setType("股票");
                disburseService.newDisburse(disburse);
                json.put("status_code",0);
                json.put("msg","success buy stock");
            }

        }
        return JSONObject.toJSONString(json);
    }

    @RequestMapping(value="/table",method=RequestMethod.POST)
    public JSONArray getStockTable(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String queryid=map.get("queryid");
        if (queryid==null || queryid.equals("")){
            Account account=accountService.getAccount(userid);
            try{
                String familyid= account.getFamilyid();
                List<Account> accountList=accountService.getAllAccountByFamilyid(familyid);
                JSONArray funds=new JSONArray();
                for (Account familyMember : accountList) {
                    JSONArray jsonArray=stockService.getStockAPIInfoByUserid(familyMember.getUserid());
                    funds.addAll(jsonArray);
                }
                JSONObject json = new JSONObject();
                return funds;
            }
            catch (Exception e){
                return  stockService.getStockAPIInfoByUserid(userid);
            }
        }
        return stockService.getStockAPIInfoByUserid(queryid);
    }


    @RequestMapping(value="/total",method=RequestMethod.POST)
    public JSONObject getTotal(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String queryid=map.get("queryid");
        BigDecimal total=new BigDecimal(0);
        JSONObject jsonObject=new JSONObject();
        if(queryid==null || queryid.equals("")){
            Account user = accountService.getAccount(userid);
            if(user.getFamilyid()!=null){
                List<Account> accountList = accountService.getAllAccountByFamilyid(user.getFamilyid());
                for (Account account : accountList) {
                    JSONArray jsonArray=stockService.getStockAPIInfoByUserid(account.getUserid());
                    for (int i = 0; i <jsonArray.size() ; i++) {
                        System.out.println("currentValue : "+jsonArray.getJSONObject(i).get("currentValue"));
                        BigDecimal currentvalue = new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString());
                        total=total.add(currentvalue);
                    }
                }
            }
            else{
                JSONArray jsonArray = stockService.getStockAPIInfoByUserid(userid);
                for (int i = 0; i < jsonArray.size(); i++) {
                    total = total.add(new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString()));
                }
            }
            jsonObject.put("type", "person stock total");
        }
        else {
            JSONArray jsonArray = stockService.getStockAPIInfoByUserid(userid);
            for (int i = 0; i < jsonArray.size(); i++) {
                total = total.add(new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString()));
            }
            jsonObject.put("type", "person stock total");
        }
        jsonObject.put("userid",userid);
        jsonObject.put("total",total);
        return jsonObject;
    }

}
