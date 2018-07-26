package com.txd.hzj.wjlp.bean;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：15:37
 * 描述：线下商家
 */

public class Mell {

    /**
     * 有更多折扣
     */
    private boolean moreDiscount;
    /**
     * 显示跟多折扣
     */
    private boolean isShow;

    public Mell(boolean moreDiscount, boolean isShow) {
        this.moreDiscount = moreDiscount;
        this.isShow = isShow;
    }

    public boolean isMoreDiscount() {
        return moreDiscount;
    }

    public void setMoreDiscount(boolean moreDiscount) {
        this.moreDiscount = moreDiscount;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
