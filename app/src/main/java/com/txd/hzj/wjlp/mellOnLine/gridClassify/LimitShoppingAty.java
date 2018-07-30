package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.DateTool;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.LimitBuyBean;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.LimitFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:09
 * 描述：限量购(2-2限量购2)
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
    private LimitBuyBean limitBuyBean;

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
        L.e("限量购：", requestUrl + " " + jsonStr);
        if (requestUrl.contains("limitBuyIndex")) {

            // 将回传的Json字符串转换为对象
            limitBuyBean = GsonUtil.GsonToBean(jsonStr, LimitBuyBean.class);
            // 获取对象中的数据值
            LimitBuyBean.DataBean data = limitBuyBean.getData();

            // 获取对象中的标签对象
            List<LimitBuyBean.DataBean.StageListBean> stage_list = data.getStage_list();
            // 循环遍历标签对象，将其设置到界面标签上
            for (LimitBuyBean.DataBean.StageListBean stageListBean : stage_list) {
                fragments.add(LimitFgt.getFgt(stageListBean.getStage_id(), stageListBean.getStatus()));
                if (stageListBean.getStatus().equals("即将开始")) {
                    fragments.add(LimitFgt.getFgt(stageListBean.getStage_id(), 1));
                } else if (stageListBean.getStatus().equals("已结束")) {
                    fragments.add(LimitFgt.getFgt(stageListBean.getStage_id(), -1));
                } else {
                    fragments.add(LimitFgt.getFgt(stageListBean.getStage_id(), 0));
                }
            }

            for (int i = 0; i < tabTitle.size(); i++) {
                String now = DateTool.getUserDate("HH:mm");
                String interval = DateTool.getTwoHour(stage_list.get(i).getEnd_time(), now);
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
            return limitBuyBean.getData().getStage_list().get(position).getStart_time() + "\n" + limitBuyBean.getData().getStage_list().get(position).getStatus();
        }
    }
}
