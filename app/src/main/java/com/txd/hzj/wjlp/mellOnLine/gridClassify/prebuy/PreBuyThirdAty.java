package com.txd.hzj.wjlp.mellOnLine.gridClassify.prebuy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：13:53
 * 描述：无界预购三级分类
 * ===============Txunda===============
 */

public class PreBuyThirdAty extends BaseAty {
    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * TabLayout
     */
    @ViewInject(R.id.sub_classify_stl)
    private SlidingTabLayout sub_classify_stl;

    @ViewInject(R.id.sub_classify_vp)
    private ViewPager sub_classify_vp;

    private List<Map<String, String>> mTitles;

    private ArrayList<Fragment> mFragments;

    private MyPagerAdapter myPagerAdapter;

    /**
     * 标题
     */
    private String appBarTitle = "";
    /**
     * 二级地址
     */
    private String two_cate_id = "";
    /**
     * 无界预购
     */
    private GroupBuyPst groupBuyPst;
    /**
     * 票券区
     */
    private TicketBuyPst ticketBuyPst;
    /**
     * 数据类型
     * 1.票券区
     * 2.无界预购
     */
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(appBarTitle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_subclassification;
    }

    @Override
    protected void initialized() {
        groupBuyPst = new GroupBuyPst(this);
        ticketBuyPst = new TicketBuyPst(this);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        appBarTitle = getIntent().getStringExtra("appBarTitle");
        two_cate_id = getIntent().getStringExtra("two_cate_id");
        type = getIntent().getIntExtra("type", 0);
        L.e("Aty=====type=====", String.valueOf(type));
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected void requestData() {
        switch (type) {
            case 1:// 票券区
                ticketBuyPst.threeList(two_cate_id, "", 1);
                break;
            case 2:// 无界预购
                groupBuyPst.threeList(two_cate_id, 1, "");
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("threeList")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            if (ToolKit.isList(data, "three_cate_list")) {
                mTitles = JSONUtils.parseKeyAndValueToMapList(data.get("three_cate_list"));
                for (Map<String, String> title : mTitles) {
                    mFragments.add(PreBuyThirdFgt.getFgt(two_cate_id, title.get("three_cate_id"), type));
                }
                sub_classify_vp.setAdapter(myPagerAdapter);
                sub_classify_stl.setViewPager(sub_classify_vp);
                sub_classify_vp.setCurrentItem(0);
            }
        }
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
            return mTitles.get(position).get("name");
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
