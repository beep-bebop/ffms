package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Family;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.FamilyService;
import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
@RequestMapping("/family/")
public class FamilyController {
    @Autowired
    private AccountService accountService;


    @Autowired
    private FamilyService familyService;

    //创建新的家庭组
    @UserLoginToken
    @PostMapping("newFamily")
    public String newFamily(Account account,Family family)
    {
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
        json.put("status_code",200);//放入键值对
        json.put("data",family);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return "JSONObject.toJSONString(json)";
    }

    //加入家庭组
    @UserLoginToken
    @PostMapping("joinFamily")
    public String joinFamily(Account account,String familyid,String familykey)
    {

        Family family = familyService.getFamily(familyid,familykey);
        if(family != null)  //加入成功
        {
            account.setFamilyid(familyid);
            accountService.updateAccount(account);
            JSONObject json = new JSONObject();
            json.put("status_code",200);
            json.put("data",account);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
        else  //家庭组id或者密钥不正确
        {
            JSONObject json = new JSONObject();
            json.put("status_code",403);
            json.put("data",account);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
    }


    //退出家庭组
    @UserLoginToken
    @PostMapping("quitFamily")
    public String quitFamily(Account account)
    {
        accountService.quitFamily(account.getUserid());
        account.setFamilyid(null);
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",account);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

    //返回四个资产部分的类型和总额
    @UserLoginToken
    @GetMapping("searchFamily")
    public String searchFamily(String userid,String searchId)
    {
        JSONObject json = new JSONObject();
        json.put("status_code",1);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

    //家庭总资产折线图



    //债券api接口



}
