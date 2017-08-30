package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/25 0025
 * 时间：11:30
 * 描述：
 * ===============Txunda===============
 */

public class MellInfoList {
    /**
     * isSelect : false
     * merInfo : {"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介","score":"评分","logo":"店铺logo"}
     * goodsList : [{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"商品价格"}]
     */

    private MerInfo merInfo;
    private List<GoodsList> goodsList;

    /**
     * 收藏id
     */
    private String collect_id;

    private boolean select = false;

    public MerInfo getMerInfo() {
        return merInfo;
    }

    public void setMerInfo(MerInfo merInfo) {
        this.merInfo = merInfo;
    }

    public List<GoodsList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsList> goodsList) {
        this.goodsList = goodsList;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public static class MerInfo {
        /**
         * merchant_id : 店铺id
         * merchant_name : 店铺名称
         * merchant_desc : 店铺简介
         * score : 评分
         * logo : 店铺logo
         */

        private String merchant_id;
        private String merchant_name;
        private String merchant_desc;
        private String score;
        private String logo;

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

        public String getMerchant_desc() {
            return merchant_desc;
        }

        public void setMerchant_desc(String merchant_desc) {
            this.merchant_desc = merchant_desc;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class GoodsList {
        /**
         * goods_id : 商品ID
         * goods_img : 商品图片
         * shop_price : 商品价格
         */

        private String goods_id;
        private String goods_img;
        private String shop_price;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
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


    }
}
