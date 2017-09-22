package com.txd.hzj.wjlp.mellOffLine;

import android.graphics.Color;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOffLine.dialog.NoticeDialog;
import com.txd.hzj.wjlp.mellOffLine.fgt.GoodsByMySelfFgt;
import com.txd.hzj.wjlp.mellOffLine.fgt.GoodsByOtherFgt;
import com.txd.hzj.wjlp.view.UPMarqueeView;

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

    /**
     * 商家其他信息
     */
    @ViewInject(R.id.other_info_layout)
    private LinearLayout other_info_layout;

    /**
     * 上滑
     */
    @ViewInject(R.id.up_tip_tv)
    private TextView up_tip_tv;


    private NoticeDialog noticeDialog;

    /**
     * 公告
     */
    @ViewInject(R.id.noty_up_view)
    private UPMarqueeView noty_up_view;

    /**
     * 误解头条数据
     */
    private List<String> data;
    /**
     * 无界头条View
     */
    private List<View> views;


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
                if (verticalOffset <= -head_layout.getHeight() / 5) {
                    collapsing_toolbar_layout.setTitle("好收成超市");
                } else {
                    collapsing_toolbar_layout.setTitle(" ");
                }
            }
        });
        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));

        other_info_layout.setVisibility(View.GONE);
        up_tip_tv.setVisibility(View.GONE);

        setView();
        noty_up_view.setViews(views);


    }

    @Override
    @OnClick({R.id.off_line_mell_collect_layout, R.id.off_line_mell_share_tv, R.id.notice_layout,
            R.id.mell_info_by_off_line})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.off_line_mell_collect_layout:// 收藏
                showRightTip("收藏");
                break;
            case R.id.off_line_mell_share_tv:// 分享
//                toShare();
                break;
            case R.id.notice_layout:// 公告
                break;
            case R.id.mell_info_by_off_line://详情
                startActivity(OffLineMellInfoAty.class, null);
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

        data = new ArrayList<>();
        views = new ArrayList<>();
        data.add("这只是公告");
        data.add("这只是公告");
        data.add("这只是公告");
        data.add("这只是公告");
        data.add("这只是公告");

    }

    @Override
    protected void requestData() {

    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(OffLineDetailsAty.this).inflate(
                    R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            tv1.setTextColor(Color.WHITE);
            tv1.setGravity(Gravity.CENTER_VERTICAL);
            tv1.setTextSize(10);
            /**
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeDialog = new NoticeDialog(OffLineDetailsAty.this);
                    noticeDialog.show();
                }
            });
            //进行对控件赋值
            tv1.setText(data.get(i));
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    public class VPAdapter extends FragmentPagerAdapter {

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
