package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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
public class ThemeStreetHzjAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 主题街列表
     */
    @ViewInject(R.id.hzj_theme_lv)
    private ListView hzj_theme_lv;

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
