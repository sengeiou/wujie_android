package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:26
 * 描述：团购店铺详情
 * ===============Txunda===============
 */

public class MellInfoBean {

    /**
     * merchant_id : 店铺id
     * merchant_name : 店铺名称
     * level : 店铺等级
     * logo : 店铺logo
     * view_num : 关注人数
     * all_goods : 宝贝总数
     * goods_score : 宝贝评分
     * merchant_score : 卖家评分
     * shipping_score : 物流评分
     */

    private String merchant_id;
    private String merchant_name;
    private String level;
    private String logo;
    private String view_num;
    private String all_goods;
    private String goods_score;
    private String merchant_score;
    private String shipping_score;
    private String easemob_account;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getAll_goods() {
        return all_goods;
    }

    public void setAll_goods(String all_goods) {
        this.all_goods = all_goods;
    }

    public String getGoods_score() {
        return goods_score;
    }

    public void setGoods_score(String goods_score) {
        this.goods_score = goods_score;
    }

    public String getMerchant_score() {
        return merchant_score;
    }

    public void setMerchant_score(String merchant_score) {
        this.merchant_score = merchant_score;
    }

    public String getShipping_score() {
        return shipping_score;
    }

    public void setShipping_score(String shipping_score) {
        this.shipping_score = shipping_score;
    }

    public String getEasemob_account() {
        return easemob_account;
    }

    public void setEasemob_account(String easemob_account) {
        this.easemob_account = easemob_account;
    }

    @Override
    public String toString() {
        return "MellInfoBean{" +
                "merchant_id='" + merchant_id + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", level='" + level + '\'' +
                ", logo='" + logo + '\'' +
                ", view_num='" + view_num + '\'' +
                ", all_goods='" + all_goods + '\'' +
                ", goods_score='" + goods_score + '\'' +
                ", merchant_score='" + merchant_score + '\'' +
                ", shipping_score='" + shipping_score + '\'' +
                ", easemob_account='" + easemob_account + '\'' +
                '}';
    }
}
