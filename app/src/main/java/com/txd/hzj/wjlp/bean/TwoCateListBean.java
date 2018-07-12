package com.txd.hzj.wjlp.bean;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：13:10
 * 描述：二级分类(拼团购等)
 *
 */

public class TwoCateListBean {
    /**
     * two_cate_id : 5
     * short_name : 零食
     * name : 零食
     * cate_img : http://wjyp.txunda.com/Uploads/Cate/default.png
     */

    private String two_cate_id;
    private String short_name;
    private String name;
    private String cate_img;

    public String getTwo_cate_id() {
        return two_cate_id;
    }

    public void setTwo_cate_id(String two_cate_id) {
        this.two_cate_id = two_cate_id;
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

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    @Override
    public String toString() {
        return "TwoCateListBean{" +
                "two_cate_id='" + two_cate_id + '\'' +
                ", short_name='" + short_name + '\'' +
                ", name='" + name + '\'' +
                ", cate_img='" + cate_img + '\'' +
                '}';
    }
}
