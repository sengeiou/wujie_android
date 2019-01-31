package com.txd.hzj.wjlp.catchDoll.base;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseFragment;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.bumptech.glide.Glide;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：15:10
 * 描述：所有Fragment的基类
 */

public abstract class BaseFgt extends BaseFragment {
    /**
     * 登录
     */
    public void toLogin() {
    }

    public void showStatusBar(int vid) {
        String name = Build.BRAND;
        if (name.equals("Huawei")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).init();
            }
        } else {
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(L.isDebug){
//            RefWatcher refWatcher = DemoApplication.getRefWatcher(getActivity());
//            refWatcher.watch(this);
//        }
    }

    /**
     * 设置列表项数据为空展示的界面数据
     *
     * @param listView       列表控件
     * @param nullDataLayout 展示空信息的布局
     * @param nullDataImgv   展示空数据的图片
     * @param nullDataTv     展示空数据的文字展示
     * @param imgResId       展示的图片
     * @param showMsg        展示的文字信息
     */
    public void showNullData(@NonNull View listView, @NonNull LinearLayout nullDataLayout, @NonNull ImageView nullDataImgv, @NonNull TextView nullDataTv, int imgResId, String showMsg) {
        listView.setVisibility(View.GONE);
        nullDataLayout.setVisibility(View.VISIBLE);
        nullDataImgv.setImageResource(imgResId);
        nullDataTv.setText(showMsg);
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
