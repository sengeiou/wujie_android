package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOffLine.fgt.GoodsByMySelfFgt;
import com.txd.hzj.wjlp.mellOffLine.fgt.GoodsByOtherFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 2:58
 * 描述：线下店铺详情
 * ===============Txunda===============
 */
public class OffLineDetailsAty extends BaseAty {

    /**
     * 最外层布局
     */
    @ViewInject(R.id.root_layout)
    private CoordinatorLayout root_layout;

    /**
     * AppBarLAyout
     */
    @ViewInject(R.id.app_bar_layout)
    private AppBarLayout app_bar_layout;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    @ViewInject(R.id.head_layout)
    private LinearLayout head_layout;

    /**
     * ToolBar
     */
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    /**
     * TabLayout
     */
    @ViewInject(R.id.toolbar_tab)
    private TabLayout toolbar_tab;

    /**
     * ViewPager
     */
    @ViewInject(R.id.main_vp_container)
    private ViewPager main_vp_container;

    private List<String> mTitle;
    private List<Fragment> mFragment;
    private VPAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 沉浸式
        showStatusBar(R.id.toolbar);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    collapsing_toolbar_layout.setTitle("好收成超市");
                } else {
                    collapsing_toolbar_layout.setTitle(" ");
                }
            }
        });

        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));


    }

    @Override
    @OnClick({R.id.off_line_mell_collect_layout,R.id.off_line_mell_share_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.off_line_mell_collect_layout:// 收藏
                showRightTip("收藏");
                break;
            case R.id.off_line_mell_share_tv:// 分享
                toShare();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_off_line_details;
    }

    @Override
    protected void initialized() {
        mTitle = new ArrayList<>();
        mTitle.add("流转商品");
        mTitle.add("自营商品");
        mFragment = new ArrayList<>();
        mFragment.add(GoodsByOtherFgt.newInstance(0));
        mFragment.add(GoodsByMySelfFgt.newInstance(1));
        vpAdapter = new VPAdapter(getSupportFragmentManager());
    }

    @Override
    protected void requestData() {

    }

    public class VPAdapter extends FragmentPagerAdapter{

        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }

}
