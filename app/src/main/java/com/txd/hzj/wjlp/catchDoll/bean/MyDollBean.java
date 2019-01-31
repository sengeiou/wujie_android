package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面列表项对象
 */
public class MyDollBean {

    private int id;
    private String imageUrl; // 图片Url
    private long maturityTime; // 倒计时时间
    private String name; // 名称
    private long time; // 抓中时间
    private int convertible; // 可兑换数目

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(long maturityTime) {
        this.maturityTime = maturityTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getConvertible() {
        return convertible;
    }

    public void setConvertible(int convertible) {
        this.convertible = convertible;
    }
}
