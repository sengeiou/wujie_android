package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.http.auction.AuctionPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.dialog.AuctionSingUpDialog;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.http.AuctionOrder;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：下午 4:17
 * 描述：8-3拍品详情
 * ===============Txunda===============
 */
public class AuctionGoodsDetailsAty extends BaseAty implements ObservableScrollView.onBottomListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private List<Map<String, String>> image;

    /**
     * 分红权
     */
    @ViewInject(R.id.auction_profit_num_tv)
    private TextView auction_profit_num_tv;

    /**
     * 当前价
     */
    @ViewInject(R.id.auction_price_tv)
    private TextView auction_price_tv;
    /**
     * 围观人数
     */
    @ViewInject(R.id.on_lookers_tv)
    private TextView on_lookers_tv;
    /**
     * 报名人数
     */
    @ViewInject(R.id.be_sign_up_tv)
    private TextView be_sign_up_tv;
    /**
     * 设置提醒人数
     */
    @ViewInject(R.id.set_remind_tv)
    private TextView set_remind_tv;

    /**
     * 起拍价
     */
    @ViewInject(R.id.left_tv_1)
    private TextView left_tv_1;
    /**
     * 加价幅度
     */
    @ViewInject(R.id.left_tv_2)
    private TextView left_tv_2;
    /**
     * 开拍时间
     */
    @ViewInject(R.id.left_tv_3)
    private TextView left_tv_3;
    /**
     * 保留价
     */
    @ViewInject(R.id.left_tv_4)
    private TextView left_tv_4;
    /**
     * 保证金
     */
    @ViewInject(R.id.righr_tv_1)
    private TextView righr_tv_1;
    /**
     * 拍卖佣金
     */
    @ViewInject(R.id.righr_tv_2)
    private TextView righr_tv_2;
    /**
     * 结束时间
     */
    @ViewInject(R.id.righr_tv_3)
    private TextView righr_tv_3;
    /**
     * 延时周期
     */
    @ViewInject(R.id.righr_tv_4)
    private TextView righr_tv_4;

    /**
     * 商家名称和评分
     */
    @ViewInject(R.id.mell_name_and_grade_tv)
    private TextView mell_name_and_grade_tv;

    /**
     * 出价id
     */
    @ViewInject(R.id.user_auction_id_tv)
    private TextView user_auction_id_tv;

    /**
     * 拍卖记录列表
     */
    @ViewInject(R.id.auction_info_lv)
    private ListViewForScrollView auction_info_lv;
    @ViewInject(R.id.layout_chujia)
    private LinearLayout layout_chujia;
    private List<Map<String, String>> auctionInfoList;

    private AuctionInfoAdapter auctionInfoAdapter;

    /**
     * 去报名
     */
    @ViewInject(R.id.sing_up_tv)
    private TextView sing_up_tv;
    /**
     * 竞拍出价(去报名)
     */
    private AuctionSingUpDialog singUpDialog;


    private AuctionPst auctionPst;

    private String auction_id = "";

    /**
     * 店铺logo
     */
    @ViewInject(R.id.auction_mell_logo_iv)
    private ImageView auction_mell_logo_iv;
    /**
     * 店铺logo大小
     */
    private int logoSize = 0;
    /**
     * 拍卖状态
     */
    @ViewInject(R.id.auction_status_tv)
    private TextView auction_status_tv;
    /**
     * 轮播图大小
     */
    private int bannerSize = 0;
    /**
     * 商品大小
     */
    @ViewInject(R.id.action_goods_name_tv)
    private TextView action_goods_name_tv;

    /**
     * 设置提醒
     */
    @ViewInject(R.id.remind_me_tv)
    private TextView remind_me_tv;
    /**
     * 是否提醒了
     * 1 提醒 0未提醒
     */
    private String is_remind = "";

    @ViewInject(R.id.tv_expirationdate)
    private TextView tv_expirationdate;//保质期提示
    @ViewInject(R.id.tv_hdsm)
    private TextView tv_hdsm;

    @ViewInject(R.id.tv_tab_1)
    private TextView tv_tab_1;
    @ViewInject(R.id.tv_tab_2)
    private TextView tv_tab_2;
    @ViewInject(R.id.tv_tab_3)
    private TextView tv_tab_3;
    @ViewInject(R.id.goods_desc_wv)
    private WebView goods_desc_wv;
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;
    @ViewInject(R.id.layout_aftersale)
    private LinearLayout layout_aftersale;//包装售后

    @ViewInject(R.id.tv_bzqd)
    private TextView tv_bzqd;//包装清单
    @ViewInject(R.id.tv_shfw)
    private TextView tv_shfw;//售后服务
    @ViewInject(R.id.tv_jgsm)
    private TextView tv_jgsm;//价格说明


    @ViewInject(R.id.layout_djq)
    private LinearLayout layout_djq;//代金券布局
    @ViewInject(R.id.layout_djq0)
    private LinearLayout layout_djq0;
    @ViewInject(R.id.layout_djq1)
    private LinearLayout layout_djq1;
    @ViewInject(R.id.layout_djq2)
    private LinearLayout layout_djq2;
    @ViewInject(R.id.tv_djq_color0)
    private TextView tv_djq_color0;
    @ViewInject(R.id.tv_djq_color1)
    private TextView tv_djq_color1;
    @ViewInject(R.id.tv_djq_color2)
    private TextView tv_djq_color2;
    @ViewInject(R.id.tv_djq_desc0)
    private TextView tv_djq_desc0;
    @ViewInject(R.id.tv_djq_desc1)
    private TextView tv_djq_desc1;
    @ViewInject(R.id.tv_djq_desc2)
    private TextView tv_djq_desc2;


    @ViewInject(R.id.im_country_logo)
    private ImageView im_country_logo;//国旗
    @ViewInject(R.id.tv_country_desc)
    private TextView tv_country_desc;//国家
    @ViewInject(R.id.tv_country_tax)
    private TextView tv_country_tax;//进口税


    @ViewInject(R.id.layout_service)
    private LinearLayout layout_service;//服务布局
    @ViewInject(R.id.rv_service)
    private RecyclerView rv_service;

    ArrayList<Map<String, String>> ser_list;//服务的列表

    @ViewInject(R.id.limit_goods_details_sc)
    private ObservableScrollView limit_goods_details_sc;

    @ViewInject(R.id.layout_jinkoushui)
    private LinearLayout layout_jinkoushui;

    private String vouchers_desc = "";//代金券弹窗下面的提示文字
    @ViewInject(R.id.ticket_gv)//推荐商品列表
    private GridViewForScrollView ticket_gv;
    private AllGvLvAdapter allGvLvAdapter1;
    private boolean is_f = true;//判断刷新
    private int page = 1;
    private List<AllGoodsBean> ticket = new ArrayList<>();
    private List<AllGoodsBean> more = new ArrayList<>();
    private ArrayList<Map<String, String>> dj_ticket;
    private Map<String, String> mInfo;


    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";
    private String goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("拍品详情");
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_goods_details;
    }

    @Override
    protected void initialized() {
        auctionPst = new AuctionPst(this);
        auction_id = getIntent().getStringExtra("auction_id");
        image = new ArrayList<>();
        logoSize = ToolKit.dip2px(this, 50);
        bannerSize = Settings.displayWidth;
        auctionInfoList = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        auctionPst.auctionInfo(auction_id, page);
        singUpDialog = new AuctionSingUpDialog(this, new AuctionSingUpDialog.SignUpClick() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.submit_auction_price_tv:
                        AuctionOrder.SetOrder("", auction_id, "1", singUpDialog.getEditText().getText().toString(), "", "", "", "", "", "", AuctionGoodsDetailsAty.this);
                        singUpDialog.dismiss();
                        break;
                }
            }
        });
    }

    @Override
    @OnClick({R.id.sing_up_tv, R.id.remind_me_tv, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3,
            R.id.tv_tochar, R.id.im_service_more, R.id.layout_djq, R.id.sing_up, R.id.tv_chose_ads})
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tv_chose_ads:
//                if (isLoaded) {
//                    ShowPickerView();
//                }
                ProUrbAreaUtil.gainInstance().showPickerView(tv_chose_ads, goods_id, this);
                break;
            case R.id.tv_tochar:
                toChat(easemob_account, merchant_logo, merchant_name);
                break;
            case R.id.sing_up_tv://报名
                AuctionOrder.auct(auction_id, this);
                showProgressDialog();
                break;
            case R.id.remind_me_tv:// 是否提醒
                if (is_remind.equals("1")) {
                    showRightTip("您已设置提醒");
                    break;
                }
                auctionPst.remindMe(auction_id);
                break;
            case R.id.tv_tab_1:
                goods_desc_wv.setVisibility(View.VISIBLE);
                goods_common_attr_lv.setVisibility(View.GONE);
                layout_aftersale.setVisibility(View.GONE);
                tv_tab_1.setTextColor(Color.parseColor("#F23030"));
                tv_tab_2.setTextColor(Color.parseColor("#666666"));
                tv_tab_3.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.tv_tab_2:
                layout_aftersale.setVisibility(View.GONE);
                goods_desc_wv.setVisibility(View.GONE);
                goods_common_attr_lv.setVisibility(View.VISIBLE);
                tv_tab_2.setTextColor(Color.parseColor("#F23030"));
                tv_tab_1.setTextColor(Color.parseColor("#666666"));
                tv_tab_3.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.tv_tab_3:
                tv_tab_3.setTextColor(Color.parseColor("#F23030"));
                tv_tab_1.setTextColor(Color.parseColor("#666666"));
                tv_tab_2.setTextColor(Color.parseColor("#666666"));
                goods_desc_wv.setVisibility(View.GONE);
                goods_common_attr_lv.setVisibility(View.GONE);
                layout_aftersale.setVisibility(View.VISIBLE);
                break;
            case R.id.im_service_more:
                if (ser_list != null) {
                    showPop(v, "服务说明", ser_list, 1);
                }
                break;
            case R.id.layout_djq:
                showDjqPop(v, dj_ticket);
            case R.id.sing_up:
                Bundle bundle = new Bundle();
                bundle.putString("type", "9");
                bundle.putString("mid", mInfo.get("merchant_id"));
                bundle.putString("group_buy_id", auction_id);
                startActivity(BuildOrderAty.class, bundle);
                break;
        }
    }

    /**
     * 代金券的弹窗
     *
     * @param view
     */
    public void showDjqPop(final View view, final List<Map<String, String>> list) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.layout_popp_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
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
                                case 0: {
                                    layout_djq0.setVisibility(View.VISIBLE);
                                    tv_djq_desc0.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                                case 1: {
                                    layout_djq1.setVisibility(View.VISIBLE);
                                    tv_djq_desc1.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                                case 2: {
                                    layout_djq2.setVisibility(View.VISIBLE);
                                    tv_djq_desc2.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                            }

                            switch (list.get(i).get("type")) {
                                case "0": {
                                    tv_djq_color0.setBackgroundResource(R.drawable.shape_red_bg);
                                }
                                break;
                                case "1": {
                                    tv_djq_color1.setBackgroundResource(R.drawable.shape_yellow_bg);
                                }
                                break;
                                case "2": {
                                    tv_djq_color2.setBackgroundResource(R.drawable.shape_blue_bg);
                                }

                                break;
                            }
                        }


                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        if (requestUrl.contains("freight")) {
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                    "运费" + map.get("pay") + "元", 2, Color.parseColor("#FF0000"));
        }
        if (requestUrl.contains("AuctionOrder/SetOrder")) {
            showToast(map.get("message"));
            return;
        }
        if (requestUrl.contains("AuctionOrder/auct")) {

            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (map.get("auc_type").equals("1")) {
                singUpDialog.show();
                MoneyUtils.setPricePoint(singUpDialog.getEditText());
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", map.get("order_id"));
                bundle.putString("balance", map.get("base_balance"));
                bundle.putString("type", "8");
                bundle.putString("money", map.get("base_money"));
                bundle.putString("merchant_name", map.get("merchant_name"));
                startActivity(PayForAppAty.class, bundle);
            }

        }
        if (requestUrl.contains("auctionInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            mInfo = JSONUtils.parseKeyAndValueToMap(data.get("mInfo"));
            // 商品图片轮播图
            if (ToolKit.isList(data, "goods_banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goods_banner"));
                forBanner();
            }

            easemob_account = mInfo.get("merchant_easemob_account");
            merchant_logo = mInfo.get("server_head_pic");
            merchant_name = mInfo.get("server_name");

            /**判断这块儿显示和隐藏
             * "is_new_goods": "1",//是否是新品  0不是 1是
             "is_new_goods_desc": "此件商品是旧货八五成新",//新品描述
             "is_end": "0",//是否临期 0未临期 1临期
             "is_end_desc": "此商品属于临期商品，商品保质期到期日为2017-20-30",//临期描述
             */
            // 商品其他信息
            Map<String, String> auctionInfo = JSONUtils.parseKeyAndValueToMap(data.get("goodsInfo"));
            goods_id = auctionInfo.get("goods_id");
            String tx = DemoApplication.getInstance().getLocInfo().get("province")
                    + "," + DemoApplication.getInstance().getLocInfo().get("city") + "," + DemoApplication.getInstance().getLocInfo().get("district");
            tv_chose_ads.setText(tx);
            Freight.freight(goods_id, tx, AuctionGoodsDetailsAty.this);
            showProgressDialog();
            if (auctionInfo.get("is_new_goods").equals("0") && auctionInfo.get("is_end").equals("1")) {
                tv_expirationdate.setText(auctionInfo.get("is_new_goods_desc") + "\n" + auctionInfo.get("is_end_desc"));
            } else if (auctionInfo.get("is_new_goods").equals("0")) {
                tv_expirationdate.setText(auctionInfo.get("is_new_goods_desc"));
            } else if (auctionInfo.get("is_end").equals("1")) {
                tv_expirationdate.setText(auctionInfo.get("is_end_desc"));
            } else {
                tv_expirationdate.setVisibility(View.GONE);
            }

            vouchers_desc = auctionInfo.get("vouchers_desc");
            auction_status_tv.setText(auctionInfo.get("stage_status"));
            action_goods_name_tv.setText(auctionInfo.get("auct_name"));
            SpannableString msp = new SpannableString("活动说明：" + auctionInfo.get("auct_desc"));
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
            tv_hdsm.setText(msp);

            goods_desc_wv.loadDataWithBaseURL(null, auctionInfo.get("goods_desc"), "text/html", "utf-8", null);


            if (ToolKit.isList(auctionInfo, "dj_ticket")) {
                dj_ticket = JSONUtils.parseKeyAndValueToMapList(auctionInfo.get("dj_ticket"));
                for (int i = 0; i < dj_ticket.size(); i++) {
                    if (i == 2) {
                        break;
                    }
                    switch (i) {
                        case 0: {
                            layout_djq0.setVisibility(View.VISIBLE);
                            tv_djq_desc0.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                        case 1: {
                            layout_djq1.setVisibility(View.VISIBLE);
                            tv_djq_desc1.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                        case 2: {
                            layout_djq2.setVisibility(View.VISIBLE);
                            tv_djq_desc2.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                    }

                    switch (dj_ticket.get(i).get("type")) {
                        case "0": {
                            //  tv_djq_color0.setBackgroundColor(Color.parseColor("#FF534C"));
                            tv_djq_color0.setBackgroundResource(R.drawable.shape_red_bg);
                        }
                        break;
                        case "1": {
                            tv_djq_color1.setBackgroundResource(R.drawable.shape_yellow_bg);
                        }
                        break;
                        case "2": {
                            tv_djq_color2.setBackgroundResource(R.drawable.shape_blue_bg);
                        }

                        break;
                    }

                }
            } else {
                layout_djq.setVisibility(View.GONE);
            }
            Glide.with(this).load(auctionInfo.get("country_logo")).into(im_country_logo);
            tv_country_desc.setText(auctionInfo.get("country_desc"));
            tv_country_tax.setText(auctionInfo.get("country_tax") + "元");
            if (Double.parseDouble(auctionInfo.get("country_tax")) <= 0) {
                layout_jinkoushui.setVisibility(View.GONE);
            }
            if (ToolKit.isList(data, "goods_server")) {
                ser_list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_server"));
                rv_service.setAdapter(new service_adp(ser_list, 3));
            } else {
                layout_service.setVisibility(View.GONE);
            }

            // 积分
            ChangeTextViewStyle.getInstance().forTextColor(this, auction_profit_num_tv,
                    "积分" + auctionInfo.get("integral"), 2, Color.parseColor("#FF0000"));
            // 当前价
            ChangeTextViewStyle.getInstance().forAuctionPrice(this, auction_price_tv, "当前价 ￥" +
                    auctionInfo.get("now_price"));
            // 去报名(保证金额)
//            ChangeTextViewStyle.getInstance().forSingUpWhite(this, sing_up_tv, "去报名\n(保证金金额￥" +
//                    auctionInfo.get("base_money") + ")");
            // 围观人数
            String on_lookers = "围观 " + auctionInfo.get("click_num") + " 次";
            ChangeTextViewStyle.getInstance().forTextColor(this, on_lookers_tv, on_lookers, 3, 6,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 报名人数
            String be_sign_up = "报名 " + auctionInfo.get("apply_num") + " 人";
            ChangeTextViewStyle.getInstance().forTextColor(this, be_sign_up_tv, be_sign_up, 3, 4,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 设置提醒人数
            String set_remind = "设置提醒 " + auctionInfo.get("remind_num") + " 人";
            ChangeTextViewStyle.getInstance().forTextColor(this, set_remind_tv, set_remind, 5, 8,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 起拍价
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_1, "起拍价        ￥" +
                    auctionInfo.get("start_price"), 8, ContextCompat.getColor(this, R.color.gray_text_color));
            // 加价幅度
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_2, "加价幅度    ￥" +
                    auctionInfo.get("add_price"), 5, ContextCompat.getColor(this, R.color.gray_text_color));

            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_3, "开拍时间    " +
                            //        DateTool.timestampToStrTime(auctionInfo.get("start_time"))
                            auctionInfo.get("start_time"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_4, "保留价        " +
                    auctionInfo.get("leave_price"), 8, ContextCompat.getColor(this, R.color.gray_text_color));

            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_1, "保证金        ￥" +
                    auctionInfo.get("base_money"), 8, ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_2, "拍卖佣金 " + auctionInfo.get("commission"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_3, "结束时间    " +
                            //DateTool.timestampToStrTime(auctionInfo.get("end_time"))
                            auctionInfo.get("end_time"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_4, "延时周期    " + auctionInfo.get("delay_time")
                    + "分/次", 5, ContextCompat.getColor(this, R.color.gray_text_color));

            // 出价id
            ChangeTextViewStyle.getInstance().forTextColor(this, user_auction_id_tv, "您的出价ID: " + data.get("mybid"), 8,
                    ContextCompat.getColor(this, R.color.app_text_color));
            is_remind = auctionInfo.get("is_remind");
            if (is_remind.equals("1")) {
                remind_me_tv.setText("已提醒");
            } else {
                remind_me_tv.setText("提醒");
            }


            tv_bzqd.setText(auctionInfo.get("package_list")); //包装清单
            tv_shfw.setText(auctionInfo.get("after_sale_service")); //售后服务
            tv_jgsm.setText(Html.fromHtml(data.get("price_desc"))); //价格说明

            if (ToolKit.isList(data, "guess_goods_list")) {
                if (page == 1) {
                    ticket = GsonUtil.getObjectList(data.get("guess_goods_list"), AllGoodsBean.class);
                    allGvLvAdapter1 = new AllGvLvAdapter(this, ticket, 1);
                    ticket_gv.setAdapter(allGvLvAdapter1);
                } else {
                    more = GsonUtil.getObjectList(data.get("guess_goods_list"), AllGoodsBean.class);
                    ticket.addAll(more);
                    allGvLvAdapter1.notifyDataSetChanged();
                }
            } else {
                is_f = false;
            }

//            if (auctionInfo.get("is_running").equals("0")) {
//                auction_status_tv.setText("正在进行 " + auctionInfo.get("end_true_time") + "结束");
//            } else {
//                auction_status_tv.setText("已结束");
//            }

//            Map<String, String> merchantInfo = JSONUtils.parseKeyAndValueToMap(data.get("merchantInfo"));
//            // 竞拍详情(商家名称，评分)
//            ChangeTextViewStyle.getInstance().forAuctionNameAndGrade(this, mell_name_and_grade_tv,
//                    merchantInfo.get("merchant_name") + "\n评分" + merchantInfo.get("score") + "分");
//            Glide.with(this).load(merchantInfo.get("logo"))
//                    .override(logoSize, logoSize)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .error(R.drawable.ic_default)
//                    .placeholder(R.drawable.ic_default)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(auction_mell_logo_iv);
            if (ToolKit.isList(data, "auction_log")) {
                auctionInfoList = JSONUtils.parseKeyAndValueToMapList(data.get("auction_log"));
                auctionInfoAdapter = new AuctionInfoAdapter();
                auction_info_lv.setAdapter(auctionInfoAdapter);
            } else {
                layout_chujia.setVisibility(View.GONE);
            }
            if (ToolKit.isList(data, "goods_common_attr")) {
                List<GoodsCommonAttrBean> gca = GsonUtil.getObjectList(data.get("goods_common_attr"),
                        GoodsCommonAttrBean.class);
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
            }

            return;
        }
        if (requestUrl.contains("remindMe")) {
            is_remind = "1";
            remind_me_tv.setText("已提醒");
        }
    }

    CommonPopupWindow commonPopupWindow;

    public void showPop(View view, final String title, final List<Map<String, String>> list, final int type) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new LinearLayoutManager(AuctionGoodsDetailsAty.this, 1, false));
                        recyclerview.setAdapter(new service_adp(list, type));
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
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(AuctionGoodsDetailsAty.this).load(image.get(position).get("path"))
                    .centerCrop()
                    .override(bannerSize, bannerSize)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    public void onBottom() {
        if (is_f) {

        }
    }


    /**
     * 运费
     */
    @ViewInject(R.id.freight_tv)
    private TextView freight_tv;

    @ViewInject(R.id.tv_chose_ads)
    private TextView tv_chose_ads;

    /**
     * 省
     */
    private String province = "";
    /**
     * 市
     */
    private String city = "";
    /**
     * 区
     */
    private String area = "";

    /**
     * 省id
     */
    private String province_id = "";
    /**
     * 市id
     */
    private String city_id = "";
    /**
     * 区id
     */
    private String area_id = "";

    /**
     * 街道id
     */
    private String street_id = "";

    private class AuctionInfoAdapter extends BaseAdapter {
        private AuctionVh auctionVh;

        @Override
        public int getCount() {
            return auctionInfoList.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return auctionInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (null == view) {
                view = LayoutInflater.from(AuctionGoodsDetailsAty.this).inflate(R.layout.item_auction_info_lv, null);
                auctionVh = new AuctionVh();
                ViewUtils.inject(auctionVh, view);
                view.setTag(auctionVh);
            } else {
                auctionVh = (AuctionVh) view.getTag();
            }
            if (0 == i) {
                auctionVh.item_auction_name_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_id_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_price_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_time_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
            } else {
                auctionVh.item_auction_name_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_id_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_price_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_time_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
            }
            auctionVh.item_auction_name_tv.setText(map.get("nickname"));
            auctionVh.item_auction_id_tv.setText(map.get("log_id"));
            auctionVh.item_auction_price_tv.setText(map.get("bid_price"));
            auctionVh.item_auction_time_tv.setText(map.get("bid_time"));

            return view;
        }

        class AuctionVh {
            /**
             * 出价人
             */
            @ViewInject(R.id.item_auction_name_tv)
            private TextView item_auction_name_tv;

            /**
             * 出价ID
             */
            @ViewInject(R.id.item_auction_id_tv)
            private TextView item_auction_id_tv;
            /**
             * 价格
             */
            @ViewInject(R.id.item_auction_price_tv)
            private TextView item_auction_price_tv;
            /**
             * 出价时间
             */
            @ViewInject(R.id.item_auction_time_tv)
            private TextView item_auction_time_tv;

        }
    }

    class service_adp extends RecyclerView.Adapter<service_adp.ViewHolder> {
        List<Map<String, String>> list;
        int tpye = 0;

        public service_adp(List<Map<String, String>> list, int type) {
            this.list = list;
            this.tpye = type;
        }

        @Override
        public service_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new service_adp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lh_service, null));
        }

        @Override
        public void onBindViewHolder(service_adp.ViewHolder holder, int position) {
            String name = "";
            String desc = "";
            if (this.tpye == 0) {
                name = list.get(position).get("price_name") + "：";
                desc = list.get(position).get("desc");
                SpannableString msp = new SpannableString(name + desc);
                msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                holder.tv_text.setText(msp);
            } else if (this.tpye == 1) {
                name = list.get(position).get("server_name") + "：";
                desc = list.get(position).get("desc");
                SpannableString msp = new SpannableString(name + desc);
                msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                holder.tv_text.setText(msp);
            } else {
                holder.tv_text.setText(list.get(position).get("server_name"));
                holder.tv_text.setTextColor(Color.parseColor("#F23030"));
            }
            Glide.with(AuctionGoodsDetailsAty.this).load(list.get(position).get("icon")).into(holder.im_logo);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView im_logo;
            TextView tv_text;

            public ViewHolder(View itemView) {
                super(itemView);
                im_logo = (ImageView) itemView.findViewById(R.id.im_logo);
                tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            }
        }
    }

}
