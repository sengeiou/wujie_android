package com.txd.hzj.wjlp.bean.auction;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：13:51
 * 描述：竞拍汇首页广告
 * ===============Txunda===============
 */

public class AdsBean {
    /**
     * ads_id : 广告id
     * picture : 广告图片
     * desc : 广告描述
     * href : 广告链接
     */

    private String ads_id;
    private String picture;
    private String desc;
    private String href;

    public String getAds_id() {
        return ads_id;
    }

    public void setAds_id(String ads_id) {
        this.ads_id = ads_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "AdsBean{" +
                "ads_id='" + ads_id + '\'' +
                ", picture='" + picture + '\'' +
                ", desc='" + desc + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
