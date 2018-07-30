package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/30 15:27
 * 功能描述：
 */
public class OffLineOrderInfoBean {
    /**
     * code : 1
     * message : 请求成功
     * data : [{"order_name":"订单编号","order_value":"153259647872567"},{"order_name":"商家名称","order_value":"达令商城"},{"order_name":"订单总价","order_value":"0.10元"},{"order_name":"下单时间","order_value":"2018-07-26 17:14:38"},{"order_name":"支付时间","order_value":"2018-07-26 17:14:49"},{"order_name":"支付方式","order_value":"余额支付"},{"order_name":"订单状态","order_value":"已支付"}]
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
         * order_name : 订单编号
         * order_value : 153259647872567
         */

        private String order_name;
        private String order_value;

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getOrder_value() {
            return order_value;
        }

        public void setOrder_value(String order_value) {
            this.order_value = order_value;
        }
    }
}
