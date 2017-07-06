package com.txd.hzj.wjlp.mellOnLine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.HotBrandAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.ThreeClassifyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：下午 5:05
 * 描述：全部分类(1-2分类)
 * ===============Txunda===============
 */
public class AllClassifyAty extends BaseAty {

    /**
     * 标题栏
     */
    @ViewInject(R.id.search_title_layout)
    public RelativeLayout search_title_layout;
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
     * 左侧大分类列表
     */
    @ViewInject(R.id.classify_left_lv)
    private ListView classify_left_lv;
    /**
     * 右侧小分类列表
     */
    @ViewInject(R.id.classify_right_lv)
    private ListView classify_right_lv;

    private List<String> left;
    private List<String> right;
    private List<String> three;
    private List<String> hot_brand;

    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        // 设置沉浸式状态栏
        showStatusBar(R.id.search_title_layout);

        classify_left_lv.setAdapter(leftAdapter);
        classify_left_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                leftAdapter.setSelected(i);
            }
        });
        classify_right_lv.setAdapter(rightAdapter);
    }

    /**
     * 标题栏
     */
    private void forTitle() {
        search_title_layout.setBackgroundColor(Color.WHITE);
        title_scan_tv.setVisibility(View.GONE);
        title_classify_tv.setVisibility(View.GONE);

        title_search_tv.setBackgroundResource(R.drawable.shape_search_tv_bg);
        title_search_tv.setCompoundDrawables(search_left, null, null, null);
        title_search_tv.setHintTextColor(Color.parseColor("#9E9E9E"));

        search_title_message_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        search_title_message_tv.setCompoundDrawables(null, top, null, null);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_all_classify;
    }

    @Override
    protected void initialized() {
        top = ContextCompat.getDrawable(this, R.drawable.icon_message_gray);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        search_left = ContextCompat.getDrawable(this, R.drawable.icon_search_gray);
        search_left.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());

        left = new ArrayList<>();
        right = new ArrayList<>();
        three = new ArrayList<>();
        hot_brand = new ArrayList<>();
        leftAdapter = new LeftAdapter();
        rightAdapter = new RightAdapter();

    }

    @Override
    protected void requestData() {

    }

    /**
     * 左边的适配器
     */
    private class LeftAdapter extends BaseAdapter {
        private LeftViewHolder lvh;
        private int selected = 0;

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return left.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(AllClassifyAty.this).inflate(R.layout.item_all_classify_left_tv,
                        viewGroup, false);
                lvh = new LeftViewHolder();
                ViewUtils.inject(lvh, view);
                view.setTag(lvh);
            } else {
                lvh = (LeftViewHolder) view.getTag();
            }

            if (i == selected) {
                lvh.left_classify_tv.setTextColor(ContextCompat.getColor(AllClassifyAty.this, R.color.theme_color));
                lvh.left_classify_tv.setBackgroundColor(ContextCompat.getColor(AllClassifyAty.this, R.color.white));
                lvh.left_indicator_view.setBackgroundColor(ContextCompat.getColor(AllClassifyAty.this, R.color
                        .theme_color));
            } else {
                lvh.left_classify_tv.setBackgroundColor(ContextCompat.getColor(AllClassifyAty.this, R.color.bg_color));
                lvh.left_classify_tv.setTextColor(ContextCompat.getColor(AllClassifyAty.this, R.color.app_text_color));
                lvh.left_indicator_view.setBackgroundColor(ContextCompat.getColor(AllClassifyAty.this, R.color
                        .bg_color));
            }

            return view;
        }

        public void setSelected(int selected) {
            this.selected = selected;
            notifyDataSetChanged();
        }

        class LeftViewHolder {
            @ViewInject(R.id.left_indicator_view)
            private View left_indicator_view;
            @ViewInject(R.id.left_classify_tv)
            private TextView left_classify_tv;
        }
    }

    /**
     * ===============Txunda===============
     * 作者：DUKE_HwangZj
     * 日期：2017/7/5 0005
     * 时间：下午 5:56
     * 描述：右侧的适配器
     * ===============Txunda===============
     */
    private class RightAdapter extends BaseAdapter {

        private RightViewHolder rvh;

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return right.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(AllClassifyAty.this).inflate(R.layout.item_right_classify,
                        viewGroup, false);
                rvh = new RightViewHolder();
                ViewUtils.inject(rvh, view);
                view.setTag(rvh);
            } else {
                rvh = (RightViewHolder) view.getTag();
            }
            rvh.classify_three_level_gv.setAdapter(new ThreeClassifyAdapter(AllClassifyAty.this, right));
            rvh.hot_brand_rv.setLayoutManager(new LinearLayoutManager(AllClassifyAty.this, LinearLayoutManager
                    .HORIZONTAL, false));
            rvh.hot_brand_rv.setItemAnimator(new DefaultItemAnimator());

            rvh.hot_brand_rv.setAdapter(new HotBrandAdapter(AllClassifyAty.this, hot_brand));
            return view;
        }

        class RightViewHolder {
            @ViewInject(R.id.classify_three_level_gv)
            private GridViewForScrollView classify_three_level_gv;

            @ViewInject(R.id.hot_brand_rv)
            private RecyclerView hot_brand_rv;

        }
    }

}
