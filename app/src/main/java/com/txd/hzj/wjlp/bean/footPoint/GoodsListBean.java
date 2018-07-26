package com.txd.hzj.wjlp.bean.footPoint;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：09:54
 * 描述：足迹，收藏商家商品
 */

public class GoodsListBean {

    /**
     * goods_id : 商品ID
     * goods_img : 商品图片
     * shop_price : 商品价格
     */

    private String goods_id;
    private String goods_img;
    private String shop_price;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

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

    @Override
    public String toString() {
        return "GoodsListBean{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", shop_price='" + shop_price + '\'' +
                '}';
    }

}
