package com.txd.hzj.wjlp.bean.commodity;

import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:17
 * 功能描述：可选属性数值
 * 联系方式：常用邮箱或电话
 */
public class FirstValBean implements Serializable {

    private String attr_combine_id;
    private String arrt_name;//"商品属性名称",
    private String arrt_value;
    private String goods_code;
    private String is_default;
    private String create_time;
    private String all_goods_num;
    private String discount;
    private String id;
    private String price_id;
    private String goods_id;
    private String codes;
    private String goods_num;
    private String settlement_price;
    private String shop_price;//"售价",
    private String market_price;// "市场价",
    private String goods_img;//"商品图片",
    private String discoun;
    private String integral;
    private String red_return_integral;
    private String yellow_discount;
    private String yellow_return_integral;
    private String blue_discount;
    private String wy_price;
    private String yx_price;
    private String wy_price_special;
    private String yx_price_special;
    private String integral_special;
    private String discount_special;
    private String red_rurn_integral_special;
    private String yellow_discount_special;
    private String yellow_return_integral_special;
    private String blue_discount_special;
    private String special_id;
    private String special_name;
    private String special;
    private String special_time;
    private String examine_id;
    private String examine_time;
    private String examine;
    private String arrtValue;
    private List<DjTicketBean> dj_ticket;

    public List<DjTicketBean> getDj_ticket() {
        return dj_ticket;
    }

    public void setDj_ticket(List<DjTicketBean> dj_ticket) {
        this.dj_ticket = dj_ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(String settlement_price) {
        this.settlement_price = settlement_price;
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

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getDiscoun() {
        return discoun;
    }

    public void setDiscoun(String discoun) {
        this.discoun = discoun;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getRed_return_integral() {
        return red_return_integral;
    }

    public void setRed_return_integral(String red_return_integral) {
        this.red_return_integral = red_return_integral;
    }

    public String getYellow_discount() {
        return yellow_discount;
    }

    public void setYellow_discount(String yellow_discount) {
        this.yellow_discount = yellow_discount;
    }

    public String getYellow_return_integral() {
        return yellow_return_integral;
    }

    public void setYellow_return_integral(String yellow_return_integral) {
        this.yellow_return_integral = yellow_return_integral;
    }

    public String getBlue_discount() {
        return blue_discount;
    }

    public void setBlue_discount(String blue_discount) {
        this.blue_discount = blue_discount;
    }

    public String getWy_price() {
        return wy_price;
    }

    public void setWy_price(String wy_price) {
        this.wy_price = wy_price;
    }

    public String getYx_price() {
        return yx_price;
    }

    public void setYx_price(String yx_price) {
        this.yx_price = yx_price;
    }

    public String getWy_price_special() {
        return wy_price_special;
    }

    public void setWy_price_special(String wy_price_special) {
        this.wy_price_special = wy_price_special;
    }

    public String getYx_price_special() {
        return yx_price_special;
    }

    public void setYx_price_special(String yx_price_special) {
        this.yx_price_special = yx_price_special;
    }

    public String getIntegral_special() {
        return integral_special;
    }

    public void setIntegral_special(String integral_special) {
        this.integral_special = integral_special;
    }

    public String getDiscount_special() {
        return discount_special;
    }

    public void setDiscount_special(String discount_special) {
        this.discount_special = discount_special;
    }

    public String getRed_rurn_integral_special() {
        return red_rurn_integral_special;
    }

    public void setRed_rurn_integral_special(String red_rurn_integral_special) {
        this.red_rurn_integral_special = red_rurn_integral_special;
    }

    public String getYellow_discount_special() {
        return yellow_discount_special;
    }

    public void setYellow_discount_special(String yellow_discount_special) {
        this.yellow_discount_special = yellow_discount_special;
    }

    public String getYellow_return_integral_special() {
        return yellow_return_integral_special;
    }

    public void setYellow_return_integral_special(String yellow_return_integral_special) {
        this.yellow_return_integral_special = yellow_return_integral_special;
    }

    public String getBlue_discount_special() {
        return blue_discount_special;
    }

    public void setBlue_discount_special(String blue_discount_special) {
        this.blue_discount_special = blue_discount_special;
    }

    public String getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(String special_id) {
        this.special_id = special_id;
    }

    public String getSpecial_name() {
        return special_name;
    }

    public void setSpecial_name(String special_name) {
        this.special_name = special_name;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSpecial_time() {
        return special_time;
    }

    public void setSpecial_time(String special_time) {
        this.special_time = special_time;
    }

    public String getExamine_id() {
        return examine_id;
    }

    public void setExamine_id(String examine_id) {
        this.examine_id = examine_id;
    }

    public String getExamine_time() {
        return examine_time;
    }

    public void setExamine_time(String examine_time) {
        this.examine_time = examine_time;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getArrtValue() {
        return arrtValue;
    }

    public void setArrtValue(String arrtValue) {
        this.arrtValue = arrtValue;
    }

    public String getAttr_combine_id() {
        return attr_combine_id;
    }

    public void setAttr_combine_id(String attr_combine_id) {
        this.attr_combine_id = attr_combine_id;
    }

    public String getArrt_name() {
        return arrt_name;
    }

    public void setArrt_name(String arrt_name) {
        this.arrt_name = arrt_name;
    }

    public String getArrt_value() {
        return arrt_value;
    }

    public void setArrt_value(String arrt_value) {
        this.arrt_value = arrt_value;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAll_goods_num() {
        return all_goods_num;
    }

    public void setAll_goods_num(String all_goods_num) {
        this.all_goods_num = all_goods_num;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}

