package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/08/29 029 上午 09:22
 * 功能描述：支付结果Bean
 */
public class PaymentResultBean implements Serializable {


    /**
     * code : 1
     * message : 请求成功
     * data : {"order_id":"807","logo":"http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg","merchant_id":"38","merchant_name":"达令商城","pay_time":"2018-08-29 10:05:39","pay_status":"1","order_price":29.64,"ticket_color":"0","pay_tickets":"0.00","return_integral":"0.00"}
     * nums : 0
     */

    private String code;
    private String message;
    private DataBean data;
    private String nums;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * order_id : 807
         * logo : http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg
         * merchant_id : 38
         * merchant_name : 达令商城
         * pay_time : 2018-08-29 10:05:39
         * pay_status : 1
         * order_price : 29.64
         * ticket_color : 0
         * pay_tickets : 0.00
         * return_integral : 0.00
         */

        private String order_id; // 订单id
        private String logo; // 门头
        private String merchant_id; // 门店id
        private String merchant_name; // 门店名称
        private String pay_time; // 支付时间
        private String pay_status; // 支付状态 1成功 0失败
        private double order_price; // 订单金额
        private String ticket_color; // 使用券类型 0未使用 1红 2黄 3蓝
        private String pay_tickets; // 使用券数量
        private String return_integral; // 返回积分数

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public double getOrder_price() {
            return order_price;
        }

        public void setOrder_price(double order_price) {
            this.order_price = order_price;
        }

        public String getTicket_color() {
            return ticket_color;
        }

        public void setTicket_color(String ticket_color) {
            this.ticket_color = ticket_color;
        }

        public String getPay_tickets() {
            return pay_tickets;
        }

        public void setPay_tickets(String pay_tickets) {
            this.pay_tickets = pay_tickets;
        }

        public String getReturn_integral() {
            return return_integral;
        }

        public void setReturn_integral(String return_integral) {
            this.return_integral = return_integral;
        }
    }
}
