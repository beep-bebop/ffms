package org.csu.ffms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Family;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.FamilyService;
import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/family/")
public class FamilyController {
    @Autowired
    private AccountService accountService;


    @Autowired
    private FamilyService familyService;


    //创建新的家庭组
    /*    参数形式：
    {
        "account":{
            "userid":"1",
            "password":"1",
            "username":"lzh",
            "email":"1@qq.com",
            "phone":"1234567",
            "familyid":"1"
        },
        "family":{
            "familyid":"",
            "familyname":""
        },
    }

 */
    @UserLoginToken
    @RequestMapping(value="insert",method = RequestMethod.POST)
    public String newFamily(@RequestBody Map<String,JSONObject>p)
    {
        JSONObject accountJson = p.get("account");
        JSONObject familyJson = p.get("family");
        Account account=(Account) JSONObject.parseObject(JSONObject.toJSONString(accountJson),Account.class);
        Family family=(Family) JSONObject.parseObject(JSONObject.toJSONString(familyJson), Family.class);
        accountService.updateAccount(account);
        //生成密钥
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<7;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        family.setFamilykey(sb.toString());

        familyService.insertFamily(family);

        JSONObject json = new JSONObject();//新建一个json对象
        json.put("status_code",0);//放入键值对
        json.put("data",family);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return JSONObject.toJSONString(json);
    }

    //加入家庭组
 /*        参数形式：
    {
        "account":{
            "userid":"1",
            "password":"1",
            "username":"lzh",
            "email":"1@qq.com",
            "phone":"1234567",
            "familyid":"1"
        },
        "family":{
            "familyid":"123",
            "familykey":"123"
        },
    }
 */
    @UserLoginToken
    @RequestMapping(value="joinFamily",method = RequestMethod.POST)
    public String joinFamily(@RequestBody Map<String,JSONObject>p)
    {
        JSONObject accountJson = p.get("account");
        JSONObject familyJson = p.get("family");
        Account account=(Account) JSONObject.parseObject(JSONObject.toJSONString(accountJson),Account.class);
        String familyid=familyJson.getString("familyid");
        String familykey=familyJson.getString("familykey");

        Family family = familyService.getFamily(familyid,familykey);
        if(family != null)  //加入成功
        {
            account.setFamilyid(familyid);
            accountService.updateAccount(account);
            JSONObject json = new JSONObject();
            json.put("status_code",0);
            json.put("data",account);
            System.out.println(JSONObject.toJSONString(json));
            return JSONObject.toJSONString(json);
        }
        else  //家庭组id或者密钥不正确
        {
            JSONObject json = new JSONObject();
            json.put("status_code",-2);
            return JSONObject.toJSONString(json);
        }
    }


    //退出家庭组
    @UserLoginToken
    @RequestMapping(value="quitFamily",method = RequestMethod.POST)
    public String quitFamily(@RequestBody Account account)
    {
        accountService.quitFamily(account.getUserid());
        account.setFamilyid(null);
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",0);
        json.put("data",account);
        return JSONObject.toJSONString(json);
    }


}
