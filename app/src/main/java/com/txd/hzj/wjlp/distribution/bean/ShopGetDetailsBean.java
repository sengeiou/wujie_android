package com.txd.hzj.wjlp.distribution.bean;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/16 0016 9:46
 * 功能描述：
 * 联系方式：无
 */
public class ShopGetDetailsBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"1","shop_name":"abc","shop_pic":"23869","shop_desc":"12312321321","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/Distribution/2018-07-16/5b4befeb41adb.png","shop_url":"http://test2.wujiemall.com/Uploads/Distribution/2018-07-16/5b4befeb41adb.png"},{"id":"10","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"11","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"12","shop_name":"","shop_pic":"0","shop_desc":null,"user_id":"0","set_id":"0","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"0","update_time":"0","other":null,"path":null,"shop_url":"http://test2.wujiemall.com/Uploads/User/default.png"},{"id":"13","shop_name":"","shop_pic":"0","shop_desc":null,"user_id":"0","set_id":"0","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"0","update_time":"0","other":null,"path":null,"shop_url":"http://test2.wujiemall.com/Uploads/User/default.png"},{"id":"14","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"15","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"16","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"18","shop_name":"我是小店更新信息","shop_pic":"2","shop_desc":"呵呵","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/ThrMenu/2016-10-28/581317abae365.jpg","shop_url":"http://test2.wujiemall.com/Uploads/ThrMenu/2016-10-28/581317abae365.jpg"},{"id":"19","shop_name":"我是小店更新信息","shop_pic":"23","shop_desc":"呵呵2","user_id":"2","set_id":"1","shop_status":"0","pay_money":"0","pay_orders":"0","visit_nums":"0","create_time":"1223546464","update_time":"0","other":null,"path":"/Uploads/Merchant/2016-11-11/5825678bae36b.jpg","shop_url":"http://test2.wujiemall.com/Uploads/Merchant/2016-11-11/5825678bae36b.jpg"}]
     * nums : 0
     */

    private int code;
    private String message;
    private String nums;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
         * id : 1
         * shop_name : abc
         * shop_pic : 23869
         * shop_desc : 12312321321
         * user_id : 2
         * set_id : 1
         * shop_status : 0
         * pay_money : 0
         * pay_orders : 0
         * visit_nums : 0
         * create_time : 1223546464
         * update_time : 0
         * other : null
         * path : /Uploads/Distribution/2018-07-16/5b4befeb41adb.png
         * shop_url : http://test2.wujiemall.com/Uploads/Distribution/2018-07-16/5b4befeb41adb.png
         */

        private String id;
        private String shop_name;
        private String shop_pic;
        private String shop_desc;
        private String user_id;
        private String set_id;
        private String shop_status;
        private String pay_money;
        private String pay_orders;
        private String visit_nums;
        private String create_time;
        private String update_time;
        private Object other;
        private String path;
        private String shop_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_pic() {
            return shop_pic;
        }

        public void setShop_pic(String shop_pic) {
            this.shop_pic = shop_pic;
        }

        public String getShop_desc() {
            return shop_desc;
        }

        public void setShop_desc(String shop_desc) {
            this.shop_desc = shop_desc;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSet_id() {
            return set_id;
        }

        public void setSet_id(String set_id) {
            this.set_id = set_id;
        }

        public String getShop_status() {
            return shop_status;
        }

        public void setShop_status(String shop_status) {
            this.shop_status = shop_status;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getPay_orders() {
            return pay_orders;
        }

        public void setPay_orders(String pay_orders) {
            this.pay_orders = pay_orders;
        }

        public String getVisit_nums() {
            return visit_nums;
        }

        public void setVisit_nums(String visit_nums) {
            this.visit_nums = visit_nums;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public Object getOther() {
            return other;
        }

        public void setOther(Object other) {
            this.other = other;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getShop_url() {
            return shop_url;
        }

        public void setShop_url(String shop_url) {
            this.shop_url = shop_url;
        }
    }
}
