package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：11:55
 * 描述：
 * ===============Txunda===============
 */

public class GroupBuyBean {

    /**
     * code : 1
     * message : 获取成功
     * data : {"top_nav":[{"cate_id":"3","short_name":"生鲜","name":"生鲜"}],"two_cate_list":[{"two_cate_id":"5",
     * "short_name":"零食","name":"零食","cate_img":"http://wjyp.txunda.com/Uploads/Cate/default.png"}],
     * "ads":{"ads_id":"9","picture":"http://wjyp.txunda.com/Uploads/Ads/2017-08-14/59910a55e3f3f.png","desc":"【心愿】",
     * "href":"http://www.baidu.com"},"group_buy_list":[{"group_buy_id":"1","group_price":"2.80","group_num":"2",
     * "total":"0","integral":"0","goods_name":"格力大薯片","goods_img":"http://wjyp.txunda
     * .com/Uploads/Goods/2017-07-29/597c58e7c3d49.jpg","country_id":"12","ticket_buy_id":"3",
     * "country_logo":"http://wjyp.txunda.com/Uploads/Country/2017-08-15/59926545b9cbc.jpg",
     * "ticket_buy_discount":"20","append_person":[{"log_id":"2","user_id":"5","head_pic":"http://wjyp.txunda
     * .com/Uploads/User/2017-07-29/597c1ad6a538e.jpg"}]}]}
     * nums : 2
     */

    private String code;
    private String message;
    private Data data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class Data {
        /**
         * top_nav : [{"cate_id":"3","short_name":"生鲜","name":"生鲜"}]
         * two_cate_list : [{"two_cate_id":"5","short_name":"零食","name":"零食","cate_img":"http://wjyp.txunda
         * .com/Uploads/Cate/default.png"}]
         * ads : {"ads_id":"9","picture":"http://wjyp.txunda.com/Uploads/Ads/2017-08-14/59910a55e3f3f.png",
         * "desc":"【心愿】","href":"http://www.baidu.com"}
         * group_buy_list : [{"group_buy_id":"1","group_price":"2.80","group_num":"2","total":"0","integral":"0",
         * "goods_name":"格力大薯片","goods_img":"http://wjyp.txunda.com/Uploads/Goods/2017-07-29/597c58e7c3d49.jpg",
         * "country_id":"12","ticket_buy_id":"3","country_logo":"http://wjyp.txunda
         * .com/Uploads/Country/2017-08-15/59926545b9cbc.jpg","ticket_buy_discount":"20",
         * "append_person":[{"log_id":"2","user_id":"5","head_pic":"http://wjyp.txunda
         * .com/Uploads/User/2017-07-29/597c1ad6a538e.jpg"}]}]
         */

        private AdsBean ads;
        private List<TopNavBean> top_nav;
        private List<TwoCateListBean> two_cate_list;
        //==========拼团购==========
        private List<AllGoodsBean> group_buy_list;
<<<<<<< HEAD
        // ==========无界预购==========
        private List<AllGoodsBean> pre_buy_list;
        // ==========票券区=========
        private List<AllGoodsBean> ticket_buy_list;
        // ==========无界商店=========
=======
        // ==========xfte预购==========
        private List<AllGoodsBean> pre_buy_list;
        // ==========票券区=========
        private List<AllGoodsBean> ticket_buy_list;
        // ==========xfte商店=========
>>>>>>> master
        private List<AllGoodsBean> integral_buy_list;
        // ==========进口馆==========
        private List<AllGoodsBean> list;


        public AdsBean getAds() {
            return ads;
        }

        public void setAds(AdsBean ads) {
            this.ads = ads;
        }

        public List<TopNavBean> getTop_nav() {
            return top_nav;
        }

        public void setTop_nav(List<TopNavBean> top_nav) {
            this.top_nav = top_nav;
        }

        public List<TwoCateListBean> getTwo_cate_list() {
            return two_cate_list;
        }

        public void setTwo_cate_list(List<TwoCateListBean> two_cate_list) {
            this.two_cate_list = two_cate_list;
        }

        public List<AllGoodsBean> getGroup_buy_list() {
            return group_buy_list;
        }

        public void setGroup_buy_list(List<AllGoodsBean> group_buy_list) {
            this.group_buy_list = group_buy_list;
        }

        public List<AllGoodsBean> getPre_buy_list() {
            return pre_buy_list;
        }

        public void setPre_buy_list(List<AllGoodsBean> pre_buy_list) {
            this.pre_buy_list = pre_buy_list;
        }

        public List<AllGoodsBean> getTicket_buy_list() {
            return ticket_buy_list;
        }

        public void setTicket_buy_list(List<AllGoodsBean> ticket_buy_list) {
            this.ticket_buy_list = ticket_buy_list;
        }

        public List<AllGoodsBean> getIntegral_buy_list() {
            return integral_buy_list;
        }

        public void setIntegral_buy_list(List<AllGoodsBean> integral_buy_list) {
            this.integral_buy_list = integral_buy_list;
        }

        public List<AllGoodsBean> getList() {
            return list;
        }

        public void setList(List<AllGoodsBean> list) {
            this.list = list;
        }

        public static class AdsBean {
            /**
             * ads_id : 9
             * picture : http://wjyp.txunda.com/Uploads/Ads/2017-08-14/59910a55e3f3f.png
             * desc : 【心愿】
             * href : http://www.baidu.com
             */

            private String ads_id;
            private String picture;
            private String desc;
            private String href;
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

            @Override
            public String toString() {
                return "AdsBean{" +
                        "ads_id='" + ads_id + '\'' +
                        ", picture='" + picture + '\'' +
                        ", desc='" + desc + '\'' +
                        ", href='" + href + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "ads=" + ads +
                    ", top_nav=" + top_nav +
                    ", two_cate_list=" + two_cate_list +
                    ", group_buy_list=" + group_buy_list +
                    ", pre_buy_list=" + pre_buy_list +
                    ", ticket_buy_list=" + ticket_buy_list +
                    ", integral_buy_list=" + integral_buy_list +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GroupBuyBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
