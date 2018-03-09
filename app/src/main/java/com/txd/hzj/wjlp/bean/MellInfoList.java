package com.txd.hzj.wjlp.bean;

import com.txd.hzj.wjlp.bean.footPoint.GoodsListBean;

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
     * collect_id : 收藏编号
     * mid : 店铺ID
     * merchantFace : {"merInfo":{"merchant_id":"店铺ID","merchant_name":"店铺名称","merchant_desc":"店铺描述","score":"评分",
     * "logo":"店铺Logo"},"goodsList":[{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"售价"}]}
     */

    private String collect_id;
    private String mid;
    private MerchantFaceBean merchantFace;

    private boolean select = false;

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public MerchantFaceBean getMerchantFace() {
        return merchantFace;
    }

    public void setMerchantFace(MerchantFaceBean merchantFace) {
        this.merchantFace = merchantFace;
    }
    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public static class MerchantFaceBean {
        /**
         * merInfo : {"merchant_id":"店铺ID","merchant_name":"店铺名称","merchant_desc":"店铺描述","score":"评分","logo":"店铺Logo"}
         * goodsList : [{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"售价"}]
         */

        private MerInfoBean merInfo;
        private List<GoodsListBean> goodsList;

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

        public static class MerInfoBean {
            /**
             * merchant_id : 店铺ID
             * merchant_name : 店铺名称
             * merchant_desc : 店铺描述
             * score : 评分
             * logo : 店铺Logo
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
            return "MerchantFaceBean{" +
                    "merInfo=" + merInfo +
                    ", goodsList=" + goodsList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MellInfoList{" +
                "collect_id='" + collect_id + '\'' +
                ", mid='" + mid + '\'' +
                ", merchantFace=" + merchantFace +
                '}';
    }
}
