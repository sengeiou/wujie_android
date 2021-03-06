package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.street.ThemePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 3:24
 * 描述：5-1主题街
 */
public class ThemeStreetHzjAty extends BaseAty implements NestedScrollView.OnScrollChangeListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 主题街列表
     */
    @ViewInject(R.id.hzj_theme_lv)
    private ListViewForScrollView hzj_theme_lv;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @ViewInject(R.id.theme_sc)
    private NestedScrollView theme_sc;

    @ViewInject(R.id.theme_be_back_top_iv)
    private ImageView theme_be_back_top_iv;

    private List<Map<String, String>> list;
    private List<Map<String, String>> list2;

    private ThemeAdapter themeAdapter;

    private ThemePst themePst;

    private int p = 1;

    @ViewInject(R.id.ptr_theme_layout)
    private SuperSwipeRefreshLayout ptr_theme_layout;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private int size1;
    private int size2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("主题街");

        hzj_theme_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("theme_id", list.get(i).get("theme_id"));
                L.e("=====主题街id=====", list.get(i).get("theme_id"));
                startActivity(ThemeGoodsListAty.class, bundle);
            }
        });
        theme_sc.smoothScrollTo(0, 0);
        theme_sc.setOnScrollChangeListener(this);

        hzj_theme_lv.setEmptyView(no_data_layout);

        ptr_theme_layout.setHeaderViewBackgroundColor(Color.WHITE);
        ptr_theme_layout.setHeaderView(createHeaderView());// add headerView
        ptr_theme_layout.setFooterView(createFooterView());
        ptr_theme_layout.setTargetScrollWithLayout(true);
        ptr_theme_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                themePst.themeList(p);
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        ptr_theme_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                themePst.themeList(p);
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });

    }

    @Override
    @OnClick({R.id.theme_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.theme_be_back_top_iv:
                theme_sc.smoothScrollTo(0, 0);
                theme_be_back_top_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_theme_street_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        themePst = new ThemePst(this);
        size1 = Settings.displayWidth;
        size2 = Settings.displayWidth / 2;

    }

    @Override
    protected void requestData() {
        themePst.themeList(p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        if (requestUrl.contains("themeList")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    themeAdapter = new ThemeAdapter();
                    hzj_theme_lv.setAdapter(themeAdapter);
                }
                progressBar.setVisibility(View.GONE);
                ptr_theme_layout.setRefreshing(false); // 刷新成功
            } else {
                if (ToolKit.isList(map, "data")) {
                    list2 = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    if (!ListUtils.isEmpty(list)) {
                        list.addAll(list2);
                    }
                    themeAdapter.notifyDataSetChanged();
                }
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                ptr_theme_layout.setLoadMore(false); // 加载成功
            }
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (requestUrl.contains("themeList")) {
            if (1 == p) {
                progressBar.setVisibility(View.GONE);
                ptr_theme_layout.setRefreshing(false); // 刷新成功
            } else {
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                ptr_theme_layout.setLoadMore(false); // 加载成功
            }
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY < Settings.displayWidth / 2) {
            theme_be_back_top_iv.setVisibility(View.GONE);
        } else {
            theme_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private class ThemeAdapter extends BaseAdapter {

        private ThemeVh tvh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> theme = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(ThemeStreetHzjAty.this).inflate(R.layout.item_theme_lv, viewGroup, false);
                tvh = new ThemeVh();
                ViewUtils.inject(tvh, view);
                view.setTag(tvh);
            } else {
                tvh = (ThemeVh) view.getTag();
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size1, size2);
            tvh.theme_piv_iv.setLayoutParams(params);
            Glide.with(ThemeStreetHzjAty.this).load(theme.get("theme_img"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(size1, size2)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(tvh.theme_piv_iv);


            return view;
        }

        class ThemeVh {
            /**
             * 主题街图片
             */
            @ViewInject(R.id.theme_piv_iv)
            private ImageView theme_piv_iv;

        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(ptr_theme_layout.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(ptr_theme_layout.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
