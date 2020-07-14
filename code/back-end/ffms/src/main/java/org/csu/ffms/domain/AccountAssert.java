package org.csu.ffms.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/13
 * @描述
 **/
public class AccountAssert {
    private String userid;
    private BigDecimal stock;
    private BigDecimal fund;
    private BigDecimal cash;
    private Date time;

    public AccountAssert(String userid, BigDecimal stock, BigDecimal fund, BigDecimal cash, Date time) {
        this.userid = userid;
        this.stock = stock;
        this.fund = fund;
        this.cash = cash;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AccountAssert() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "AccountAssert{" +
                "userid='" + userid + '\'' +
                ", stock=" + stock +
                ", fund=" + fund +
                ", cash=" + cash +
                ", time=" + time +
                '}';
    }
}
