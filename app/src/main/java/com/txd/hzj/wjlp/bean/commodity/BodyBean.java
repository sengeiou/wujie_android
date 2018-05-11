package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:56
 * 功能描述：//评论主体
 * 联系方式：常用邮箱或电话
 */
public class BodyBean implements Serializable {
    /**
     * comment_id : 9
     * goods_id : 12
     * goods_name : 香脆小麻花 50根/100g根/200根多规格可选 就是酥脆好吃
     * user_id : 27
     * nickname : GYM
     * pictures : [{"path":"http://wjyp.txunda.com/Uploads/Comment/2017-12-09/5a2b7055d2116.png"}]
     * content : 该用户未做出任何评论
     * all_star : 4
     * product_id :
     * order_goods_id :
     * create_time : 1512796245
     * user_head_pic : http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png
     * good_attr :
     * goods_num :
     * shop_price :
     * goods_img : http://wjyp.txunda.com/Uploads/Goods/2017-12-01/5a20b882f1e70.jpg
     * order_type :
     */
    private String comment_id;//"评论ID",
    private String goods_id;//"商品ID",
    private String goods_name;// "商品名称",
    private String user_id;// "用户ID",
    private String nickname;// "用户昵称",
    private String content;//"评论内容",
    private String all_star;//"评论星级",
    private String product_id;//"货品ID",
    private String order_goods_id;
    private String create_time;//"创建时间",
    private String user_head_pic;//"用户头像",
    private String good_attr;//"所评价商品属性",
    private String goods_num;//"所买数量",
    private String shop_price;// "售价",
    private String goods_img;// "商品图片",
    private String order_type;//"订单类型" ////0普通订单 1团购订单 2无界预购 3竞拍汇 4一元夺宝 5无界商店 6汽车购 7房产购 8线下商城
    private List<PicturesBean> pictures;//评论图片列表

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAll_star() {
        return all_star;
    }

    public void setAll_star(String all_star) {
        this.all_star = all_star;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(String order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_head_pic() {
        return user_head_pic;
    }

    public void setUser_head_pic(String user_head_pic) {
        this.user_head_pic = user_head_pic;
    }

    public String getGood_attr() {
        return good_attr;
    }

    public void setGood_attr(String good_attr) {
        this.good_attr = good_attr;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public List<PicturesBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<PicturesBean> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BodyBean{");
        sb.append("\ncomment_id=");
        sb.append(comment_id);
        sb.append("\ngoods_id=");
        sb.append(goods_id);
        sb.append("\ngoods_name=");
        sb.append(goods_name);
        sb.append("\nuser_id=");
        sb.append(user_id);
        sb.append("\nnickname=");
        sb.append(nickname);
        sb.append("\ncontent=");
        sb.append(content);
        sb.append("\nall_star=");
        sb.append(all_star);
        sb.append("\nproduct_id=");
        sb.append(product_id);
        sb.append("\norder_goods_id=");
        sb.append(order_goods_id);
        sb.append("\ncreate_time=");
        sb.append(create_time);
        sb.append("\nuser_head_pic=");
        sb.append(user_head_pic);
        sb.append("\ngood_attr=");
        sb.append(good_attr);
        sb.append("\ngoods_num=");
        sb.append(goods_num);
        sb.append("\nshop_price=");
        sb.append(shop_price);
        sb.append("\ngoods_img=");
        sb.append(goods_img);
        sb.append("\norder_type=");
        sb.append(order_type);
        sb.append("\npictures=");
        sb.append(pictures.toString());
        sb.append("\n}");
        return sb.toString();
    }
}
