package com.txd.hzj.wjlp.catchDoll.bean;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面列表项对象
 */
public class MyDollBean implements Serializable {


    /**
     * product_id
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
    private String goods_id;
    private String roomId;
    private String catcherNum;
    private String GraspingNum;
    private String maturityTime;
    private String userNickName;
    private String roomName;
    private String depositStatus;
    private String coinStatus;
    private String goodsStatus;
    private String userHeader;
    private String product_id;

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

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCatcherNum() {
        return catcherNum;
    }

    public void setCatcherNum(String catcherNum) {
        this.catcherNum = catcherNum;
    }

    public String getGraspingNum() {
        return GraspingNum;
    }

    public void setGraspingNum(String graspingNum) {
        GraspingNum = graspingNum;
    }

    public String getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(String maturityTime) {
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

    public String getCoinStatus() {
        return coinStatus;
    }

    public void setCoinStatus(String coinStatus) {
        this.coinStatus = coinStatus;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
