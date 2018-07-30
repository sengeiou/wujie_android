package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/30 030 上午 10:35
 * 功能描述：限量购对象
 */
public class LimitBuyBean {


    /**
     * code : 1
     * message : 获取成功
     * data : {"stage_list":[{"stage_id":"1","stage_name":"早场","start_time":"10:00","end_time":"12:00","status":"抢购进行中"},{"stage_id":"2","stage_name":"午场","start_time":"12:00","end_time":"14:00","status":"即将开始"},{"stage_id":"3","stage_name":"下午场","start_time":"14:00","end_time":"16:00","status":"即将开始"}],"ads":{"ads_id":"47","picture":"http://img.wujiemall.com/Uploads/Ads/2018-03-09/5aa2425768884.png","desc":"放血特价，限量抢购","href":"","position":"22","merchant_id":"0","goods_id":"0"},"end_time":"12:00","start_time":"10:00","limitBuyList":[{"limit_buy_id":"1","limit_price":"54.00","limit_store":"54","limit_num":"66","integral":"27.00","sell_num":"0","market_price":"0.00","goods_name":"测试价格体系1","goods_img":"http://img.wujiemall.com/Uploads/Goods/2018-05-21/5b026334b5592.png","country_id":"37","ticket_buy_id":"0","id":"657","start_time":"1532916000","end_time":"1532923200","is_remind":"0","country_logo":"http://img.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png","ticket_buy_discount":"0"}],"list":[{"ticket_buy_discount":0}]}
     * nums : 1
     */

    private String code; // 回传值
    private String message; // 回传消息
    private DataBean data; // 回传数据
    private String nums; // 商品数量，保留字段

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
        /**
         * stage_list : [{"stage_id":"1","stage_name":"早场","start_time":"10:00","end_time":"12:00","status":"抢购进行中"},{"stage_id":"2","stage_name":"午场","start_time":"12:00","end_time":"14:00","status":"即将开始"},{"stage_id":"3","stage_name":"下午场","start_time":"14:00","end_time":"16:00","status":"即将开始"}]
         * ads : {"ads_id":"47","picture":"http://img.wujiemall.com/Uploads/Ads/2018-03-09/5aa2425768884.png","desc":"放血特价，限量抢购","href":"","position":"22","merchant_id":"0","goods_id":"0"}
         * end_time : 12:00
         * start_time : 10:00
         * limitBuyList : [{"limit_buy_id":"1","limit_price":"54.00","limit_store":"54","limit_num":"66","integral":"27.00","sell_num":"0","market_price":"0.00","goods_name":"测试价格体系1","goods_img":"http://img.wujiemall.com/Uploads/Goods/2018-05-21/5b026334b5592.png","country_id":"37","ticket_buy_id":"0","id":"657","start_time":"1532916000","end_time":"1532923200","is_remind":"0","country_logo":"http://img.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png","ticket_buy_discount":"0"}]
         * list : [{"ticket_buy_discount":0}]
         */

        private AdsBean ads; // 广告轮播图对象
        private String end_time; // 结束时间
        private String start_time; // 开始时间
        private List<StageListBean> stage_list; // 时段标签对象
        private List<LimitBuyListBean> limitBuyList; // 商品对象
        private List<ListBean> list; // 可使用购物券折扣率对象

        public AdsBean getAds() {
            return ads;
        }

