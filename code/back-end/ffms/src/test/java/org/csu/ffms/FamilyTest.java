package org.csu.ffms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.domain.*;
import org.csu.ffms.service.*;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/10
 * @描述
 **/
@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class FamilyTest {
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
    @Test
    public void test(){
        Family family=familyService.getFamily("123","123");
        //Family family=familyService.getFamily("123");
        System.out.println(family.toString());
    }


    @Test
    public void testupdate(){

    }

    @Test
    public void testSearchFamily(){
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

    }

    @Test
    public void tsstTotal(){
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
    }

}
