package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:01
 * 功能描述：//商品属性
 * 联系方式：常用邮箱或电话
 */
public class AttrListBean implements Serializable {

    /**
     * id
      */
    private String id;
    /**
     * 商品属性值id
     */
    private String goods_attr_id;
    /**
     * 属性名称
     */
    private String attr_name;
    /**
     * 属性值
     */
    private String attr_value;
    /**
     * 属性附加价格
     */
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
        StringBuffer sb = new StringBuffer();
        sb.append("AttrListBean{");
        sb.append("\nid=");
        sb.append(id);
        sb.append("\ngoods_attr_id=");
        sb.append(goods_attr_id);
        sb.append("\nattr_name=");
        sb.append(attr_name);
        sb.append("\nattr_value=");
        sb.append(attr_value);
        sb.append("\nattr_price=");
        sb.append(attr_price);
        sb.append("}");
        return sb.toString();
    }
}
