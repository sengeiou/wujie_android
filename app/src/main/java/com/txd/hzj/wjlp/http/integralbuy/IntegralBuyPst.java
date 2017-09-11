package com.txd.hzj.wjlp.http.integralbuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：17:03
 * 描述：无界商店
 * ===============Txunda===============
 */

public class IntegralBuyPst extends BasePresenter {
    private IntegralBuy integralBuy;

    public IntegralBuyPst(BaseView baseView) {
        super(baseView);
        integralBuy = new IntegralBuy();
    }

    // 首页
    public void integralBuyIndex(int p, String cate_id) {
        baseView.showDialog();
        integralBuy.integralBuyIndex(p, cate_id, baseView);
    }

    // 详情
    public void integralBuyInfo(String integral_buy_id) {
        baseView.showDialog();
        integralBuy.integralBuyInfo(integral_buy_id, baseView);
    }

    // 三级分类商品
    public void threeList(String two_cate_id, int p, String three_cate_id) {
        baseView.showDialog();
        integralBuy.threeList(two_cate_id, p, three_cate_id, baseView);
    }

}
