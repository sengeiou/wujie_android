package com.txd.hzj.wjlp.mellonLine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mellonLine.fgt.ClassifyFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：上午 11:48
 * 描述：线上商城分类(1-1-1 首页 2)
 */
public class MellOnLineClassifyAty extends BaseAty {
    /**
     * 标题栏
     */
    @ViewInject(R.id.search_title_layout)
    public RelativeLayout search_title_layout;

    @ViewInject(R.id.search_title_be_back_iv)
    public ImageView search_title_be_back_iv;
    /**
     * 扫一扫
     */
    @ViewInject(R.id.title_scan_tv)
    public TextView title_scan_tv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;
    /**
     * 分类
     */
    @ViewInject(R.id.title_classify_tv)
    public TextView title_classify_tv;
    /**
     * 消息
     */
    @ViewInject(R.id.search_title_message_tv)
    public TextView search_title_message_tv;
    /**
     * 消息顶部的图标
     */
    private Drawable top;
    /**
     * 搜索左侧的图标
     */
    private Drawable search_left;
    /**
     * 分类列表
     */
    private List<Map<String, String>> horizontal_classify;

    /**
     * 前一页选中的分类标识
     */
    private int pos = 0;

    private ArrayList<Fragment> fragments;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    private MyPagerAdapter myPagerAdapter;

    private GoodsPst goodsPst;
    private String cate_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);

    }

    /**
     * 标题栏
     */
    private void forTitle() {
        search_title_layout.setBackgroundColor(Color.WHITE);
        title_scan_tv.setVisibility(View.INVISIBLE);
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_classify_tv.setVisibility(View.GONE);

        title_search_tv.setBackgroundResource(R.drawable.shape_search_tv_bg);
        title_search_tv.setCompoundDrawables(search_left, null, null, null);
        title_search_tv.setHintTextColor(Color.parseColor("#9E9E9E"));

        search_title_message_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        search_title_message_tv.setCompoundDrawables(null, top, null, null);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_on_line_classify;
    }

    @Override
    protected void initialized() {
        goodsPst = new GoodsPst(this);
        fragments = new ArrayList<>();
        pos = getIntent().getIntExtra("pos", 0);
        cate_id = getIntent().getStringExtra("cate_id");
        top = ContextCompat.getDrawable(this, R.drawable.icon_message_gray);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        search_left = ContextCompat.getDrawable(this, R.drawable.icon_search_gray);
        search_left.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        horizontal_classify = new ArrayList<>();

    }

    @Override
    protected void requestData() {
        goodsPst.goodsList(1, cate_id, 0,"");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (ToolKit.isList(data, "top_nav")) {
            horizontal_classify = JSONUtils.parseKeyAndValueToMapList(data.get("top_nav"));
            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            for (Map<String, String> title : horizontal_classify) {
                fragments.add(ClassifyFgt.newInstance(title.get("cate_id")));
            }
            // ViewPager设置适配器
            vp_for_title.setAdapter(myPagerAdapter);
            // TabLayout绑定ViewPager
            title_s_tab_layout.setViewPager(vp_for_title);
            // 设置选中第pos个Tab
            title_s_tab_layout.setCurrentTab(pos);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
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
            return horizontal_classify.get(position).get("short_name");
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
