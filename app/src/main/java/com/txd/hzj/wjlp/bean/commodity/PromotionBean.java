package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:50
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class PromotionBean implements Serializable {
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
}
