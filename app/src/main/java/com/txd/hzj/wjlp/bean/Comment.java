package com.txd.hzj.wjlp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/23 0023
 * 时间：14:30
 * 描述：
 * ===============Txunda===============
 */

public class Comment {


    /**
     * code : 1
     * message : 请求成功
     * data : [{"comment_id":"评论ID","goods_id":"商品ID","goods_name":"商品名称","user_id":"用户ID","nickname":"昵称",
     * "pictures":[{"path":"评论图片"}],"content":"评论内容","all_star":"评论星级","product_id":"货品id",
     * "order_goods_id":"订单对应的商品","create_time":"创建时间","user_head_pic":"用户头像","good_attr":"商品属性","goods_num":"商品数量",
     * "shop_price":"价格","goods_img":"商品图片","\u201corder_type\u201d":"订单类型"}]
     * nums : 评价条数
     */

    private String code;
    private String message;
    private int nums;
    private List<CommentList> data;

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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public List<CommentList> getData() {
        return data;
    }

    public void setData(List<CommentList> data) {
        this.data = data;
    }

    public static class CommentList {
        /**
         * comment_id : 评论ID
         * goods_id : 商品ID
         * goods_name : 商品名称
         * user_id : 用户ID
         * nickname : 昵称
         * pictures : [{"path":"评论图片"}]
         * content : 评论内容
         * all_star : 评论星级
         * product_id : 货品id
         * order_goods_id : 订单对应的商品
         * create_time : 创建时间
         * user_head_pic : 用户头像
         * good_attr : 商品属性
         * goods_num : 商品数量
         * shop_price : 价格
         * goods_img : 商品图片
         * “order_type” : 订单类型
         */

        private String comment_id;
        private String goods_id;
        private String goods_name;
        private String user_id;
        private String nickname;
        private String content;
        private String all_star;
        private String product_id;
        private String order_goods_id;
        private String create_time;
        private String user_head_pic;
        private String good_attr;
        private String goods_num;
        private String shop_price;
        private String goods_img;
        private String order_type; //  '订单类型'  //0普通订单 1团购订单 2无界预购 3竞拍汇 4一元夺宝 5无界商店 6汽车购 7房产购 8线下商城
        private List<Pictures> pictures;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAll_star() {
            return all_star;
        }

        public void setAll_star(String all_star) {
            this.all_star = all_star;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOrder_goods_id() {
            return order_goods_id;
        }

        public void setOrder_goods_id(String order_goods_id) {
            this.order_goods_id = order_goods_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_head_pic() {
            return user_head_pic;
        }

        public void setUser_head_pic(String user_head_pic) {
            this.user_head_pic = user_head_pic;
        }

        public String getGood_attr() {
            return good_attr;
        }

        public void setGood_attr(String good_attr) {
            this.good_attr = good_attr;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public List<Pictures> getPictures() {
            return pictures;
        }

        public void setPictures(List<Pictures> pictures) {
            this.pictures = pictures;
        }

        public static class Pictures {
            /**
             * path : 评论图片
             */

            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            @Override
            public String toString() {
                return "Pictures{" +
                        "path='" + path + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "CommentList{" +
                    "comment_id='" + comment_id + '\'' +
                    ", goods_id='" + goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", content='" + content + '\'' +
                    ", all_star='" + all_star + '\'' +
                    ", product_id='" + product_id + '\'' +
                    ", order_goods_id='" + order_goods_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", user_head_pic='" + user_head_pic + '\'' +
                    ", good_attr='" + good_attr + '\'' +
                    ", goods_num='" + goods_num + '\'' +
                    ", shop_price='" + shop_price + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", _$Order_type49='" + order_type + '\'' +
                    ", pictures=" + pictures +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", nums=" + nums +
                ", data=" + data +
                '}';
    }
}
