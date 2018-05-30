package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.util.JSONUtils;
import com.synnapps.carouselview.CarouselView;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.Map;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:27
 * 功能描述： 商品详情pranster
 * 联系方式：常用邮箱或电话
 */
public class CommodityDetailsPranster implements CommodityDetailsInter.CommodityPranster, ObservableScrollView.ScrollViewListener,BaseView {
    protected CommodityDetailsInter.CommodityView commodityView;

    @Override
    public void setView(CommodityDetailsInter.CommodityView view) {
        commodityView = view;
    }


    private ImageView be_back_top_iv;
    private int bannerHeight;
    private int topHeight;
    private int secondHeight;
    private int clickType = 0;
    private TextView title_goods_tv, title_details_tv, title_evaluate_tv;
    private View title_goods_view, title_details_view, title_evaluate_view;
    private View title_goods_layout, title_details_layout, title_evaluate_layout;
    private Context context;

    public void setTabViews(View title_goods_layout, View title_details_layout, View title_evaluate_layout) {
        this.context = title_details_layout.getContext();

        this.title_details_layout = title_details_layout;
        title_details_tv = title_details_layout.findViewById(R.id.title_details_tv);
        title_details_view = title_details_layout.findViewById(R.id.title_details_view);
        title_details_layout.setOnClickListener(listener);

        this.title_evaluate_layout = title_evaluate_layout;
        title_evaluate_tv = title_evaluate_layout.findViewById(R.id.title_evaluate_tv);
        title_evaluate_view = title_evaluate_layout.findViewById(R.id.title_evaluate_view);
        title_evaluate_layout.setOnClickListener(listener);

        this.title_goods_layout = title_goods_layout;
        title_goods_tv = title_goods_layout.findViewById(R.id.title_goods_tv);
        title_goods_view = title_goods_layout.findViewById(R.id.title_goods_view);
        title_goods_layout.setOnClickListener(listener);

        be_back_top_iv.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.be_back_top_iv:
//                    setTextViewAndViewColor(0);
                case R.id.title_goods_layout: {
                    clickType = 1;
                    limit_goods_details_sc.smoothScrollTo(0, 0);
                }
                break;
                case R.id.title_details_layout: {
                    clickType = 2;
                    limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                }
                break;
                case R.id.title_evaluate_layout: {
                    clickType = 3;
                    limit_goods_details_sc.smoothScrollTo(0, topHeight);
                }
                break;
            }
        }
    };
    private ObservableScrollView limit_goods_details_sc;

    boolean init = false;

    public void getHeight(final CarouselView online_carvouse_view,final LinearLayout top_lin_layout,final LinearLayout second_lin_layout, final ObservableScrollView limit_goods_details_sc, ImageView be_back_top_iv) {


        ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = online_carvouse_view.getHeight();
                topHeight = top_lin_layout.getHeight();
                secondHeight = second_lin_layout.getHeight();
//                limit_goods_details_sc.setScrollViewListener(GoodLuckDetailsAty.this);
            }
        });

//        bannerHeight = online_carvouse_view.getHeight();
//        topHeight = top_lin_layout.getHeight();
//        secondHeight = second_lin_layout.getHeight();
        if (!init) {
            limit_goods_details_sc.setScrollViewListener(this);
            this.limit_goods_details_sc = limit_goods_details_sc;
            this.be_back_top_iv = be_back_top_iv;
        }
        init = true;
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            be_back_top_iv.setVisibility(View.GONE);
            setTextViewAndViewColor(0);
        } else if (y > bannerHeight) {
            be_back_top_iv.setVisibility(View.VISIBLE);
            if (y < topHeight) {
                setTextViewAndViewColor(0);
            } else if (y >= topHeight && y < secondHeight) {
                setTextViewAndViewColor(2);
            } else {
                setTextViewAndViewColor(1);
            }
        }
        if (oldy > y) {
            clickType = 0;
        }
    }

    private void setTextViewAndViewColor(int next) {
        title_goods_tv.setTextColor(Color.BLACK);
        title_details_tv.setTextColor(Color.BLACK);
        title_evaluate_tv.setTextColor(Color.BLACK);

        title_goods_view.setBackgroundColor(Color.WHITE);
        title_details_view.setBackgroundColor(Color.WHITE);
        title_evaluate_view.setBackgroundColor(Color.WHITE);

        if (1 == clickType) {
            title_goods_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
            return;
        }
        if (2 == clickType) {
            title_details_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
            return;
        }
        if (3 == clickType) {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
            return;
        }

        if (0 == next) {
            title_goods_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
        } else if (1 == next) {
            title_details_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
        } else {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
        }
    }

    @Override
    public void freight(String goods_id, String tx) {

        Freight.freight(goods_id, tx, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void removeDialog() {

    }

    @Override
    public void removeContent() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {

    }

    @Override
    public void onException(Exception exception) {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
            if(requestUrl.contains("Freight/freight")){
                if (requestUrl.contains("freight")) {
                    Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
                    map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                    commodityView.getFreightPay(map.get("pay"));
                }
            }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }
}
