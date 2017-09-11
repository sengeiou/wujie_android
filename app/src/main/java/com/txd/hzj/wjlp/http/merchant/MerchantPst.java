package com.txd.hzj.wjlp.http.merchant;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：17:23
 * 描述：商家模块
 * ===============Txunda===============
 */

public class MerchantPst extends BasePresenter {

    private Merchant merchant;

    public MerchantPst(BaseView baseView) {
        super(baseView);
        merchant = new Merchant();
    }

    // 店铺首页
    public void merIndex(String merchant_id, int p) {
        baseView.showDialog();
        merchant.merIndex(merchant_id, p, baseView);
    }

    // 店铺详情
    public void merInfo(String merchant_id) {
        baseView.showDialog();
        merchant.merInfo(merchant_id, baseView);
    }

    // 商品页
    public void goodsList(String merchant_id, String is_hot, String new_buy, int p) {
        baseView.showDialog();
        merchant.goodsList(merchant_id, is_hot, new_buy, p, baseView);
    }

    // 获取评论列表
    public void commentList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.commentList(merchant_id, p, baseView);
    }

    // 活动商品——拼团购
    public void groupList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.groupList(merchant_id, p, baseView);
    }

    // 活动商品——无界预购
    public void preList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.preList(merchant_id, p, baseView);
    }

    // 活动商品——一元购
    public void oneBuyList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.oneBuyList(merchant_id, p, baseView);
    }

    // 活动商品——竞拍汇
    public void auctionList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.auctionList(merchant_id, p, baseView);
    }

    // 活动商品——限量购
    public void limitList(String merchant_id, int p) {
        baseView.showDialog();
        merchant.limitList(merchant_id, p, baseView);
    }

    // 商检资质
    public void license(String merchant_id) {
        baseView.showDialog();
        merchant.license(merchant_id, baseView);
    }

    // 举报商家
    public void report(String report_type_id, String report_content, String merchant_id) {
        baseView.showDialog();
        merchant.report(report_type_id, report_content, merchant_id, baseView);
    }

}
