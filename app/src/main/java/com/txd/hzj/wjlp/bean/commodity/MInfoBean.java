package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:54
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class MInfoBean implements Serializable {
    /**
     * merchant_id : 1
     * merchant_name : 第一家店
     * level : 1
     * logo : http://wjyp.txunda.com/Uploads/Merchant/2017-09-18/59bf96e145a92.jpg
     * view_num : 4
     * easemob_account : 150632770313199
     * all_goods : 9
     * goods_score : 4.7
     * merchant_score : 4.8
     * shipping_score : 4.7
     */
    private String merchant_id;
    private String merchant_name;
    private String level;
    private String logo;
    private String view_num;
    private String easemob_account;
    private String all_goods;
    private String goods_score;
    private String merchant_score;
    private String shipping_score;
    private String merchant_easemob_account;
    private String score;
    private String send_score;
    private String merchant_head_pic;
    private String merchant_nickname;

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

    public String getEasemob_account() {
        return easemob_account;
    }

    public void setEasemob_account(String easemob_account) {
        this.easemob_account = easemob_account;
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

    public String getMerchant_easemob_account() {
        return merchant_easemob_account;
    }

    public void setMerchant_easemob_account(String merchant_easemob_account) {
        this.merchant_easemob_account = merchant_easemob_account;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSend_score() {
        return send_score;
    }

    public void setSend_score(String send_score) {
        this.send_score = send_score;
    }

    public String getMerchant_head_pic() {
        return merchant_head_pic;
    }

    public void setMerchant_head_pic(String merchant_head_pic) {
        this.merchant_head_pic = merchant_head_pic;
    }

    public String getMerchant_nickname() {
        return merchant_nickname;
    }

    public void setMerchant_nickname(String merchant_nickname) {
        this.merchant_nickname = merchant_nickname;
    }
}
