package com.txd.hzj.wjlp.distribution.bean;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/6/13 16:53
 * 功能描述：小店上货商品信息列表bean类
 * 联系方式：
 */
public class ExhibitGoosBean {
    private String imageUrl;
    private String goodsTitle;
    private String daijinquan;
    private String price;
    private String jifen;

    public ExhibitGoosBean() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getDaijinquan() {
        return daijinquan;
    }

    public void setDaijinquan(String daijinquan) {
        this.daijinquan = daijinquan;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }
}
