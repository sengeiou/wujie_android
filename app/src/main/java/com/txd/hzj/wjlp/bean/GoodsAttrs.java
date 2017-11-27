package com.txd.hzj.wjlp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：20:56
 * 描述：商品属性
 * ===============Txunda===============
 */

public class GoodsAttrs implements Parcelable {
    /**
     * attr_name : 颜色
     * attr_list : [{"id":"6","goods_attr_id":"7","attr_name":"颜色","attr_value":"jfg","attr_price":"1.00"}]
     */

    private String attr_name;
    private List<AttrListBean> attr_list;

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public List<AttrListBean> getAttr_list() {
        return attr_list;
    }

    public void setAttr_list(List<AttrListBean> attr_list) {
        this.attr_list = attr_list;
    }

    public static class AttrListBean implements Parcelable {

        /**
         * id : 6
         * goods_attr_id : 7
         * attr_name : 颜色
         * attr_value : jfg
         * attr_price : 1.00
         */

        private String id;
        private String goods_attr_id;
        private String attr_name;
        private String attr_value;
        private String attr_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_attr_id() {
            return goods_attr_id;
        }

        public void setGoods_attr_id(String goods_attr_id) {
            this.goods_attr_id = goods_attr_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public String getAttr_value() {
            return attr_value;
        }

        public void setAttr_value(String attr_value) {
            this.attr_value = attr_value;
        }

        public String getAttr_price() {
            return attr_price;
        }

        public void setAttr_price(String attr_price) {
            this.attr_price = attr_price;
        }

        @Override
        public String toString() {
            return "AttrListBean{" +
                    "id='" + id + '\'' +
                    ", goods_attr_id='" + goods_attr_id + '\'' +
                    ", attr_name='" + attr_name + '\'' +
                    ", attr_value='" + attr_value + '\'' +
                    ", attr_price='" + attr_price + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.goods_attr_id);
            dest.writeString(this.attr_name);
            dest.writeString(this.attr_value);
            dest.writeString(this.attr_price);
        }

        public AttrListBean() {
        }

        protected AttrListBean(Parcel in) {
            this.id = in.readString();
            this.goods_attr_id = in.readString();
            this.attr_name = in.readString();
            this.attr_value = in.readString();
            this.attr_price = in.readString();
        }

        public static final Creator<AttrListBean> CREATOR = new Creator<AttrListBean>() {
            @Override
            public AttrListBean createFromParcel(Parcel source) {
                return new AttrListBean(source);
            }

            @Override
            public AttrListBean[] newArray(int size) {
                return new AttrListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attr_name);
        dest.writeList(this.attr_list);
    }

    public GoodsAttrs() {
    }

    protected GoodsAttrs(Parcel in) {
        this.attr_name = in.readString();
        this.attr_list = new ArrayList<AttrListBean>();
        in.readList(this.attr_list, AttrListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsAttrs> CREATOR = new Parcelable.Creator<GoodsAttrs>() {
        @Override
        public GoodsAttrs createFromParcel(Parcel source) {
            return new GoodsAttrs(source);
        }

        @Override
        public GoodsAttrs[] newArray(int size) {
            return new GoodsAttrs[size];
        }
    };

    @Override
    public String toString() {
        return "GoodsAttrs{" +
                "attr_name='" + attr_name + '\'' +
                ", attr_list=" + attr_list +
                '}';
    }

    public static class product implements Parcelable {
        String id;
        String goods_id;
        String goods_attr_str;
        String product_sn;
        String product_number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_attr_str() {
            return goods_attr_str;
        }

        public void setGoods_attr_str(String goods_attr_str) {
            this.goods_attr_str = goods_attr_str;
        }

        public String getProduct_sn() {
            return product_sn;
        }

        public void setProduct_sn(String product_sn) {
            this.product_sn = product_sn;
        }

        public String getProduct_number() {
            return product_number;
        }

        public void setProduct_number(String product_number) {
            this.product_number = product_number;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.goods_id);
            dest.writeString(this.goods_attr_str);
            dest.writeString(this.product_sn);
            dest.writeString(this.product_number);
        }

        public product() {
        }

        protected product(Parcel in) {
            this.id = in.readString();
            this.goods_id = in.readString();
            this.goods_attr_str = in.readString();
            this.product_sn = in.readString();
            this.product_number = in.readString();
        }

        public static final Parcelable.Creator<product> CREATOR = new Parcelable.Creator<product>() {
            @Override
            public product createFromParcel(Parcel source) {
                return new product(source);
            }

            @Override
            public product[] newArray(int size) {
                return new product[size];
            }
        };
    }
}
