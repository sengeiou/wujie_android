package com.txd.hzj.wjlp.bean.footPoint;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/22 11:37
 * 功能描述：线下店铺我的收藏的bean类
 */
public class OffLineCollectBean {

    /**
     * code : 1
     * message : 获取成功
     * data : [{"collect_id":"124","s_id":"41","merchant_name":"达令服装店","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-07-25/5b5823314ce70.jpg","score":"5.0","merchant_desc":"回馈老顾客，全场8折起","user_id":"1559","star":["1","1","1","1","1"],"months_order":"1"}]
     * nums : 1
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
         * collect_id : 124
         * s_id : 41
         * merchant_name : 达令服装店
         * logo : http://dev.wujiemall.com/Uploads/Manager/2018-07-25/5b5823314ce70.jpg
         * score : 5.0
         * merchant_desc : 回馈老顾客，全场8折起
         * user_id : 1559
         * star : ["1","1","1","1","1"]
         * months_order : 1
         * goods_num
         */

        private String collect_id;
        private String s_id;
        private String merchant_name;
        private String logo;
        private String score;
        private String merchant_desc;
        private String user_id;
        private String months_order;
        private List<String> star;
        private String goods_num;

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(String collect_id) {
            this.collect_id = collect_id;
        }

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getMerchant_desc() {
            return merchant_desc;
        }

        public void setMerchant_desc(String merchant_desc) {
            this.merchant_desc = merchant_desc;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMonths_order() {
            return months_order;
        }

        public void setMonths_order(String months_order) {
            this.months_order = months_order;
        }

        public List<String> getStar() {
            return star;
        }

        public void setStar(List<String> star) {
            this.star = star;
        }
    }
}
