package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：10:17
 * 描述：团购详情轮播
 * ===============Txunda===============
 */

public class GoodsBannerBean {
    /**
     * path : 轮播图
     */

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GoodsBannerBean{" +
                "path='" + path + '\'' +
                '}';
    }
}
