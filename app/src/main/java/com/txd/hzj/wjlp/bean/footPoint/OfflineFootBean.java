package com.txd.hzj.wjlp.bean.footPoint;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/22 14:20
 * 功能描述：线下店铺我的足迹
 */
public class OfflineFootBean {

    /**
     * code : 1
     * message : 获取成功
     * data : [{"footer_id":"2457","merchant_id":"73","merchant_name":"若晨的小破店","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-14/5b72bade71db8.jpg","score":"5.0","merchant_desc":"若晨小店","user_id":"11","star":["1","1","1","1","1"],"months_order":"69"},{"footer_id":"2525","merchant_id":"93","merchant_name":"万达影城(河东IMAX店)","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-21/1146141205-0.jpg","score":"5.0","merchant_desc":"万达影城成立于2005年，隶属于万达集团。\u201c一切以观众的观影价值和观影体验为核心\u201d是万达院线连锁经营服务的核心理念，\u201c连锁经营能力、创新营销能力、服务品牌能力\u201d是彰显万达电影核心竞争力的三大基本要素。作为文化娱乐消费，万达院线始终坚持把\u201c看电影\u201d，作为一种生活方式、生活状态去倡导，始终坚持传递一种\u201c乐享万达电影生活\u201d的生活理念，通过一系列产品与渠道的打造，经过6年来的品牌积淀，已经在大众消费者心目中初步树立了高品质电影服务终端品牌形象。","user_id":"0","star":["1","1","1","1","1"],"months_order":"0"},{"footer_id":"2520","merchant_id":"86","merchant_name":"SFC上影影城(天津天河城IMAX店)","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-21/11454052b-0.jpg","score":"5.0","merchant_desc":"","user_id":"0","star":["1","1","1","1","1"],"months_order":"0"},{"footer_id":"2235","merchant_id":"44","merchant_name":"达令超哥小炒","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-07-26/5b5936b28a7a9.jpg","score":"5.0","merchant_desc":"超哥小炒","user_id":"1511","star":["1","1","1","1","1"],"months_order":"1"},{"footer_id":"2189","merchant_id":"38","merchant_name":"达令商城","logo":"http://dev.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg","score":"5.0","merchant_desc":"618爆款手机带回家，心动不如行动！！门店地址：红桥区兴县勉县鞍山西道与白堤路交口风湖里15号楼人！","user_id":"1508","star":["1","1","1","1","1"],"months_order":"26"},{"footer_id":"2198","merchant_id":"42","merchant_name":"达令友善餐厅","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-07-25/5b5841ac4b754.jpg","score":"5.0","merchant_desc":"友善餐厅","user_id":"1560","star":["1","1","1","1","1"],"months_order":"2"},{"footer_id":"2527","merchant_id":"57","merchant_name":"Hollister Co.(天银河国际购物中心店)","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-10/164512J93-0.png","score":"5.0","merchant_desc":"产品:9.1 环境:9.1 服务:8.7","user_id":"0","star":["1","1","1","1","1"],"months_order":"0"},{"footer_id":"2519","merchant_id":"50","merchant_name":"同聚成清真餐厅","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-06/1F50LT2-0.jpg","score":"5.0","merchant_desc":"口味:8.8 环境:8.0 服务:8.3","user_id":"0","star":["1","1","1","1","1"],"months_order":"1"},{"footer_id":"2445","merchant_id":"61","merchant_name":"大润发超市","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-13/5b7121ab3a4a3.png","score":"5.0","merchant_desc":"大润发超市","user_id":"1000","star":["1","1","1","1","1"],"months_order":"16"},{"footer_id":"2509","merchant_id":"49","merchant_name":"哈密古丽新疆餐厅","logo":"http://dev.wujiemall.com/Uploads/Manager/2018-08-06/1F2353507-0.jpg","score":"5.0","merchant_desc":"口味:7.6 环境:7.6 服务:7.6","user_id":"0","star":["1","1","1","1","1"],"months_order":"0"}]
     * nums : 16
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
         * footer_id : 2457
         * merchant_id : 73
         * merchant_name : 若晨的小破店
         * logo : http://dev.wujiemall.com/Uploads/Manager/2018-08-14/5b72bade71db8.jpg
         * score : 5.0
         * merchant_desc : 若晨小店
         * user_id : 11
         * star : ["1","1","1","1","1"]
         * months_order : 69
         */

        private String footer_id;
        private String merchant_id;
        private String merchant_name;
        private String logo;
        private String score;
        private String merchant_desc;
        private String user_id;
        private String months_order;
        private List<String> star;
        private String goods_num;
        private String s_id;

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getFooter_id() {
            return footer_id;
        }

        public void setFooter_id(String footer_id) {
            this.footer_id = footer_id;
        }

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
