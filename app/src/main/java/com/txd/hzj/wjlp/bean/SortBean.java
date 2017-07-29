package com.txd.hzj.wjlp.bean;


import java.io.Serializable;

public class SortBean implements Serializable {
    private String name;
    private String tag;
    private boolean isTitle;

    private boolean isCoupon;

    public SortBean(String name) {
        this.name = name;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getTag() {
        return tag;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public boolean isCoupon() {
        return isCoupon;
    }

    public void setCoupon(boolean coupon) {
        isCoupon = coupon;
    }
}
