package com.txd.hzj.wjlp.bean;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/4 0004
 * 时间：13:31
 * 描述：
 *
 */

public class OneBuyListBean {
    /**
     * one_buy_id : 一元购ID
     * person_num : 需参与人数
     * add_num : 已参与人数
     * integral : 积分
     * goods_name : 商品名称
     * goods_img : 商品图片
     * country_id : 国家ID
     * ticket_buy_id : 抵扣券id
     * diff_num : 还剩是多少人数
     * country_logo : 国家logo
     */

    private String one_buy_id;
    private String person_num;
    private String add_num;
    private String integral;
    private String goods_name;
    private String goods_img;
    private String country_id;
    private String ticket_buy_id;
    private String diff_num;
    private String country_logo;

    public String getOne_buy_id() {
        return one_buy_id;
    }

    public void setOne_buy_id(String one_buy_id) {
        this.one_buy_id = one_buy_id;
    }

    public String getPerson_num() {
        return person_num;
    }

    public void setPerson_num(String person_num) {
        this.person_num = person_num;
    }

    public String getAdd_num() {
        return add_num;
    }

    public void setAdd_num(String add_num) {
        this.add_num = add_num;
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

    public String getTicket_buy_id() {
        return ticket_buy_id;
    }

    public void setTicket_buy_id(String ticket_buy_id) {
        this.ticket_buy_id = ticket_buy_id;
    }

    public String getDiff_num() {
        return diff_num;
    }

    public void setDiff_num(String diff_num) {
        this.diff_num = diff_num;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }

    @Override
    public String toString() {
        return "OneBuyListBean{" +
                "one_buy_id='" + one_buy_id + '\'' +
                ", person_num='" + person_num + '\'' +
                ", add_num='" + add_num + '\'' +
                ", integral='" + integral + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", country_id='" + country_id + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", diff_num='" + diff_num + '\'' +
                ", country_logo='" + country_logo + '\'' +
                '}';
    }
}
