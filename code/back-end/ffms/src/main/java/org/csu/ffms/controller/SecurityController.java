package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.Security;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    SecurityService securityService;

    @Autowired
    AccountService accountService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /**
     *@描述 返回该用户所拥有的证券的具体信息
     *@参数  userid：用户id，securitycode：基金代码
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/securityInfo")
    public String getSecurity(@RequestBody Map<String,String>map){
        String number=map.get("number");
        String userid=map.get("userid");
        JSONObject jsonObject = new JSONObject();
        try{
            Security security = securityService.getSecurityBySecurityNumber(number,userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",security);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/security/securityInfo]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 该user的所有证券
     *@参数  userid
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/ownSecurity")
    public String getAllSecurity(@RequestBody Map<String,String>map){
        String userid = map.get("userid");
        JSONObject jsonObject = new JSONObject();
        try{
            List<Security> securityList =securityService.getSecurityByUserId(userid);
            jsonObject.put("status_code",0);
            jsonObject.put("data",securityList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/security/ownSecurity] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 获取该用户家庭中的所有证券
     *@参数  familyid：家庭id
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @GetMapping("/familySecuritys")
    public String getFamilySecuritys(@RequestBody Map<String,String>map){
        String familyid=map.get("familyid");
        JSONObject jsonObject = new JSONObject();
        try{
            List<Account> accountList= accountService.getAllAccountByFamilyid(familyid);
            List<Security> securityList=new ArrayList<>();
            for (Account account : accountList) {
                List<Security> securitys = securityService.getSecurityByUserId(account.getUserid());
                for (Security security : securitys) {
                    securityList.add(security);
                }
            }
            jsonObject.put("status_code",0);
            jsonObject.put("data",securityList);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/security/familySecuritys] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 将指定证券插入表中，表示该用户新购了证券
     *@参数  security：证券对象
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @PostMapping("/insertSecurity")
    public String insertSecurity(@RequestBody Security security){
        JSONObject jsonObject = new JSONObject();
        try{
            securityService.insertSecurity(security);
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/security/insertSecurity] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 删除指定证券，表示该用户出售证券
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @DeleteMapping("/deleteSecurity")
    public String deleteSecurity(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String number=map.get("number");
        JSONObject jsonObject = new JSONObject();
        try{
            securityService.deleteSecurity(securityService.getSecurityBySecurityNumber(number, userid));
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) +  " 错误--[/security/deleteSecurity] ");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    /**
     *@描述 更新证券信息，表示该用户持有证券的变化
     *@参数  同上
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    @UserLoginToken
    @PutMapping("/updateSecurity")
    public String updateSecurity(@RequestBody Security security){
        JSONObject jsonObject = new JSONObject();
        try{
            securityService.updateSecurity(security);
            jsonObject.put("status_code",0);
        }
        catch (Exception e){
            System.out.println(new SimpleDateFormat().format(new Date()) + " 错误--[/security/updateSecurity]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    @UserLoginToken
    @PostMapping("/time")
    public String getSecurityByTime(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String starttime=map.get("starttime");
        String endtime=map.get("endtime");
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
             simpleDateFormat.parse(starttime);
             simpleDateFormat.parse(endtime);
             jsonObject.put("status_code",0);
             List<Security> securitieList = securityService.getSecurityByTime(userid,starttime,endtime);
             jsonObject.put("data",securitieList);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(new SimpleDateFormat().format(new Date()) + " 错误--[/security/time]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }

    @UserLoginToken
    @PostMapping("/type")
    public String getSecurityByType(@RequestBody Map<String,String>map){
        String userid=map.get("userid");
        String type=map.get("type");
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            jsonObject.put("status_code",0);
            List<Security> securitieList = securityService.getSecurityByType(userid,type);
            jsonObject.put("data",securitieList);
        } catch (Exception e) {
            System.out.println(new SimpleDateFormat().format(new Date()) + " 错误--[/security/type]");
            jsonObject.put("status_code",-2);
        }
        return JSONObject.toJSONString(jsonObject);
    }


}
