package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/25 0025
 * 时间：09:21
 * 描述：
 * ===============Txunda===============
 */

public class CFGoodsList {
    /**
     * goods_id : 商品ID
     * goods_name : 商品名称
     * goods_img : 商品图片
     * market_price : 市场价
     * shop_price : 销售价
     * integral : 积分
     * sell_num : 销量
     * ticket_buy_id :
     * country_id : 0
     * country_logo : 国家logo
     * is_buy : 1
     * add_time : 0
     * ticket_buy_discount : 购物券折扣
     * isSelect : 是否被选中
     */

    private String goods_id;
    private String goods_name;
    private String goods_img;
    private String market_price;
    private String shop_price;
    private String integral;
    private String sell_num;
    private String ticket_buy_id;
    private String country_id;
    private String country_logo;
    private String is_buy;
    private String add_time;
    private String ticket_buy_discount;
    private boolean isSelect = false;

    private String collect_id;

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

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }
}
