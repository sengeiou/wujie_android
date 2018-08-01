package com.txd.hzj.wjlp.bean.offline;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/31 13:43
 * 功能描述：线下商城搜索列表bean类
 */
public class OffLineListBean {
    /**
     * code : 1
     * message : 请求成功
     * data : [{"merchant_id":"38","merchant_name":"达令商城","logo":"http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg","score":"5.0","merchant_desc":"618爆款手机带回家，心动不如行动！！门店地址：红桥区兴县勉县鞍山西道与白堤路交口风湖里15号楼人！"},{"merchant_id":"39","merchant_name":"达令服饰","logo":"http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca1a10d09.jpg","score":"5.0","merchant_desc":"无界商城战略合作供应商，专营各类精挑细选、百里挑一、保证品质的高性价比食品，欢迎您选购！"}]
     * nums : 0
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
         * merchant_id : 38
         * merchant_name : 达令商城
         * logo : http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg
         * score : 5.0
         * merchant_desc : 618爆款手机带回家，心动不如行动！！门店地址：红桥区兴县勉县鞍山西道与白堤路交口风湖里15号楼人！
         */

        private String merchant_id;
        private String merchant_name;
        private String logo;
        private String score;
        private String merchant_desc;

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
    }
}
