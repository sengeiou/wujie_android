package com.txd.hzj.wjlp.http.auction;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：13:26
 * 描述：
 * ===============Txunda===============
 */

public class AuctionPst extends BasePresenter {

    private Auction auction;

    public AuctionPst(BaseView baseView) {
        super(baseView);
        auction = new Auction();
    }

    // 竞拍汇首页
    public void auctionIndex(int next, int p) {
        baseView.showDialog();
        auction.auctionIndex(next, p, baseView);
    }

    // 竞拍汇商品详情页
    public void auctionInfo(String auction_id,int page) {
        baseView.showDialog();
        auction.auctionInfo(auction_id,page, baseView);
    }

    // 设置提醒
    public void remindMe(String auction_id) {
        baseView.showDialog();
        auction.remindMe(auction_id, baseView);
    }
}
