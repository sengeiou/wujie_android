package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:03
 * 功能描述： //商品图片轮播图
 * 联系方式：常用邮箱或电话
 */
public class GoodsBannerBean implements Serializable {
    /**
     * path : http://wjyp.txunda.com/Uploads/Goods/2017-09-18/59bf9414cb5ed.jpg
     */

    private String path;// "轮播图"

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
