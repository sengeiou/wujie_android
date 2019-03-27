package com.txd.hzj.wjlp.clearinventory;

import android.support.v4.app.Fragment;
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
 * 创建时间：2019/3/26 14:09
 * 功能描述：已退款
 */
public class AlreadyRefundedAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.slidingTabLayout)
    private SlidingTabLayout slidingTabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private String[] titles = {"退款中","已完成"};

    private List<Fragment> mFragments;
    @Override
    protected int getLayoutResId() {
        return R.layout.layout_tablayout_viewpager;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("已退款");
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(OrderFragment.getInstance("已退款",titles[i]));
        }

        AlreadyTradedAty.PagerAdapter pagerAdapter = new AlreadyTradedAty.PagerAdapter(getSupportFragmentManager(),mFragments,titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected void requestData() {

    }
}
