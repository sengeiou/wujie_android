package com.txd.hzj.wjlp.bean.groupbuy;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:26
 * 描述：团购评论
 * ===============Txunda===============
 */

public class CommentBean {
    /**
     * body : {"comment_id":"评论ID","goods_id":"商品ID","goods_name":"商品名称","user_id":"用户ID","nickname":"用户昵称",
     * "pictures":[{"path":"图片路径"}],"content":"评论内容","all_star":"评论星级","product_id":"货品ID",
     * "order_goods_id":"1","create_time":"创建时间","user_head_pic":"用户头像","good_attr":"所评价商品属性",
     * "goods_num":"所买数量","shop_price":"售价","goods_img":"商品图片","order_type":"订单类型"}
     * total : 1
     */

    private BodyBean body;
    private String total;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    // 评论详情
    public static class BodyBean {
        /**
         * comment_id : 评论ID
         */
        private String comment_id;
        /**
         * goods_id : 商品ID
         */
        private String goods_id;
        /**
         * goods_name : 商品名称
         */
        private String goods_name;
        /**
         * "user_id": "用户ID",
         */
        private String user_id;
        /**
         * 昵称
         */
        private String nickname;
        /**
         * 评论内容
         */
        private String content;
        /**
         * 评论星级
         */
        private String all_star;
        /**
         * 货品id
         */
        private String product_id;
        /**
         *
         */
        private String order_goods_id;
        /**
         * 创建时间
         */
        private String create_time;
        /**
         * 用户头像
         */
        private String user_head_pic;
        /**
         * 商品属性
         */
        private String good_attr;
        /**
         * 商品数量
         */
        private String goods_num;
        /**
         * 售价
         */
        private String shop_price;
        /**
         * 商品图片
         */
        private String goods_img;
        /**
         * 订单类型
         */
        private String order_type;
        /**
         * 评论图片
         */
        private List<PicturesBean> pictures;

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

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }
        // 图片
        public static class PicturesBean {
            /**
             * path : 图片路径
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
                return "PicturesBean{" +
                        "path='" + path + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "BodyBean{" +
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
                    ", order_type='" + order_type + '\'' +
                    ", pictures=" + pictures +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "body=" + body +
                ", total='" + total + '\'' +
                '}';
    }
}
