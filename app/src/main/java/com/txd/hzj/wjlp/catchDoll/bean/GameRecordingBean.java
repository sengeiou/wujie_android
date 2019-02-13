package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录对象
 */
public class GameRecordingBean {


    /**
     * id : 2
     * user_id : 22
     * cid : 12332
     * goods_id : 706
     * status : 1
     * create_time : 1509691966
     * update_time : 1548397421
     * other : null
     * goods_name : 抓抓抓
     * nickname : Cyf
     */

    private String id;
    private String user_id;
    private String cid;
    private String goods_id;
    private String status;
    private String create_time;
    private String update_time;
    private Object other;
    private String goods_name;
    private String nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "GameRecordingBean{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", cid='" + cid + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", other=" + other +
                ", goods_name='" + goods_name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
