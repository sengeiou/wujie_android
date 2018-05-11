package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:03
 * 功能描述：//价格说明
 * 联系方式：常用邮箱或电话
 */
public class GoodsPriceDescBean implements Serializable {
    private String icon;  //图标"http://wjyp.txunda.com/Uploads/GoodsPriceDesc/2017-11-13/5a092c91a8bce.png",
    private String price_name;//价格名称 "无忧价"
    private String desc;//价格说明文字

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
