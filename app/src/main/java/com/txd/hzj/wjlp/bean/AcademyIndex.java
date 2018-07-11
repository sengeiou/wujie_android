package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：10:48
 * 描述：无界书院首页
 */

public class AcademyIndex {


    /**
     * code : 1
     * message : 获取成功
     * data : {"bannerList":[{"ads_id":"轮播图id","desc":"轮播图说明","href":"图片链接","picture":"图片路径"}],
     * "academy_list":[{"academy_id":"文章id","title":"文章标题","logo":"文章封面图","page_views":"浏览量","collect_num":"收藏数"}]}
     * nums : 文章数
     */

    private String code;
    private String message;
    private DataBean data;
    private int nums;

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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class DataBean {
        private List<BannerList> bannerList;
        private List<AcademyList> academy_list;

        public List<BannerList> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerList> bannerList) {
            this.bannerList = bannerList;
        }

        public List<AcademyList> getAcademy_list() {
            return academy_list;
        }

        public void setAcademy_list(List<AcademyList> academy_list) {
            this.academy_list = academy_list;
        }

        public static class BannerList {
            /**
             * ads_id : 轮播图id
             * desc : 轮播图说明
             * href : 图片链接
             * picture : 图片路径
             */

            private String ads_id;
            private String desc;
            private String href;
            private String picture;
            private String goods_id;
            private String merchant_id;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getAds_id() {
                return ads_id;
            }

            public void setAds_id(String ads_id) {
                this.ads_id = ads_id;
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

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
