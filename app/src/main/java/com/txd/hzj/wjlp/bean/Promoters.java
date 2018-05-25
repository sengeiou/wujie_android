package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 作者 Created by 王尧 on 2018/5/25.
 */

public class Promoters {
    /**
     * code : 1
     * message : 获取成功
     * data : {"ads":[{"ads_id":"96","picture":"http://test.wujiemall.com/Uploads/Ads/2018-05-25/5b07a861c79e2.jpg","desc":"诚招无界推广员","href":"","position":"40","merchant_id":"0","goods_id":"0"}]}
     * nums : 1
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
        private List<AdsBean> ads;

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class AdsBean {
            /**
             * ads_id : 96
             * picture : http://test.wujiemall.com/Uploads/Ads/2018-05-25/5b07a861c79e2.jpg
             * desc : 诚招无界推广员
             * href :
             * position : 40
             * merchant_id : 0
             * goods_id : 0
             */

            private String ads_id;
            private String picture;
            private String desc;
            private String href;
            private String position;
            private String merchant_id;
            private String goods_id;

            public String getAds_id() {
                return ads_id;
            }

            public void setAds_id(String ads_id) {
                this.ads_id = ads_id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            @Override
            public String toString() {
                return "AdsBean{" +
                        "ads_id='" + ads_id + '\'' +
                        ", picture='" + picture + '\'' +
                        ", desc='" + desc + '\'' +
                        ", href='" + href + '\'' +
                        ", position='" + position + '\'' +
                        ", merchant_id='" + merchant_id + '\'' +
                        ", goods_id='" + goods_id + '\'' +
                        '}';
            }
        }

    }

    @Override
    public String toString() {
        return "Promoters{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums='" + nums + '\'' +
                '}';
    }
}
