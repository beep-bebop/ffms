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

@RestController
@RequestMapping("/account/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //注册新用户
    @PassToken
    @PostMapping("newAccount")
    public String newAccount(Account account)
    {
        accountService.insertAccount(account);
        JSONObject json = new JSONObject();//新建一个json对象
        json.put("status_code",0);//放入键值对
        json.put("data",account);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return JSONObject.toJSONString(json);
    }

    //登录
    @PassToken
    @PostMapping("signon")
    public String signon(String userid,String password)
    {
        Account account = accountService.getAccount(userid,password);
        JSONObject json = new JSONObject();
        if(account != null)  //登录成功
        {
            json.put("status_code",0);
            json.put("data",account);
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
    @PostMapping("editAccountForm")
    public String editAccouintForm(Account account)
    {
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",0);
        json.put("data",account);
        return JSONObject.toJSONString(json);
    }


    @PassToken
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(String username,String password){
        System.out.println("hello,login");
        Account account = accountService.getAccount(username,password);
        JSONObject json = new JSONObject();

        if(account==null){
            json.put("code",0);
            json.put("msg","用户名或密码错误");
        }
        else{
            json.put("code",1);
            String token = TokenUtil.getToken(account);
            json.put("token",token);
        }
        return json;
    }


}
