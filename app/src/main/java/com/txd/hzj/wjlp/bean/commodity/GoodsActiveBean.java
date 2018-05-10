package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:53
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodsActiveBean implements Serializable {
    /**
     * act_type : 2
     * act_id : 2
     * act_desc : 本商品正在进行预购活动
     */

    private String act_type;
    private String act_id;
    private String act_desc;

    public String getAct_type() {
        return act_type;
    }

    public void setAct_type(String act_type) {
        this.act_type = act_type;
    }

    public String getAct_id() {
        return act_id;
    }

    public void setAct_id(String act_id) {
        this.act_id = act_id;
    }

    public String getAct_desc() {
        return act_desc;
    }

    public void setAct_desc(String act_desc) {
        this.act_desc = act_desc;
    }
}
