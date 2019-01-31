package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间商品
 */
public class GameRoomGoodsBean {

    private String imgUrl; // 商品图片
    private String name; // 商品名称
    private int number; // 需要抓中次数

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GameRoomGoodsBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
