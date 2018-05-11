package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 17:37
 * 功能描述：商品
 * 联系方式：常用邮箱或电话
 */
public class GoodsBean implements Serializable {
    private String goods_img;//"商品图片",
    private String shop_price;//"商品价格"

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }
}
