package com.txd.hzj.wjlp.new_wjyp;



public class Card_bean {

    private String cart_id;
    private String goods_id;
    private String product_id;
    private String num;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Card_bean(String cart_id, String goods_id, String product_id, String num) {
        this.cart_id = cart_id;
        this.goods_id = goods_id;
        this.product_id = product_id;
        this.num = num;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public Card_bean(String cart_id) {
        this.cart_id = cart_id;
    }

    @Override
    public String toString() {
        return "Card_bean{" +
                "cart_id='" + cart_id + '\'' +
                '}';
    }
}
