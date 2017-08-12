package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.fgt.TicketZoonFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 4:07
 * 描述：领券
 * ===============Txunda===============
 */
public class GetCouponFgt extends BaseFgt {

    @ViewInject(R.id.title_coupon_tab_layout)
    private SlidingTabLayout title_coupon_tab_layout;


    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    /**
     * 分类列表
     */
    private List<String> horizontal_classify;
    private List<Fragment> mFragments;
    private MyPagerAdapter myPagerAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp_for_title.setAdapter(myPagerAdapter);
        title_coupon_tab_layout.setViewPager(vp_for_title);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_get_coupon;
    }

    @Override
    protected void initialized() {
        horizontal_classify = new ArrayList<>();
        horizontal_classify.add("全部");
        horizontal_classify.add("食品");
        horizontal_classify.add("生鲜");
        horizontal_classify.add("服饰");
        horizontal_classify.add("家居");
        horizontal_classify.add("进口");
        horizontal_classify.add("美妆");
        horizontal_classify.add("母婴");
        horizontal_classify.add("电子");
        mFragments = new ArrayList<>();
        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        for (String title : horizontal_classify) {
            mFragments.add(CouponListFgt.newInstance(title));
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
            return horizontal_classify.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
