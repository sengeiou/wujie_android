package com.txd.hzj.wjlp.distribution.bean;

import java.util.List;

/**
 * 创建者：wjj
 * 创建时间：${DATA} 上午 09:20
 * 功能描述：分销商品对象
 */
public class DistributionGoodsBean {


    /**
     * code : 200
     * message : 操作成功
     * data : [{"dsg_id":"15","goods_id":"57","product_id":"34","shop_id":"ZAAADU","integral":"13.00","goods_name":"（女士凉鞋）欧美夏季新款女式尖头凉鞋","g_integral":"0.00","goods_img":"http://test2.wujiemall.com/Uploads/Goods/2018-02-23/5a8fb5483cd25.jpg","country_id":"47","ticket_buy_id":"0","shop_price":"121.40","market_price":"150.00","sell_num":"0","ticket_buy_discount":30},{"dsg_id":"59","goods_id":"261","product_id":"444","shop_id":"ZAAADU","integral":"11.00","goods_name":"（女式内裤）日系暖宫棉提臀收腹3D蜂巢内裤","g_integral":"0.00","goods_img":"http://test2.wujiemall.com/Uploads/Goods/2018-02-24/5a91070a3304b.jpg","country_id":"47","ticket_buy_id":"0","shop_price":"34.41","market_price":"40.00","sell_num":"0","ticket_buy_discount":60},{"dsg_id":"64","goods_id":"628","product_id":"967","shop_id":"ZAAADU","integral":"3.00","goods_name":"（基质）全能型育苗基质","g_integral":"0.00","goods_img":"http://test2.wujiemall.com/Uploads/Goods/2018-03-01/5a9759e392567.jpg","country_id":"47","ticket_buy_id":"0","shop_price":"30.80","market_price":"50.00","sell_num":"0","ticket_buy_discount":30}]
     * nums : 3
     *
     */


    private int code;
    private String message;
    private String nums;
    private List<DataBean> data;
    /**
     * num_arr : {"normal":"12","down":"0","out":"1"}
     */

    private NumArrBean num_arr;

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

    public NumArrBean getNum_arr() {
        return num_arr;
    }

    public void setNum_arr(NumArrBean num_arr) {
        this.num_arr = num_arr;
    }

    public static class DataBean {
        /**
         * dsg_id : 15
         * goods_id : 57
         * product_id : 34
         * shop_id : ZAAADU
         * integral : 13.00
         * goods_name : （女士凉鞋）欧美夏季新款女式尖头凉鞋
         * goods_brief:
         * g_integral : 0.00
         * goods_img : http://test2.wujiemall.com/Uploads/Goods/2018-02-23/5a8fb5483cd25.jpg
         * country_id : 47
         * ticket_buy_id : 0
         * shop_price : 121.40
         * market_price : 150.00
         * sell_num : 0
         * active_type
         * shop_goods_rec  //是否推荐   1 推荐  0 未推荐
         * ticket_buy_discount : 30
         */

        private String dsg_id;
        private String goods_id;
        private String product_id;
        private String shop_id;
        private String integral;
        private String goods_name;
        private String goods_brief;
        private String g_integral;
        private String goods_img;
        private String country_id;
        private String ticket_buy_id;
        private String shop_price;
        private String market_price;
        private String sell_num;
        private String goods_gift;
        private String active_type;
        private String shop_goods_rec;
        private int ticket_buy_discount;
        private boolean mChecked = false;

        public String getShop_goods_rec() {
            return shop_goods_rec;
        }

        public void setShop_goods_rec(String shop_goods_rec) {
            this.shop_goods_rec = shop_goods_rec;
        }

        public String getActive_type() {
            return active_type;
        }

        public void setActive_type(String active_type) {
            this.active_type = active_type;
        }

        public String getGoods_brief() {
            return goods_brief;
        }

        public void setGoods_brief(String goods_brief) {
            this.goods_brief = goods_brief;
        }

        public String getDsg_id() {
            return dsg_id;
        }

        public void setDsg_id(String dsg_id) {
            this.dsg_id = dsg_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
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

        public String getG_integral() {
            return g_integral;
        }

        public void setG_integral(String g_integral) {
            this.g_integral = g_integral;
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

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getSell_num() {
            return sell_num;
        }

        public void setSell_num(String sell_num) {
            this.sell_num = sell_num;
        }

        public int getTicket_buy_discount() {
            return ticket_buy_discount;
        }

        public void setTicket_buy_discount(int ticket_buy_discount) {
            this.ticket_buy_discount = ticket_buy_discount;
        }

        public String getGoods_gift() {
            return goods_gift;
        }

        public void setGoods_gift(String goods_gift) {
            this.goods_gift = goods_gift;
        }

        public void setChecked(boolean checked) {
            mChecked = checked;
        }

        public boolean isChecked() {
            return mChecked;
        }
    }

    public static class NumArrBean {
        /**
         * normal : 12
         * down : 0
         * out : 1
         */

        private String normal;
        private String down;
        private String out;

        public String getNormal() {
            return normal;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getDown() {
            return down;
        }

        public void setDown(String down) {
            this.down = down;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }
    }
}
