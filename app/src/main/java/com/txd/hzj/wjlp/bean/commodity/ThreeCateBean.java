package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/11
 * 功能描述：//三级分类
 * 联系方式：常用邮箱或电话
 */

public class ThreeCateBean implements Serializable {
    /**
     *  "三级分类id",
     */
    private String three_cate_id;
    /**
     * "分类简称",
     */
    private String short_name;
    /**
     * "分类名称"
     */
    private String name;

    public String getThree_cate_id() {
        return three_cate_id;
    }

    public void setThree_cate_id(String three_cate_id) {
        this.three_cate_id = three_cate_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
