package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/4/6 006 12:08:54.
 * 功能描述： 线上支付对象
 * 联系方式： jingjie.office@qq.com
 */

public class UserBalanceHjs {

    /**
     * code : 状态回传
     * message : 回传消息
     * data : 充值数据列表
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

    @Override
    public String toString() {
        return "UserBalanceHjs{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", nums='" + nums + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * id : 充值列表记录id
         * order_sn : 订单编号
         * create_time : 创建时间
         * pay_time : 支付时间
         * money : 支付金额
         * pay_type : 支付类型
         * status : 支付状态
         */
        private String id;
        private String order_sn;
        private String create_time;
        private String pay_time;
        private String money;
        private String pay_type;
        private String status;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", order_sn='" + order_sn + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", pay_time='" + pay_time + '\'' +
                    ", money='" + money + '\'' +
                    ", pay_type='" + pay_type + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
