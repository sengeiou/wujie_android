package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：15:11
 * 描述： //商品团购订单表（相同商品的不同制品）
 * ===============Txunda===============
 */

public class    AllGoodsBean implements Serializable {
    // TODO==========拼团购==========
    private String  group_type; //类型 1试用品拼单 2常规拼单",
    /**
     * 团购id
     */
    private String group_buy_id;
    /**
     * 团购价
     */
    private String group_price;
    /**
     * 团购所需人数
     */
    private String group_num;
    /**
     * 已经团购数量
     */
    private String total;

    /**
     * 参团头像列表
     */
    private List<AppendPersonBean> append_person;

    // TODU========无界预购==========
    /**
     * 预购id
     */
    private String pre_buy_id;
    /**
     * 定金
     */
    private String deposit;
    /**
     * 预购库存
     */
    private String pre_store;
    /**
     * 销量
     */
    private String sell_num;
    /**
     * 开始时间
     */
    private String start_time;
    /**
     * 结束时间
     */
    private String end_time;
    /**
     * 市场价
     */
    private String market_price;
    // TODO==========公共部分========BEGIN
    /**
     * 积分
     */
    private String integral;
    /**
     * 商品名称
     */
    private String goods_name;
    /**
     * 商品图片
     */
    private String goods_img;
    /**
     * 国家id
     */
    private String country_id;
    /**
     * 抵扣券id
     */
    private String ticket_buy_id;
    /**
     * 国旗
     */
    private String country_logo;
    /**
     * 折扣率
     */
    private String ticket_buy_discount;
    // TODO==========公告部分==========END

    // TODO==========票券区部分==========
    /**
     * 商品id
     */
    private String goods_id;
    /**
     * 售价
     */
    private String shop_price;

    private String auction_id;
    private String start_price;

    // TODO==========无界商店==========
    /**
     * 兑换所需积分
     */
    private String use_integral;
    /**
     * 积分商品对应的id
     */
    private String integral_buy_id;
    /**
     * 总数
     */
    private String success_max_num;

    // TODO==========汽车购==========
    private String car_id;
    private String car_name;
    private String car_img;
    private String lng;
    private String lat;
    private String pre_money;
    private String true_pre_money;
    private String all_price;
    private String distance;
    private String brand_id;
    private String brand_logo;
    private String ticket_discount;


    // TODO==========房产购==========

    private String house_id;
    private String house_name;
    private String house_img;
    private String min_price;
    private String max_price;
    private String now_num;
    private String developer;

    // TODO==========一元夺宝==========

    private String one_buy_id;
    private String person_num;
    private String add_num;
    private String diff_num;

    // TODO==========限量购==========
    private String limit_buy_id;
    private String limit_price;
    private String limit_store;
    private String limit_num;
    //private String shop_price;


    public String getGroup_buy_id() {
        return group_buy_id;
    }

    public void setGroup_buy_id(String group_buy_id) {
        this.group_buy_id = group_buy_id;
    }

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getTicket_buy_id() {
        return ticket_buy_id;
    }

