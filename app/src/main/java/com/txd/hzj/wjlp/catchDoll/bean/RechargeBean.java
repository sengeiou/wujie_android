package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：充值对象
 */
public class RechargeBean {

    private int coinsNumber; // 充值游戏币
    private boolean isGift; // 是否赠送
    private int giftNumber; // 赠送数量
    private double price; // 实应付价格

    public RechargeBean() {
    }

    public RechargeBean(int coinsNumber, boolean isGift, int giftNumber, double price) {
        this.coinsNumber = coinsNumber;
        this.isGift = isGift;
        this.giftNumber = giftNumber;
        this.price = price;
    }

    public int getCoinsNumber() {
        return coinsNumber;
    }

    public void setCoinsNumber(int coinsNumber) {
        this.coinsNumber = coinsNumber;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public int getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(int giftNumber) {
        this.giftNumber = giftNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RechargeBean{" +
                "coinsNumber=" + coinsNumber +
                ", isGift=" + isGift +
                ", giftNumber=" + giftNumber +
                ", price=" + price +
                '}';
    }
}
