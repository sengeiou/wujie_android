package com.txd.hzj.wjlp.minetoAty.feedback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.FeedbackType;
import com.txd.hzj.wjlp.http.article.ArticlePst;
import com.txd.hzj.wjlp.minetoAty.feedback.fgt.FeedbackFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：上午 9:24
 * 描述：意见反馈
 * ===============Txunda===============
 */
public class FeedBackAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private List<FeedbackType.DataBean.FeedbackTypeBean> horizontal_list;

    @ViewInject(R.id.title_feedbac_tab_layout)
    private SlidingTabLayout title_feedbac_tab_layout;

    @ViewInject(R.id.feedback_vp_for_title)
    private ViewPager feedback_vp_for_title;

    private List<Fragment> mFragments;
    private MyPagerAdapter myPagerAdapter;

    private ArticlePst articlePstc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("意见反馈");

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_feed_back;
    }

    @Override
    protected void initialized() {
        horizontal_list = new ArrayList<>();
        mFragments = new ArrayList<>();
        articlePstc = new ArticlePst(this);

    }

    @Override
    protected void requestData() {
        articlePstc.feedbackType();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        FeedbackType feedbackType = GsonUtil.GsonToBean(jsonStr, FeedbackType.class);
        String user_id = feedbackType.getData().getUser_id();
        String real_name = feedbackType.getData().getReal_name();
        horizontal_list = feedbackType.getData().getFeedback_type();
        for (FeedbackType.DataBean.FeedbackTypeBean ftb : horizontal_list) {
            mFragments.add(FeedbackFgt.newInstance(ftb.getF_type_id(), user_id, real_name));
        }
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        feedback_vp_for_title.setAdapter(myPagerAdapter);
        title_feedbac_tab_layout.setViewPager(feedback_vp_for_title);
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
            return horizontal_list.get(position).getF_type_name();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
