package com.txd.hzj.wjlp.bean.auction;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：13:48
 * 描述：竞拍汇首页实体类
 */

public class AuctonIndex {

    /**
     * code : 1
     * message : 获取成功
     * data : {"ads":{"ads_id":"广告id","picture":"广告图片","desc":"广告描述","href":"广告链接"},
     * "auction_list":[{"auction_id":"拍卖id","start_time":"拍卖开始时间","end_time":"结束时间","integral":"积分",
     * "goods_name":"商品名称","goods_img":"商品图片","country_id":"国家id","country_logo":"国家logo","ticket_buy_id":"折扣id",
     * "ticket_buy_discount":"折扣率"}]}
     * nums : 商品数
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
         * ads : {"ads_id":"广告id","picture":"广告图片","desc":"广告描述","href":"广告链接"}
         * auction_list : [{"auction_id":"拍卖id","start_time":"拍卖开始时间","end_time":"结束时间","integral":"积分",
         * "goods_name":"商品名称","goods_img":"商品图片","country_id":"国家id","country_logo":"国家logo","ticket_buy_id":"折扣id",
         * "ticket_buy_discount":"折扣率"}]
         */

        private AdsBean ads;
        private List<AuctionList> auction_list;

        public AdsBean getAds() {
            return ads;
        }

        public void setAds(AdsBean ads) {
            this.ads = ads;
        }

        public List<AuctionList> getAuction_list() {
            return auction_list;
        }

        public void setAuction_list(List<AuctionList> auction_list) {
            this.auction_list = auction_list;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "ads=" + ads +
                    ", auction_list=" + auction_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuctonIndex{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
