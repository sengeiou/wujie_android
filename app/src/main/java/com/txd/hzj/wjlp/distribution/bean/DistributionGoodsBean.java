package com.txd.hzj.wjlp.distribution.bean;

/**
 * 创建者：wjj
 * 创建时间：${DATA} 上午 09:20
 * 功能描述：分销商品对象
 */
public class DistributionGoodsBean {

    private int _id; // 商品Id
    private String imageUrl; // 商品图片
    private String goodsName; // 商品名称
    private String daijinquan; // 可使用代金券
    private String meny; // 价格
    private String jifen; // 积分
    private boolean isChecked; // 是否选中

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDaijinquan() {
        return daijinquan;
    }

    public void setDaijinquan(String daijinquan) {
        this.daijinquan = daijinquan;
    }

    public String getMeny() {
        return meny;
    }

    public void setMeny(String meny) {
        this.meny = meny;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "DistributionGoodsBean{" +
                "商品id=" + _id +
                ", 图片地址='" + imageUrl + '\'' +
                ", 名称='" + goodsName + '\'' +
                ", 代金券='" + daijinquan + '\'' +
                ", 价格='" + meny + '\'' +
                ", 积分='" + jifen + '\'' +
                ", 选中状态='" + isChecked + '\'' +
                '}';
    }

}
