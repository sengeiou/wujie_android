package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:51
 * 功能描述：商品信息
 * 联系方式：常用邮箱或电话
 */
public class GoodsInfoBean implements Serializable {
    private String goods_id;//" "商品id ",
    private String goods_name;//"商品名称",
    private String market_price;//"市场价",
    private String shop_price;// "售价",
    private String sell_num;//"销量",
    private String goods_img;//"商品缩略图",
    private String share_content;//分享内容"
    private String integral;//"赠送积分",
    private String p_integral;//原积分
    private String goods_desc = "";//"商品图文详情",//HTML格式
    private String goods_brief;// "商品简介",
    private String merchant_id;//"店铺id",
    private String ticket_buy_id;// "是否是折扣商品",//0 不是 大于0就是
    private String is_buy;//
    private String all_goods_num;//
    private String red_return_integral;//
    private String buy_status;//"1正常上架 0已下架"
    private String arrt_name;//
    private String arrt_value;//
    private String settlement_price;//
    private String mall_status;//"1有库存 0无库存"；
    private String goods_code;//
    private String country_id;//"国家ID",// 0表示中国
    private String country_desc;// "商品进口国家描述",//如：越南进口，本地发货
    private String country_tax;//进口税",
    private String discount;//
    private String yellow_discount;//
    private String blue_discount;//
    private String is_new_goods;//是否是新品  0不是 1是
    private String a_fee_new;//
    private String is_end;////是否临期 0未临期 1临期
    private String end_date;//
    private String goods_num;// "库存"，
    private String integral_buy_id;// "积分兑换id，如果不为0 显示此商品可使用xxx积分兑换，如想使用积分兑换，亲到无界商店中进行兑换"。xxx使用user_integral字段
    private String package_list;//"包装清单",
    private String after_sale_service;// "售后服务",
    private String one_buy_id;//
    private String pre_buy_id;//
    private String auction_id;//
    private String limit_buy_id;//
    private String group_buy_id;//
    private String sales;//
    private String delivery_price;//
    private String is_new_goods_desc;//旧货 描述
    private String is_end_desc;//临期描述
    private String use_integral;//"12",//积分兑换需要多少积分
    private String wy_price;// "19.50",//无忧价
    private String yx_price;//"19.00",//优享价
    private String ticket_buy_discount;//"10%",//最多可使用多少代金券
    private String country_logo;//  "国家logo"
    private List<DjTicketBean> dj_ticket;//   //可使用券
    private List<GoodsActiveBean> goods_active;////商品正在进行的活动
    private String pcate_id;//"商品三级分类id"
    private String two_cate_name;//"休闲食品",//二级分类名称
    private String top_cate_id;//商品顶级分类id"
    private String pic;//"属性图片"
    private String balance_num;//
    private String product_id;// //属性id
    private String cate_id;//"商品二级分类id",


    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
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

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }


    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getTicket_buy_id() {
        return ticket_buy_id;
    }

    public void setTicket_buy_id(String ticket_buy_id) {
        this.ticket_buy_id = ticket_buy_id;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getAll_goods_num() {
        return all_goods_num;
    }

    public void setAll_goods_num(String all_goods_num) {
        this.all_goods_num = all_goods_num;
    }

    public String getRed_return_integral() {
        return red_return_integral;
    }

    public void setRed_return_integral(String red_return_integral) {
        this.red_return_integral = red_return_integral;
    }

    public String getBuy_status() {
        return buy_status;
    }

    public void setBuy_status(String buy_status) {
        this.buy_status = buy_status;
    }

    public String getArrt_name() {
        return arrt_name;
    }

    public void setArrt_name(String arrt_name) {
        this.arrt_name = arrt_name;
    }

    public String getArrt_value() {
        return arrt_value;
    }

    public void setArrt_value(String arrt_value) {
        this.arrt_value = arrt_value;
    }

    public String getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(String settlement_price) {
        this.settlement_price = settlement_price;
    }

    public String getMall_status() {
        return mall_status;
    }

    public void setMall_status(String mall_status) {
        this.mall_status = mall_status;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_desc() {
        return country_desc;
    }

    public void setCountry_desc(String country_desc) {
        this.country_desc = country_desc;
    }

    public String getCountry_tax() {
        return country_tax;
    }

    public void setCountry_tax(String country_tax) {
        this.country_tax = country_tax;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getYellow_discount() {
        return yellow_discount;
    }

    public void setYellow_discount(String yellow_discount) {
        this.yellow_discount = yellow_discount;
    }

    public String getBlue_discount() {
        return blue_discount;
    }

    public void setBlue_discount(String blue_discount) {
        this.blue_discount = blue_discount;
    }

    public String getIs_new_goods() {
        return is_new_goods;
    }

    public void setIs_new_goods(String is_new_goods) {
        this.is_new_goods = is_new_goods;
    }

    public String getA_fee_new() {
        return a_fee_new;
    }

    public void setA_fee_new(String a_fee_new) {
        this.a_fee_new = a_fee_new;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getIntegral_buy_id() {
        return integral_buy_id;
    }

    public void setIntegral_buy_id(String integral_buy_id) {
        this.integral_buy_id = integral_buy_id;
    }

    public String getPackage_list() {
        return package_list;
    }

    public void setPackage_list(String package_list) {
        this.package_list = package_list;
    }

    public String getAfter_sale_service() {
        return after_sale_service;
    }

    public void setAfter_sale_service(String after_sale_service) {
        this.after_sale_service = after_sale_service;
    }

    public String getOne_buy_id() {
        return one_buy_id;
    }

    public void setOne_buy_id(String one_buy_id) {
        this.one_buy_id = one_buy_id;
    }

    public String getPre_buy_id() {
        return pre_buy_id;
    }

    public void setPre_buy_id(String pre_buy_id) {
        this.pre_buy_id = pre_buy_id;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getLimit_buy_id() {
        return limit_buy_id;
    }

    public void setLimit_buy_id(String limit_buy_id) {
        this.limit_buy_id = limit_buy_id;
    }

    public String getGroup_buy_id() {
        return group_buy_id;
    }

    public void setGroup_buy_id(String group_buy_id) {
        this.group_buy_id = group_buy_id;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getIs_new_goods_desc() {
        return is_new_goods_desc;
    }

    public void setIs_new_goods_desc(String is_new_goods_desc) {
        this.is_new_goods_desc = is_new_goods_desc;
    }

    public String getIs_end_desc() {
        return is_end_desc;
    }

    public void setIs_end_desc(String is_end_desc) {
        this.is_end_desc = is_end_desc;
    }

    public String getUse_integral() {
        return use_integral;
    }

    public void setUse_integral(String use_integral) {
        this.use_integral = use_integral;
    }

    public String getWy_price() {
        return wy_price;
    }

    public void setWy_price(String wy_price) {
        this.wy_price = wy_price;
    }

    public String getYx_price() {
        return yx_price;
    }

    public void setYx_price(String yx_price) {
        this.yx_price = yx_price;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }

    public List<DjTicketBean> getDj_ticket() {
        return dj_ticket;
    }

    public List<GoodsActiveBean> getGoods_active() {
        return goods_active;
    }

    public void setDj_ticket(List<DjTicketBean> dj_ticket) {
        this.dj_ticket = dj_ticket;
    }

    public void setGoods_active(List<GoodsActiveBean> goods_active) {
        this.goods_active = goods_active;
    }

    public String getPcate_id() {
        return pcate_id;
    }

    public void setPcate_id(String pcate_id) {
        this.pcate_id = pcate_id;
    }

    public String getTwo_cate_name() {
        return two_cate_name;
    }

    public void setTwo_cate_name(String two_cate_name) {
        this.two_cate_name = two_cate_name;
    }

    public String getTop_cate_id() {
        return top_cate_id;
    }

    public void setTop_cate_id(String top_cate_id) {
        this.top_cate_id = top_cate_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBalance_num() {
        return balance_num;
    }

    public void setBalance_num(String balance_num) {
        this.balance_num = balance_num;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getP_integral() {
        return p_integral;
    }

    public void setP_integral(String p_integral) {
        this.p_integral = p_integral;
    }
}
