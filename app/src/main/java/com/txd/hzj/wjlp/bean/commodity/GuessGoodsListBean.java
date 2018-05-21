package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/21 16:13
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GuessGoodsListBean implements Serializable{

   private String goods_id;//商品id",
    private String goods_name;//商品名称",
    private String goods_img;//商品图片",
    private String ticket_buy_id;//是否是折扣商品",//0 不是 大于0就是
    private String ticket_buy_discount;//折扣率",//在是折扣商品时才会有
    private String country_id;//国家id",
    private String country_logo;//国家logo"
    private String shop_price;//售价",
    private String market_price;//市场价",
    private String sell_num;//卖出数量",

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
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

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }
}
