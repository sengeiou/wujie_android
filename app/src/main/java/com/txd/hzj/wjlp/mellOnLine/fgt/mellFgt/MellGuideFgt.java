package com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/1 0001
 * 时间：09:06
 * 描述：
 * ===============Txunda===============
 */

public class MellGuideFgt extends BaseFgt {
    private String title;

    private String[] mTitles;
    private List<Fragment> mFragments;
    private MyPagerAdapter pAdapter;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    public static MellGuideFgt newInstance(String title) {
        MellGuideFgt fragment = new MellGuideFgt();
        fragment.title = title;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
                        vp_for_title.setAdapter(pAdapter);
                        title_s_tab_layout.setViewPager(vp_for_title);
                    }
                });
            }
        }).start();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_all_goods;
    }

    @Override
    protected void initialized() {
        mTitles = new String[]{"限量购", "拼团购", "xfte预购", "竞拍汇", "一元夺宝"};
        mFragments = new ArrayList<>();
        for (String str : mTitles) {
            mFragments.add(ForClassifyGoodsFgt.newInstance(str));
        }

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
