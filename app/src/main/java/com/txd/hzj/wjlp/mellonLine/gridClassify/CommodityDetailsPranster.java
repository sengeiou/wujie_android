package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.synnapps.carouselview.CarouselView;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.EasemobBean;
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.Event_msgBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsMsgBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.http.Easemob;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.http.Goods;
import com.txd.hzj.wjlp.mellonLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea.GiveAwayDetailsAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.ToastView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
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
    private Activity activity;
    private String phoneNo;
    //1、普通商品(进口管、主题街) 2、拼单购 3、积分商城 4、分销（普通商品） 5、分销（399商品）
    private String mType;
    //商品id
    private String mGoods_id;
    //轮播图地址
    private ArrayList<String> mImageUrls;

    //商品名称
    private String mGoods_name;
    //商品简介
    private String mGoods_brief;
    //售价
    private String mShop_price;
    //市场价
    private String mMarket_price;
    //积分
    private String mIntegral;
    private String mTicket_buy_discount;
    //已售数量
    private String mSell_num;

    //分销的shop_id
    private String mShop_id;

    public CommodityDetailsPranster(CommodityDetailsInter.CommodityView view) {
        setView(view);
    }

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

    @Override
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

    public void setData(Activity activity,String type,String id, ArrayList<String> images, String goods_name, String integral, String discount, String shop_price, String market_price, String shop_id, String goods_brief, String sell_num){
        this.activity=activity;
        mType=type;
        mGoods_id=id;
        mImageUrls=images;
        mGoods_name=goods_name;
        mIntegral=integral;
        mTicket_buy_discount=discount;
        mShop_price=shop_price;
        mMarket_price=market_price;
        mShop_id=shop_id;
        mGoods_brief=goods_brief;
        mSell_num=sell_num;

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
                    if (activity instanceof GiveAwayDetailsAty){
                        limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                    }else {
                        PosterAty.getInstance(activity,mType,mGoods_id,mImageUrls,mGoods_name,mIntegral,mTicket_buy_discount,mShop_price,mMarket_price,mShop_id,mGoods_brief,mSell_num);
                    }
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

    @Override
    public void getHeight(final CarouselView online_carvouse_view, final LinearLayout top_lin_layout, final LinearLayout second_lin_layout, final ObservableScrollView limit_goods_details_sc, ImageView be_back_top_iv) {


        final ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (vto.isAlive()){
                    vto.removeOnGlobalLayoutListener(this);
                    online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    bannerHeight = online_carvouse_view.getHeight();
                    topHeight = top_lin_layout.getHeight();
                    secondHeight = second_lin_layout.getHeight();
                }
            }
        });
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
                if (activity instanceof  GiveAwayDetailsAty){
                    setTextViewAndViewColor(1);
                }
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
    public void freight(String goods_id, String tx, String goods_num, String product_id) {

        Freight.freight(goods_id, tx, goods_num, product_id, this);
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
        } else if (requestUrl.contains("Easemob/bind")) {
            /**
             * 获取商家环信账号
             */
            L.e("Easemob/bind：" + jsonStr);
            if (jsonStr == null || jsonStr.equals("")) {
                commodityView.showErrorTip("获取数据为空，请联系我们");
                return;
            }

            Gson gson = new Gson();
            final EasemobBean easemobBean = gson.fromJson(jsonStr, EasemobBean.class); // 如果Json有值 bean 对象必定有值

            // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓创建Dialog弹窗显示列表项↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            AlertDialog.Builder builder = new AlertDialog.Builder(activity); // 创建对话框构建器
            View view2 = View.inflate(activity, R.layout.popup_sel_chat, null); // 获取布局
            builder.setView(view2); // 设置参数主要是设置获取的布局View
            // 获取布局中的控件
            View serverPhoneLayout = view2.findViewById(R.id.serverPhoneLayout);
            if (android.text.TextUtils.isEmpty(phoneNo)) {
                serverPhoneLayout.setVisibility(View.GONE);
            } else {
                serverPhoneLayout.setVisibility(View.VISIBLE);
                TextView serPhoneTv = view2.findViewById(R.id.serPhoneTv);
                serPhoneTv.setText("客服电话： " + phoneNo);
                serverPhoneLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callMerchantPhone(phoneNo, activity);
                    }
                });
            }
            ListView dataLv = (ListView) view2.findViewById(R.id.popSelChat_data_lv);
            LinearLayout nodataLayout = (LinearLayout) view2.findViewById(R.id.popSelChat_nodata_layout);
            // 以上判断Bean有值，但是以防万一还是先判空
            if (easemobBean == null || easemobBean.getData().getEasemob_account().size() < 1) {
                // 如果Bean为空或者获取的在线客服账号数小于1，也就是没有在线客服
                dataLv.setVisibility(View.GONE); // 隐藏List列表
                nodataLayout.setVisibility(View.VISIBLE); // 显示空数据提示
            } else {
                // 否则就是有在线客服
                dataLv.setVisibility(View.VISIBLE); // 显示List列表
                nodataLayout.setVisibility(View.GONE); // 隐藏空数据提示
            }

            final AlertDialog alertDialog = builder.create();// 创建对话框
            // 设置相应的控件操作，赋值、点击事件等等

            dataLv.setAdapter(new MerchantDialogAdapter(easemobBean.getData().getEasemob_account(), activity));

            dataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EasemobBean.DataBean.EasemobAccountBean easemobAccountBean = easemobBean.getData().getEasemob_account().get(position);
                    // 参数说明：账号、头像、昵称
                    commodityView.toChat(easemobAccountBean.getHx(), easemobAccountBean.getHead_pic(), easemobAccountBean.getNickname());
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑创建Dialog弹窗显示列表项↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        } else if (requestUrl.contains("Goods/goodsMsg")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, GoodsMsgBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    GoodsMsgBean dataBean = (GoodsMsgBean) t;
                    List<Event_msgBean> event_msgBeans = dataBean.getData().getEvent_msg();
                    if (null != event_msgBeans) {
                        toastView.setVisibility(View.VISIBLE);
                        toastView.setDatas(event_msgBeans);
                    }
                }
            });
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
    @Override
    public void showDjqPop(View view, List<DjTicketBean> list, Activity activity, String vouchers_desc) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
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
            if (list != null) {
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
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
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
    public void showLQPop(View view, final String title, final Activity activity, final TheTrickAdapter theTrickAdapter) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
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


    public void showPop(View view, final String title, final List<GoodsServerBean> list, final int type, final Activity activity) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
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
                        recyclerview.setAdapter(new Service_adp(list, type, activity));
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
     * 拼单购、积分商店和进口馆商品详情页公共属性封装
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
     * 拼单购、积分商店和进口馆商品详情页收藏
     */
    @Override
    public void isCollect(String is_collect, String viewContent, View view, Context context) {
        if (is_collect.equals("0")) {
            ((TextView) view).setCompoundDrawables(null, TextUtils.toDrawable(context, R.drawable.icon_collect), null, null);
        } else {
            ((TextView) view).setCompoundDrawables(null, TextUtils.toDrawable(context, R.drawable.icon_collected), null, null);
        }
        ((TextView) view).setText(viewContent);
    }

    @Override
    public void chat_merchant(String merchant_id, Activity activity, String phoneNo) {
        this.activity = activity;
        this.phoneNo = phoneNo;
        Easemob.bind(merchant_id, this); // 获取商铺的环信账号
    }

    @Override
    public void callMerchantPhone(String phoneNo, Activity activity) {
        /**
         * 暂时不需要的权限，维护用户体验
         * !AndPermission.hasPermission(MainAty.this, Manifest.permission.CALL_PHONE) ||电话
         * !AndPermission.hasPermission(MainAty.this, Manifest.permission.RECORD_AUDIO)录音
         *
         * Manifest.permission.RECORD_AUDIO, // 启用录音权限
         * Manifest.permission.CALL_PHONE,
         * */
        // 先判断是否有权限。
        if (!AndPermission.hasPermission(activity, Manifest.permission.CALL_PHONE)) {
            // 申请权限。
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(Manifest.permission.CALL_PHONE)
                    .send();
        } else {
            commodityView.call(phoneNo);
        }
    }

    @Override
    public PermissionListener requestPhoneListener(final String phoneNo, final Activity activity) {
        PermissionListener listener = new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantedPermissions) {
                commodityView.call(phoneNo);
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                // 权限申请失败回调。
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(activity, 300)
                            .setTitle("权限申请失败")
                            .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                            .setPositiveButton("好，去设置")
                            .show();
                }
            }
        };
        return listener;
    }

    private ToastView toastView;

    @Override
    public void goodsMsg(ToastView toastView) {
        this.toastView = toastView;
        Goods.goodsMsg(this);
    }
}
