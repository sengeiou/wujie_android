package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.fgt.TicketZoonFgt;

import java.util.ArrayList;

public class TicketZoonAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    private String[] mTitles;
    private ArrayList<Fragment> mFragments;
    private MyPagerAdapter myPagerAdapter;
    private int type = 0;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(title);
        vp_for_title.setAdapter(myPagerAdapter);
        title_s_tab_layout.setViewPager(vp_for_title);
        title_s_tab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (0 == position && type != 10) {
                    finish();
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        vp_for_title.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position && type != 10) {
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_ticket_zoon;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 10) {
            mTitles = new String[]{"食品", "生鲜", "服饰", "家居", "进口", "美妆", "母婴", "电子", "运动", "户外"};
        } else {
            mTitles = new String[]{"推荐", "食品", "生鲜", "服饰", "家居", "进口", "美妆", "母婴", "电子", "运动", "户外"};
        }

        title = getIntent().getStringExtra("title");
        mFragments = new ArrayList<>();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for (String title : mTitles) {
            mFragments.add(TicketZoonFgt.getFgt(title, type));
        }
    }

    @Override
    protected void requestData() {

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