        public void setAds(AdsBean ads) {
            this.ads = ads;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public List<StageListBean> getStage_list() {
            return stage_list;
        }

        public void setStage_list(List<StageListBean> stage_list) {
            this.stage_list = stage_list;
        }

        public List<LimitBuyListBean> getLimitBuyList() {
            return limitBuyList;
        }

        public void setLimitBuyList(List<LimitBuyListBean> limitBuyList) {
            this.limitBuyList = limitBuyList;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class AdsBean {
            /**
             * ads_id : 47
             * picture : http://img.wujiemall.com/Uploads/Ads/2018-03-09/5aa2425768884.png
             * desc : 放血特价，限量抢购
             * href :
             * position : 22
             * merchant_id : 0
             * goods_id : 0
             */

            private String ads_id; // 广告id
            private String picture; // 广告图片
            private String desc; // 应该是描述哈
            private String href; // 广告链接
            private String position; // 索引位置应该
            private String merchant_id; // 商家id
            private String goods_id; // 商品id

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
        }

        /**
         * 标签对象
         */
        public static class StageListBean {
            /**
             * stage_id : 1
             * stage_name : 早场
             * start_time : 10:00
             * end_time : 12:00
             * status : 抢购进行中
             */

            private String stage_id; // 状态标签id
            private String stage_name; // 状态名称：早场，中午场....
            private String start_time; // 开始时间
            private String end_time; // 结束时间
            private String status; // 当前状态

            public String getStage_id() {
                return stage_id;
            }

            public void setStage_id(String stage_id) {
                this.stage_id = stage_id;
            }

            public String getStage_name() {
                return stage_name;
            }

            public void setStage_name(String stage_name) {
                this.stage_name = stage_name;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class LimitBuyListBean {
            /**
             * limit_buy_id : 1
             * limit_price : 54.00
             * limit_store : 54
             * limit_num : 66
             * integral : 27.00
             * sell_num : 0
             * market_price : 0.00
             * goods_name : 测试价格体系1
             * goods_img : http://img.wujiemall.com/Uploads/Goods/2018-05-21/5b026334b5592.png
             * country_id : 37
             * ticket_buy_id : 0
             * id : 657
             * start_time : 1532916000
             * end_time : 1532923200
             * is_remind : 0
             * country_logo : http://img.wujiemall.com/Uploads/Country/2018-02-06/5a7942dcee41c.png
             * ticket_buy_discount : 0
             */

            private String limit_buy_id; // 限量购商品id
            private String limit_price; // 限量购商品价格
            private String limit_store; // 商店
            private String limit_num; // 数量
            private String integral; // 返还积分
            private String sell_num; // 出售数量
            private String market_price; // 市场价格
            private String goods_name; // 商品名称
            private String goods_img; // 商品图片
            private String country_id; // 国家id
            private String ticket_buy_id; // 票券id
            private String id; // id
            private String start_time; // 商品开始时间
            private String end_time; // 结束时间
            private String is_remind; // 是否提醒，0没有提醒，1有提醒
            private String country_logo; // 国家Logo
            private String ticket_buy_discount; // 可使用购物券折扣率

            public String getLimit_buy_id() {
                return limit_buy_id;
            }

            public void setLimit_buy_id(String limit_buy_id) {
                this.limit_buy_id = limit_buy_id;
            }

            public String getLimit_price() {
                return limit_price;
            }

            public void setLimit_price(String limit_price) {
                this.limit_price = limit_price;
            }

            public String getLimit_store() {
                return limit_store;
            }

            public void setLimit_store(String limit_store) {
                this.limit_store = limit_store;
            }

            public String getLimit_num() {
                return limit_num;
            }

            public void setLimit_num(String limit_num) {
                this.limit_num = limit_num;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getSell_num() {
                return sell_num;
            }

            public void setSell_num(String sell_num) {
                this.sell_num = sell_num;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
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

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getTicket_buy_id() {
                return ticket_buy_id;
            }

            public void setTicket_buy_id(String ticket_buy_id) {
                this.ticket_buy_id = ticket_buy_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getIs_remind() {
                return is_remind;
            }

            public void setIs_remind(String is_remind) {
                this.is_remind = is_remind;
            }

            public String getCountry_logo() {
                return country_logo;
            }

            public void setCountry_logo(String country_logo) {
                this.country_logo = country_logo;
            }

            public String getTicket_buy_discount() {
                return ticket_buy_discount;
            }

            public void setTicket_buy_discount(String ticket_buy_discount) {
                this.ticket_buy_discount = ticket_buy_discount;
            }
        }

        public static class ListBean {
            /**
             * ticket_buy_discount : 0
             */

            private int ticket_buy_discount; // 可使用购物券折扣率

            public int getTicket_buy_discount() {
                return ticket_buy_discount;
            }

            public void setTicket_buy_discount(int ticket_buy_discount) {
                this.ticket_buy_discount = ticket_buy_discount;
            }
        }
    }
}
