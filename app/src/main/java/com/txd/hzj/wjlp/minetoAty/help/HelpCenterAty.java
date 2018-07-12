package com.txd.hzj.wjlp.minetoAty.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：下午 2:52
 * 描述：帮助中心
 *
 */
public class HelpCenterAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 页卡
     */
    @ViewInject(R.id.tabs)
    private TabLayout mTabLayout;
    /**
     * ViewPager
     */
    @ViewInject(R.id.vp_view)
    private ViewPager mViewPager;

    private MyPagerAdapter pagerAdapter;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("帮助中心");
        mViewPager.setAdapter(pagerAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_help_center_li;
    }

    @Override
    protected void initialized() {
        //添加页卡标题
        mTitleList.add("商家篇");
        mTitleList.add("用户篇");
        mTitleList.add("运营中心篇");
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for (String title : mTitleList) {
            mViewList.add(HelpFgt.getFgt(title));
        }
    }

    @Override
    protected void requestData() {

    }

    //ViewPager适配器
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

}
