package org.csu.ffms.crontab;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/10
 * @描述
 **/
@Component
@EnableScheduling
public class CalculateAssert {
    @Scheduled(cron="0 0 12  L * ?")
    public void updateAssert(){

    }
}
