package org.csu.ffms;

import org.csu.ffms.controller.DisburseController;
import org.csu.ffms.domain.Disburse;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
public class DisburseTests {
    @Autowired
    Disburse disburse;
    @Autowired
    DisburseController disburseController;


    @Test
    public void findDisburseByFamily(){
        disburseController.findDisburseByFamily("S1");
    }

    @Test
    public void findDisburseList(){
        disburse.setUserId("S1");
        disburse.setType("餐饮");
        disburseController.findDisburseList(disburse);
    }

    @Test
    public void total_paid() {
        disburseController.total_paid("S1");
    }

}
