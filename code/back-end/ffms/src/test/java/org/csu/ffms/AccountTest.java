package org.csu.ffms;

import org.junit.jupiter.api.Test;
import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;
import static org.junit.jupiter.api.Assertions.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class AccountTest {

    @Autowired
    AccountService accountService;

    @Test
    void getAccountByUserid() {
        System.out.println("get userid:1 ");
        Account account=accountService.getAccount("1");
        System.out.println(account.getUsername());
        System.out.println(account.toString());
    }

    @Test
    void getAccountByUseridAndPassword() {
        Account account=accountService.getAccount("1","123");
        System.out.println(account.getUsername());
    }

    @Test
    void insertAccount() {
    }

    @Test
    void insertRelation() {
    }

    @Test
    void insertSignon() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void updateRelation() {
    }

    @Test
    void updateSignon() {
    }

    @Test
    void getAllAccount() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void deleteRelation() {
    }

    @Test
    void deleteSignon() {
    }
}