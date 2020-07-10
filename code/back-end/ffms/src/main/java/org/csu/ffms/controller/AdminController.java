package org.csu.ffms.controller;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.domain.Admin;
import org.csu.ffms.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AdminService adminService;

    //注册新的管理员账号
    @PostMapping("newAdmin")
    public String newAdmin(Admin admin)
    {
        adminService.insertAdmin(admin);
        JSONObject json = new JSONObject();//新建一个json对象
        json.put("status_code",1);//放入键值对
        json.put("data",admin);
        System.out.println(JSONObject.toJSONString(json));//输出JSONObject对象转化成的json字符串
        return "JSONObject.toJSONString(json)";
    }

    //登录
    @PostMapping("signon")
    public String signon(String adminid,String password)
    {
        Admin admin = adminService.getAdmin(adminid,password);
        if(admin != null)  //登录成功
        {
            JSONObject json = new JSONObject();
            json.put("status_code",1);
            json.put("data",admin);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
        else  //管理员账号名或密码不正确
        {
            JSONObject json = new JSONObject();
            json.put("status_code",-2);
            System.out.println(JSONObject.toJSONString(json));
            return "JSONObject.toJSONString(json)";
        }
    }

    //退出登录
    @GetMapping("signout")
    public String signout()
    {
        JSONObject json = new JSONObject();
        json.put("status_code",1);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

    //查看用户列表
    @RequestMapping("viewAccount")
    public String viewAccount()
    {
        List<Account> list = accountService.getAllAccount();
        JSONObject json = new JSONObject();
        json.put("status_code",1);
        json.put("data",list);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }


}
