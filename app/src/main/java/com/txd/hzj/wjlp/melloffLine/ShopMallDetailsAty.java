package com.txd.hzj.wjlp.melloffLine;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tools.RegexUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.google.gson.Gson;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.offlineStoreInfo.StoreInfoPst;
import com.txd.hzj.wjlp.mainfgt.Constant;
import com.txd.hzj.wjlp.mainfgt.Pranster;
import com.txd.hzj.wjlp.melloffLine.adapter.ShopEvaluateAdapter;
import com.txd.hzj.wjlp.tool.GlideImageLoader;
import com.txd.hzj.wjlp.tool.MapIntentUtil;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 创建者：Qyl
 * 创建时间：2018/7/23 0023 14:18
 * 功能描述：线下店铺详情页面
 * 联系方式：无
 */
public class ShopMallDetailsAty extends BaseAty implements View.OnClickListener, Constant.View {
    /**
     * 刷新加载控件
     */
    /*private VpSwipeRefreshLayout mallRefrsh;*/
    /**
     * 轮播图控件
     */
    private Banner bannerIma;
    /**
     * 轮播图父布局
     */
    private RelativeLayout downShopMallDetail_rlayout;

    /*// Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;*/
    //图片集合
    private ArrayList urls;
    //评价列表
    private RecyclerView shopEvaluate;
    //详情接口
    private StoreInfoPst pst;
    //商品数量
    private TextView shopGoddsNumber;
    // 商品数量容器，当数量为0时隐藏该行
    private TableRow shop_goods_number_tabRow;
    private TableRow month_layout;
    //月销单量
    private TextView monthOrderNumber;
    //关注人数
    private TextView shopFollowPersons;
    //营业时间
    private TextView shopBusinessHours;
    //门店地址
    private TextView shopAddress;
    //门店电话
    private TextView shopTelephone;
    //门店描述
    private TextView shop_desc_tv;
    //查看商家资质
    private RelativeLayout shopBusinessAptitude;
    //举报商家
    private RelativeLayout shopReportBusiness;
    //我要结账
    private TextView mySettleAccounts;
    //title名字
    private TextView titleName;
    private TextView titleCollect;
    //title分享
    private TextView titleShare;
    //商家id
    private String s_id;
    //评价列表数据
    private List<ShopOffLineBean.DataBean.CommentBean.ListBean> listBeanList;
    //评价列表适配器
    private ShopEvaluateAdapter adapter;
    //评价数量
    private TextView evaluateNumbers;
    //评价评分
    private TextView evaluateBranch;
    private ListView nearbyBusinessList;
    private int page;
    private Pranster pranster;
    private OffLineDataBean mellInfo;
    //是否收藏标识
    private boolean isCollection;
    private UserCollectPst collectPst;
    private ShopOffLineBean offLineBean;
    private String sId;
    //店铺评价
    private LinearLayout comment_layout;
    private View evaluateDivis;
    private View evaluateModle;
    private TextView addressMap;
    private double baiDuLng; // 商家定位（百度）
    private double baiDuLat; // 商家定位（百度）
    private double gaoDeLng; // 商家定位（高德）
    private double gaoDeLat; // 商家定位（高德）

