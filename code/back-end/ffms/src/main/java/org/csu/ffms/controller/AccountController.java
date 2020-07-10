package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
        import org.csu.ffms.domain.Account;
import org.csu.ffms.jwt.note.PassToken;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.jwt.token.TokenUtil;
import org.csu.ffms.service.AccountService;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //注册新用户
    @PassToken
    @RequestMapping(value="newAccount",method = RequestMethod.POST)
    public String newAccount(@RequestBody Account account)
    {
        System.out.println(account.toString());
        accountService.insertAccount(account);
        JSONObject json = new JSONObject();//新建一个json对象
        json.put("status_code",0);//放入键值对
        json.put("data",account);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return JSONObject.toJSONString(json);
    }

    //登录
    @PassToken
    @RequestMapping(value="signon",method = RequestMethod.POST)
    public String signon(@RequestBody Map<String, String> map)
    {
        String userid=map.get("userid");
        String password=map.get("password");
        Account account = accountService.getAccount(userid,password);
        JSONObject json = new JSONObject();
        if(account != null)  //登录成功
        {
            json.put("status_code",0);
            json.put("data",account);
            String token = TokenUtil.getToken(account);
            json.put("token",token);
        }
        else  //用户名或密码不正确
        {
            json.put("status_code",-2);
        }
        return JSONObject.toJSONString(json);

    }

    //退出登录
    @UserLoginToken
    @GetMapping("signout")
    public String signout()
    {
        JSONObject json = new JSONObject();
        json.put("status_code",0);
        return JSONObject.toJSONString(json);
    }

    //登录后修改用户信息
    @UserLoginToken
    @RequestMapping(value="editAccountForm",method = RequestMethod.POST)
    public String editAccouintForm(@RequestBody Account account)
    {
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",0);
        json.put("data",account);
        return JSONObject.toJSONString(json);
    }


}
