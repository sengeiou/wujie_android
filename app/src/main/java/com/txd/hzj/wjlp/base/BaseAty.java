package com.txd.hzj.wjlp.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.mellOnLine.AllClassifyAty;
import com.txd.hzj.wjlp.mellOnLine.MessageAty;
import com.txd.hzj.wjlp.mellOnLine.ScanAty;
import com.txd.hzj.wjlp.mellOnLine.SearchAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsEvaluateAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ToShareAty;

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
        String name = android.os.Build.BRAND;

        if(name.equals("Huawei")){
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

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        // 先隐藏键盘
        ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 评价
     *
     * @param v View
     */
    public void toEvaluate(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", 0);
        startActivity(GoodsEvaluateAty.class, bundle);
    }

    /**
     * 商品属性
     *
     * @param v View
     */
    public void toAttrs(View v) {
        startActivity(GoodsAttributeAty.class, null);
    }

    /**
     * 消息
     *
     * @param v View
     */
    public void toMessage(View v) {
        startActivity(MessageAty.class, null);
    }

    /**
     * 分类
     *
     * @param v View
     */
    public void toClassify(View v) {
        startActivity(AllClassifyAty.class, null);
    }

    /**
     * 搜索
     *
     * @param v View
     */
    public void toSearch(View v) {
        startActivity(SearchAty.class, null);
    }

    /**
     * 分享
     */
    public void toShare() {
        startActivity(ToShareAty.class, null);
    }

    /**
     * 扫一扫
     */
    public void toScan(View v) {
        startActivity(ScanAty.class, null);
    }
}
