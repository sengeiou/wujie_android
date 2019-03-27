package com.txd.hzj.wjlp.clearinventory;

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
 * 创建时间：2019/3/26 13:53
 * 功能描述：已交易
 */
public class AlreadyTradedAty extends BaseAty{
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.slidingTabLayout)
    private SlidingTabLayout slidingTabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private String[] titles = {"待发货","待收货","已退款","已完成"};

    private List<Fragment> mFragments;
    @Override
    protected int getLayoutResId() {
        return R.layout.layout_tablayout_viewpager;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("已交易");
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(OrderFragment.getInstance("已交易",titles[i]));
        }

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),mFragments,titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected void requestData() {
    }



    public static class PagerAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> mFragments;
        private String[] mStrings;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] strings) {
            super(fm);
            mFragments = fragments;
            mStrings = strings;
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
            return mStrings[position];
        }
    }
}
