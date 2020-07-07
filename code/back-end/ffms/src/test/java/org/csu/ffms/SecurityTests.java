package org.csu.ffms;

import org.csu.ffms.domain.Security;
import org.csu.ffms.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class SecurityTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    SecurityService securityService;

    @Test
    public void testGetSecurityBySecurityNumber(){
        System.out.println("get userid:1 and number:1 security");
        Security security=securityService.getSecurityBySecurityNumber("1","1");
        System.out.println(security.toString());
    }

    @Test
    public void testGetSecurityByUserId(){
        System.out.println("get userid:1 all securitys");
        List<Security> securityList=securityService.getSecurityByUserId("1");
        for (Security security:securityList
        ) {
            System.out.println(security.toString());
        }
    }
    @Test
    public void testInsertSecurity(){
        System.out.println("before insert");
        testGetSecurityByUserId();

        System.out.println("insert security number:4 ,userid :1");
        Security security=new Security("4","1","fourth","wooo",new Date(1),500);
        securityService.insertSecurity(security);
        System.out.println("get security by number:4 ,userid :1");

        System.out.println("after insert");
        testGetSecurityByUserId();
    }
    @Test
    public void testDeleteSecurity(){
        System.out.println("before delete");
        testGetSecurityByUserId();

        System.out.println("delete security number:4 ,userid :1");
        Security security=new Security("4","1","fourth","wooo",new Date(1),500);
        securityService.deleteSecurity(security);
        System.out.println("get security by number:4 ,userid :1");

        System.out.println("after delete");
        testGetSecurityByUserId();
    }
    @Test
    public void testUpdateSecurity(){
        System.out.println("before update"+securityService.getSecurityBySecurityNumber("1","1").toString());
        System.out.println("update Security code:1 ,userid :1");
        Security Security=new Security("1","1","first++fourth",":::::",new Date (5000),5000);
        securityService.updateSecurity(Security);
        System.out.println("after update"+securityService.getSecurityBySecurityNumber("1","1").toString());
    }

}