package com.txd.hzj.wjlp.mainfgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.offline.OffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.http.Recommending;
import com.txd.hzj.wjlp.mainfgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.OffLineMenuGvAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.melloffLine.ShopMallAty;
import com.txd.hzj.wjlp.melloffLine.ShopMallDetailsAty;
import com.txd.hzj.wjlp.mellonLine.SearchAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:52
 * 描述：线下店铺
 */
public class MellOffLineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener, Constant.View {

    /**
     * 标题栏
     */
    @ViewInject(R.id.off_line_title_layout)
    public RelativeLayout off_line_title_layout;
    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    /***
     * 刷新 ---加载
     * */
    @ViewInject(R.id.super_offline_layout)
    private VpSwipeRefreshLayout swipeRefreshLayout;
    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;

    @ViewInject(R.id.under_banner_menu_vp)
    ViewPager under_banner_menu_vp;

    private int allHeight = 0;

    /**
     * 优质商家
     */
    @ViewInject(R.id.high_quality_business_lv)
    private ListViewForScrollView high_quality_business_lv;

    /**
     * 附近商家列表
     */
    @ViewInject(R.id.mell_near_by_lv)
    private ListViewForScrollView mell_near_by_lv;


    @ViewInject(R.id.to_location_tv)
    private TextView to_location_tv;
    @ViewInject(R.id.to_search)
    private TextView to_search;

    @ViewInject(R.id.im_ads)
    private ImageView im_ads;
    @ViewInject(R.id.three_image_left_iv)
    private ImageView three_image_left_iv;
    @ViewInject(R.id.three_image_center_iv)
    private ImageView three_image_center_iv;
    @ViewInject(R.id.three_image_right_iv)
    private ImageView three_image_right_iv;


    private int pageSize = 10;
    /**
     * 当前选中的第几页
     */
    private int curIndex = 0;

    /**
     * 标题的集合
     */
    private List<String> title_list;

    /**
     * 图片的集合
     */
    private List<String> image_list;

    /**
     * 每个图标对应的type_id
     */
    private List<String> rec_type_id_list;

    private int img_w = 0;
    private int img_h = 0;


    /**
     * 广告高度
     */
    private int ads_w = 0;
    private int ads_h = 0;
    private Bundle bundle;
    private Pranster pranster;
    private int page = 1;

    private MellNearByHzjAdapter mMellNearByHzjAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 3 / 5;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        // 改变标题栏颜色
        img_w = Settings.displayWidth / 3;
        img_h = Settings.displayWidth * 5 / 14;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(img_w, img_h);
        three_image_left_iv.setLayoutParams(params);
        three_image_center_iv.setLayoutParams(params);
        three_image_right_iv.setLayoutParams(params);

        // 广告宽高
        ads_h = Settings.displayWidth * 300 / 1242;
        ads_w = Settings.displayWidth;


        LinearLayout.LayoutParams adsParam = new LinearLayout.LayoutParams(ads_w, ads_h);
        im_ads.setLayoutParams(adsParam);

        off_line_to_change_sc.setScrollViewListener(MellOffLineFgt.this);
        swipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout.setFooterView(createFooterView());
        swipeRefreshLayout.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                OfflineStore.Index(MellOffLineFgt.this);
                page = 1;
                Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
                if (null != locMap && !TextUtils.isEmpty(locMap.get("lon"))) {
                    requestHighQualityBusiness(locMap.get("lon"), locMap.get("lat"),page,"", "", "");
                    pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", "", "", getContext(), mell_near_by_lv);
                } else {
                    requestHighQualityBusiness("", "", page,"", "", "");
                    pranster.requestStoreData(page, "", "", "", "", "", getContext(), mell_near_by_lv);
                }

                title_list.clear();
                image_list.clear();
                rec_type_id_list.clear();
                Recommending.businessType(MellOffLineFgt.this);
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
        swipeRefreshLayout.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                page += 1;
                Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
                if (null != locMap && !TextUtils.isEmpty(locMap.get("lon"))) {
                    pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", "", "", getContext(), mell_near_by_lv);
                } else {
                    pranster.requestStoreData(page, "", "", "", "", "", getContext(), mell_near_by_lv);
                }
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

