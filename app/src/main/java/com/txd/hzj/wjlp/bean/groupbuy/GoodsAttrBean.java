package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:29
 * 描述：商品属性
 * ===============Txunda===============
 */

public class GoodsAttrBean {
    /**
     * id : id
     * aid : 属性ID
     * attr_name : 属性名称
     * attr_value : 属性值
     * attr_price : 属性附加价格
     */

    private String id;
    private String aid;
    private String attr_name;
    private String attr_value;
    private String attr_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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
        return "GoodsAttrBean{" +
                "id='" + id + '\'' +
                ", aid='" + aid + '\'' +
                ", attr_name='" + attr_name + '\'' +
                ", attr_value='" + attr_value + '\'' +
                ", attr_price='" + attr_price + '\'' +
                '}';
    }
}
