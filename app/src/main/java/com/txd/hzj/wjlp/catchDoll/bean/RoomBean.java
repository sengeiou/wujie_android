package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间对象
 */
public class RoomBean {

    private String id; // 记录id
    private String pid; // 娃娃机id
    private String status; // 机器状态（0：空闲 1：占用 9：删除）
    private String des; // 娃娃机介绍
    private String name; // 娃娃机名称
    private String mac; // 房间MAC地址
    private String price; // 价格
    private String exchange_price; // 交换价格，我想应该是商品兑换价格
    private String room_pic; // 房间图片
    private String status_ming; // 状态描述（热抓中、空闲中）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExchange_price() {
        return exchange_price;
    }

    public void setExchange_price(String exchange_price) {
        this.exchange_price = exchange_price;
    }

    public String getRoom_pic() {
        return room_pic;
    }

    public void setRoom_pic(String room_pic) {
        this.room_pic = room_pic;
    }

    public String getStatus_ming() {
        return status_ming;
    }

    public void setStatus_ming(String status_ming) {
        this.status_ming = status_ming;
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", status='" + status + '\'' +
                ", des='" + des + '\'' +
                ", name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", price='" + price + '\'' +
                ", exchange_price='" + exchange_price + '\'' +
                ", room_pic='" + room_pic + '\'' +
                ", status_ming='" + status_ming + '\'' +
                '}';
    }
}
