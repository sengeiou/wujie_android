package com.txd.hzj.wjlp.http.updataApp;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.txd.hzj.wjlp.http.Upgrade;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/8 0008
 * 时间：09:11
 * 描述：
 * ===============Txunda===============
 */

public class UpdataPst extends BasePresenter {

    private Upgrade upgrade;

    public UpdataPst(BaseView baseView) {
        super(baseView);
        upgrade = new Upgrade();
    }

    public void toUpdata() {
        baseView.showDialog();
        upgrade.upgrade(baseView);
    }

}
