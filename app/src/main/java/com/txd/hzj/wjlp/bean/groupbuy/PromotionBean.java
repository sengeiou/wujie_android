package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:27
 * 描述：团购店铺活动
 * ===============Txunda===============
 */

public class PromotionBean {
    /**
     * title : 优惠活动名称
     * promotion_id : 优惠id
     * type : 类型
     */

    private String title;
    private String promotion_id;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(String promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PromotionBean{" +
                "title='" + title + '\'' +
                ", promotion_id='" + promotion_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
