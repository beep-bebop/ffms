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
        disburseController.findDisburseByFamily("33333");
    }

    @Test
    public void findDisburseList(){
        disburse.setUserId("11111");
        disburse.setType("eat");
        disburseController.findDisburseList(disburse);
    }

    @Test
    public void total_paid() {
        disburseController.total_paid("S1");
    }

}
