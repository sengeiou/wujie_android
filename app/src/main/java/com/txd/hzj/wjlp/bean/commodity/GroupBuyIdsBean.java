package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:46
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GroupBuyIdsBean implements Serializable {
    private String id;
    private String goods_name;
    private String arrt_name;
    private String goods_img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getArrt_name() {
        return arrt_name;
    }

    public void setArrt_name(String arrt_name) {
        this.arrt_name = arrt_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }
}
