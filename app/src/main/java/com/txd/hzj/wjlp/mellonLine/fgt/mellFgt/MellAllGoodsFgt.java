package com.txd.hzj.wjlp.mellonLine.fgt.mellFgt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ants.theantsgo.tool.ToolKit;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：下午 5:43
 * 描述：全部商品
 */
public class MellAllGoodsFgt extends BaseFgt {
    private String title;

    private int height = 0;
    private String[] mTitles;
    private List<Fragment> mFragments;
    private MyPagerAdapter pAdapter;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    public static MellAllGoodsFgt newInstance(String title) {
        MellAllGoodsFgt fragment = new MellAllGoodsFgt();
        fragment.title = title;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp_for_title.setAdapter(pAdapter);
        title_s_tab_layout.setViewPager(vp_for_title);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_all_goods;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        mTitles = new String[]{"食品", "生鲜", "服饰", "家居", "进口", "美妆", "母婴", "电子", "运动", "户外"};
        pAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
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
