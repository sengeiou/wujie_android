package com.txd.hzj.wjlp.http.prebuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：10:51
 * 描述：无界预购Pst
 * ===============Txunda===============
 */

public class PerBuyPst extends BasePresenter {
    private PreBuy preBuy;

    public PerBuyPst(BaseView baseView) {
        super(baseView);
        preBuy = new PreBuy();
    }

    // 无界预购首页
    public void preBuyIndex(int p, String cate_id) {
        baseView.showDialog();
        preBuy.preBuyIndex(p, cate_id, baseView);
    }

    // 三级分类商品列表
    public void threeList(String two_cate_id, int p, String three_cate_id) {
        baseView.showDialog();
        preBuy.threeList(two_cate_id, p, three_cate_id, baseView);
    }

}
