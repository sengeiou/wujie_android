package com.txd.hzj.wjlp.bean.search;

import com.txd.hzj.wjlp.bean.CFGoodsList;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：10:55
 * 描述：商品搜索
 * ===============Txunda===============
 */

public class SeaechBean {


    /**
     * code : 1
     * message : 搜索成功
     * data : {"list":[{"goods_id":"商品ID","goods_name":"商品名称","goods_img":"商品图片","market_price":"市场价",
     * "shop_price":"销售价","integral":"积分","sell_num":"销量","ticket_buy_id":"","country_id":"0",
     * "country_logo":"国家Logo","ticket_buy_discount":"购物券折扣"}],"search_content":"搜索的内容"}
     * nums : 商品数量
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
        /**
         * list : [{"goods_id":"商品ID","goods_name":"商品名称","goods_img":"商品图片","market_price":"市场价","shop_price":"销售价",
         * "integral":"积分","sell_num":"销量","ticket_buy_id":"","country_id":"0","country_logo":"国家Logo",
         * "ticket_buy_discount":"购物券折扣"}]
         * search_content : 搜索的内容
         */

        private String search_content;
        private List<CFGoodsList> list;

        public String getSearch_content() {
            return search_content;
        }

        public void setSearch_content(String search_content) {
            this.search_content = search_content;
        }

        public List<CFGoodsList> getList() {
            return list;
        }

        public void setList(List<CFGoodsList> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "search_content='" + search_content + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SeaechBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
