package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:25
 * 描述：商品信息
 * ===============Txunda===============
 */

public class GoodsInfoBean {
    /**
     * goods_id : 商品id
     * goods_name : 商品名称
     * market_price : 市场价
     * shop_price : 售价
     * integral : 赠送积分
     * goods_desc : 商品图文详情
     * goods_brief : 商品简介
     * merchant_id : 店铺id
     * sell_num : 销量
     * ticket_buy_id : 是否属于票券区
     * country_id : 国家ID
     * country_logo : 国家logo
     * country_desc : 商品进口国家描述
     * country_tax : 进口税
     * ticket_buy_discount : 商品享受购物券折扣
     */

    private String goods_id;
    private String goods_name;
    private String market_price;
    private String shop_price;
    private String integral;
    private String goods_desc;
    private String goods_brief;
    private String merchant_id;
    private String sell_num;
    private String ticket_buy_id;
    private String country_id;
    private String country_logo;
    private String country_desc;
    private String country_tax;
    private String ticket_buy_discount;

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

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }

    public String getTicket_buy_id() {
        return ticket_buy_id;
    }

    public void setTicket_buy_id(String ticket_buy_id) {
        this.ticket_buy_id = ticket_buy_id;
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

    public String getCountry_desc() {
        return country_desc;
    }

    public void setCountry_desc(String country_desc) {
        this.country_desc = country_desc;
    }

    public String getCountry_tax() {
        return country_tax;
    }

    public void setCountry_tax(String country_tax) {
        this.country_tax = country_tax;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    @Override
    public String toString() {
        return "GoodsInfoBean{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", market_price='" + market_price + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", integral='" + integral + '\'' +
                ", goods_desc='" + goods_desc + '\'' +
                ", goods_brief='" + goods_brief + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", sell_num='" + sell_num + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", country_id='" + country_id + '\'' +
                ", country_logo='" + country_logo + '\'' +
                ", country_desc='" + country_desc + '\'' +
                ", country_tax='" + country_tax + '\'' +
                ", ticket_buy_discount='" + ticket_buy_discount + '\'' +
                '}';
    }
}
