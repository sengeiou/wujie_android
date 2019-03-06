package com.txd.hzj.wjlp.catchDoll.bean;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面列表项对象
 */
public class MyDollBean implements Serializable {

    private int id; // 商品id
    private String dollImageUrl; // 娃娃图片Url
    private long maturityTime; // 倒计时时间
    private String name; // 名称
    private long time; // 抓中时间
    private int convertible; // 可兑换数目

    private String userHeader; // 玩家头像
    private String userNickName; // 玩家昵称
    private String depositStatus; // 寄存状态
    private int roomId; // 房间id
    private String roomName; // 房间名称
    private String roomNumber; // 房间编号

    private String coinStatus;  //兑换银两按钮  0 不显示  1 显示

    private String goodsStatus; //兑换商品按钮  0 不显示  1 显示

    private String catcherNum; //兑换商品需要的数量

    private String GraspingNum; //用户已抓取到数量


    private String exchange_price;


    public String getExchange_price() {
        return exchange_price;
    }

    public void setExchange_price(String exchange_price) {
        this.exchange_price = exchange_price;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDollImageUrl() {
        return dollImageUrl;
    }

    public void setDollImageUrl(String dollImageUrl) {
        this.dollImageUrl = dollImageUrl;
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

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
