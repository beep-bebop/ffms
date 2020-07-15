package org.csu.ffms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.*;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/9
 * @描述
 **/
@CrossOrigin
@RestController
@RequestMapping("/fund")
public class FundController {
    @Autowired
    FundService fundService;

    @Autowired
    AccountService accountService;
    
    @Autowired
    FamilyService familyService;

    @Autowired
    DisburseService disburseService;

    @Autowired
    IncomeService incomeService;

    /**
     *@描述 返回该用户所拥有的基金的具体信息
     *@参数  userid：用户id，fundcode：基金代码
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @RequestMapping(value="fundInfo",method = RequestMethod.GET)
    public String getFund(@RequestBody Map<String,String> map){
        String fundcode = map.get("fundcode");
        String userid = map.get("userid");
        JSONObject jsonObject = new JSONObject();
        try{
            Fund fund = fundService.getFundByFundCode(fundcode,userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",fund);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/fund/fundInfo]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 该user的所有基金
     *@参数  userid
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @RequestMapping(value="/ownFund",method = RequestMethod.GET)
    public String getAllFund(@RequestBody Map<String,String> map){
        String userid=map.get("userid");
        JSONObject jsonObject = new JSONObject();
        try{
            List<Fund> fundList =fundService.getFundByUserId(userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",fundList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/fund/ownFund] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 获取该用户家庭中的所有基金
     *@参数  familyid：家庭id
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @RequestMapping(value="/familyFunds",method = RequestMethod.GET)
    public String getFamilyFunds(@RequestBody Map<String,String> map){
        String familyid=map.get("familyid");
        JSONObject jsonObject = new JSONObject();
        try{
            List<Account> accountList= accountService.getAllAccountByFamilyid(familyid);
            List<Fund> fundList=new ArrayList<>();
            for (Account account : accountList) {
                List<Fund> funds = fundService.getFundByUserId(account.getUserid());
                for (Fund fund : funds) {
                    fundList.add(fund);
                }
            }
            jsonObject.put("status_code",0);
            jsonObject.put("data",fundList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/fund/familyFunds] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 将指定基金插入表中，表示该用户新购了基金
     *@参数  fund：基金对象
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @RequestMapping(value="/insertFund",method = RequestMethod.POST)
    public String insertFund(@RequestBody Fund fund){
        JSONObject jsonObject = new JSONObject();
        try{
            fundService.insertFund(fund);
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/fund/insertFund] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 删除指定基金，表示该用户基金的抛出
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @RequestMapping(value="/deleteFund",method = RequestMethod.DELETE)
    public String deleteFund(@RequestBody Map<String,String>map){
        String userid = map.get("userid");
        String fundcode=map.get("fundcode");
        JSONObject jsonObject = new JSONObject();
        try{
            fundService.deleteFund(fundService.getFundByFundCode(fundcode, userid));
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/fund/deleteFund] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 更新基金信息，表示该用户持有基金的变化
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    //@UserLoginToken
    @RequestMapping(value="/updateFund",method = RequestMethod.PUT)
    public String updateFund(@RequestBody Map<String,String> map) {
        String userid = map.get("userid");
        String code = map.get("fundid");
        int quantity = Integer.parseInt(map.get("quantity"));
        BigDecimal price = new BigDecimal(map.get("price"));

        JSONObject json = new JSONObject();

        if(fundService.getFundAPIInfoByCode(code)==null){
            json.put("status_code",-2);
            json.put("msg","stock id is error");
            return JSONObject.toJSONString(json);
        }

        //查看用户之前是否已购该基金
        Fund fund = fundService.getFundByFundCode(code, userid);
        //stock为空说明用户之前没有购买该基金，不为空说明该用户之前已购该基金
        if (fund == null) {
            //数量小于0，说明是卖出基金,大于0，说明是买入基金
            if (quantity < 0) {
                json.put("status_code", -2);
                json.put("msg", "error , quantity<0 , the fund not exist in table,you can't sold it.");
            } else {
                //插入股票信息
                fund = new Fund();
                fund.setUserid(userid);
                fund.setCode(code);
                String name=null;
                name=(String) fundService.getFundAPIInfoByCode(code).get("name");
                fund.setName(name);
                fund.setQuantity(quantity);
                fundService.insertFund(fund);
                //新增支出信息
                Disburse disburse = new Disburse();
                disburse.setAmount_paid(price.multiply(new BigDecimal(quantity)).intValue());
                disburse.setUserId(userid);
                disburse.setTime(new Date());
                disburse.setDescription("购入基金");
                disburse.setType("基金");
                disburseService.newDisburse(disburse);
                json.put("status_code", 0);
                json.put("msg", "success buy fund");
            }
        } else {
            //数量小于0，说明是卖出基金大于0，说明是买入基金
            if (quantity < 0) {
                Income income = new Income();
                income.setUserId(userid);
                income.setTime(new Date());
                income.setDescription("卖出基金");
                income.setType("基金");

                if ((fund.getQuantity() + quantity) <= 0) {
                    income.setIncome(price.multiply(new BigDecimal(fund.getQuantity())).floatValue());
                    fundService.deleteFund(fund);
                    json.put("msg", "success sell fund,you can only sell fund quantity you have ");
                } else {
                    income.setIncome(price.multiply(new BigDecimal(-quantity)).floatValue());
                    fund.setQuantity(fund.getQuantity() + quantity);
                    fundService.updateFund(fund);
                    json.put("msg", "success sell fund");
                }
                incomeService.newIncome(income);
                json.put("status_code", 0);
            } else {
                fund.setQuantity(fund.getQuantity() + quantity);
                fundService.updateFund(fund);

                Disburse disburse = new Disburse();
                disburse.setAmount_paid(price.multiply(new BigDecimal(quantity)).intValue());
                disburse.setUserId(userid);
                disburse.setTime(new Date());
                disburse.setDescription("购入基金");
                disburse.setType("基金");
                disburseService.newDisburse(disburse);
                json.put("status_code", 0);
                json.put("msg", "success buy fund");
            }

        }
        return JSONObject.toJSONString(json);
    }
    @RequestMapping(value="/table",method=RequestMethod.GET)
    public JSONArray getFundTable(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String queryid=map.get("queryid");
        if (queryid==null || queryid.equals("")){
            Account account=accountService.getAccount(userid);
            try{
                String familyid= account.getFamilyid();
                List<Account> accountList=accountService.getAllAccountByFamilyid(familyid);
                JSONArray funds=new JSONArray();
                for (Account familyMember : accountList) {
                    JSONArray jsonArray=fundService.getFundAPIInfoByUserid(familyMember.getUserid());
                    funds.addAll(jsonArray);
                }
                JSONObject json = new JSONObject();
                return funds;
            }
            catch (Exception e){
                return  fundService.getFundAPIInfoByUserid(userid);
            }
        }
        return fundService.getFundAPIInfoByUserid(queryid);
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
                    JSONArray jsonArray=fundService.getFundAPIInfoByUserid(account.getUserid());
                    for (int i = 0; i <jsonArray.size() ; i++) {
                        System.out.println("currentValue : "+jsonArray.getJSONObject(i).get("currentValue"));
                        BigDecimal currentvalue = new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString());
                        total=total.add(currentvalue);
                    }
                }
            }
            else{
                JSONArray jsonArray=fundService.getFundAPIInfoByUserid(userid);
                for (int i = 0; i <jsonArray.size() ; i++) {
                    total=total.add(new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString()));
                }
            }
            jsonObject.put("type","family fund total");
        }
        else{
            JSONArray jsonArray=fundService.getFundAPIInfoByUserid(userid);
            for (int i = 0; i <jsonArray.size() ; i++) {
                total=total.add(new BigDecimal(jsonArray.getJSONObject(i).get("currentValue").toString()));
            }
            jsonObject.put("type","person fund total");
        }
        jsonObject.put("userid",userid);
        jsonObject.put("total",total);
        return jsonObject;
    }

}
