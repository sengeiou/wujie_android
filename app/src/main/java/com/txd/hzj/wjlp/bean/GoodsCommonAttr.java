package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：17:43
 * 描述：产品规格列表
 * ===============Txunda===============
 */

public class GoodsCommonAttr {

    /**
     * id : 规格编号
     * attr_name : 规格名称
     * attr_value : 规格值
     */

    private String id;
    private String attr_name;
    private String attr_val;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getAttr_value() {
        return attr_val;
    }

    public void setAttr_value(String attr_value) {
        this.attr_val = attr_value;
    }
}
