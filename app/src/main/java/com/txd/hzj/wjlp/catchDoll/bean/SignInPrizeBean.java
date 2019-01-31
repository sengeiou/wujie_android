package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：签到奖品对象
 */
public class SignInPrizeBean {

    private int type;
    private String bgImg;

    // type = 1; 奖励
    // type = 2; 再来一次
    // type = 3; 未中奖 使用奖品图设置哭脸
    private String prizeImg; // 奖品图
    private String content; // 奖品描述

    public SignInPrizeBean(int type, String bgImg, String prizeImg, String content) {
        this.type = type;
        this.bgImg = bgImg;
        this.prizeImg = prizeImg;
        this.content = content;
    }

    public SignInPrizeBean() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SignInPrizeBean{" +
                "type=" + type +
                ", bgImg='" + bgImg + '\'' +
                ", prizeImg='" + prizeImg + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
