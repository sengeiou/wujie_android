package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.welfare.WelfarePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 4:07
 * 描述：领券
 */
public class GetCouponFgt extends BaseFgt {

    @ViewInject(R.id.title_coupon_tab_layout)
    private SlidingTabLayout title_coupon_tab_layout;


    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    /**
     * 分类列表
     */
    private List<Map<String, String>> horizontal_classify;
    private List<Fragment> mFragments;
    private MyPagerAdapter myPagerAdapter;

    private WelfarePst welfarePst;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_get_coupon;
    }

    @Override
    protected void initialized() {
        welfarePst = new WelfarePst(this);
        horizontal_classify = new ArrayList<>();
        mFragments = new ArrayList<>();

    }

    @Override
    protected void requestData() {
        welfarePst.ticketList(1, "", 0);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (ToolKit.isList(data, "top_nav")) {
            horizontal_classify = JSONUtils.parseKeyAndValueToMapList(data.get("top_nav"));
            for (Map<String, String> title : horizontal_classify) {
                mFragments.add(CouponListFgt.newInstance(title.get("cate_id")));
            }
            myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
            vp_for_title.setAdapter(myPagerAdapter);
            title_coupon_tab_layout.setViewPager(vp_for_title);
        }
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
            return horizontal_classify.get(position).get("name");
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
