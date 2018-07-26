package com.txd.hzj.wjlp.http.welfare;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/15 0015
 * 时间：14:27
 * 描述：
 *
 */

public class WelfarePst extends BasePresenter {

    private Welfare welfare;

    public WelfarePst(BaseView baseView) {
        super(baseView);
        welfare = new Welfare();
    }

    // 优惠券
    public void ticketList(int p, String cate_id, int getType) {
        if (1 == getType) {
            baseView.showDialog();
        }
        welfare.ticketList(p, cate_id, baseView);
    }

    // 领取优惠券
    public void getTicket(String ticket_id) {
        baseView.showDialog();
        welfare.getTicket(ticket_id, baseView);
    }

    // 红包列表
    public void faceList(int p, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        welfare.faceList(p, baseView);
    }

    // 红包列表
    public void bonusList(String bonus_id) {
        baseView.showDialog();
        welfare.bonusList(bonus_id, baseView);
    }

    // 领取红包
    public void getBonus(String bonus_id, String bonus_val) {
        baseView.showDialog();
        welfare.getBonus(bonus_id, bonus_val, baseView);
    }

    // 分享内容
    public void shareContent(String bonus_id) {
        baseView.showDialog();
        welfare.shareContent(bonus_id, baseView);
    }

}
