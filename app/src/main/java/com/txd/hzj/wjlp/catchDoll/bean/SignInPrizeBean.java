package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：签到奖品对象
 */
public class SignInPrizeBean {

    private int _id; // 奖品id，服务器的id
    private int type;
    private String bgImg;

    // type = 1; 奖励
    // type = 2; 再来一次
    // type = 3; 未中奖 使用奖品图设置哭脸
    private String prizeImg; // 奖品图
    private String contentType; // 奖品类别：银两、红色代金券、蓝色代金券....
    private String content; // 奖品描述

    public SignInPrizeBean(int id, int type, String bgImg, String prizeImg, String contentType, String content) {
        this._id = id;
        this.type = type;
        this.bgImg = bgImg;
        this.prizeImg = prizeImg;
        this.contentType = contentType;
        this.content = content;
    }

    public SignInPrizeBean() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getPrizeImg() {
        return prizeImg;
    }

    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SignInPrizeBean{" +
                "_id=" + _id +
                ", type=" + type + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", prizeImg='" + prizeImg + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
