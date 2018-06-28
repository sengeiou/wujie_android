package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/28 15:16
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Reply_picturesBean implements Serializable {
    private String review_path;//"http://www.wjyp.com/Uploads/2018-06-28/5b345b38a4c0f.jpg
    private String review_name;//"9341950_123944651000_2.jpg",
    private String review_id;//"23662",
    private String review_status;//"1"

    public String getReview_path() {
        return review_path;
    }

    public void setReview_path(String review_path) {
        this.review_path = review_path;
    }

    public String getReview_name() {
        return review_name;
    }

    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }
}
