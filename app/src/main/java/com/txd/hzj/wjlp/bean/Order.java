package com.txd.hzj.wjlp.bean;

/**
 * Created by lienchao on 2017/7/14 0014.
 * 订单实体
 */

public class Order {
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order(int type, String name) {

        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
