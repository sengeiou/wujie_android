package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：13:09
 * 描述：一级分类(拼团购等)
 * ===============Txunda===============
 */

public class TopNavBean {
    /**
     * cate_id : 3
     * short_name : 生鲜
     * name : 生鲜
     */

    private String cate_id;
    private String short_name;
    private String name;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
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

    @Override
    public String toString() {
        return "TopNavBean{" +
                "cate_id='" + cate_id + '\'' +
                ", short_name='" + short_name + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
