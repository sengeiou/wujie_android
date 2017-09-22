package com.txd.hzj.wjlp.bean.groupbuy;

import com.txd.hzj.wjlp.bean.GoodsCommonAttr;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：09:34
 * 描述：
 * ===============Txunda===============
 */

public class GroupBuyInfo {

    /**
     * code : 1
     * message : 获取成功
     * data : {"msg_tip":"消息提醒数","is_collect":"是否收藏","cart_num":"购物车数量","goodsInfo":{"goods_id":"商品id ",
     * "goods_name":"商品名称","market_price":"市场价","shop_price":"售价","integral":"赠送积分","goods_desc":"商品图文详情",
     * "goods_brief":"商品简介","merchant_id":"店铺id","sell_num":"销量","ticket_buy_id":"是否属于票券区","country_id":"国家ID",
     * "country_logo":"国家logo","country_desc":"商品进口国家描述","country_tax":"进口税","ticket_buy_discount":"商品享受购物券折扣"},
     * "mInfo":{"merchant_id":"店铺id","merchant_name":"店铺名称","level":"店铺等级","logo":"店铺logo","view_num":"关注人数"},
     * "promotion":[{"title":"优惠活动名称","promotion_id":"优惠id","type":"类型"}],"ticketList":[{"ticket_id":"优惠券ID",
     * "ticket_name":"优惠券名称","ticket_desc":"优惠券详情","ticket_type":"优惠券类型","value":"面额","condition":"满足条件",
     * "start_time":"开始时间","end_time":"结束时间"}],"goodsAttr":[{"id":"id","aid":"属性ID","attr_name":"属性名称",
     * "attr_value":"属性值","attr_price":"属性附加价格"}],"goods_banner":[{"path":"轮播图"}],"attr_images":[{"attr_id":"属性id",
     * "pic":"属性图片"}],"product":[{"id":"75","goods_id":"12","goods_attr":"属性组合","product_sn":"货品号",
     * "product_number":"货品库存"}],"comment":{"body":{"comment_id":"评论ID","goods_id":"商品ID","goods_name":"商品名称",
     * "user_id":"用户ID","nickname":"用户昵称","pictures":[{"path":"图片路径"}],"content":"评论内容","all_star":"评论星级",
     * "product_id":"货品ID","order_goods_id":"1","create_time":"创建时间","user_head_pic":"用户头像","good_attr":"所评价商品属性",
     * "goods_num":"所买数量","shop_price":"售价","goods_img":"商品图片","order_type":"订单类型"},"total":"1"},
     * "group":[{"id":"团购ID","group_buy_id":"组团ID","start_time":"开始时间","status":"状态","group_num":"需参团人数",
     * "head_user":{"head_pic":"头像","nickname":"昵称"},"diff":"参团信息"}],"group_price":"团购价","one_price":"单买价"}
     * nums : 0
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
         * msg_tip : 消息提醒数
         * is_collect : 是否收藏
         * cart_num : 购物车数量
         * goodsInfo : {"goods_id":"商品id ","goods_name":"商品名称","market_price":"市场价","shop_price":"售价",
         * "integral":"赠送积分","goods_desc":"商品图文详情","goods_brief":"商品简介","merchant_id":"店铺id","sell_num":"销量",
         * "ticket_buy_id":"是否属于票券区","country_id":"国家ID","country_logo":"国家logo","country_desc":"商品进口国家描述",
         * "country_tax":"进口税","ticket_buy_discount":"商品享受购物券折扣"}
         * mInfo : {"merchant_id":"店铺id","merchant_name":"店铺名称","level":"店铺等级","logo":"店铺logo","view_num":"关注人数"}
         * promotion : [{"title":"优惠活动名称","promotion_id":"优惠id","type":"类型"}]
         * ticketList : [{"ticket_id":"优惠券ID","ticket_name":"优惠券名称","ticket_desc":"优惠券详情","ticket_type":"优惠券类型",
         * "value":"面额","condition":"满足条件","start_time":"开始时间","end_time":"结束时间"}]
         * goodsAttr : [{"id":"id","aid":"属性ID","attr_name":"属性名称","attr_value":"属性值","attr_price":"属性附加价格"}]
         * goods_banner : [{"path":"轮播图"}]
         * attr_images : [{"attr_id":"属性id","pic":"属性图片"}]
         * product : [{"id":"75","goods_id":"12","goods_attr":"属性组合","product_sn":"货品号","product_number":"货品库存"}]
         * comment : {"body":{"comment_id":"评论ID","goods_id":"商品ID","goods_name":"商品名称","user_id":"用户ID",
         * "nickname":"用户昵称","pictures":[{"path":"图片路径"}],"content":"评论内容","all_star":"评论星级","product_id":"货品ID",
         * "order_goods_id":"1","create_time":"创建时间","user_head_pic":"用户头像","good_attr":"所评价商品属性","goods_num":"所买数量",
         * "shop_price":"售价","goods_img":"商品图片","order_type":"订单类型"},"total":"1"}
         * group : [{"id":"团购ID","group_buy_id":"组团ID","start_time":"开始时间","status":"状态","group_num":"需参团人数",
         * "head_user":{"head_pic":"头像","nickname":"昵称"},"diff":"参团信息"}]
         * group_price : 团购价
         * one_price : 单买价
         */

