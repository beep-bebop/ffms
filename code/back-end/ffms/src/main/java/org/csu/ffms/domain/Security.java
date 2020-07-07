package org.csu.ffms.domain;

import java.util.Date;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
public class Security {
    private String number;
    private String userid;
    private String type;
    private String name;
    private Date date;
    private float income;

    public Security(String number, String userid, String type, String name, Date date, float income) {
        this.number = number;
        this.userid = userid;
        this.type = type;
        this.name = name;
        this.date = date;
        this.income = income;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Security{" +
                "number='" + number + '\'' +
                ", userid='" + userid + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", income=" + income +
                '}';
    }
}
