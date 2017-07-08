package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.LimitFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:09
 * 描述：限量购(2-2限量购2)
 * ===============Txunda===============
 */
public class LimitShoppingAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.tabLayout)
    private TabLayout tabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private List<String> tabTitle;
    private List<Fragment> fragments;

    private VpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("限量购");
        showStatusBar(R.id.title_re_layout);
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_limit_shopping;
    }

    @Override
    protected void initialized() {
        tabTitle = new ArrayList<>();
        fragments = new ArrayList<>();
        vpAdapter = new VpAdapter(getSupportFragmentManager());
        tabTitle.add("13:00\n抢购进行中");
        tabTitle.add("17:00\n即将开始");
        tabTitle.add("21:00\n即将开始");
        fragments.add(LimitFgt.getFgt(0));
        fragments.add(LimitFgt.getFgt(1));
        fragments.add(LimitFgt.getFgt(1));
    }

    @Override
    protected void requestData() {

    }

    private class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position);
        }
    }

}