package org.csu.ffms.domain;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component("Income")
public class Income {
    private int incomeId;
    private int income;
    private String userId;
    private Date time;
    private String description;
    private String type;

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        String str = "income: id:"+incomeId+
                ",income:"+income+
                ",userid:"+userId+
                ",date:"+time+
                ",description:"+description+
                ",type:"+type;
        return  str;
    }
}
