package com.txd.hzj.wjlp.http.ticketbuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：16:57
 * 描述：票券区PST
 * ===============Txunda===============
 */

public class TicketBuyPst extends BasePresenter {

    private TicketBuy ticketBuy;

    public TicketBuyPst(BaseView baseView) {
        super(baseView);
        ticketBuy = new TicketBuy();
    }

    // 首页
    public void ticketBuyIndex(int p, String cate_id) {
        baseView.showDialog();
        ticketBuy.ticketBuyIndex(p, cate_id, baseView);
    }

    // 详情
    public void ticketBuyInfo(String ticket_buy_id) {
        baseView.showDialog();
        ticketBuy.ticketBuyInfo(ticket_buy_id, baseView);
    }

    // 三级分类商品
    public void threeList(String two_cate_id, String three_cate_id, int p) {
        baseView.showDialog();
        ticketBuy.threeList(two_cate_id, three_cate_id, p, baseView);
    }
}
