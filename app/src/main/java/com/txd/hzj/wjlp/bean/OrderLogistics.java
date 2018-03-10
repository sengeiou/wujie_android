package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * Created by Txd_lienchao on 2018/3/8 0008 下午 7:46.
 * 功能描述:
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class OrderLogistics {
    /**
     * code : 1
     * message : 获取数据成功
     * data : [{"id":"订单相关的订单商品ID","trans_type":"运输方式 1快递 2EMS 3平邮 4物流","express_no":"物流单号","express_company":"快递ID","goods_img":"图片","goods_name":"商品名称","attr":"商品属性","shop_price":"商品金额","goods_num":"商品数量"}]
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
         * id : 订单相关的订单商品ID
         * trans_type : 运输方式 1快递 2EMS 3平邮 4物流
         * express_no : 物流单号
         * express_company : 快递ID
         * goods_img : 图片
         * goods_name : 商品名称
         * attr : 商品属性
         * shop_price : 商品金额
         * goods_num : 商品数量
         */

        private String id;
        private String trans_type;
        private String express_no;
        private String express_company;
        private String goods_img;
        private String goods_name;
        private String attr;
        private String shop_price;
        private String goods_num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrans_type() {
            return trans_type;
        }

        public void setTrans_type(String trans_type) {
            this.trans_type = trans_type;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }
    }
}
