package com.txd.hzj.wjlp.http.onebuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/4 0004
 * 时间：11:08
 * 描述：
 */

public class OneBuyPst extends BasePresenter {
    private OneBuy oneBuy;

    public OneBuyPst(BaseView baseView) {
        super(baseView);
        oneBuy = new OneBuy();
    }

    // 一元夺宝首页
    public void oneBuyIndex(int p, String add_num, String person_num, String integral, String is_hot) {
        baseView.showDialog();
        oneBuy.oneBuyIndex(p, add_num, person_num, integral, is_hot, baseView);
    }

    // 一元夺宝详情
    public void oneBuyInfo(String one_buy_id) {
        baseView.showDialog();
        oneBuy.oneBuyInfo(one_buy_id, baseView);
    }
}
