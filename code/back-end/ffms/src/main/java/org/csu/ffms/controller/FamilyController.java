package org.csu.ffms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.*;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.FamilyService;
import org.csu.ffms.service.AccountService;
import org.csu.ffms.service.StockService;
import org.csu.ffms.service.FundService;
import org.csu.ffms.service.SecurityService;
import org.csu.ffms.service.DisburseService;
import org.csu.ffms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/family/")
public class FamilyController {

    @Autowired
    private AccountService accountService;


    @Autowired
    private FamilyService familyService;

    @Autowired
    private StockService stockService;

    @Autowired
    private FundService fundService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DisburseService disburseService;

    @Autowired
    private IncomeService incomeService;
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
    //查询用户现有家庭组
    @UserLoginToken
    @RequestMapping(value="findFamily",method = RequestMethod.POST)
    public String findFamily(@RequestBody Map<String, String> map)
    {
        String userid=map.get("userid");
        Account account = accountService.getAccount(userid);
        if(account.getFamilyid() != null)  //返回用户现有家庭组
        {
           String familyid = account.getFamilyid();
           String familykey=familyService.getFamily(familyid).getFamilykey();
            JSONObject json = new JSONObject();
            json.put("status_code",0);
            json.put("data",new String[]{familyid,familykey});
            System.out.println(JSONObject.toJSONString(json));
            return JSONObject.toJSONString(json);
        }
        else  //用户现在没有家庭组
        {
            JSONObject json = new JSONObject();
            json.put("status_code",-2);
            return JSONObject.toJSONString(json);
        }
    }



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
    public String joinFamily(@RequestBody Map<String,String> map)
    {
        String userid =  map.get("userid");
        String familyid= map.get("familyid");
        String familykey= map.get("familykey");
        Account account= accountService.getAccount(userid);


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
    public String quitFamily(@RequestBody Map<String, String> map)
    {
        String userid =map.get("userid");
        Account account = accountService.getAccount(userid);
        account.setFamilyid(null);
        accountService.updateAccount(account);
        JSONObject json = new JSONObject();
        json.put("status_code",0);
        json.put("data",account);
        return JSONObject.toJSONString(json);
    }

    //返回四个资产部分的类型和总额
    @UserLoginToken
    @RequestMapping(value="searchFamily",method = RequestMethod.POST)
    public String searchFamily(@RequestBody Map<String, String> map)
    {
        String userid ="1";
        String searchId = "2";
        BigDecimal stockQuantity=new BigDecimal(0);
        BigDecimal OnestockQuantity=new BigDecimal(0);
        BigDecimal fundQuantity=new BigDecimal(0);
        BigDecimal OnefundQuantity=new BigDecimal(0);
        int moneyQuantity = 0;
        int OnemoneyQuantity = 0;
        Account account = accountService.getAccount(userid);
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());
        System.out.println(account.getFamilyid());
        String familyid = account.getFamilyid();
        List<Account> List = accountService.getAllAccountByFamilyid(familyid);
        for(int i=0;i<List.size();i++)
        {
            JSONArray jsonArray1=stockService.getStockAPIInfoByUserid(account.getUserid());
            for (int a = 0; a <jsonArray1.size() ; a++) {
                System.out.println("currentValue : "+jsonArray1.getJSONObject(a).get("currentValue"));
                BigDecimal currentvalue = new BigDecimal(jsonArray1.getJSONObject(a).get("currentValue").toString());
                stockQuantity=stockQuantity.add(currentvalue);
                if(List.get(i).getUserid().equals(searchId))
                {
                    OnestockQuantity=OnestockQuantity.add(currentvalue);
                }
            }


//计算基金
            JSONArray jsonArray2=fundService.getFundAPIInfoByUserid(List.get(i).getUserid());
            for (int b = 0; b <jsonArray2.size() ; b++)
            {
                System.out.println("currentValue : "+jsonArray2.getJSONObject(b).get("currentValue"));
                BigDecimal currentvalue = new BigDecimal(jsonArray2.getJSONObject(b).get("currentValue").toString());
                fundQuantity=fundQuantity.add(currentvalue);
                if(List.get(i).getUserid().equals(searchId))
                {
                    OnefundQuantity=OnefundQuantity.add(currentvalue);
                }
            }

            Income income = new Income();
            income.setUserId(List.get(i).getUserid());
            List<Income> List3 = incomeService.findIncomeList(income);
            System.out.println("收入有多少条："+List3.size());
            for(int c=0;c<List3.size();c++)
            {
                if(List3.get(c).getUserId().equals(List.get(i).getUserid())) {
                    moneyQuantity = moneyQuantity + (int) List3.get(c).getIncome();

                    if (List.get(i).getUserid().equals(searchId)) {
                        OnemoneyQuantity = OnemoneyQuantity + (int) List3.get(c).getIncome();
                    }
                }
            }

            Disburse disburse = new Disburse();
            disburse.setUserId(List.get(i).getUserid());
            List<Disburse> List4 = disburseService.findDisburseList(disburse);
            for(int d=0;d<List4.size();d++)
            {
                if(List4.get(d).getUserId().equals(List.get(i).getUserid())) {
                    moneyQuantity = moneyQuantity + List4.get(d).getAmount_paid();

                    if (List.get(i).getUserid().equals(searchId)) {
                        OnemoneyQuantity = OnemoneyQuantity + List4.get(d).getAmount_paid();
                    }
                }
            }


        }

        JSONObject json = new JSONObject();
        json.put("status_code",1);
        json.put("family",new String[][]{{"现金", ""+moneyQuantity},
                {"股票", ""+stockQuantity},
                {"基金", ""+fundQuantity}});
        json.put("user",new String[][]{{"现金", ""+OnemoneyQuantity},
                {"股票", ""+OnestockQuantity},
                {"基金", ""+OnefundQuantity}});
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

    //家庭总资产折线图
    @UserLoginToken
    @RequestMapping(value="getFamilyProperty",method = RequestMethod.POST)
    public String getFamilyProperty(@RequestBody Map<String, String> map)
    {
        String userid="1";
        BigDecimal stockQuantity=new BigDecimal(0);
        BigDecimal fundQuantity=new BigDecimal(0);
        int[] totalQuantity = new int[54];
        int[] changeQuantity = new int[54];
        Calendar calendar = Calendar.getInstance();
        Account account = accountService.getAccount(userid);
        String familyid = account.getFamilyid();
        List<Account> List = accountService.getAllAccountByFamilyid(familyid);
        for(int i=0;i<List.size();i++)
        {
            JSONArray jsonArray1=stockService.getStockAPIInfoByUserid(account.getUserid());
            for (int a = 0; a <jsonArray1.size() ; a++) {
                System.out.println("currentValue : "+jsonArray1.getJSONObject(a).get("currentValue"));
                BigDecimal currentvalue = new BigDecimal(jsonArray1.getJSONObject(a).get("currentValue").toString());
                stockQuantity=stockQuantity.add(currentvalue);
            }


//计算基金
            JSONArray jsonArray2=fundService.getFundAPIInfoByUserid(List.get(i).getUserid());
            for (int b = 0; b <jsonArray2.size() ; b++)
            {
                System.out.println("currentValue : "+jsonArray2.getJSONObject(b).get("currentValue"));
                BigDecimal currentvalue = new BigDecimal(jsonArray2.getJSONObject(b).get("currentValue").toString());
                fundQuantity=fundQuantity.add(currentvalue);
            }

            Income income = new Income();
            income.setUserId(List.get(i).getUserid());
            List<Income> List3 = incomeService.findIncomeList(income);
            for(int c=0;c<List3.size();c++)
            {
                calendar.setTime(List3.get(c).getTime());
                int weekno=calendar.get(Calendar.WEEK_OF_YEAR);
                changeQuantity[weekno] = changeQuantity[weekno] + (int)List3.get(c).getIncome();
            }

            Disburse disburse = new Disburse();
            disburse.setUserId(List.get(i).getUserid());
            List<Disburse> List4 = disburseService.findDisburseList(disburse);
            for(int d=0;d<List4.size();d++)
            {
                calendar.setTime(List4.get(d).getTime());
                int weekno=calendar.get(Calendar.WEEK_OF_YEAR);
                changeQuantity[weekno] = changeQuantity[weekno] + List4.get(d).getAmount_paid();
            }
        }
        totalQuantity[0] =  fundQuantity.intValue() + stockQuantity.intValue();
        for(int i=1;i<53;i++)
        {
            totalQuantity[i] = totalQuantity[i-1] + changeQuantity[i];
        }

        JSONObject json = new JSONObject();
        json.put("status_code",1);
        String[][] str = new String[52][2];
        for(int i=0;i<52;i++)
        {
            str[i][0] = (i+1)+"";
            str[i][1] = totalQuantity[i+1]+"";
        }
        json.put("data",str);
        System.out.println(JSONObject.toJSONString(json));
        return "JSONObject.toJSONString(json)";
    }

}
