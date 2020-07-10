package org.csu.ffms.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("Disburse")
public class Disburse {
    private int disburseId;
    private int amount_paid;
    private String userId;

    @JSONField(format = "yyyy-MM-dd")  //FastJson包使用注解
    public Date time;
    private String description;
    private String type;

    public int getDisburseId() { return disburseId; }

    public void setDisburseId(int disburseId) { this.disburseId = disburseId; }

    public int getAmount_paid() { return amount_paid; }

    public void setAmount_paid(int amount_paid) { this.amount_paid = amount_paid; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public String toString(){
        String str = "disburse: id:"+disburseId+
                ",amount_paid:"+amount_paid+
                ",userid:"+userId+
                ",date:"+time+
                ",description:"+description+
                ",type:"+type;
        return  str;
    }
}
