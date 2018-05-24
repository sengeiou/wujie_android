package com.txd.hzj.wjlp.base;

import android.os.Build;
import android.support.v4.app.Fragment;

import com.ants.theantsgo.base.BaseFragment;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.txd.hzj.wjlp.DemoApplication;
import com.umeng.analytics.MobclickAgent;

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
        String name = android.os.Build.BRAND;
        if (name.equals("Huawei")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).init();
            }
        } else {
            if (ImmersionBar.isSupportStatusBarDarkFont())
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true).init();
            else
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(L.isDebug){
            RefWatcher refWatcher = DemoApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if(isVisibleToUser){
                Glide.with((Fragment) this).resumeRequests();
            }else{
                Glide.with((Fragment)this).pauseRequests();
            }
        }catch (IllegalArgumentException e){

        }
    }
}
