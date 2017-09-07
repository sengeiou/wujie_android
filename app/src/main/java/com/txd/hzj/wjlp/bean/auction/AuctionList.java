package com.txd.hzj.wjlp.bean.auction;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：13:50
 * 描述：竞拍汇首页商品
 * ===============Txunda===============
 */

public class AuctionList {
    /**
     * auction_id : 拍卖id
     */
    private String auction_id;
    /**
     * start_time : 拍卖开始时间
     */
    private String start_time;
    /**
     * end_time : 结束时间
     */
    private String end_time;
    /**
     * integral : 积分
     */
    private String integral;
    /**
     * goods_name : 商品名称
     */
    private String goods_name;
    /**
     * goods_img : 商品图片
     */
    private String goods_img;
    /**
     * country_id : 国家id
     */
    private String country_id;
    /**
     * country_logo : 国家logo
     */
    private String country_logo;
    /**
     * ticket_buy_id : 折扣id
     */
    private String ticket_buy_id;
    /**
     * ticket_buy_discount : 折扣率
     */
    private String ticket_buy_discount;
    /**
     * 起拍价
     */
    private String start_price;
    /**
     * 原价
     */
    private String market_price;
    /**
     * 是否已经设置提醒
     */
    private String is_remind;

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

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

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }

    public String getTicket_buy_id() {
        return ticket_buy_id;
    }

    public void setTicket_buy_id(String ticket_buy_id) {
        this.ticket_buy_id = ticket_buy_id;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getIs_remind() {
        return is_remind;
    }

    public void setIs_remind(String is_remind) {
        this.is_remind = is_remind;
    }

    @Override
    public String toString() {
        return "AuctionList{" +
                "auction_id='" + auction_id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", integral='" + integral + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", country_id='" + country_id + '\'' +
                ", country_logo='" + country_logo + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", ticket_buy_discount='" + ticket_buy_discount + '\'' +
                ", start_price='" + start_price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", is_remind='" + is_remind + '\'' +
                '}';
    }
}
