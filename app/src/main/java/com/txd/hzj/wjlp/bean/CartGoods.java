package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：13:40
 * 描述：
 * ===============Txunda===============
 */

public class CartGoods {
    /**
     * 商品Id
     */
    private String goodsId;
    /**
     * 商家Id
     */
    private String mellId;
    /**
     * 商品图片
     */
    private String goodsPic;
    /**
     * 商品属性
     */
    private String attrs;
    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private String price;
    /**
     * 商品数量
     */
    private int num;

    private boolean select;

    public CartGoods(String goodsId, String mellId, String goodsPic, String attrs, String title, String price,
                     int num, boolean select) {
        this.goodsId = goodsId;
        this.mellId = mellId;
        this.goodsPic = goodsPic;
        this.attrs = attrs;
        this.title = title;
        this.price = price;
        this.num = num;
        this.select = select;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMellId() {
        return mellId;
    }

    public void setMellId(String mellId) {
        this.mellId = mellId;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "CartGoods{" +
                "goodsId='" + goodsId + '\'' +
                ", mellId='" + mellId + '\'' +
                ", goodsPic='" + goodsPic + '\'' +
                ", attrs='" + attrs + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", num=" + num +
                ", select=" + select +
                '}';
    }
}
