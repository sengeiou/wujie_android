package com.txd.hzj.wjlp.bean.mainbean;

/**
 * Created by Administrator on 2018/5/24.
 */

public class HouseBean {

    /**
     * house_id : 楼盘id
     * house_name : 楼盘名称
     * house_img : 封面图片
     * lng : 经度
     * lat : 纬度
     * min_price : 最低房价
     * max_price : 最高房价
     * now_num : 在售房源
     * developer : 开发商
     * distance : 距离
     */

    private String house_id;
    private String house_name;
    private String house_img;
    private String lng;
    private String lat;
    private String min_price;
    private String max_price;
    private String now_num;
    private String developer;
    private String distance;

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

    public String getHouse_img() {
        return house_img;
    }

    public void setHouse_img(String house_img) {
        this.house_img = house_img;
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

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getNow_num() {
        return now_num;
    }

    public void setNow_num(String now_num) {
        this.now_num = now_num;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
