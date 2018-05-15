package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:46
 * 功能描述：//商品团购订单表（相同商品的不同制品
 * 联系方式：常用邮箱或电话
 */
public class GroupBuyIdsBean implements Serializable {
    private String id;//"团购订单id",
    private String proid;
    private String goods_name;//"商品名称",
    private String arrt_name;//"商品属性名称",
    private String goods_img;// "商品图片",

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getArrt_name() {
        return arrt_name;
    }

    public void setArrt_name(String arrt_name) {
        this.arrt_name = arrt_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }
}
