package com.txd.hzj.wjlp.bean;

import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;

import java.util.List;

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
     * cart_id : 3
     * goods_id : 17
     * product_id : 1
     * merchant_id : 1
     * num : 1
     * goods_name : 小麻花50根特惠套餐
     * shop_price : 25.00
     * goods_img : http://wjyp.txunda.com/Uploads/Goods/2017-11-09/5a041dcb9da9f.jpg
     * attr_group : jfg,jgh,是
     * attr_group_num : 2
     * goods_attr : [{"attr_name":"颜色","attr_list":[{"id":"6","goods_attr_id":"7","attr_name":"颜色","attr_value":"jfg","attr_price":"1.00"}]},{"attr_name":"测试属性名称3","attr_list":[{"id":"3","goods_attr_id":"8","attr_name":"测试属性名称3","attr_value":"jgh","attr_price":"1.00"}]},{"attr_name":"是否为有机食品","attr_list":[{"id":"1","goods_attr_id":"9","attr_name":"是否为有机食品","attr_value":"是","attr_price":"1.00"}]}]
     * product : [{"id":"1","goods_id":"17","goods_attr_str":"7|8|9","product_sn":"WJ100017_P1","product_number":"2"}]
     */

    private String cart_id;
    private String goods_id;
    private String product_id;
    private String merchant_id;
    private String num;
    private String goods_name;
    private String goods_attr_name;
    private String shop_price;
    private String goods_num;
    private String goods_img;
    private String attr_group;
    private boolean check;
    private String attr_group_num;
    private String return_integral;
    private List<GoodsAttributeAty.Goods_Attr> goods_attr_first;
    private List<GoodsAttrs.product> product;

    public String getGoods_num(){
        return goods_num;
    }
    public void setGoods_num(String goods_num){
        this.goods_num=goods_num;
    }
    public String getGoods_attr_name() {
        return goods_attr_name;
    }

    public void setGoods_attr_name(String goods_attr_name) {
        this.goods_attr_name = goods_attr_name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

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

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getAttr_group() {
        return attr_group;
    }

    public void setAttr_group(String attr_group) {
        this.attr_group = attr_group;
    }

    public String getAttr_group_num() {
        return attr_group_num;
    }

    public void setAttr_group_num(String attr_group_num) {
        this.attr_group_num = attr_group_num;
    }

    public List<GoodsAttributeAty.Goods_Attr> getGoods_attr_first() {
        return goods_attr_first;
    }

    public void setGoods_attr_first(List<GoodsAttributeAty.Goods_Attr> goods_attr_first) {
        this.goods_attr_first = goods_attr_first;
    }

    public String getReturn_integral() {
        return return_integral;
    }

    public void setReturn_integral(String return_integral) {
        this.return_integral = return_integral;
    }
    //
//    public List<GoodsAttrs> getGoods_attr() {
//        return goods_attr;
//    }
//
//    public void setGoods_attr(List<GoodsAttrs> goods_attr) {
//        this.goods_attr = goods_attr;
//    }
//
//    public List<GoodsAttrs.product> getProduct() {
//        return product;
//    }
//
//    public void setProduct(List<GoodsAttrs.product> product) {
//        this.product = product;
//    }


    @Override
    public String toString() {
        return "CartGoods{" +
                "cart_id='" + cart_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", num='" + num + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", attr_group='" + attr_group + '\'' +
                ", check=" + check +
                ", attr_group_num='" + attr_group_num + '\'' +
                ", return_integral='" + return_integral + '\'' +
                 ", goods_attr_first=" + goods_attr_first +
//                ", product=" + product +
                '}';
    }
}