        private int msg_tip;
        private String is_collect;
        private String cart_num;
        private GoodsInfoBean goodsInfo;
        private MellInfoBean mInfo;
        private CommentBean comment;
        private String group_price;
        private String one_price;
        private List<PromotionBean> promotion;
        private List<TicketListBean> ticketList;
        private List<GoodsAttrBean> goodsAttr;
        private List<GoodsBannerBean> goods_banner;
        private List<AttrImagesBean> attr_images;
        private List<ProductBean> product;
        private List<GroupBean> group;
        private List<GoodsCommonAttr> goods_common_attr;

        private String share_url;
        private String share_img;//": "分享图片",
        private String share_content;//": "分享内容"

        public int getMsg_tip() {
            return msg_tip;
        }

        public void setMsg_tip(int msg_tip) {
            this.msg_tip = msg_tip;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getCart_num() {
            return cart_num;
        }

        public void setCart_num(String cart_num) {
            this.cart_num = cart_num;
        }

        public GoodsInfoBean getGoodsInfo() {
            return goodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        public MellInfoBean getMInfo() {
            return mInfo;
        }

        public void setMInfo(MellInfoBean mInfo) {
            this.mInfo = mInfo;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public String getGroup_price() {
            return group_price;
        }

        public void setGroup_price(String group_price) {
            this.group_price = group_price;
        }

        public String getOne_price() {
            return one_price;
        }

        public void setOne_price(String one_price) {
            this.one_price = one_price;
        }

        public List<PromotionBean> getPromotion() {
            return promotion;
        }

        public void setPromotion(List<PromotionBean> promotion) {
            this.promotion = promotion;
        }

        public List<TicketListBean> getTicketList() {
            return ticketList;
        }

        public void setTicketList(List<TicketListBean> ticketList) {
            this.ticketList = ticketList;
        }

        public List<GoodsAttrBean> getGoodsAttr() {
            return goodsAttr;
        }

        public void setGoodsAttr(List<GoodsAttrBean> goodsAttr) {
            this.goodsAttr = goodsAttr;
        }

        public List<GoodsBannerBean> getGoods_banner() {
            return goods_banner;
        }

        public void setGoods_banner(List<GoodsBannerBean> goods_banner) {
            this.goods_banner = goods_banner;
        }

        public List<AttrImagesBean> getAttr_images() {
            return attr_images;
        }

        public void setAttr_images(List<AttrImagesBean> attr_images) {
            this.attr_images = attr_images;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public List<GoodsCommonAttr> getGoods_common_attr() {
            return goods_common_attr;
        }

        public void setGoods_common_attr(List<GoodsCommonAttr> goods_common_attr) {
            this.goods_common_attr = goods_common_attr;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getShare_img() {
            return share_img;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }

        public String getShare_content() {
            return share_content;
        }

        public void setShare_content(String share_content) {
            this.share_content = share_content;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "msg_tip=" + msg_tip +
                    ", is_collect='" + is_collect + '\'' +
                    ", cart_num='" + cart_num + '\'' +
                    ", goodsInfo=" + goodsInfo +
                    ", mInfo=" + mInfo +
                    ", comment=" + comment +
                    ", group_price='" + group_price + '\'' +
                    ", one_price='" + one_price + '\'' +
                    ", promotion=" + promotion +
                    ", ticketList=" + ticketList +
                    ", goodsAttr=" + goodsAttr +
                    ", goods_banner=" + goods_banner +
                    ", attr_images=" + attr_images +
                    ", product=" + product +
                    ", group=" + group +
                    ", goods_common_attr=" + goods_common_attr +
                    ", share_url='" + share_url + '\'' +
                    ", share_img='" + share_img + '\'' +
                    ", share_content='" + share_content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GroupBuyInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
