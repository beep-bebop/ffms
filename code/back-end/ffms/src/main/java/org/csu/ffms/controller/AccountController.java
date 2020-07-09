package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
        import org.csu.ffms.domain.Account;
        import org.csu.ffms.service.AccountService;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //注册新用户
    @PostMapping("newAccount")
    public String newAccount(Account account)
    {
        accountService.insertAccount(account);
        JSONObject json = new JSONObject();//新建一个json对象
        json.put("status_code",200);//放入键值对
        json.put("data",account);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return "JSONObject.toJSONString(json)";
    }

    //登录
    @PostMapping("signon")
    public String signon(String userid,String password)
    {
        Account account = accountService.getAccount(userid,password);
        if(account != null)  //登录成功
        {
            JSONObject json = new JSONObject();
            json.put("status_code",200);
            json.put("data",account);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
        else  //用户名或密码不正确
        {
            JSONObject json = new JSONObject();
            json.put("status_code",403);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
    }

    //退出登录
    @GetMapping("signout")
    public String signout()
    {
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

    //登录后修改用户信息
    @PostMapping("editAccountForm")
    public String editAccouintForm(Account account)
    {
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",200);
        json.put("data",account);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
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
