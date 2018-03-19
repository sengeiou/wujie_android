package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * Created by Txd_lienchao on 2018/3/16 0016 上午 11:11.
 * 功能描述:会员卡订单列表实体类
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class MemberOrderList {

    /**
     * code : 1
     * message : 成功
     * data : [{"id":"34","order_sn":"152118605915847","life":1,"create_time":"2018-03-16 15:40:59,年限:1年","rank_name":"无忧会员","member_coding":"2","order_status":"1","validity":"尚未支付"}]
     * nums : 0
     */

    private String code;
    private String message;
    private String nums;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 34
         * order_sn : 152118605915847
         * life : 1
         * create_time : 2018-03-16 15:40:59,年限:1年
         * rank_name : 无忧会员
         * member_coding : 2
         * order_status : 1
         * validity : 尚未支付
         */

        private String id;
        private String order_sn;
        private int life;
        private String create_time;
        private String rank_name;
        private String member_coding;
        private String order_status;
        private String validity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getLife() {
            return life;
        }

        public void setLife(int life) {
            this.life = life;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRank_name() {
            return rank_name;
        }

        public void setRank_name(String rank_name) {
            this.rank_name = rank_name;
        }

        public String getMember_coding() {
            return member_coding;
        }

        public void setMember_coding(String member_coding) {
            this.member_coding = member_coding;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }
    }
}
