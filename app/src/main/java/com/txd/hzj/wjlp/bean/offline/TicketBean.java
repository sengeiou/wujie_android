package com.txd.hzj.wjlp.bean.offline;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/24 8:55
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class TicketBean implements Serializable{
    private String type;//1.红色代金券  2.黄色代金券 3.蓝色代金券
    private String discount_desc;  //描述

    /**
     * 1.红色代金券  2.黄色代金券 3.蓝色代金券
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }
}
