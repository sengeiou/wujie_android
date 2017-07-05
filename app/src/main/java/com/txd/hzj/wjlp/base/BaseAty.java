package com.txd.hzj.wjlp.base;

import android.view.View;

import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：15:08
 * 描述：所有Activity的基类
 * ===============Txunda===============
 */

public abstract class BaseAty extends BaseActivity {
    /**
     * 返回
     *
     * @param v 按钮
     */
    public void beBack(View v) {
        finish();
    }

    /**
     * 登录
     */
    public void toLogin() {
    }

    /**
     * 修改StatusBar颜色
     *
     * @param vid
     */
    public void showStatusBar(int vid) {
        ImmersionBar.with(this).titleBar(vid).init();
    }
}
