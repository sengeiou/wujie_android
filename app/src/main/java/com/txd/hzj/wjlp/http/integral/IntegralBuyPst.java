package com.txd.hzj.wjlp.http.integral;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：17:19
 * 描述：无界商店首页
 * ===============Txunda===============
 */

public class IntegralBuyPst extends BasePresenter {
    private IntegralBuy integralBuy;

    public IntegralBuyPst(BaseView baseView) {
        super(baseView);
        integralBuy = new IntegralBuy();
    }

    // 无界商店首页
    public void integralBuyIndex(int p, String cate_id) {
        baseView.showDialog();
        integralBuy.integralBuyIndex(p, cate_id, baseView);
    }

    // 无界商店详情
    public void integralBuyInfo(String integral_buy_id,int page) {
        baseView.showDialog();
        integralBuy.integralBuyInfo(integral_buy_id, page,baseView);
    }

    // 三级分类
    public void threeList(String two_cate_id, String three_cate_id, int p) {
        baseView.showDialog();
        integralBuy.threeList(two_cate_id, three_cate_id, p, baseView);
    }
}
