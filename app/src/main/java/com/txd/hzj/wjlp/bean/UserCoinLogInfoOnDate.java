package com.txd.hzj.wjlp.bean;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 15:25
 * 功能描述：
 */
public class UserCoinLogInfoOnDate {

    /**
     * id : 32
     * user_id : 100
     * type : 订单
     * money : 4.00
     * id_val : 769
     * sub_type : 1
     * from_type : 手气拼单
     * desc : 嘿嘿嘿
     * create_time : 2018-11-27 00:00:01
     * img : http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png
     * sub_type_desc : 收入
     */
    private String title;
    private String id;
    private String user_id;
    private String type;
    private String money;
    private String id_val;
    private String sub_type;
    private String from_type;
    private String desc;
    private String create_time;
    private String img;
    private String sub_type_desc;

    public UserCoinLogInfoOnDate(String title,String id, String user_id, String type, String money, String id_val, String sub_type, String from_type, String desc, String create_time, String img, String sub_type_desc) {
        this.title = title;
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.money = money;
        this.id_val = id_val;
        this.sub_type = sub_type;
        this.from_type = from_type;
        this.desc = desc;
        this.create_time = create_time;
        this.img = img;
        this.sub_type_desc = sub_type_desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId_val() {
        return id_val;
    }

    public void setId_val(String id_val) {
        this.id_val = id_val;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSub_type_desc() {
        return sub_type_desc;
    }

    public void setSub_type_desc(String sub_type_desc) {
        this.sub_type_desc = sub_type_desc;
    }
}
