package com.txd.hzj.wjlp.bean.search;

import com.txd.hzj.wjlp.bean.footPoint.FootMellsBan;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：11:15
 * 描述：商家搜索
 *
 */

public class SearchMell {


    /**
     * code : 1
     * message : 搜索成功
     * data : {"list":[{"merInfo":{"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介","score":"店铺评分",
     * "logo":"店铺logo"},"goodsList":[{"goods_id":"商品id","goods_img":"商品图片","shop_price":"售价"}]}],
     * "search_content":"搜索的内容"}
     * nums : 搜索的店铺数量
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
         * list : [{"merInfo":{"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介","score":"店铺评分",
         * "logo":"店铺logo"},"goodsList":[{"goods_id":"商品id","goods_img":"商品图片","shop_price":"售价"}]}]
         * search_content : 搜索的内容
         */

        private String search_content;
        private List<FootMellsBan> list;

        public String getSearch_content() {
            return search_content;
        }

        public void setSearch_content(String search_content) {
            this.search_content = search_content;
        }

        public List<FootMellsBan> getList() {
            return list;
        }

        public void setList(List<FootMellsBan> list) {
            this.list = list;
        }
    }
}
