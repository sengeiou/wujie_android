package com.txd.hzj.wjlp.catchDoll.bean;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面列表项对象
 */
public class MyDollBean implements Serializable {


    /**
     * goods_name : 娃娃
     * goods_img : http://test.wujiemall.com/Uploads/Goods/2019-01-19/5c42bb05656a8.png
     * exchange_price : 30.00
     * create_time : 0
     * nickname : 刘博
     * name : 抓手机
     * goods_id : 705
     * roomId : 2
     * catcherNum : 1
     * GraspingNum : 5
     * maturityTime : 2592000000
     * userNickName : 刘博
     * roomName : 抓手机
     * depositStatus : 寄存中
     * coinStatus : 1
     * goodsStatus : 1
     * userHeader : http://test.wujiemall.com/Uploads/User/2018-12-25/5c21ee0212af7.jpg
     */

    private String goods_name;
    private String goods_img;
    private String exchange_price;
    private String create_time;
    private String nickname;
    private String name;
    private int goods_id;
    private int roomId;
    private int catcherNum;
    private int GraspingNum;
    private long maturityTime;
    private String userNickName;
    private String roomName;
    private String depositStatus;
    private int coinStatus;
    private int goodsStatus;
    private String userHeader;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getExchange_price() {
        return exchange_price;
    }

    public void setExchange_price(String exchange_price) {
        this.exchange_price = exchange_price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCatcherNum() {
        return catcherNum;
    }

    public void setCatcherNum(int catcherNum) {
        this.catcherNum = catcherNum;
    }

    public int getGraspingNum() {
        return GraspingNum;
    }

    public void setGraspingNum(int GraspingNum) {
        this.GraspingNum = GraspingNum;
    }

    public long getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(long maturityTime) {
        this.maturityTime = maturityTime;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public int getCoinStatus() {
        return coinStatus;
    }

    public void setCoinStatus(int coinStatus) {
        this.coinStatus = coinStatus;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }
}
