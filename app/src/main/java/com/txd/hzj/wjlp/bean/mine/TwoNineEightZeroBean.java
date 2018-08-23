package com.txd.hzj.wjlp.bean.mine;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/23 15:19
 * 功能描述：2980专区列表
 */
public class TwoNineEightZeroBean {

    /**
     * code : 1
     * message : 请求成功
     * data : {"list":[{"goods_id":"679","goods_name":"非转基因黑豆油 食用油 植物油","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90c1bd2d8ae.jpg","market_price":"67.00","shop_price":"56.00","integral":"26.00","ticket_buy_id":"0","discount":"90.00","yellow_discount":"45.00","blue_discount":"45.00","sell_num":"0","is_active":"3","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"90","all_goods_num":"22"},{"goods_id":"14","goods_name":"(洋酒)喆园爵雅桃味威士忌 ","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f7cb8dd52c.jpg","market_price":"150.00","shop_price":"122.00","integral":"44.00","ticket_buy_id":"0","discount":"70.00","yellow_discount":"35.00","blue_discount":"35.00","sell_num":"0","is_active":"3","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"70","all_goods_num":"4"},{"goods_id":"11","goods_name":"(肉干肉脯)飘零大叔手撕猪肉脯50g","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f7a127965b.jpg","market_price":"10.00","shop_price":"5.20","integral":"0.00","ticket_buy_id":"0","discount":"20.00","yellow_discount":"10.00","blue_discount":"10.00","sell_num":"0","is_active":"3","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"20","all_goods_num":"20"},{"goods_id":"9","goods_name":"（收藏酒/陈年老酒）贵州茅台镇洞藏老酒","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f794971cca.jpg","market_price":"130.00","shop_price":"85.00","integral":"25.00","ticket_buy_id":"0","discount":"60.00","yellow_discount":"30.00","blue_discount":"30.00","sell_num":"0","is_active":"3","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"60","all_goods_num":"10"},{"goods_id":"7","goods_name":"（白酒）山西特产原浆酒","country_id":"47","path":"/Uploads/Country/2017-11-29/5a1e84bd339ca.png","goods_img":"http://test.wujiemall.com/Uploads/Goods/2018-02-23/5a8f753d0c159.jpg","market_price":"130.00","shop_price":"110.00","integral":"4.00","ticket_buy_id":"0","discount":"20.00","yellow_discount":"10.00","blue_discount":"10.00","sell_num":"0","is_active":"3","country_logo":"http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png","ticket_buy_discount":"20","all_goods_num":"6"}]}
     * nums : 5
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * goods_id : 679
             * goods_name : 非转基因黑豆油 食用油 植物油
             * country_id : 47
             * path : /Uploads/Country/2017-11-29/5a1e84bd339ca.png
             * goods_img : http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a90c1bd2d8ae.jpg
             * market_price : 67.00
             * shop_price : 56.00
             * integral : 26.00
             * ticket_buy_id : 0
             * discount : 90.00
             * yellow_discount : 45.00
             * blue_discount : 45.00
             * sell_num : 0
             * is_active : 3
             * country_logo : http://test.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png
             * ticket_buy_discount : 90
             * all_goods_num : 22
             */

            private String goods_id;
            private String goods_name;
            private String country_id;
            private String path;
            private String goods_img;
            private String market_price;
            private String shop_price;
            private String integral;
            private String ticket_buy_id;
            private String discount;
            private String yellow_discount;
            private String blue_discount;
            private String sell_num;
            private String is_active;
            private String country_logo;
            private String ticket_buy_discount;
            private String all_goods_num;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getTicket_buy_id() {
                return ticket_buy_id;
            }

            public void setTicket_buy_id(String ticket_buy_id) {
                this.ticket_buy_id = ticket_buy_id;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getYellow_discount() {
                return yellow_discount;
            }

            public void setYellow_discount(String yellow_discount) {
                this.yellow_discount = yellow_discount;
            }

            public String getBlue_discount() {
                return blue_discount;
            }

            public void setBlue_discount(String blue_discount) {
                this.blue_discount = blue_discount;
            }

            public String getSell_num() {
                return sell_num;
            }

            public void setSell_num(String sell_num) {
                this.sell_num = sell_num;
            }

            public String getIs_active() {
                return is_active;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
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

            public String getAll_goods_num() {
                return all_goods_num;
            }

            public void setAll_goods_num(String all_goods_num) {
                this.all_goods_num = all_goods_num;
            }
        }
    }
}
