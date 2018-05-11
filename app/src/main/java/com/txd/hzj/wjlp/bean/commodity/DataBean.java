package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:50
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class DataBean implements Serializable {
    private CheapGroupBean cheap_group; //优惠组合
    private List<AllGoodsBean> guessGoodsList; //商品团购订单表（相同商品的不同制品）
    private String cart_num;//"购物车数量", //在会员登录情况下查看
    private String msg_tip;//消息提醒数, //在会员登录情况下才有
    private String is_collect;//"是否收藏", //在会员登录情况下查看
    private GoodsInfoBean goodsInfo;//商品信息
    private String price_desc;// "1.无忧价：无忧价格说明。\r\n2.优享价：优享价说明\r\n3.折扣：折扣说明。\r\n4.异常问题：异常问题说明",//价格说明
    private MInfoBean mInfo;//店铺信息
    private CommentBean comment;//评论
    private String share_url;//"http://wjyp.txunda.com",//分享链接
    private String share_img;//"分享图片",
    private String share_content;// "分享内容"
    private String send_city;
    private String send_fee;
    private String group_price;////优惠组合价格
    private String one_price;// "单买价"
    private List<PromotionBean> promotion;////店铺优惠活动列表
    private List<GoodsCommonAttrBean> goods_common_attr;//商品公共属性列表
    private List<GoodsAttrBean> goods_attr;// //商品属性列表
    private List<GoodsBannerBean> goods_banner;//[//商品图片轮播图列表
    private List<AttrImagesBean> attr_images;////属性图片列表
    private List<ProductBean> product;////货品
    private List<GoodsServerBean> goods_server;////商品服务信息
    private List<GroupBean> group;//团购列表
    private String is_attr;// //是否有商品属性 0->没有   1->有
    private List<GoodsPriceDescBean> goods_price_desc;// //价格说明
    private String vouchers_desc;//
    private List<FirstListBean> first_list;//   // 属性列表
    private String is_cart;//
    private String remarks;// 运输备注
    private List<GroupBuyIdsBean> group_buy_ids;// //商品团购订单表（相同商品的不同制品
    private String total;//"已团件数"
    private List<FirstValBean> first_val;//可选属性数值
    private List<TicketListBean> ticketList;// //优惠券列表

    public List<AllGoodsBean> getGuessGoodsList() {
        return guessGoodsList;
    }

    public void setGuessGoodsList(List<AllGoodsBean> guessGoodsList) {
        this.guessGoodsList = guessGoodsList;
    }

    public List<GroupBuyIdsBean> getGroup_buy_ids() {
        return group_buy_ids;
    }

    public void setGroup_buy_ids(List<GroupBuyIdsBean> group_buy_ids) {
        this.group_buy_ids = group_buy_ids;
    }

    public String getCart_num() {
        return cart_num;
    }

    public void setCart_num(String cart_num) {
        this.cart_num = cart_num;
    }

    public String getMsg_tip() {
        return msg_tip;
    }

    public void setMsg_tip(String msg_tip) {
        this.msg_tip = msg_tip;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public GoodsInfoBean getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfoBean goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getPrice_desc() {
        return price_desc;
    }

    public void setPrice_desc(String price_desc) {
        this.price_desc = price_desc;
    }

    public MInfoBean getmInfo() {
        return mInfo;
    }

    public void setmInfo(MInfoBean mInfo) {
        this.mInfo = mInfo;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getSend_city() {
        return send_city;
    }

    public void setSend_city(String send_city) {
        this.send_city = send_city;
    }

    public String getSend_fee() {
        return send_fee;
    }

    public void setSend_fee(String send_fee) {
        this.send_fee = send_fee;
    }

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }

    public String getOne_price() {
        return one_price;
    }

    public void setOne_price(String one_price) {
        this.one_price = one_price;
    }

    public List<PromotionBean> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<PromotionBean> promotion) {
        this.promotion = promotion;
    }

    public List<TicketListBean> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketListBean> ticketList) {
        this.ticketList = ticketList;
    }

    public List<GoodsCommonAttrBean> getGoods_common_attr() {
        return goods_common_attr;
    }

    public void setGoods_common_attr(List<GoodsCommonAttrBean> goods_common_attr) {
        this.goods_common_attr = goods_common_attr;
    }

    public List<GoodsAttrBean> getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
        this.goods_attr = goods_attr;
    }

    public List<GoodsBannerBean> getGoods_banner() {
        return goods_banner;
    }

    public void setGoods_banner(List<GoodsBannerBean> goods_banner) {
        this.goods_banner = goods_banner;
    }

    public List<AttrImagesBean> getAttr_images() {
        return attr_images;
    }

    public void setAttr_images(List<AttrImagesBean> attr_images) {
        this.attr_images = attr_images;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public List<GoodsServerBean> getGoods_server() {
        return goods_server;
    }

    public void setGoods_server(List<GoodsServerBean> goods_server) {
        this.goods_server = goods_server;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public String getIs_attr() {
        return is_attr;
    }

    public void setIs_attr(String is_attr) {
        this.is_attr = is_attr;
    }

    public List<GoodsPriceDescBean> getGoods_price_desc() {
        return goods_price_desc;
    }

    public void setGoods_price_desc(List<GoodsPriceDescBean> goods_price_desc) {
        this.goods_price_desc = goods_price_desc;
    }

    public String getVouchers_desc() {
        return vouchers_desc;
    }

    public void setVouchers_desc(String vouchers_desc) {
        this.vouchers_desc = vouchers_desc;
    }

    public List<FirstListBean> getFirst_list() {
        return first_list;
    }

    public void setFirst_list(List<FirstListBean> first_list) {
        this.first_list = first_list;
    }

    public String getIs_cart() {
        return is_cart;
    }

    public void setIs_cart(String is_cart) {
        this.is_cart = is_cart;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<FirstValBean> getFirst_val() {
        return first_val;
    }

    public void setFirst_val(List<FirstValBean> first_val) {
        this.first_val = first_val;
    }

    public CheapGroupBean getCheap_group() {
        return cheap_group;
    }

    public void setCheap_group(CheapGroupBean cheap_group) {
        this.cheap_group = cheap_group;
    }
}
