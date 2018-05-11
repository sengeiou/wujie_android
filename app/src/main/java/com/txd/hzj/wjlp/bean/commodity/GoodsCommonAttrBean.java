package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:59
 * 功能描述：商品公共属性
 * 联系方式：常用邮箱或电话
 */
public class GoodsCommonAttrBean implements Serializable {
    private String title;// "宝贝属性",  "食品安全",
    private List<GoodsCommonAttrItemBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GoodsCommonAttrItemBean> getList() {
        return list;
    }

    public void setList(List<GoodsCommonAttrItemBean> list) {
        this.list = list;
    }
}
