package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/30 11:28
 * 功能描述：线下店铺订单列表
 */
public class OffLineOrderListBean {

    /**
     * code : 1
     * message : 请求成功
     * data : [{"order_id":"33","order_sn":"153248735848611","merchant_id":"38","merchant_name":"达令商城","create_time":"2018-07-25 10:55:58","pay_time":"2018-07-25 10:55:58","order_price":"0.10","pay_status":"1","status":"0"}]
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
         * order_id : 33
         * order_sn : 153248735848611
         * merchant_id : 38
         * merchant_name : 达令商城
         * create_time : 2018-07-25 10:55:58
         * pay_time : 2018-07-25 10:55:58
         * order_price : 0.10
         * pay_status : 1
         * pay_type:1,2,3,4
         * common_status："1"//评价状态     0未评价   只有在pay_status=1且status=0时有评价按钮   1评价  无评价按钮
         * status : 0
         */

        private String order_id;
        private String order_sn;
        private String merchant_id;
        private String merchant_name;
        private String create_time;
        private String pay_time;
        private String order_price;
        private String pay_status;
        private String pay_type;
        private String common_status;
        private String status;

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getCommon_status() {
            return common_status;
        }

        public void setCommon_status(String common_status) {
            this.common_status = common_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
