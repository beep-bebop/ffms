package org.csu.ffms.domain;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
public class Stock {
    private String code;
    private String userid;
    private String name;
    private int quantity;
    private float cost;

    public Stock(String code,  String userid,String name, int quantity, float cost) {
        this.code = code;
        this.name = name;
        this.userid = userid;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", userid='" + userid + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}