    private String aptitudeName = ""; // 商家资质名称列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_down_shopmall_details;
    }

    @Override
    protected void initialized() {
        Intent intent = getIntent();
        if (intent != null) {
            mellInfo = (OffLineDataBean) intent.getSerializableExtra("mellInfo");
            if (mellInfo != null) {
                s_id = mellInfo.getS_id();
            }
        }

        urls = new ArrayList();

        //mallRefrsh = findViewById(R.id.shop_mall_refrsh);
        bannerIma = findViewById(R.id.banner);
        downShopMallDetail_rlayout = findViewById(R.id.downShopMallDetail_rlayout);
        downShopMallDetail_rlayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                downShopMallDetail_rlayout.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams layoutParams = downShopMallDetail_rlayout.getLayoutParams();
                layoutParams.height = (int) (downShopMallDetail_rlayout.getWidth() * 618 / 1000);
                downShopMallDetail_rlayout.setLayoutParams(layoutParams);
                return true;
            }
        });
        shopEvaluate = findViewById(R.id.shop_mall_evaluate_list);
        shopGoddsNumber = findViewById(R.id.shop_goods_number);
        shop_goods_number_tabRow = findViewById(R.id.shop_goods_number_tabRow);
        month_layout = findViewById(R.id.month_layout);
        monthOrderNumber = findViewById(R.id.shop_month_order_number);
        shopFollowPersons = findViewById(R.id.shop_follow_persons);
        shopBusinessHours = findViewById(R.id.shop_business_hours);
        shopAddress = findViewById(R.id.shop_address);
        shopTelephone = findViewById(R.id.shop_tele_phone);
        shop_desc_tv = findViewById(R.id.shop_desc_tv);
        shopBusinessAptitude = findViewById(R.id.shop_business_aptitude);
        shopReportBusiness = findViewById(R.id.shop_Report_business);
        mySettleAccounts = findViewById(R.id.shop_my_settle_accounts);
        titleName = findViewById(R.id.aty_down_title_name);
        titleCollect = findViewById(R.id.goods_title_collect_tv);
        titleShare = findViewById(R.id.goods_title_share_tv);
        evaluateNumbers = findViewById(R.id.shop_evaluate_numbers);
        evaluateBranch = findViewById(R.id.shop_evaluate_branch);
        nearbyBusinessList = findViewById(R.id.shop_nearby_business_list);
        comment_layout = findViewById(R.id.comment_layout);
        evaluateDivis = findViewById(R.id.shop_evaluate_divis);
        evaluateModle = findViewById(R.id.shop_evaluate_modle);
        addressMap = findViewById(R.id.shop_address_map);
        pranster = new Pranster();
        pranster.setView(this);
        page = 1;

        nearbyBusinessList.setFocusable(false);


        //设置图片加载器
        bannerIma.setImageLoader(new GlideImageLoader());
        //设置样式
        bannerIma.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置动画
        bannerIma.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        bannerIma.setDelayTime(2000);


        //设置下拉刷新上拉加载
        /*mallRefrsh.setHeaderViewBackgroundColor(Color.WHITE);
        mallRefrsh.setHeaderView(createHeaderView());// add headerView
        mallRefrsh.setFooterView(createFooterView());
        mallRefrsh.setTargetScrollWithLayout(true); // 跟随手指滑动
        mallRefrsh.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                pst.offlineStoreInfo(s_id);
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
        mallRefrsh.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                pst.offlineStoreInfo(s_id);
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
        });*/

        /**
         * 注册点击事件
         * */
        shopBusinessAptitude.setOnClickListener(this);
        shopReportBusiness.setOnClickListener(this);
        mySettleAccounts.setOnClickListener(this);
        titleCollect.setOnClickListener(this);
        titleShare.setOnClickListener(this);
        comment_layout.setOnClickListener(this);
        findViewById(R.id.shop_Report_busines).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle;
        switch (v.getId()) {
            //查看商家资质
            case R.id.shop_business_aptitude:
                if (!aptitudeName.equals("")) {
                    bundle = new Bundle();
                    bundle.putInt("type", 1); // 目前为止下一界面没有使用到该属性
                    bundle.putString("merchant_id", s_id);
                    bundle.putString("AptitudeName", aptitudeName);
                    startActivity(MellAptitudeAty.class, bundle);
                } else {
                    showToast("该商家暂无已认证的商家资质！");
                }
                break;
            //举报商家
            case R.id.shop_Report_business:
                bundle = new Bundle();
                bundle.putInt("type", 1); // 目前为止下一界面没有使用到该属性
                bundle.putString("merchant_id", s_id);
                startActivity(MellReportedAty.class, bundle);
                break;
            //我要结账
            case R.id.shop_my_settle_accounts:
                Intent intent = new Intent(ShopMallDetailsAty.this, PaymentAty.class);
                bundle = new Bundle();
                bundle.putString("stage_merchant_id", sId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //收藏
            case R.id.goods_title_collect_tv:
                if (isCollection == false) {

                    collectPst.addCollect("5", s_id);
                } else {

                    collectPst.delOneCollect("5", s_id);
                }
                break;
            //分享
            case R.id.goods_title_share_tv:
                toShare(offLineBean.getData().getMerchant_name(), offLineBean.getData().getLogo(), offLineBean.getData().getShare_url(), offLineBean.getData().getMerchant_desc(), offLineBean.getData().getS_id(), "1");
                break;
            //跳转到评论列表页
            case R.id.comment_layout:
                Bundle bundle1 = new Bundle();
                bundle1.putString("merchant_id", s_id);
                startActivity(OffLineEvaluationShopListAty.class, bundle1);
                break;
            case R.id.shop_Report_busines:

                MapIntentUtil mapIntentUtil = new MapIntentUtil();
                mapIntentUtil.openMap(this, baiDuLat, baiDuLng, gaoDeLat, gaoDeLng);

//                List<String> mapList = new ArrayList<>();
//                if (MapIntentUtil.isInstallByread(this, "com.baidu.BaiduMap")) {
//                    mapList.add("百度地图");
//                }
//                if (MapIntentUtil.isInstallByread(this, "com.autonavi.minimap")) {
//                    mapList.add("高德地图");
//                }
//                if (mapList.size() > 0) {
//                    final String[] items = new String[mapList.size()];
//                    for (int i = 0; i < mapList.size(); i++) {
//                        items[i] = mapList.get(i);
//                    }
//                    new AlertDialog.Builder(this)
//                            .setTitle("选择地图")
//                            .setItems(items, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if ("百度地图".equals(items[which])) {
//                                        // 跳转到百度地图APP进行导航
//                                        CustomoLocation customoLocation = new CustomoLocation();
//                                        customoLocation.setLat(baiDuLat);
//                                        customoLocation.setLng(baiDuLng);
//                                        customoLocation.setName("");
//                                        MapIntentUtil.startNative_Baidu(ShopMallDetailsAty.this, null, customoLocation);
//                                    } else if ("高德地图".equals(items[which])) {
////                                        showToast("回传的是百度地图，暂时未集成高德地图SDK");
////                                        暂时跳转到高德地图拿百度位置标注
//                                        CustomoLocation customoLocation = new CustomoLocation();
//                                        customoLocation.setLat(gaoDeLat);
//                                        customoLocation.setLng(gaoDeLng);
//                                        customoLocation.setName("");
//                                        MapIntentUtil.startNative_Gaode(ShopMallDetailsAty.this, customoLocation);
//                                    }
//                                }
//                            })
//                            .setPositiveButton("取消", null)
//                            .create().show();
//                } else {
//                    showToast("请先安装地图导航APP");
//                }

                break;
        }
    }

    @Override
    protected void requestData() {
        pst = new StoreInfoPst(this);
        collectPst = new UserCollectPst(this);
        pst.offlineStoreInfo(s_id);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
       /* mallRefrsh.setRefreshing(false);
        mallRefrsh.setLoadMore(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);*/
        if (requestUrl.contains("offlineStoreInfo")) {
            Log.i("线下店铺详情页数据", jsonStr.toString());
            Gson gson = new Gson();
            offLineBean = gson.fromJson(jsonStr, ShopOffLineBean.class);
            //设置评价列表数据
            listBeanList = offLineBean.getData().getComment().getList();
            if (listBeanList != null && listBeanList.size() != 0) {
                adapter = new ShopEvaluateAdapter(this, listBeanList);
                shopEvaluate.setLayoutManager(new LinearLayoutManager(this));
                shopEvaluate.setAdapter(adapter);
            } else {
                evaluateDivis.setVisibility(View.GONE);
                evaluateModle.setVisibility(View.GONE);
            }
            //设置titleName
            titleName.setText(offLineBean.getData().getMerchant_name());
            //设置轮播图照片
            urls.clear();
            for (int i = 0; i < offLineBean.getData().getGallery().size(); i++) {
                urls.add(offLineBean.getData().getGallery().get(i).getPath());
            }
            bannerIma.setImages(urls);

            if (offLineBean.getData().getS_id() != null) {
                sId = offLineBean.getData().getS_id();
            }

            //开始
            bannerIma.start();
            //设置营业时间
            shopBusinessHours.setText(offLineBean.getData().getOpen_time());
            // 获取商家资质列表
            List<ShopOffLineBean.DataBean.OtherLicenseBean> other_license = offLineBean.getData().getOther_license();
            if (other_license != null && other_license.size() > 0) {
                for (ShopOffLineBean.DataBean.OtherLicenseBean otherLicenseBean : other_license) {
                    aptitudeName = aptitudeName + otherLicenseBean.getLicense_name();
                    if (!otherLicenseBean.getLicense_name().equals(other_license.get(other_license.size() - 1).getLicense_name())) {
                        aptitudeName = aptitudeName + ",";
                    }
                }
            }
            //设置门店电话
            shopTelephone.setText(offLineBean.getData().getMerchant_phone());
            shopTelephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String merchant_phone = offLineBean.getData().getMerchant_phone();
                    if (!android.text.TextUtils.isEmpty(merchant_phone) && RegexUtils.checkPhone(merchant_phone)){
                        call(merchant_phone);
                    }else {
                        showToast("电话号码不正确");
                    }

                }
            });
            //门店描述
            shop_desc_tv.setText(offLineBean.getData().getMerchant_desc());
            //设置关注人数
            shopFollowPersons.setText(offLineBean.getData().getFocus_num() + "人");
            //设置商品数量
            shopGoddsNumber.setText(offLineBean.getData().getGoods_num() + "件");
            if ("0".equals(offLineBean.getData().getGoods_num()) || "0".equals(offLineBean.getData().getUser_id())) { // 如果商品数量为0，则隐藏掉商品数量
                shop_goods_number_tabRow.setVisibility(View.GONE);
            }

            if ("0".equals(offLineBean.getData().getUser_id())){
                shopBusinessAptitude.setVisibility(View.GONE);
                shopReportBusiness.setVisibility(View.GONE);
            }else {
                shopBusinessAptitude.setVisibility(View.VISIBLE);
                shopReportBusiness.setVisibility(View.VISIBLE);
            }

            if ("0".equals(offLineBean.getData().getMonths_orders())){
                month_layout.setVisibility(View.GONE);
            }else {
                month_layout.setVisibility(View.VISIBLE);
            }
            if ("0".equals(offLineBean.getData().getUser_id()) || "2".equals(mellInfo.getShow_type())) {
                mySettleAccounts.setVisibility(View.GONE); // 我要结账隐藏
            } else {
                mySettleAccounts.setVisibility(View.VISIBLE); // 显示我要结账
            }
            //设置月单量
            monthOrderNumber.setText(offLineBean.getData().getMonths_orders() + "单");
            //设置地址
            shopAddress.setText(offLineBean.getData().getFinal_address());
            //评价数量
            evaluateNumbers.setText("店铺评价(" + offLineBean.getData().getComment().getCount() + ")");
            //设置位置
            addressMap.setText(offLineBean.getData().getAddress());
            //综合星级
            evaluateBranch.setText(offLineBean.getData().getComment().getStar_cate());
            //设置是否收藏
            if (0 == offLineBean.getData().getIs_collect()) {
                titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
                isCollection = false;
                titleCollect.setText("收藏");
            } else {
                titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
                isCollection = true;
                titleCollect.setText("已收藏");
            }
            if (mellInfo.getLng() != null && mellInfo.getLat() != null) {
                baiDuLng = Double.parseDouble(mellInfo.getLng()); // 获取回传的商家位置（百度）
                baiDuLat = Double.parseDouble(mellInfo.getLat()); // 获取回传的商家位置（百度）
                pranster.requestStoreData(page, mellInfo.getLng(), mellInfo.getLat(), s_id, "", "", ShopMallDetailsAty.this, nearbyBusinessList);
            } else {
                pranster.requestStoreData(page, "", "", s_id, "", "", ShopMallDetailsAty.this, nearbyBusinessList);
            }
            if (offLineBean.getData().getGaode_lat() != null && offLineBean.getData().getGaode_lng() != null) {
                gaoDeLng = Double.parseDouble(offLineBean.getData().getGaode_lng()); // 获取回传的商家位置（高德）
                gaoDeLat = Double.parseDouble(offLineBean.getData().getGaode_lat()); // 获取回传的商家位置（高德）
            }

        }
        if (requestUrl.contains("addCollect")) {
            showRightTip("收藏成功");
            titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            isCollection = true;
            titleCollect.setText("已收藏");
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            isCollection = false;
            titleCollect.setText("收藏");
        }
    }


    /*private View createFooterView() {
        View footerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        bannerIma.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bannerIma != null) {
            bannerIma.stopAutoPlay();
        }
    }


    @Override
    public void onItemClickListener(OffLineDataBean offLineDataBean, int position) {
        String goods_num = offLineDataBean.getGoods_num();
        Bundle bundle = new Bundle();
        if (!android.text.TextUtils.isEmpty(goods_num) && Integer.parseInt(goods_num) > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Config.SHARE_URL);
            stringBuffer.append("Wap/OfflineStore/offlineShop/os_type/1/merchant_id/");
            stringBuffer.append(offLineDataBean.getS_id());
            if (Config.isLogin()) {
                stringBuffer.append("/invite_code/");
                stringBuffer.append(PreferencesUtils.getString(ShopMallDetailsAty.this, "invite_code"));
            }
            stringBuffer.append(".html");
            bundle.putString("url", stringBuffer.toString());
            startActivity(WebViewAty.class, bundle);
        } else {
            bundle.putSerializable("mellInfo", offLineDataBean);
            startActivity(ShopMallDetailsAty.class, bundle);
        }
        finish();
    }

    @Override
    public void loadComplate() {
       /* mallRefrsh.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        mallRefrsh.setLoadMore(false);*/
    }

}
