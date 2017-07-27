package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 3:24
 * 描述：5-1主题街
 * ===============Txunda===============
 */
public class ThemeStreetHzjAty extends BaseAty implements ObservableScrollView.ScrollViewListener{

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 主题街列表
     */
    @ViewInject(R.id.hzj_theme_lv)
    private ListViewForScrollView hzj_theme_lv;

    @ViewInject(R.id.theme_sc)
    private ObservableScrollView theme_sc;

    @ViewInject(R.id.theme_be_back_top_iv)
    private ImageView theme_be_back_top_iv;

    private List<String> list;

    private ThemeAdapter themeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("主题街");
        hzj_theme_lv.setAdapter(themeAdapter);
        hzj_theme_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ThemeGoodsListAty.class, null);
            }
        });
        theme_sc.smoothScrollTo(0,0);
        theme_sc.setScrollViewListener(this);
    }

    @Override
    @OnClick({R.id.theme_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.theme_be_back_top_iv:
                theme_sc.smoothScrollTo(0,0);
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
        themeAdapter = new ThemeAdapter();
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y< Settings.displayWidth/2){
            theme_be_back_top_iv.setVisibility(View.GONE);
        } else {
            theme_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private class ThemeAdapter extends BaseAdapter {

        private ThemeVh tvh;

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return list.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(ThemeStreetHzjAty.this).inflate(R.layout.item_theme_lv, null);
                tvh = new ThemeVh();
                ViewUtils.inject(tvh, view);
                view.setTag(tvh);
            } else {
                tvh = (ThemeVh) view.getTag();
            }
            return view;
        }

        class ThemeVh {

        }
    }

}
