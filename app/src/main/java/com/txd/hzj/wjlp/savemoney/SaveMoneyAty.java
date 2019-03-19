package com.txd.hzj.wjlp.savemoney;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/18 10:02
 * 功能描述：省钱购
 */
public class SaveMoneyAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.slidingTabLayout)
    private SlidingTabLayout slidingTabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private List<Fragment> mFragmentList;

    private String[] mTitles = new String[]{"淘宝","拼多多"};
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_save_money;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(SaveMoneyFgt.newInstance("淘宝"));
        mFragmentList.add(SaveMoneyFgt.newInstance("拼多多"));
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),mFragmentList,mTitles));
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    protected void requestData() {

    }

    public static class PageAdapter extends FragmentStatePagerAdapter{

        private List<Fragment> mFragments;
        private String[] mTitles;

        public PageAdapter(FragmentManager fm,List<Fragment> fragments,String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
