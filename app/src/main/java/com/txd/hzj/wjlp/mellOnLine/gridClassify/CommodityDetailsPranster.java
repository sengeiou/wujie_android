package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.List;
import java.util.Map;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:27
 * 功能描述： 商品详情pranster
 * 联系方式：常用邮箱或电话
 */
public class CommodityDetailsPranster implements CommodityDetailsInter.CommodityPranster, ObservableScrollView.ScrollViewListener, BaseView {
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

    public void getHeight(final CarouselView online_carvouse_view, final LinearLayout top_lin_layout, final LinearLayout second_lin_layout, final ObservableScrollView limit_goods_details_sc, ImageView be_back_top_iv) {


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
        if (requestUrl.contains("Freight/freight")) {
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

    CommonPopupWindow commonPopupWindow = null;

    /**
     * 代金券的弹窗
     *
     * @param view
     */
    public void showDjqPop(View view, List<DjTicketBean> list, Activity activity, String vouchers_desc) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        CommonPopupWindow.Builder builder = new CommonPopupWindow.Builder(activity).setView(R.layout.layout_popp_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new ViewInterface(list, vouchers_desc), 0)
                .setAnimationStyle(R.style.animbottom);
        commonPopupWindow = builder.create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    class ViewInterface implements CommonPopupWindow.ViewInterface {
        List<DjTicketBean> list;
        String vouchers_desc;

        public ViewInterface(List<DjTicketBean> list, String vouchers_desc) {
            this.list = list;
            this.vouchers_desc = vouchers_desc;
        }

        @Override
        public void getChildView(View view, int layoutResId, int position) {
            LinearLayout layout_djq0 = (LinearLayout) view.findViewById(R.id.layout_djq0);
            LinearLayout layout_djq1 = (LinearLayout) view.findViewById(R.id.layout_djq1);
            LinearLayout layout_djq2 = (LinearLayout) view.findViewById(R.id.layout_djq2);
            TextView tv_djq_color0 = (TextView) view.findViewById(R.id.tv_djq_color0);
            TextView tv_djq_color1 = (TextView) view.findViewById(R.id.tv_djq_color1);
            TextView tv_djq_color2 = (TextView) view.findViewById(R.id.tv_djq_color2);
            TextView tv_djq_desc0 = (TextView) view.findViewById(R.id.tv_djq_desc0);
            TextView tv_djq_desc1 = (TextView) view.findViewById(R.id.tv_djq_desc1);
            TextView tv_djq_desc2 = (TextView) view.findViewById(R.id.tv_djq_desc2);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonPopupWindow.dismiss();
                }
            });
            tv_desc.setText(vouchers_desc);
            for (int i = 0; i < list.size(); i++) {
                switch (i) {
                    case 0:
                        setStates(layout_djq0, tv_djq_desc0, list.get(i).getDiscount_desc());
                        break;
                    case 1:
                        setStates(layout_djq1, tv_djq_desc1, list.get(i).getDiscount_desc());
                        break;
                    case 2:
                        setStates(layout_djq2, tv_djq_desc2, list.get(i).getDiscount_desc());
                        break;
                }
                switch (list.get(i).getType()) {
                    case "0":
                        setTypeStates(tv_djq_color0, R.drawable.shape_red_bg);
                        break;
                    case "1":
                        setTypeStates(tv_djq_color1, R.drawable.shape_yellow_bg);
                        break;
                    case "2":
                        setTypeStates(tv_djq_color2, R.drawable.shape_blue_bg);
                        break;
                }
            }
        }

        private void setStates(LinearLayout layout_djq, TextView tv_djq_desc, String txt) {
            layout_djq.setVisibility(View.VISIBLE);
            tv_djq_desc.setText(txt);
        }

        private void setTypeStates(TextView tv_djq_color, int shap_color) {
            tv_djq_color.setBackgroundResource(shap_color);
        }
    }


    /**
     * 促销（抽）
     *
     * @param view
     */
    @Override
    public void showCXPop(View view, final Activity activity, final List<PromotionBean> promotion) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(activity)
                .setView(R.layout.layou_popp_cuxiao)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        ListView promotion_lv = (ListView) view.findViewById(R.id.promotion_lv);
                        PromotionAdapter promotionAdapter = new PromotionAdapter(activity, promotion);
                        promotion_lv.setAdapter(promotionAdapter);
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });

                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }



    /**
     * 领券(抽)
     */
    @Override
    public void showLQPop(View view, final String title,final Activity activity, final TheTrickAdapter theTrickAdapter) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(activity)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new GridLayoutManager(activity, 2));
                        recyclerview.setAdapter(theTrickAdapter);
                        TextView tv_title = (TextView) view.findViewById(R.id.popp_title);
                        tv_title.setText(title);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });

                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }




    public void showPop(View view, final String title, final List<GoodsServerBean> list, final int type,final Activity activity) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(activity)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new LinearLayoutManager(activity, 1, false));
                        recyclerview.setAdapter(new Service_adp(list, type,activity));
                        TextView tv_title = (TextView) view.findViewById(R.id.popp_title);
                        tv_title.setText(title);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 拼单购、无界商店和进口馆商品详情页公共属性封装
     */
    @Override
    public void setBitmap(Context context, String url, View view) {
        Glide.with(context).load(url).into((ImageView) view);
    }

    @Override
    public void setTextContent(String content, View view) {
        ((TextView) view).setText(content);

    }

    /**
     * 拼单购、无界商店和进口馆商品详情页收藏
     */
    @Override
    public void isCollect(String is_collect, String viewContent, View view, Context context) {
        if (is_collect.equals("0")){
            ((TextView)view).setCompoundDrawables(null, TextUtils.toDrawable(context, R.drawable.icon_collect), null, null);
        }else {
            ((TextView)view).setCompoundDrawables(null, TextUtils.toDrawable(context, R.drawable.icon_collected), null, null);
        }
        ((TextView)view).setText(viewContent);
    }

}
