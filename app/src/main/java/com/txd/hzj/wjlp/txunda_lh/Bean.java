package com.txd.hzj.wjlp.txunda_lh;

import java.util.List;

/**
 * {@link com.txd.hzj.wjlp.txunda_lh.aty_collocations(com.txd.hzj.wjlp.txunda_lh)}
 * by Txunda_LH on 2017/11/24.
 * 搭配购的实体类
 */

public class Bean {

    /**
     * cheap_group_id : 4
     * group_name : 搭配购测试2
     * group_price : 30.00
     * integral : 21.57
     * ticket_buy_discount : 10
     * goods_price : 115
     * goods : [{"goods_id":"13","goods_name":"蓝莓酒糯米酒","goods_img":"http://wjyp.txunda.com/Uploads/Goods/2017-11-09/5a041865d69a5.jpg","shop_price":"66.00"},{"goods_id":"11","goods_name":"非转基因黑豆油","goods_img":"http://wjyp.txunda.com/Uploads/Goods/2017-11-09/5a041517d0793.jpg","shop_price":"49.00"}]
     */

    private String cheap_group_id;
    private String group_name;
    private String group_price;
    private String integral;
    private String ticket_buy_discount;
    private String goods_price;
    private boolean isChack = true;
    private List<GoodsBean> goods;

    public boolean isChack() {
        return isChack;
    }

    public void setChack(boolean chack) {
        isChack = chack;
    }

    public String getCheap_group_id() {
        return cheap_group_id;
    }

    public void setCheap_group_id(String cheap_group_id) {
        this.cheap_group_id = cheap_group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 13
         * goods_name : 蓝莓酒糯米酒
         * goods_img : http://wjyp.txunda.com/Uploads/Goods/2017-11-09/5a041865d69a5.jpg
         * shop_price : 66.00
         */

        private String goods_id;
        private String goods_name;
        private String goods_img;
        private String shop_price;

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

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        @Override
        public String toString() {
            return "GoodsBean{" +
                    "goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", shop_price='" + shop_price + '\'' +
                    '}';
        }
    }
}
