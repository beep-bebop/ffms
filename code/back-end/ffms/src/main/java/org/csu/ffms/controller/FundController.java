package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.Fund;
import org.csu.ffms.domain.Security;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/fund")
public class FundController {
    @Autowired
    FundService fundService;

    @Autowired
    AccountService accountService;

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
    @UserLoginToken
    @RequestMapping(value="/updateFund",method = RequestMethod.PUT)
    public String updateFund(@RequestBody Fund fund){
        JSONObject jsonObject = new JSONObject();
        try{
            fundService.updateFund(fund);
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) + " 错误--[/fund/updateFund]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

}
