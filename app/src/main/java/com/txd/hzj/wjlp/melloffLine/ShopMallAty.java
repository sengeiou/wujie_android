package com.txd.hzj.wjlp.melloffLine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.offline.OffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.http.Recommending;
import com.txd.hzj.wjlp.mainfgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.mellonLine.MessageAty;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellonLine.SearchAty;
import com.txd.hzj.wjlp.tool.GlideImageLoader;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/16 14:56
 * 功能描述：线下店铺店铺列表页
 */
public class ShopMallAty extends BaseAty {

    @ViewInject(R.id.super_offline_layout)
    private VpSwipeRefreshLayout super_offline_layout;

    @ViewInject(R.id.title_tv)
    private TextView title_tv;

    @ViewInject(R.id.title_img)
    private ImageView title_img;


    //消息数量
    @ViewInject(R.id.message_num_tv)
    private TextView message_num_tv;

    //广告图片
    @ViewInject(R.id.ad_img)
    private ImageView ad_img;

    @ViewInject(R.id.banner)
    private Banner bannerImg;

    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;


    @ViewInject(R.id.line_view)
    private View line_view;

    @ViewInject(R.id.titles_tab_layout)
    private TabLayout titles_tab_layout;

    @ViewInject(R.id.mell_near_by_lv)
    private ListViewForScrollView mell_near_by_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.bg_view)
    private View bg_view;


    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;


    private String mTitle;
    private String mTop_cate = "";
    private String mLittle_cate = "";

    private int page = 1;
    private MellNearByHzjAdapter mellNearByHzjAdapter;
    private List<OffLineBean.NumsBean> mNumsBeans;

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


    private int leftPosition = 0;
    private ShopMallPop shopMallPop;

    private boolean isSelected = false;
    private int mInt = 0;
    private int rightPositon = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_mall;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.off_line_title_layout);
        title_list = new ArrayList<>();
        image_list = new ArrayList<>();
        rec_type_id_list = new ArrayList<>();
        mell_near_by_lv.setFocusable(false);
        mTitle = getIntent().getStringExtra("menu_title");
        mTop_cate = getIntent().getStringExtra("top_cate");
        if (!TextUtils.isEmpty(mTitle)) {
            title_tv.setText(mTitle);
        }

        //设置图片加载器
        bannerImg.setImageLoader(new GlideImageLoader());
        //设置样式
        bannerImg.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置动画
        bannerImg.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        bannerImg.setDelayTime(2000);
    }

    private void postUrl(String top_cate, String little_cate) {
        Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
        if (locMap != null) {
            OfflineStore.offlineStoreList(locMap.get("lon"), locMap.get("lat"), page, "", top_cate, little_cate, this);
        } else {
            OfflineStore.offlineStoreList("", "", page, "", top_cate, little_cate, this);
        }
    }

    @Override
    protected void requestData() {
        Recommending.businessType(this);
        postUrl(mTop_cate, mLittle_cate);
        OfflineStore.stageAds(mTop_cate, this);
        if (null == mellNearByHzjAdapter) {
            mellNearByHzjAdapter = new MellNearByHzjAdapter(this);
            mell_near_by_lv.setAdapter(mellNearByHzjAdapter);
            mell_near_by_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OffLineDataBean offLineDataBea = mellNearByHzjAdapter.getItem(position);
                    String goods_num = offLineDataBea.getGoods_num();
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(goods_num) && Integer.parseInt(goods_num) > 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        if (Config.OFFICIAL_WEB.contains("api")) {
                            stringBuffer.append("http://www.wujiemall.com/");
                        } else {
                            stringBuffer.append(Config.OFFICIAL_WEB);
                        }
                        stringBuffer.append("Wap/OfflineStore/offlineShop/merchant_id/");
                        stringBuffer.append(offLineDataBea.getS_id());
                        if (Config.isLogin()) {
                            stringBuffer.append("/invite_code/");
                            stringBuffer.append(PreferencesUtils.getString(ShopMallAty.this, "invite_code"));
                        }
                        stringBuffer.append(".html");

                        bundle.putString("desc", ""); // 传过去没什么用
                        bundle.putString("href", stringBuffer.toString()); // url
                        bundle.putInt("from", 2);
                        startActivity(NoticeDetailsAty.class, bundle);
                    } else {
                        bundle.putSerializable("mellInfo", offLineDataBea);
                        startActivity(ShopMallDetailsAty.class, bundle);
                    }
                }
            });
        }

        titles_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page = 1;
                int tabPosition = tab.getPosition();
                if (mNumsBeans.size() > 0) {
                    if (!isSelected) {
                        isSelected = true;
                    } else {
                        mLittle_cate = mNumsBeans.get(tabPosition).getRec_type_id();
                        postUrl(mTop_cate, mLittle_cate);
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        super_offline_layout.setHeaderViewBackgroundColor(Color.WHITE);
        super_offline_layout.setHeaderView(createHeaderView());// add headerView
        super_offline_layout.setTargetScrollWithLayout(true);
        super_offline_layout.setFooterView(createFooterView());

        super_offline_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                page = 1;
                postUrl(mTop_cate, mLittle_cate);
                OfflineStore.stageAds(mTop_cate, ShopMallAty.this);
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


        super_offline_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                page++;
                postUrl(mTop_cate, mLittle_cate);
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
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("offlineStoreList")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, OffLineBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    OffLineBean offLineBean = (OffLineBean) t;
                    if ("1".equals(offLineBean.getCode())) {
                        if (page == 1) {
                            mellNearByHzjAdapter.getList().clear();
                        }
                        if (offLineBean.getData().size() > 0) {
                            mellNearByHzjAdapter.getList().addAll(offLineBean.getData());
                        }
                        if (mellNearByHzjAdapter.getList().size() > 0) {
                            mell_near_by_lv.setVisibility(View.VISIBLE);
                            no_data_layout.setVisibility(View.GONE);
                            mellNearByHzjAdapter.notifyDataSetChanged();
                        } else {
                            mell_near_by_lv.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                        }
                        mNumsBeans = offLineBean.getNums();
                        if (mNumsBeans.size() > 0) {
                            OffLineBean.NumsBean numsBean = new OffLineBean.NumsBean();
                            numsBean.setRec_type_id("");
                            numsBean.setType("全部");
                            numsBean.setCate_img("");
                            mNumsBeans.add(0, numsBean);
                            if (!isSelected) {
                                for (int i = 0; i < mNumsBeans.size(); i++) {
                                    String type = mNumsBeans.get(i).getType();
                                    titles_tab_layout.addTab(titles_tab_layout.newTab().setText(type));

                                }
                                titles_tab_layout.getTabAt(rightPositon).select();
                                titles_tab_layout.setScrollPosition(rightPositon, 0f, false);
                            }
                        }
                        String tip_num = offLineBean.getTip_num();
                        if (!TextUtils.isEmpty(tip_num)) {
                            message_num_tv.setText(tip_num);
                        }
                    }
                    if (shopMallPop != null && shopMallPop.isShowing()) {
                        shopMallPop.notifyRightData(mNumsBeans, title_list.get(leftPosition));
                    }

                }
            });
        }
        if (requestUrl.contains("stageAds")) {
            Map<String, String> jsonMap = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (jsonMap.containsKey("code") && "1".equals(jsonMap.get("code"))) {
                if (jsonMap.containsKey("data")) {
                    ArrayList<Map<String, String>> dataList = JSONUtils.parseKeyAndValueToMapList(jsonMap.get("data"));
                    if (dataList.size() > 0) {
                        if (dataList.size() == 1) {
                            ad_img.setVisibility(View.VISIBLE);
                            bannerImg.setVisibility(View.GONE);
                            Map<String, String> dataMap = dataList.get(0);
                            if (dataMap.containsKey("picture")) {
                                String picUrl = dataMap.get("picture");
                                if (picUrl.contains("gif")) {
                                    Glide.with(ShopMallAty.this).load(picUrl).asGif().into(ad_img);
                                } else {
                                    Glide.with(ShopMallAty.this).load(picUrl).asBitmap().into(ad_img);
                                }

                            }
                        } else {
                            ad_img.setVisibility(View.GONE);
                            bannerImg.setVisibility(View.VISIBLE);
                            List<String> picList = new ArrayList<>();
                            for (int i = 0; i < dataList.size(); i++) {
                                Map<String, String> dataMap = dataList.get(i);
                                if (dataMap.containsKey("picture")) {
                                    picList.add(dataMap.get("picture"));
                                }
                            }
                            bannerImg.setImages(picList);
                            bannerImg.start();
                        }

                    }

                }
            }
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
                }
            }
        }
        loadComplate();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (null != bannerImg && bannerImg.getVisibility() == View.VISIBLE) {
            bannerImg.startAutoPlay();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bannerImg != null) {
            bannerImg.stopAutoPlay();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        loadComplate();
    }

    private void loadComplate() {
        super_offline_layout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        super_offline_layout.setLoadMore(false);
    }

    @Override
    @OnClick({R.id.to_search, R.id.off_line_message_layout, R.id.title_layout})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.to_search) {
            Bundle b = new Bundle();
            startActivity(SearchAty.class, b);
        }
        if (id == R.id.off_line_message_layout) {
            startActivity(MessageAty.class, null);
        }
        if (id == R.id.title_layout) {
            off_line_to_change_sc.smoothScrollTo(0, ad_img.getLayoutParams().height + 50);
            if (null != rec_type_id_list && rec_type_id_list.size() > 0) {
                for (int i = 0; i < rec_type_id_list.size(); i++) {
                    if (mTop_cate.equals(rec_type_id_list.get(i))) {
                        mInt = i;
                        break;
                    }
                }
            }
            shopMallPop = new ShopMallPop(ShopMallAty.this, title_list, image_list, rec_type_id_list, mNumsBeans, mInt);
            shopMallPop.setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2);
            shopMallPop.setOnPopItemListener(new ShopMallPop.OnPopItemListener() {
                @Override
                public void leftClick(String top_cate, int position) {
                    mTop_cate = top_cate;
                    postUrl(top_cate, mLittle_cate);
                    leftPosition = position;

                }

                @Override
                public void rightClick(String top_cate, String little_cate, int position) {

                    rightPositon = position;
                    Log.e("TAG", "rightClick: " + rightPositon);
                    mTop_cate = top_cate;
                    mLittle_cate = little_cate;
                    titles_tab_layout.removeAllTabs();
                    isSelected = false;
                    for (int i = 0; i < rec_type_id_list.size(); i++) {
                        if (top_cate.equals(rec_type_id_list.get(i))) {
                            title_tv.setText(title_list.get(i));
                            break;
                        }
                    }
                    postUrl(top_cate, little_cate);
                    OfflineStore.stageAds(mTop_cate, ShopMallAty.this);
                    shopMallPop.dismiss();
                }

            });
            title_img.setRotation(90);
            bg_view.setVisibility(View.VISIBLE);
            bg_view.setFocusable(true);
            shopMallPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    title_img.setRotation(270);
                    bg_view.setVisibility(View.GONE);
                }
            });
            shopMallPop.showPopupWindow(line_view);

            if (null != shopMallPop && shopMallPop.isShowing()) {
                title_img.setRotation(90f);
            }
        }

    }


    private View createHeaderView() {
        View headerView = LayoutInflater.from(super_offline_layout.getContext()).inflate(R.layout.layout_head, null);
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
        View footerView = LayoutInflater.from(super_offline_layout.getContext()).inflate(R.layout.layout_footer, super_offline_layout, false);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

}