    public void setTicket_buy_id(String ticket_buy_id) {
        this.ticket_buy_id = ticket_buy_id;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public List<AppendPersonBean> getAppend_person() {
        return append_person;
    }

    public void setAppend_person(List<AppendPersonBean> append_person) {
        this.append_person = append_person;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getUse_integral() {
        return use_integral;
    }

    public void setUse_integral(String use_integral) {
        this.use_integral = use_integral;
    }

    public String getSuccess_max_num() {
        return success_max_num;
    }

    public void setSuccess_max_num(String success_max_num) {
        this.success_max_num = success_max_num;
    }
    public String getPre_buy_id() {
        return pre_buy_id;
    }

    public void setPre_buy_id(String pre_buy_id) {
        this.pre_buy_id = pre_buy_id;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPre_store() {
        return pre_store;
    }

    public void setPre_store(String pre_store) {
        this.pre_store = pre_store;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getIntegral_buy_id() {
        return integral_buy_id;
    }

    public void setIntegral_buy_id(String integral_buy_id) {
        this.integral_buy_id = integral_buy_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_img() {
        return car_img;
    }

    public void setCar_img(String car_img) {
        this.car_img = car_img;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPre_money() {
        return pre_money;
    }

    public void setPre_money(String pre_money) {
        this.pre_money = pre_money;
    }

    public String getTrue_pre_money() {
        return true_pre_money;
    }

    public void setTrue_pre_money(String true_pre_money) {
        this.true_pre_money = true_pre_money;
    }

    public String getAll_price() {
        return all_price;
    }

    public void setAll_price(String all_price) {
        this.all_price = all_price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getHouse_img() {
        return house_img;
    }

    public void setHouse_img(String house_img) {
        this.house_img = house_img;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getNow_num() {
        return now_num;
    }

    public void setNow_num(String now_num) {
        this.now_num = now_num;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getOne_buy_id() {
        return one_buy_id;
    }

    public void setOne_buy_id(String one_buy_id) {
        this.one_buy_id = one_buy_id;
    }

    public String getPerson_num() {
        return person_num;
    }

    public void setPerson_num(String person_num) {
        this.person_num = person_num;
    }

    public String getAdd_num() {
        return add_num;
    }

    public void setAdd_num(String add_num) {
        this.add_num = add_num;
    }

    public String getDiff_num() {
        return diff_num;
    }

    public void setDiff_num(String diff_num) {
        this.diff_num = diff_num;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getTicket_discount() {
        return ticket_discount;
    }

    public void setTicket_discount(String ticket_discount) {
        this.ticket_discount = ticket_discount;
    }

    public String getLimit_buy_id() {
        return limit_buy_id;
    }

    public void setLimit_buy_id(String limit_buy_id) {
        this.limit_buy_id = limit_buy_id;
    }

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public String getLimit_store() {
        return limit_store;
    }

    public void setLimit_store(String limit_store) {
        this.limit_store = limit_store;
    }

    public String getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(String limit_num) {
        this.limit_num = limit_num;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    @Override
    public String toString() {
        return "AllGoodsBean{" +
                "group_type='" + group_type + '\'' +
                ", group_buy_id='" + group_buy_id + '\'' +
                ", group_price='" + group_price + '\'' +
                ", group_num='" + group_num + '\'' +
                ", total='" + total + '\'' +
                ", append_person=" + append_person +
                ", pre_buy_id='" + pre_buy_id + '\'' +
                ", deposit='" + deposit + '\'' +
                ", pre_store='" + pre_store + '\'' +
                ", sell_num='" + sell_num + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", market_price='" + market_price + '\'' +
                ", integral='" + integral + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", country_id='" + country_id + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", country_logo='" + country_logo + '\'' +
                ", ticket_buy_discount='" + ticket_buy_discount + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", auction_id='" + auction_id + '\'' +
                ", start_price='" + start_price + '\'' +
                ", use_integral='" + use_integral + '\'' +
                ", integral_buy_id='" + integral_buy_id + '\'' +
                ", success_max_num='" + success_max_num + '\'' +
                ", car_id='" + car_id + '\'' +
                ", car_name='" + car_name + '\'' +
                ", car_img='" + car_img + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", pre_money='" + pre_money + '\'' +
                ", true_pre_money='" + true_pre_money + '\'' +
                ", all_price='" + all_price + '\'' +
                ", distance='" + distance + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", brand_logo='" + brand_logo + '\'' +
                ", ticket_discount='" + ticket_discount + '\'' +
                ", house_id='" + house_id + '\'' +
                ", house_name='" + house_name + '\'' +
                ", house_img='" + house_img + '\'' +
                ", min_price='" + min_price + '\'' +
                ", max_price='" + max_price + '\'' +
                ", now_num='" + now_num + '\'' +
                ", developer='" + developer + '\'' +
                ", one_buy_id='" + one_buy_id + '\'' +
                ", person_num='" + person_num + '\'' +
                ", add_num='" + add_num + '\'' +
                ", diff_num='" + diff_num + '\'' +
                ", limit_buy_id='" + limit_buy_id + '\'' +
                ", limit_price='" + limit_price + '\'' +
                ", limit_store='" + limit_store + '\'' +
                ", limit_num='" + limit_num + '\'' +
                '}';
    }
}
