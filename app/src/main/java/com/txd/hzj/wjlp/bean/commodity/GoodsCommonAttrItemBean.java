package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:13
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodsCommonAttrItemBean implements Serializable {
    /**
     * id : 44
     * attr_name : 适用人群
     * attr_value : 大众
     */

    private String id;
    private String attr_name;
    private String attr_value;
    private String specification;

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
        return attr_value;
    }

    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
