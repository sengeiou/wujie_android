package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间对象
 */
public class RoomBean {

    private int _id; // 房间id
    private String photoUrl; // 房间图片
    private String name; // 房间名称
    private int price; // 价格 XX币一次
    private int status; // 房间状态

    public RoomBean() {
    }

    public RoomBean(int _id, String photoUrl, String name, int price, int status) {
        this._id = _id;
        this.photoUrl = photoUrl;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "_id=" + _id +
                ", photoUrl='" + photoUrl + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
