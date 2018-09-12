package com.txd.hzj.wjlp.bean.offline;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:43
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class OffLineDataBean implements Serializable {
    private String s_id;//店铺ID
    private String user_id;
    private String show_type;//1显示我要结账以及用券明细 2不显示
    private String merchant_name;//店铺名称
    private String merchant_desc;//店铺描述
    private String logo;//店铺LOGO
    private String score;//店铺评分
    private String lat;
    private String lng;
    private String proportion;
    private String months_order;//月销量
    private String distance;//距离
    private List<TicketBean> ticket;
    private boolean isShow=false;

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_desc() {
        return merchant_desc;
    }

    public void setMerchant_desc(String merchant_desc) {
        this.merchant_desc = merchant_desc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<TicketBean> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketBean> ticket) {
        this.ticket = ticket;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getMonths_order() {
        return months_order;
    }

    public void setMonths_order(String months_order) {
        this.months_order = months_order;
    }
}
