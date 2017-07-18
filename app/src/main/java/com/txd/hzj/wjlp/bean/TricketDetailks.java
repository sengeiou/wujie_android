package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：15:53
 * 描述：购物券使用详情
 * ===============Txunda===============
 */

public class TricketDetailks implements Serializable {

    private String name;
    private String tag;
    private boolean isTitle;
    private String time;
    private String price;
    private String type;

    public TricketDetailks(String name, String tag, boolean isTitle, String time, String price, String type) {
        this.name = name;
        this.tag = tag;
        this.isTitle = isTitle;
        this.time = time;
        this.price = price;
        this.type = type;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
