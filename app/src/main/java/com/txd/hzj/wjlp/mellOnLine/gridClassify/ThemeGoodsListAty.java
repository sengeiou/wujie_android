package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
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
 * 时间：下午 3:39
 * 描述：5-1主题街2(主题街商品列表)
 * ===============Txunda===============
 */
public class ThemeGoodsListAty extends BaseAty implements ObservableScrollView.ScrollViewListener{

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 主题街商品
     */
    @ViewInject(R.id.theme_goods_gv)
    private GridViewForScrollView theme_goods_gv;

    private List<String> list;
    private ThemeGoodsAdapter themeGoodsAdapter;


    @ViewInject(R.id.tg_sc)
    private ObservableScrollView tg_sc;

    @ViewInject(R.id.tg_be_back_top_iv)
    private ImageView tg_be_back_top_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("主题街");
        theme_goods_gv.setAdapter(themeGoodsAdapter);
        theme_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ThemeGoodsDetailsAty.class, null);
            }
        });
        tg_sc.smoothScrollTo(0,0);
        tg_sc.setScrollViewListener(this);
    }

    @Override
    @OnClick({R.id.tg_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tg_be_back_top_iv:
                tg_sc.smoothScrollTo(0,0);
                tg_be_back_top_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_theme_goods_list;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        themeGoodsAdapter = new ThemeGoodsAdapter();
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y< Settings.displayWidth/2){
            tg_be_back_top_iv.setVisibility(View.GONE);
        } else {
            tg_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private class ThemeGoodsAdapter extends BaseAdapter {
        private TGVH tgvh;

        @Override
        public int getCount() {
            return 12;
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
                view = LayoutInflater.from(ThemeGoodsListAty.this).inflate(R.layout.item_goods_info, null);
                tgvh = new TGVH();
                ViewUtils.inject(tgvh, view);
                view.setTag(tgvh);
            } else {
                tgvh = (TGVH) view.getTag();
            }

            tgvh.theme_lin_layout.setVisibility(View.VISIBLE);
            if (i % 2 == 0) {
                tgvh.use_coupon_tv.setText("不可使用购物券");
                tgvh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
            } else {
                tgvh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                tgvh.use_coupon_tv.setText("可使用30%购物券");
            }

            return view;
        }

        class TGVH {

            @ViewInject(R.id.theme_lin_layout)
            private LinearLayout theme_lin_layout;

            @ViewInject(R.id.use_coupon_tv)
            private TextView use_coupon_tv;

        }
    }
}
