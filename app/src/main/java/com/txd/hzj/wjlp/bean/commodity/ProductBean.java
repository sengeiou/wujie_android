package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:31
 * 描述：货品
 * ===============Txunda===============
 */

public class ProductBean implements Serializable {
    /**
     * id : 75
     * goods_id : 12
     * goods_attr : 属性组合
     * product_sn : 货品号
     * product_number : 货品库存
     */

    private String id;
    private String goods_id;
    private String goods_attr;
    private String product_sn;
    private String product_number;

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

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
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
    public String toString() {
        return "ProductBean{" +
                "id='" + id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_attr='" + goods_attr + '\'' +
                ", product_sn='" + product_sn + '\'' +
                ", product_number='" + product_number + '\'' +
                '}';
    }
}
