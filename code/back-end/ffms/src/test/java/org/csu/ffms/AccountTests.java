package org.csu.ffms;

import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/9
 * @描述
 **/
@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class AccountTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    AccountService accountService;

    @Test
    public void testGetAccountByUsernameAndPassword(){
        String userid="1";
        String password="1";
//        Account account =accountService.getAccount(userid,password);
//        System.out.println(account.toString());
        Account account1 =accountService.getAccount(userid);
        System.out.println(account1.toString());
    }

}