        if (null == mMellNearByHzjAdapter) {
            mMellNearByHzjAdapter = new MellNearByHzjAdapter(getActivity());
            high_quality_business_lv.setAdapter(mMellNearByHzjAdapter);
            high_quality_business_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OffLineDataBean offLineDataBean = mMellNearByHzjAdapter.getItem(position);
                    String goods_num = offLineDataBean.getGoods_num();
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(goods_num) && Integer.parseInt(goods_num) > 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        if (Config.OFFICIAL_WEB.contains("api")) {
                            stringBuffer.append("http://www.wujiemall.com/");
                        } else {
                            stringBuffer.append(Config.OFFICIAL_WEB);
                        }
                        stringBuffer.append("Wap/OfflineStore/offlineShop/merchant_id/");
                        stringBuffer.append(offLineDataBean.getS_id());
                        if (Config.isLogin()) {
                            stringBuffer.append("/invite_code/");
                            stringBuffer.append(PreferencesUtils.getString(getActivity(), "invite_code"));
                        }
                        stringBuffer.append(".html");

                        //            bundle.putString("desc", ""); // 传过去没什么用
                        bundle.putString("url", stringBuffer.toString()); // url
                        //            bundle.putInt("from", 2);
                        startActivity(WebViewAty.class, bundle);
                    } else {
                        bundle.putSerializable("mellInfo", offLineDataBean);
                        startActivity(ShopMallDetailsAty.class, bundle);
                    }
                }
            });
        }
    }


    /**
     * 轮播图
     */
    private void forBanner() {

        /**
         * 轮播图的点击事件
         */
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(final int position, ImageView imageView) {
                Glide.with(getActivity()).load(list_pic.get(position).get("picture"))
                        .override(Settings.displayWidth, allHeight)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.icon_200)
                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(list_pic.get(position).get("merchant_id")) && !list_pic.get(position).get("merchant_id").equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("mell_id", list_pic.get(position).get("merchant_id"));
                            startActivity(MellInfoAty.class, bundle);
                        } else if (!TextUtils.isEmpty(list_pic.get(position).get("goods_id")) && !list_pic.get(position).get("goods_id").equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ticket_buy_id", list_pic.get(position).get("goods_id"));
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        } else {
                            forShowAds(list_pic.get(position).get("desc"), list_pic.get(position).get("href"));
                        }
                    }
                });
            }
        };
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(list_pic.size());
    }

    /**
     * 广告详情
     *
     * @param limit_desc 说明
     * @param limit_href 链接
     */
    private void forShowAds(String limit_desc, String limit_href) {
        bundle = new Bundle();
        bundle.putString("desc", limit_desc);
        bundle.putString("url", limit_href);
//        bundle.putInt("from", 2);
        startActivity(WebViewAty.class, bundle);
    }

    @Override
    @OnClick({R.id.to_location_tv, R.id.to_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_location_tv:// 当前位置
//                startActivity(MellCitySelectAty.class, null);
                break;
            case R.id.to_search:
                Bundle b = new Bundle();
                startActivity(SearchAty.class, b);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_off_line;
    }

    @Override
    protected void initialized() {
        title_list = new ArrayList<>();
        image_list = new ArrayList<>();
        rec_type_id_list = new ArrayList<>();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void requestData() {
        MainAty mainAty = (MainAty) getActivity();
        mainAty.getPositioning(); // 重新获取定位
        Recommending.businessType(this);
        to_location_tv.setText(DemoApplication.getInstance().getLocInfo().get("city"));
        OfflineStore.Index(this);
        Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
        if (null == pranster) {
            pranster = new Pranster();
            pranster.setView(this);
        }
        page = 1;
        if (null != locMap && !TextUtils.isEmpty(locMap.get("lon"))) {
            requestHighQualityBusiness(locMap.get("lon"), locMap.get("lat"),page,"", "", "");
            pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", "", "", getContext(), mell_near_by_lv);
        } else {
            requestHighQualityBusiness("", "", page,"", "", "");
            pranster.requestStoreData(page, "", "", "", "", "", getContext(), mell_near_by_lv);
        }
    }


    private void requestHighQualityBusiness( String lng, String lat,int p, String merchant_id, String top_cate, String little_cate){
        OfflineStore.offlineStoreList(lng, lat, p, merchant_id, top_cate, little_cate, this);
    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    Map<String, String> data;
    List<Map<String, String>> list_pic = new ArrayList<>();
    List<Map<String, String>> list_ads = new ArrayList<>();
    List<Map<String, String>> list_brand = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("OfflineStore/Index")) {
            data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            list_pic = JSONUtils.parseKeyAndValueToMapList(data.get("banner"));
            forBanner();
            list_ads = JSONUtils.parseKeyAndValueToMapList(data.get("ads"));
            if (list_ads.size() > 0) {
                // 设置图片控件的宽高比
                ViewGroup.LayoutParams lp = im_ads.getLayoutParams();
                lp.width = Settings.displayWidth;
                lp.height = Settings.displayWidth * 400 / 1242;
                im_ads.setLayoutParams(lp);
                im_ads.setMaxWidth(lp.width);
                im_ads.setMaxHeight(lp.width * 400 / 1242);

                if (list_ads.get(0).containsKey("picture")) {
                    Glide.with(getActivity()).load(list_ads.get(0).get("picture"))
                            .override(lp.width, lp.height)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.mipmap.icon_200)
                            .placeholder(R.mipmap.icon_200)
                            .into(im_ads);
                }

                im_ads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list_ads.get(0).containsKey("merchant_id") && !"0".equals(list_ads.get(0).get("merchant_id"))) {
                            Bundle bundle = new Bundle();
                            bundle.putString("mell_id", list_ads.get(0).get("merchant_id"));
                            startActivity(MellInfoAty.class, bundle);
                        } else if (list_ads.get(0).containsKey("goods_id") && !"0".equals(list_ads.get(0).get("goods_id"))) {
                            Bundle bundle = new Bundle();
                            if (list_ads.get(0).containsKey("goods_id")) {
                                bundle.putString("ticket_buy_id", list_ads.get(0).get("goods_id"));
                            }
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        } else {
                            if (list_ads.get(0).containsKey("href") && !TextUtils.isEmpty(list_ads.get(0).get("href"))) {
                                bundle = new Bundle();
                                if (list_ads.get(0).containsKey("desc")) {
                                    bundle.putString("desc", list_ads.get(0).get("desc"));
                                }
                                bundle.putString("url", list_ads.get(0).get("href"));
                                startActivity(WebViewAty.class, bundle);
                            }
                        }

                    }
                });
            }
            list_brand = JSONUtils.parseKeyAndValueToMapList(data.get("brand"));
            if (list_brand.size() == 3) {
                Glide.with(getActivity()).load(list_brand.get(0).get("picture")).override(img_w, img_h)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.icon_200)
                        .placeholder(R.mipmap.icon_200)
                        .centerCrop().into(three_image_left_iv);
                Glide.with(getActivity()).load(list_brand.get(1).get("picture")).override(img_w, img_h)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.icon_200)
                        .placeholder(R.mipmap.icon_200)
                        .centerCrop().into(three_image_center_iv);
                Glide.with(getActivity()).load(list_brand.get(2).get("picture")).override(img_w, img_h)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.icon_200)
                        .placeholder(R.mipmap.icon_200)
                        .centerCrop().into(three_image_right_iv);

                three_image_left_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToAty(0);

                    }
                });

                three_image_center_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToAty(1);
                    }
                });

                three_image_right_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToAty(2);
                    }
                });
            }

            progressBar.setVisibility(View.GONE);
            return;

        }

        if (requestUrl.contains("Recommending/businessType")) {
            Map<String, String> businessMap = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (businessMap.containsKey("code") && "1".equals(businessMap.get("code"))) {
                if (businessMap.containsKey("data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(businessMap.get("data"));
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).containsKey("type")) {
                            title_list.add(data.get(i).get("type"));
                        }
                        if (data.get(i).containsKey("cate_img")) {
                            image_list.add(data.get(i).get("cate_img"));
                        }
                        if (data.get(i).containsKey("rec_type_id")) {
                            rec_type_id_list.add(data.get(i).get("rec_type_id"));
                        }
                    }
                    forMenu();
                }
            }
            return;
        }

        if (requestUrl.endsWith("offlineStoreList")){
            OffLineBean offLineBean = JSONObject.parseObject(jsonStr, OffLineBean.class);
            if ("1".equals(offLineBean.getCode())) {
                if (page == 1) {
                    mMellNearByHzjAdapter.getList().clear();
                }
                if (null != offLineBean.getTop_list() && offLineBean.getTop_list().size() > 0) {
                    mMellNearByHzjAdapter.getList().addAll(offLineBean.getTop_list());
                    mMellNearByHzjAdapter.notifyDataSetChanged();
                }
            }
            progressBar.setVisibility(View.GONE);
        }

    }

    private void jumpToAty(int i) {
        if (list_brand.get(i).containsKey("merchant_id") && !"0".equals(list_brand.get(i).get("merchant_id"))) {
            Bundle bundle = new Bundle();
            bundle.putString("mell_id", list_brand.get(i).get("merchant_id"));
            startActivity(MellInfoAty.class, bundle);
        } else if (list_brand.get(i).containsKey("goods_id") && !"0".equals(list_brand.get(i).get("goods_id"))) {
            Bundle bundle = new Bundle();
            if (list_brand.get(i).containsKey("goods_id")) {
                bundle.putString("ticket_buy_id", list_brand.get(i).get("goods_id"));
            }
            bundle.putInt("from", 1);
            startActivity(TicketGoodsDetialsAty.class, bundle);
        } else {
            if (list_brand.get(i).containsKey("href") && !TextUtils.isEmpty(list_brand.get(i).get("href"))) {
                bundle = new Bundle();
                if (list_brand.get(i).containsKey("desc")) {
                    bundle.putString("desc", list_brand.get(i).get("desc"));
                }
                bundle.putString("url", list_brand.get(i).get("href"));
//                bundle.putInt("from", 2);
                startActivity(WebViewAty.class, bundle);
            }
        }
    }

    private void forMenu() {

        if (title_list.size() > 0) {

            // 获取总页数
            int pageCount = (int) Math.ceil(title_list.size() * 1.0 / pageSize);
            // 初始化View列表
            ArrayList<View> mPagerList = new ArrayList<>();
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            for (int i = 0; i < pageCount; i++) {
                GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout, under_banner_menu_vp, false);
                //设置adapter  给布局填充控件（限量购及票卷区图标）
                gridView.setAdapter(new OffLineMenuGvAdapter(getActivity(), title_list, image_list, i));
                mPagerList.add(gridView);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int pos = i + curIndex * pageSize;
                        //        String[] titles={"餐饮美食","商户地图","精选美食","品质购物","休闲娱乐","生活服务","精致丽人","学习培训","母婴亲子","运动健身","我要结婚","酒店住宿","汽车服务","宠物生活","口碑家装","电影演出","K歌达人","周边玩乐","畅游海外","医疗健康","交通出行"};
//                        showToast(title_list.get(pos));
//                        if ("餐饮美食".equals(title_list.get(pos))){
//
//                        }else if ("商户地图".equals(title_list.get(pos))){
//
//                        }else if ("精选美食".equals(title_list.get(pos))){
//
//                        }else if ("品质购物".equals(title_list.get(pos))){
//
//                        }else if ("休闲娱乐".equals(title_list.get(pos))){
//
//                        }else if ("生活服务".equals(title_list.get(pos))){
//
//                        }else if ("精致丽人".equals(title_list.get(pos))){
//
//                        }else if ("学习培训".equals(title_list.get(pos))){
//
//                        }else if ("母婴亲子".equals(title_list.get(pos))){
//
//                        }else if ("运动健身".equals(title_list.get(pos))){
//
//                        }else if ("我要结婚".equals(title_list.get(pos))){
//
//                        }else if ("酒店住宿".equals(title_list.get(pos))){
//
//                        }else if ("汽车服务".equals(title_list.get(pos))){
//
//                        }else if ("宠物生活".equals(title_list.get(pos))){
//
//                        }else if ("口碑家装".equals(title_list.get(pos))){
//
//                        }else if ("电影演出".equals(title_list.get(pos))){
//
//                        }else if ("K歌达人".equals(title_list.get(pos))){
//
//                        }else if ("周边玩乐".equals(title_list.get(pos))){
//
//                        }else if ("畅游海外".equals(title_list.get(pos))){
//
//                        }else if ("医疗健康".equals(title_list.get(pos))){
//
//                        }else if ("交通出行".equals(title_list.get(pos))){
//
//                        }

                        Bundle menuBundle = new Bundle();
                        menuBundle.putString("menu_title", title_list.get(pos));
                        menuBundle.putString("top_cate", rec_type_id_list.get(pos));
                        startActivity(ShopMallAty.class, menuBundle);
                    }
                });
                // 给ViewPager设置适配器
                under_banner_menu_vp.setAdapter(new ViewPagerAdapter(mPagerList));
                // 添加页面改变监听事件
                under_banner_menu_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        curIndex = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.off_line_title_layout);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            off_line_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
        } else {
            off_line_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
        }
        immersionInit();
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    @Override
    public void onItemClickListener(OffLineDataBean offLineDataBean, int position) {
        //http://www.wujiemall.com/Wap/OfflineStore/offlineShop/merchant_id/12/invite_code/pp0IKpSv.html
        String goods_num = offLineDataBean.getGoods_num();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(goods_num) && Integer.parseInt(goods_num) > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            if (Config.OFFICIAL_WEB.contains("api")) {
                stringBuffer.append("http://www.wujiemall.com/");
            } else {
                stringBuffer.append(Config.OFFICIAL_WEB);
            }
            stringBuffer.append("Wap/OfflineStore/offlineShop/merchant_id/");
            stringBuffer.append(offLineDataBean.getS_id());
            if (Config.isLogin()) {
                stringBuffer.append("/invite_code/");
                stringBuffer.append(PreferencesUtils.getString(getActivity(), "invite_code"));
            }
            stringBuffer.append(".html");

//            bundle.putString("desc", ""); // 传过去没什么用
            bundle.putString("url", stringBuffer.toString()); // url
//            bundle.putInt("from", 2);
            startActivity(WebViewAty.class, bundle);
        } else {
            bundle.putSerializable("mellInfo", offLineDataBean);
            startActivity(ShopMallDetailsAty.class, bundle);
        }
    }

    @Override
    public void loadComplate() {
        swipeRefreshLayout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setLoadMore(false);
    }

}
