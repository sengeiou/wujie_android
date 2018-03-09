package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.AuctionRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;
import com.txd.hzj.wjlp.minetoAty.order.fgt.AuctionRecordFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienchao on 2017/7/15 0015.
 */

public class AuctionRecordAty extends BaseAty {
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
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFtagemt = new ArrayList<>();//页卡视图集合
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("比价纪录");

        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_record;
    }

    @Override
    protected void initialized() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mTitleList.add("比价中");
        mTitleList.add("比价成功");
        mTitleList.add("比价结束");
        for (int i = 0; i < mTitleList.size(); i++) {
            mFtagemt.add(AuctionRecordFgt.getFgt(mTitleList.get(i), "1" + String.valueOf(i)));
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
            return mFtagemt.get(position);
        }

        @Override
        public int getCount() {
            return mFtagemt.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
