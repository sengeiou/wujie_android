package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.tool.DateTool;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.LimitFgt;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:09
 * 描述：限量购(2-2限量购2)
 *
 */
public class LimitShoppingAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.tabLayout)
    private TabLayout tabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    private List<Map<String, String>> tabTitle;
    private List<Fragment> fragments;

    private VpAdapter vpAdapter;
    private LimitBuyPst limitBuyPst;
    private int selectPager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("限量购");
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_limit_shopping;
    }

    @Override
    protected void initialized() {
        tabTitle = new ArrayList<>();
        fragments = new ArrayList<>();
        limitBuyPst = new LimitBuyPst(this);
    }

    @Override
    protected void requestData() {
        limitBuyPst.limitBuyIndex(1, "");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("limitBuyIndex")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (ToolKit.isList(data, "stage_list")) {
                tabTitle = JSONUtils.parseKeyAndValueToMapList(data.get("stage_list"));
                for (Map<String, String> title : tabTitle) {
                    fragments.add(LimitFgt.getFgt(title.get("stage_id"), title.get("status")));
//                    if (title.get("status").equals("即将开始")) {
////                        fragments.add(LimitFgt.getFgt(title.get("stage_id"), 1));
//                    } else if (title.get("status").equals("已结束")) {
////                        fragments.add(LimitFgt.getFgt(title.get("stage_id"), -1));
//                    } else {
////                        fragments.add(LimitFgt.getFgt(title.get("stage_id"), 0));
//                    }
                }

                for (int i = 0; i < tabTitle.size(); i++) {
                    String now = DateTool.getUserDate("HH:mm");
                    String interval = DateTool.getTwoHour(tabTitle.get(i).get("end_time"), now);
                    if (!interval.equals("0")) {
                        selectPager = i;
                        break;
                    }
                }
                vpAdapter = new VpAdapter(getSupportFragmentManager());
                viewPager.setAdapter(vpAdapter);
                viewPager.setCurrentItem(selectPager);
                tabLayout.setupWithViewPager(viewPager);

            }
        }
    }


    private class VpAdapter extends FragmentPagerAdapter {


        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position).get("start_time") + "\n" + tabTitle.get(position).get("status");
        }
    }

}
