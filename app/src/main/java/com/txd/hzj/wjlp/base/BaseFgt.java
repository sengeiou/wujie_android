package com.txd.hzj.wjlp.base;

import com.ants.theantsgo.base.BaseFragment;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：15:10
 * 描述：所有Fragment的基类
 * ===============Txunda===============
 */

public abstract class BaseFgt extends BaseFragment {
    /**
     * 登录
     */
    public void toLogin() {
    }

    public void showStatusBar(int vid) {
        ImmersionBar.with(this).titleBar(vid).init();
    }

}
