package com.txd.hzj.wjlp.http.limit;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：15:07
 * 描述：
 * ===============Txunda===============
 */

public class LimitBuyPst extends BasePresenter {

    private LimitBuy limitBuy;

    public LimitBuyPst(BaseView baseView) {
        super(baseView);
        limitBuy = new LimitBuy();
    }

    // 限量购首页
    public void limitBuyIndex(int p, String stage_id) {
        baseView.showDialog();
        limitBuy.limitBuyIndex(p, stage_id, baseView);
    }

    // 限量购详情
    public void limitBuyInfo(String limit_buy_id) {
        baseView.showDialog();
        limitBuy.limitBuyInfo(limit_buy_id, baseView);
    }

    // 设置提醒
    public void remindMe(String limit_buy_id) {
        baseView.showDialog();
        limitBuy.remindMe(limit_buy_id, baseView);
    }

}
