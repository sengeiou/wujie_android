package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两变动记录对象
 */
public class MoneyRecordingBean {

    /**
     * id : 801
     * user_id : 113
     * type : 连续签到
     * money : 0.00
     * id_val : 0
     * sub_type : 1
     * from_type : 抓娃娃
     * desc : 抓娃娃连续签到额外抽取0银两。
     * create_time : 1551314950
     * status : 0
     * pay_type : 0
     * img : http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png
     * sub_type_desc : 收入
     */

    private String id;
    private String user_id;
    private String type;
    private String money;
    private String id_val;
    private String sub_type;
    private String from_type;
    private String desc;
    private long create_time;
    private String status;
    private String pay_type;
    private String img;
    private String sub_type_desc;

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

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

    @Override
    public String toString() {
        return "MoneyRecordingBean{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", type='" + type + '\'' +
                ", money='" + money + '\'' +
                ", id_val='" + id_val + '\'' +
                ", sub_type='" + sub_type + '\'' +
                ", from_type='" + from_type + '\'' +
                ", desc='" + desc + '\'' +
                ", create_time=" + create_time +
                ", status='" + status + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", img='" + img + '\'' +
                ", sub_type_desc='" + sub_type_desc + '\'' +
                '}';
    }
}
