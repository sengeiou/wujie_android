package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:56
 * 功能描述：//评论图片
 * 联系方式：常用邮箱或电话
 */
public class PicturesBean implements Serializable {
    private String path;//"图片路径"

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
