package com.txd.hzj.wjlp.bean;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/08/02 002 下午 14:29
 * 功能描述：定位导航定位点对象
 */
public class CustomoLocation {

    private String name; // 地点名称
    private double lng; // 经度
    private double lat; // 纬度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public CustomoLocation() {
    }

    public CustomoLocation(String name, double lng, double lat) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
    }
}
