package com.txd.hzj.wjlp.mellonLine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mellonLine.fgt.SubClassifyListFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：上午 11:34
 * 描述：子分类(1-1-2)二级分类
 */
public class SubclassificationAty extends BaseAty {
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

    private GoodsPst goodsPst;
    private int page = 0;
    private String mIs_active;

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
        goodsPst = new GoodsPst(this);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        appBarTitle = getIntent().getStringExtra("appBarTitle");
        two_cate_id = getIntent().getStringExtra("two_cate_id");
        mIs_active = getIntent().getStringExtra("is_active");
        page = getIntent().getIntExtra("page", 0);

    }

    @Override
    protected void requestData() {
        goodsPst.threeList(two_cate_id, "", 1,"","","","","", 1,mIs_active);
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
                    mFragments.add(SubClassifyListFgt.getFgt(two_cate_id, title.get("three_cate_id"),mIs_active));
                }
                myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
                sub_classify_vp.setAdapter(myPagerAdapter);
                sub_classify_stl.setViewPager(sub_classify_vp);
                sub_classify_vp.setCurrentItem(page);
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
