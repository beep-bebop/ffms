package org.csu.ffms;

import org.csu.ffms.domain.Family;
import org.csu.ffms.service.FamilyService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/10
 * @描述
 **/
@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class FamilyTest {
    @Autowired
    FamilyService familyService;
    @Test
    public void test(){
        Family family=familyService.getFamily("123","123");
        //Family family=familyService.getFamily("123");
        System.out.println(family.toString());
    }

    @Test
    public void testupdate(){

    }

}
