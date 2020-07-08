package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Account;
import org.csu.ffms.jwt.note.PassToken;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.jwt.token.TokenUtil;
import org.csu.ffms.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //进入登录界面
    @GetMapping("/viewSignon")
    public String viewSignon(Model model)
    {
        //登录界面页面
        return " ";
    }

    //进入注册界面
    @GetMapping("/viewEditAccountForm")
    public String viewEditAccountForm(Model model)
    {
        //注册界面页面
        return " ";
    }

    //注册新用户
    @PostMapping("/newAccount")
    public String newAccount(Account account , Model model)
    {
        accountService.insertAccount(account);
        //注册完后进入的页面
        return " ";
    }

    //登录
    @PostMapping("/signon")
    public String signon(Account account1 , Model model)
    {
        Account account = accountService.getAccount(account1.getUserid(),account1.getPassword());
        if(account != null)
        {
            model.addAttribute("account",account);
            //登录成功后进入的页面
            return " ";
        }
        else if(account == null)
        {
            model.addAttribute("errormsg", "用户名或密码不正确");
            //返回当前页面
            return " ";
        }
        return null;
    }

    //退出登录
    @GetMapping("signout")
    public String signout(Model model)
    {
        model.addAttribute("account" , null);
        //退出登录后返回的页面
        return " ";
    }

    //登录后修改用户信息
    @PostMapping("/editAccountForm")
    public String editAccouintForm(Account account ,Model model)
    {
        accountService.updateAccount(account);
        //修改用户信息后返回的页面
        return " ";
    }

    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(String username,String password){
        System.out.println("hello,login");
        //Account account = accountService.getAccount(username,password);
        Account account = new Account();
        account.setUserid("1");
        account.setPassword("1");
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

    @UserLoginToken
    @PostMapping("/msg")
    public String hello(){
        return "hello,world";
    }


}
