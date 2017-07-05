package com.txd.hzj.wjlp;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ants.theantsgo.util.L;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.CartFgt;
import com.txd.hzj.wjlp.mainFgt.MellOffLineFgt;
import com.txd.hzj.wjlp.mainFgt.MellonLineFgt;
import com.txd.hzj.wjlp.mainFgt.MineFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：下午 1:16
 * 描述：无界优品主页
 * ===============Txunda===============
 */
public class MainAty extends BaseAty implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.app_main_rg)
    private RadioGroup app_main_rg;

    /**
     * 线上商城
     */
    @ViewInject(R.id.home_pager_rb)
    private RadioButton home_pager_rb;
    /**
     * 线下商城
     */
    @ViewInject(R.id.mell_offline_rb)
    private RadioButton mell_offline_rb;
    /**
     * 购物车
     */
    @ViewInject(R.id.cart_rb)
    private RadioButton cart_rb;
    /**
     * 我的
     */
    @ViewInject(R.id.mine_rb)
    private RadioButton mine_rb;
    private int page_index = 0;
    // 碎片
    private MellonLineFgt mellonLineFgt;
    private MellOffLineFgt mellOffLineFgt;
    private CartFgt cartFgt;
    private MineFgt mineFgt;
    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_main_rg.setOnCheckedChangeListener(this);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.main_content,
                fragments);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_main;
    }

    @Override
    protected void initialized() {
        mellonLineFgt = new MellonLineFgt();
        mellOffLineFgt = new MellOffLineFgt();
        cartFgt = new CartFgt();
        mineFgt = new MineFgt();
        fragments = new ArrayList<>();
        fragments.add(mellonLineFgt);
        fragments.add(mellOffLineFgt);
        fragments.add(cartFgt);
        fragments.add(mineFgt);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (page_index) {
            case 0:// 线上商城
                home_pager_rb.setChecked(true);
                break;
            case 1:// 线下商城
                mell_offline_rb.setChecked(true);
                break;
            case 2:// 购物车
                cart_rb.setChecked(true);
                break;
            case 3:// 我的
                mine_rb.setChecked(true);
                break;
        }
    }

    @Override
    @OnClick({R.id.mach_more_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mach_more_tv:// 更多
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.home_pager_rb:// 线上商城
                page_index = 0;
                fragmentChangeManager.setFragments(0);
                break;
            case R.id.mell_offline_rb:// 线下商城
                page_index = 1;
                fragmentChangeManager.setFragments(1);
                break;
            case R.id.cart_rb:// 购物车
                page_index = 2;
                fragmentChangeManager.setFragments(2);
                break;
            case R.id.mine_rb:// 我的
                page_index = 3;
                fragmentChangeManager.setFragments(3);
                break;
        }
    }
}
