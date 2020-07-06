package org.csu.ffms;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = { @ComponentScan(value = "org.csu.ffms.*") })
@MapperScans(value = @MapperScan("org.csu.ffms.persistence"))
public class FfmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FfmsApplication.class, args);
    }

}
