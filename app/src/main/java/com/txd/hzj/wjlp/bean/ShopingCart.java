package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：13:22
 * 描述：购物车实体类
 * ===============Txunda===============
 */

public class ShopingCart {
    /**
     * 商家名称
     */
    private String mellName;
    /**
     * 商家Id
     */
    private String mellId;
    /**
     * 是否被选中
     */
    private boolean mellAllCheck;
    /**
     * 商品
     */
    private List<CartGoods> goodsInfo;

    public ShopingCart(String mellName, String mellId, boolean mellAllCheck, List<CartGoods> goodsInfo) {
        this.mellName = mellName;
        this.mellId = mellId;
        this.mellAllCheck = mellAllCheck;
        this.goodsInfo = goodsInfo;
    }

    public String getMellName() {
        return mellName;
    }

    public void setMellName(String mellName) {
        this.mellName = mellName;
    }

    public String getMellId() {
        return mellId;
    }

    public void setMellId(String mellId) {
        this.mellId = mellId;
    }

    public boolean isMellAllCheck() {
        return mellAllCheck;
    }

    public void setMellAllCheck(boolean mellAllCheck) {
        this.mellAllCheck = mellAllCheck;
    }

    public List<CartGoods> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<CartGoods> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String toString() {
        return "ShopingCart{" +
                "mellName='" + mellName + '\'' +
                ", mellId='" + mellId + '\'' +
                ", mellAllCheck=" + mellAllCheck +
                ", goodsInfo=" + goodsInfo +
                '}';
    }

}
