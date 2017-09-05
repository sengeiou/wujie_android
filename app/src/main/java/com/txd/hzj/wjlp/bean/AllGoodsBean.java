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
    /**
     * group_buy_id : 1
     * group_price : 2.80
     * group_num : 2
     * total : 0
     * integral : 0
     * goods_name : 格力大薯片
     * goods_img : http://wjyp.txunda.com/Uploads/Goods/2017-07-29/597c58e7c3d49.jpg
     * country_id : 12
     * ticket_buy_id : 3
     * country_logo : http://wjyp.txunda.com/Uploads/Country/2017-08-15/59926545b9cbc.jpg
     * ticket_buy_discount : 20
     * append_person : [{"log_id":"2","user_id":"5","head_pic":"http://wjyp.txunda
     * .com/Uploads/User/2017-07-29/597c1ad6a538e.jpg"}]
     */

    private String group_buy_id;
    private String group_price;
    private String group_num;
    private String total;
    private String integral;
    private String goods_name;
    private String goods_img;
    private String country_id;
    private String ticket_buy_id;
    private String country_logo;
    private String ticket_buy_discount;
    private List<AppendPersonBean> append_person;

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

    @Override
    public String toString() {
        return "GroupBuyListBean{" +
                "group_buy_id='" + group_buy_id + '\'' +
                ", group_price='" + group_price + '\'' +
                ", group_num='" + group_num + '\'' +
                ", total='" + total + '\'' +
                ", integral='" + integral + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", country_id='" + country_id + '\'' +
                ", ticket_buy_id='" + ticket_buy_id + '\'' +
                ", country_logo='" + country_logo + '\'' +
                ", ticket_buy_discount='" + ticket_buy_discount + '\'' +
                ", append_person=" + append_person +
                '}';
    }
}
