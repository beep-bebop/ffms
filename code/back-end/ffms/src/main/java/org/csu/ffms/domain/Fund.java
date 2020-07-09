package org.csu.ffms.domain;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
public class Fund {
    private String code;
    private String userid;
    private String name;
    private int quantity;
    private float income;

    public Fund(String code, String userid, String name, int quantity, float income) {
        this.code = code;
        this.userid = userid;
        this.name = name;
        this.quantity = quantity;
        this.income = income;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "code='" + code + '\'' +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", income=" + income +
                '}';
    }
}
