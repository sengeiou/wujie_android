package com.txd.hzj.wjlp.bean.footPoint;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：09:09
 * 描述：足迹商家详情
 * ===============Txunda===============
 */

public class FootMellsBan {
    /**
     * merInfo : {"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介","score":"评分","logo":"店铺logo"}
     * goodsList : [{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"商品价格"}]
     */

    private MerInfoBean merInfo;
    private List<GoodsListBean> goodsList;

    private boolean select = false;

    public MerInfoBean getMerInfo() {
        return merInfo;
    }

    public void setMerInfo(MerInfoBean merInfo) {
        this.merInfo = merInfo;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public static class MerInfoBean {
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

        @Override
        public String toString() {
            return "MerInfoBean{" +
                    "merchant_id='" + merchant_id + '\'' +
                    ", merchant_name='" + merchant_name + '\'' +
                    ", merchant_desc='" + merchant_desc + '\'' +
                    ", score='" + score + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FootMellsBan{" +
                "merInfo=" + merInfo +
                ", goodsList=" + goodsList +
                ", select=" + select +
                '}';
    }
}
