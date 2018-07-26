package com.txd.hzj.wjlp.http.groupbuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：11:41
 * 描述：拼团购
 *
 */

public class GroupBuyPst extends BasePresenter {
    private GroupBuy groupBuy;

    public GroupBuyPst(BaseView baseView) {
        super(baseView);
        groupBuy = new GroupBuy();
    }

    // 拼团购首页
    public void groupBuyIndex(int p, String cate_id) {
        baseView.showDialog();
        groupBuy.groupBuyIndex(p, cate_id, baseView);
    }

    // 拼团购详情
    public void groupBuyInfo(String group_buy_id,int page,String a_id) {
        baseView.showDialog();
        groupBuy.groupBuyInfo(group_buy_id,page,a_id, baseView);
    }

    // 参团页
    public  void goGroup(String log_id) {
        baseView.showDialog();
        groupBuy.goGroup(log_id, baseView);
    }


    // 三级分类商品列表
    public void threeList(String two_cate_id, int p, String three_cate_id) {
        baseView.showDialog();
        groupBuy.threeList(two_cate_id, p, three_cate_id, baseView);
    }

    // 活动商品——拼团购
    public void merchantGroupBuyList(String merchant_id, int p) {
        baseView.showDialog();
        groupBuy.merchantGroupBuyList(merchant_id, p, baseView);
    }

    /**
     * 拼单商品属性【/Goods/attrApi】
     * @param goods_id
     * @param product_id
     * @param group_type
     */
    public void attrApi(String goods_id, String product_id,int group_type){
        baseView.showDialog();
        groupBuy.attrApi(goods_id,product_id,group_type,baseView);
    }

    /**
     * 拼单购规则显示
     */
    public void changeShowStatus(BaseView baseView){
        baseView.showDialog();
        groupBuy.changeShowStatus( baseView);
    }

}