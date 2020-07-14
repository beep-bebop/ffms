package org.csu.ffms;

import org.csu.ffms.controller.DisburseController;
import org.csu.ffms.domain.Disburse;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScans(value = { @ComponentScan(value = "org.csu.ffms.*") })
@MapperScans(value = @MapperScan("org.csu.ffms.persistence"))
@EnableScheduling
public class FfmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FfmsApplication.class, args);
    }



}
