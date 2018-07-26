package com.txd.hzj.wjlp.bean;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/26 026 上午 10:17
 * 功能描述：三方账户绑定对象
 */
public class PayeeBindBean {

    /**
     * code : 1
     * message : 获取成功
     * data : {"phone":"18310197224","is_pay_password":1,"wxpay_accounts":"","alipay_accounts":"","default_account":"1","change_account_status":"1","wxpay_name":""}
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

    @Override
    public String toString() {
        return "PayeeBindBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums='" + nums + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * phone : 18310197224
         * is_pay_password : 1
         * wxpay_accounts :
         * alipay_accounts :
         * default_account : 1
         * change_account_status : 1
         * wxpay_name :
         */

        private String phone;
        private int is_pay_password;
        private String wxpay_accounts;
        private String alipay_accounts;
        private String default_account;
        private String change_account_status;
        private String wxpay_name;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getIs_pay_password() {
            return is_pay_password;
        }

        public void setIs_pay_password(int is_pay_password) {
            this.is_pay_password = is_pay_password;
        }

        public String getWxpay_accounts() {
            return wxpay_accounts;
        }

        public void setWxpay_accounts(String wxpay_accounts) {
            this.wxpay_accounts = wxpay_accounts;
        }

        public String getAlipay_accounts() {
            return alipay_accounts;
        }

        public void setAlipay_accounts(String alipay_accounts) {
            this.alipay_accounts = alipay_accounts;
        }

        public String getDefault_account() {
            return default_account;
        }

        public void setDefault_account(String default_account) {
            this.default_account = default_account;
        }

        public String getChange_account_status() {
            return change_account_status;
        }

        public void setChange_account_status(String change_account_status) {
            this.change_account_status = change_account_status;
        }

        public String getWxpay_name() {
            return wxpay_name;
        }

        public void setWxpay_name(String wxpay_name) {
            this.wxpay_name = wxpay_name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "phone='" + phone + '\'' +
                    ", is_pay_password=" + is_pay_password +
                    ", wxpay_accounts='" + wxpay_accounts + '\'' +
                    ", alipay_accounts='" + alipay_accounts + '\'' +
                    ", default_account='" + default_account + '\'' +
                    ", change_account_status='" + change_account_status + '\'' +
                    ", wxpay_name='" + wxpay_name + '\'' +
                    '}';
        }
    }
}
