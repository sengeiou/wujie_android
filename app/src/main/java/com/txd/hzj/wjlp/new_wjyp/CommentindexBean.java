package com.txd.hzj.wjlp.new_wjyp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class CommentindexBean implements Parcelable {
    /**
     * merchant_star :
     * delivery_star :
     * order_status : 0
     * goods_list : [{"order_goods_id":"190","goods_id":"3","goods_name":"全新骨正基养生魔力鞋垫","goods_img":"http://wjyp.txunda.com/Uploads/Goods/2017-11-30/5a1ffaaf89456.jpg","merchant_id":"3","status":"0","all_star":"","content":""}]
     */
    private String merchant_star = "5";
    private String delivery_star = "5";
    private int order_status;
    private List<GoodsListBean> goods_list;

    public String getMerchant_star() {
        return merchant_star;
    }

    public void setMerchant_star(String merchant_star) {
        this.merchant_star = merchant_star;
    }

    public String getDelivery_star() {
        return delivery_star;
    }

    public void setDelivery_star(String delivery_star) {
        this.delivery_star = delivery_star;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean implements Parcelable {
        /**
         * order_goods_id : 190
         * goods_id : 3
         * goods_name : 全新骨正基养生魔力鞋垫
         * goods_img : http://wjyp.txunda.com/Uploads/Goods/2017-11-30/5a1ffaaf89456.jpg
         * merchant_id : 3
         * status : 0
         * all_star :
         * content :
         */
        private String order_goods_id;
        private String goods_id;
        private String goods_name;
        private String goods_img;
        private String merchant_id;
        private String status;
        private String all_star;
        private String content;


        public String getOrder_goods_id() {
            return order_goods_id;
        }

        public void setOrder_goods_id(String order_goods_id) {
            this.order_goods_id = order_goods_id;
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

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAll_star() {
            return TextUtils.isEmpty(all_star) ? "0" : all_star;
        }

        public void setAll_star(String all_star) {
            this.all_star = all_star;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public GoodsListBean() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.order_goods_id);
            dest.writeString(this.goods_id);
            dest.writeString(this.goods_name);
            dest.writeString(this.goods_img);
            dest.writeString(this.merchant_id);
            dest.writeString(this.status);
            dest.writeString(this.all_star);
            dest.writeString(this.content);
        }

        protected GoodsListBean(Parcel in) {
            this.order_goods_id = in.readString();
            this.goods_id = in.readString();
            this.goods_name = in.readString();
            this.goods_img = in.readString();
            this.merchant_id = in.readString();
            this.status = in.readString();
            this.all_star = in.readString();
            this.content = in.readString();
        }

        public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
            @Override
            public GoodsListBean createFromParcel(Parcel source) {
                return new GoodsListBean(source);
            }

            @Override
            public GoodsListBean[] newArray(int size) {
                return new GoodsListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.merchant_star);
        dest.writeString(this.delivery_star);
        dest.writeInt(this.order_status);
        dest.writeList(this.goods_list);
    }

    public CommentindexBean() {
    }

    protected CommentindexBean(Parcel in) {
        this.merchant_star = in.readString();
        this.delivery_star = in.readString();
        this.order_status = in.readInt();
        this.goods_list = new ArrayList<GoodsListBean>();
        in.readList(this.goods_list, GoodsListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommentindexBean> CREATOR = new Parcelable.Creator<CommentindexBean>() {
        @Override
        public CommentindexBean createFromParcel(Parcel source) {
            return new CommentindexBean(source);
        }

        @Override
        public CommentindexBean[] newArray(int size) {
            return new CommentindexBean[size];
        }
    };
}
