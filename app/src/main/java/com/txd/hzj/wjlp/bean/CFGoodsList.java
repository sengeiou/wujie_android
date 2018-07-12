package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/25 0025
 * 时间：09:21
 * 描述：
 *
 */

public class CFGoodsList implements Serializable {

    /**
     * goods_id : 商品ID
     */
    private String goods_id;
    /**
     * goods_name : 商品名称
     */
    private String goods_name;
    /**
     * goods_img : 商品图片
     */
    private String goods_img;
    /**
     * market_price : 市场价
     */
    private String market_price;
    /**
     * shop_price : 销售价
     */
    private String shop_price;
    /**
     * integral : 积分
     */
    private String integral;
    /**
     * sell_num : 销量
     */
    private String sell_num;
    /**
     * ticket_buy_id : 优惠券id
     */
    private String ticket_buy_id;
    /**
     * country_id : 国家id
     */
    private String country_id;
    /**
     * country_logo : 国家logo
     */
    private String country_logo;
    /**
     * is_buy : 1 上架 2 下架
     */
    private String is_buy;
    /**
     * add_time : 添加时间
     */
    private String add_time;
    /**
     * ticket_buy_discount : 购物券折扣
     */
    private String ticket_buy_discount;
    /**
     * isSelect : 是否被选中
     */
    private boolean isSelect = false;

    private String collect_id;

    private String footer_id;
    private String all_goods_num;
    private String goods_num;
    private String discount;
    private String yellow_discount;
    private String blue_discount;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getAll_goods_num() {
        return all_goods_num;
    }

    public void setAll_goods_num(String all_goods_num) {
        this.all_goods_num = all_goods_num;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getYellow_discount() {
        return yellow_discount;
    }

    public void setYellow_discount(String yellow_discount) {
        this.yellow_discount = yellow_discount;
    }

    public String getBlue_discount() {
        return blue_discount;
    }

    public void setBlue_discount(String blue_discount) {
        this.blue_discount = blue_discount;
    }

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

    public String getFooter_id() {
        return footer_id;
    }

    public void setFooter_id(String footer_id) {
        this.footer_id = footer_id;
    }

    @Override
    public String toString() {
        return "CFGoodsList{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", market_price='" + market_price + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", integral='" + integral + '\'' +
                ", sell_num='" + sell_num + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", country_id='" + country_id + '\'' +
                ", country_logo='" + country_logo + '\'' +
                ", is_buy='" + is_buy + '\'' +
                ", add_time='" + add_time + '\'' +
                ", ticket_buy_discount='" + ticket_buy_discount + '\'' +
                ", isSelect=" + isSelect +
                ", collect_id='" + collect_id + '\'' +
                ", footer_id='" + footer_id + '\'' +
                '}';
    }
}
