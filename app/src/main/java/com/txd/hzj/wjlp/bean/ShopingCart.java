package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：13:22
 * 描述：购物车实体类
 */

public class ShopingCart {
    /**
     * 商家名称
     */
    private String merchant_name;
    /**
     * 商家Id
     */
    private String merchant_id;
    /**
     * 是否被选中
     */
    private boolean  AllCheck;
    /**
     * 商品
     */
    private List<CartGoods> goods;

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public boolean isAllCheck() {
        return AllCheck;
    }

    public void setAllCheck(boolean allCheck) {
        AllCheck = allCheck;
    }

    public List<CartGoods> getGoodsInfo() {
        return goods;
    }

    public void setGoodsInfo(List<CartGoods> goodsInfo) {
        this.goods = goodsInfo;
    }

    @Override
    public String toString() {
        return "ShopingCart{" +
                "merchant_name='" + merchant_name + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", AllCheck=" + AllCheck +
                ", goods=" + goods +
                '}';
    }
}
