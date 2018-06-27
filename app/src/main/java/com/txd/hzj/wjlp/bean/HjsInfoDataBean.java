package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/27 17:48
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class HjsInfoDataBean  implements Serializable {
    private String order_name;
    private String order_value;

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_value() {
        return order_value;
    }

    public void setOrder_value(String order_value) {
        this.order_value = order_value;
    }
}