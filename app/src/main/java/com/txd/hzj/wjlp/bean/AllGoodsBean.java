package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：15:11
 * 描述：
 * ===============Txunda===============
 */

public class AllGoodsBean {
    // TODO==========拼团购==========
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

    public static class AppendPersonBean {
        /**
         * log_id : 2
         * user_id : 5
         * head_pic : http://wjyp.txunda.com/Uploads/User/2017-07-29/597c1ad6a538e.jpg
         */

        private String log_id;
        private String user_id;
        private String head_pic;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        @Override
        public String toString() {
            return "AppendPersonBean{" +
                    "log_id='" + log_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    '}';
        }
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

    @Override
    public String toString() {
        return "AllGoodsBean{" +
                "group_buy_id='" + group_buy_id + '\'' +
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
                '}';
    }
}
