package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:00
 * 功能描述：//商品属性列表
 * 联系方式：常用邮箱或电话
 */
public class GoodsAttrBean implements Serializable {
    /**
     * attr_name : 规格
     * attr_list : [{"id":"43","goods_attr_id":"649","attr_name":"规格","attr_value":"1000g","attr_price":"0.00"}]
     */

    private String attr_name; //属性名称
    private List<AttrListBean> attr_list; //属性列表

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
}
