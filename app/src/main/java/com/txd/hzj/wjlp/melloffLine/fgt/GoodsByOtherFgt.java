package com.txd.hzj.wjlp.melloffLine.fgt;


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
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 4:38
 * 描述：流转商品
 *
 */
public class GoodsByOtherFgt extends BaseFgt {

    /**
     * 分类列表
     */
    private List<String> horizontal_classify;


    /**
     * 分类菜单
     */
    @ViewInject(R.id.title_goods_by_other_layout)
    private SlidingTabLayout title_goods_by_other_layout;

    @ViewInject(R.id.vp_for_goods_title)
    private ViewPager vp_for_goods_title;
    private List<Fragment> fragments;

    private MyPagerAdapter myPagerAdapter;


    public static GoodsByOtherFgt newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        GoodsByOtherFgt pageFragment = new GoodsByOtherFgt();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vp_for_goods_title.setAdapter(myPagerAdapter);
        title_goods_by_other_layout.setViewPager(vp_for_goods_title);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_goods_by_other;
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
        fragments = new ArrayList<>();
        for (String title : horizontal_classify) {
            fragments.add(ForGoodsByOtherFgt.newInstance(title));
        }
        myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
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
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return horizontal_classify.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
