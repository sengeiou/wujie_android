package com.txd.hzj.wjlp.bean.orderdetail;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/21 8:53
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class DetailListBean implements Serializable {

    private String goods_id;//商品id
    private String auto_time;//系统自动收货时间"
    private String sure_delivery_time;//用户收货时间
    private String is_invoice;//开发票 0 不开发票"
    private String invoice_name;//发票名称"
    private String sale_status;//是否已延长收货（0->否，1->是）
    private String after_sale_status;//存在  0不存在"
    private String after_sale_type;//免费维修",
    private String server;//放弃此商品七天无理由退换货？",              // 确认收货提示框内容
    private String server_else;//注：七天后将自动收货",                    // 确认收货提示框注释内容
    private String order_goods_id;//订单商品id
    private String goods_name;//自制DIY微波爆米花 随时自己来一包 就是香脆",
    private String market_price;// 市场价
    private String goods_img;// 图片
    private String shop_price;// 售价
    private String goods_num;//数量
    private String attr;//重量:250g;口味:辣"
    private String comment_status;//评论状态 0未评论 1已评论
    private String after_type;//0 申请售后  1售后中 2售后完成 3售后拒绝
    private String is_back_money;//0没退钱  1已退钱"
    private String is_sales;//售后页底部  0不显示退货按钮 1显示退货按钮 （快递公司 单号 联系人  手机号  收货地址）
    private String back_apply_id;//售后id
    private String status;//已收货，2->未收货  0->待发货
    private String sure_status;//是否存在七天无理由退换货（0->不存在，1存在）
    private String integrity_a;//本产品承诺正品保障"
    private String integrity_b;//本产品承诺正品保障"
    private String integrity_c;//本产品承诺正品保障"
    private String integrity_d;//成交后卖家将捐赠0.2元给公益计划"
    private String remind_status;//0未提醒发货   1以提醒发货"
    private String refund_price;//此商品退款的金额"
    private String return_integral;//返回积分数
    private String express_fee;//发票运费"
    private String tax_pay;// 税金运费

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAuto_time() {
        return auto_time;
    }

    public void setAuto_time(String auto_time) {
        this.auto_time = auto_time;
    }

    public String getSure_delivery_time() {
        return sure_delivery_time;
    }

    public void setSure_delivery_time(String sure_delivery_time) {
        this.sure_delivery_time = sure_delivery_time;
    }

    public String getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(String is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public String getSale_status() {
        return sale_status;
    }

    public void setSale_status(String sale_status) {
        this.sale_status = sale_status;
    }

    public String getAfter_sale_status() {
        return after_sale_status;
    }

    public void setAfter_sale_status(String after_sale_status) {
        this.after_sale_status = after_sale_status;
    }

    public String getAfter_sale_type() {
        return after_sale_type;
    }

    public void setAfter_sale_type(String after_sale_type) {
        this.after_sale_type = after_sale_type;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer_else() {
        return server_else;
    }

    public void setServer_else(String server_else) {
        this.server_else = server_else;
    }

    public String getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(String order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
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

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public String getAfter_type() {
        return after_type;
    }

    public void setAfter_type(String after_type) {
        this.after_type = after_type;
    }

    public String getIs_back_money() {
        return is_back_money;
    }

    public void setIs_back_money(String is_back_money) {
        this.is_back_money = is_back_money;
    }

    public String getIs_sales() {
        return is_sales;
    }

    public void setIs_sales(String is_sales) {
        this.is_sales = is_sales;
    }

    public String getBack_apply_id() {
        return back_apply_id;
    }

    public void setBack_apply_id(String back_apply_id) {
        this.back_apply_id = back_apply_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSure_status() {
        return sure_status;
    }

    public void setSure_status(String sure_status) {
        this.sure_status = sure_status;
    }

    public String getIntegrity_a() {
        return integrity_a;
    }

    public void setIntegrity_a(String integrity_a) {
        this.integrity_a = integrity_a;
    }

    public String getIntegrity_b() {
        return integrity_b;
    }

    public void setIntegrity_b(String integrity_b) {
        this.integrity_b = integrity_b;
    }

    public String getIntegrity_c() {
        return integrity_c;
    }

    public void setIntegrity_c(String integrity_c) {
        this.integrity_c = integrity_c;
    }

    public String getIntegrity_d() {
        return integrity_d;
    }

    public void setIntegrity_d(String integrity_d) {
        this.integrity_d = integrity_d;
    }

    public String getRemind_status() {
        return remind_status;
    }

    public void setRemind_status(String remind_status) {
        this.remind_status = remind_status;
    }

    public String getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(String refund_price) {
        this.refund_price = refund_price;
    }

    public String getReturn_integral() {
        return return_integral;
    }

    public void setReturn_integral(String return_integral) {
        this.return_integral = return_integral;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public String getTax_pay() {
        return tax_pay;
    }

    public void setTax_pay(String tax_pay) {
        this.tax_pay = tax_pay;
    }
}
