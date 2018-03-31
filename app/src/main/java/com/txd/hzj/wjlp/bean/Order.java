package com.txd.hzj.wjlp.bean;

/**
 * Created by lienchao on 2017/7/14 0014.
 * 订单实体
 */

public class Order {


    /**
     * order_id : 17
     * car_id : 2
     * car_img : http://wjyp.txunda.com/Uploads/CarBuy/2017-09-19/59c0b125bc749.png
     * car_name : 君越 30H全混动
     * pre_money : 99.00
     * num : 10
     * discount : 0.00
     * yellow_discount : 0.00
     * blue_discount : 0.00
     * goods_price : 990.00
     * order_price : 990.00
     * integral : 0.00
     * shop_name :
     * lng : 0.00
     * lat : 0.00
     * pay_type : 3
     * true_pre_money : 200.00
     */

    private String order_id;
    private String car_id;
    private String car_img;
    private String car_name;
    private String pre_money;
    private String num;
    private String discount;
    private String yellow_discount;
    private String blue_discount;
    private String goods_price;
    private String order_price;
    private String integral;
    private String shop_name;
    private String lng;
    private String lat;
    private String pay_type;
    private String true_pre_money;
    private String status;

    private String house_id;
    private String house_name;
    private String style_id;
    private String house_style_img;

    private String style_name;
    private String tags;
    private String return_integral;


    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getHouse_style_img() {
        return house_style_img;
    }

    public void setHouse_style_img(String house_style_img) {
        this.house_style_img = house_style_img;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getPre_money() {
        return pre_money;
    }

    public void setPre_money(String pre_money) {
        this.pre_money = pre_money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTrue_pre_money() {
        return true_pre_money;
    }

    public void setTrue_pre_money(String true_pre_money) {
        this.true_pre_money = true_pre_money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturn_integral() {
        return return_integral;
    }

    public void setReturn_integral(String return_integral) {
        this.return_integral = return_integral;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id='" + order_id + '\'' +
                ", car_id='" + car_id + '\'' +
                ", car_img='" + car_img + '\'' +
                ", car_name='" + car_name + '\'' +
                ", pre_money='" + pre_money + '\'' +
                ", num='" + num + '\'' +
                ", discount='" + discount + '\'' +
                ", yellow_discount='" + yellow_discount + '\'' +
                ", blue_discount='" + blue_discount + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", order_price='" + order_price + '\'' +
                ", integral='" + integral + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", true_pre_money='" + true_pre_money + '\'' +
                ", status='" + status + '\'' +
                ", return_integral='" + return_integral + '\'' +
                '}';
    }
}
