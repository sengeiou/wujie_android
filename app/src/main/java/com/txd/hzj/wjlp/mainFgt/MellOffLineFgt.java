package com.txd.hzj.wjlp.mainFgt;


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

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.citySelect.MellCitySelectAty;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.mainFgt.adapter.OffLineMenuGvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOffLine.ShopMallDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.SearchAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:52
 * 描述：线下商铺
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

    private List<String> gv_classify;

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
                    pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", getContext(), mell_near_by_lv);
                } else {
                    pranster.requestStoreData(page, "", "", "", getContext(), mell_near_by_lv);
                }
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
                    pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", getContext(), mell_near_by_lv);
                } else {
                    pranster.requestStoreData(page, "", "", "", getContext(), mell_near_by_lv);
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
        forMenu();
    }

    private void forMenu() {

        // 获取总页数
        int pageCount = (int) Math.ceil(gv_classify.size() * 1.0 / pageSize);
        // 初始化View列表
        ArrayList<View> mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout, under_banner_menu_vp, false);
            //设置adapter  给布局填充控件（限量购及票卷区图标）
            gridView.setAdapter(new OffLineMenuGvAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = i + curIndex * pageSize;
                    switch (pos) {
                        case 0:
                            showToast("开发中，敬请期待");
                            break;
                        case 1:
                            showToast("开发中，敬请期待");
                            break;
                        case 2:
                            break;
                        case 3:
                            showToast("开发中，敬请期待");
                            break;
                        case 4:
                            showToast("开发中，敬请期待");
                            break;
                        case 5:
                            break;
                        case 6:
                            showToast("开发中，敬请期待");
                            break;
                        case 7:
                            break;
                        case 8:
                            showToast("开发中，敬请期待");
                            break;
                        case 9:
                            showToast("开发中，敬请期待");
                            break;
                        case 10:
                            showToast("开发中，敬请期待");
                            break;
                        case 11:
                            showToast("开发中，敬请期待");
                            break;
                        case 12:
                            break;
                        case 13:
                            showToast("开发中，敬请期待");
                            break;
                        case 14:
                            showToast("开发中，敬请期待");
                            break;
                        case 15:
                            break;
                        case 16:
                            showToast("开发中，敬请期待");
                            break;
                        case 17:
                            break;
                        case 18:
                            showToast("开发中，敬请期待");
                            break;
                        case 19:
                            showToast("开发中，敬请期待");
                            break;
                        case 20:
                            showToast("开发中，敬请期待");
                            break;
                        case 21:
                            showToast("开发中，敬请期待");
                            break;

                    }
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
        bundle.putString("href", limit_href);
        bundle.putInt("from", 2);
        startActivity(NoticeDetailsAty.class, bundle);
    }

    @Override
    @OnClick({R.id.to_location_tv, R.id.to_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_location_tv:// 当前位置
                startActivity(MellCitySelectAty.class, null);
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
        gv_classify=new ArrayList<>();
        String[] titles={"餐饮美食","商户地图","精选美食","品质购物","休闲娱乐","生活服务","精致丽人","学习培训","母婴亲子","运动健身","我要结婚","酒店住宿","汽车服务","宠物生活","口碑家装","电影演出","K歌达人","周边玩乐","畅游海外","医疗健康","交通出行"};
        for (int i = 0; i < titles.length; i++) {
            gv_classify.add(titles[i]);
        }

    }

    @Override
    protected void requestData() {
        to_location_tv.setText(DemoApplication.getInstance().getLocInfo().get("city"));
        OfflineStore.Index(this);
        Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
        if (null == pranster) {
            pranster = new Pranster();
            pranster.setView(this);
        }
        page = 1;
        if (null != locMap && !TextUtils.isEmpty(locMap.get("lon"))) {
            pranster.requestStoreData(page, locMap.get("lon"), locMap.get("lat"), "", getContext(), mell_near_by_lv);
        } else {
            pranster.requestStoreData(page, "", "", "", getContext(), mell_near_by_lv);
        }
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

            // 设置图片控件的宽高比
            ViewGroup.LayoutParams lp = im_ads.getLayoutParams();
            lp.width = Settings.displayWidth;
            lp.height = Settings.displayWidth * 400 / 1242;
            im_ads.setLayoutParams(lp);
            im_ads.setMaxWidth(lp.width);
            im_ads.setMaxHeight(lp.width * 400 / 1242);

            Glide.with(getActivity()).load(list_ads.get(0).get("picture"))
                    .override(lp.width, lp.height)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .into(im_ads);
            // TODO 暂时取消点击事件
            im_ads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ShopMallDetailsAty.class, null);
               /* if (!TextUtils.isEmpty(list_ads.get(0).get("merchant_id")) && !list_ads.get(0).get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", list_ads.get(0).get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty(list_ads.get(0).get("goods_id")) && !list_ads.get(0).get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", list_ads.get(0).get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    forShowAds(list_ads.get(0).get("desc"), list_ads.get(0).get("href"));
                }*/
                }
            });
            list_brand = JSONUtils.parseKeyAndValueToMapList(data.get("brand"));
            Glide.with(getActivity()).load(list_brand.get(0).get("picture")).override(img_w, img_h)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop().into(three_image_left_iv);
            // TODO 暂时取消点击事件
//        three_image_left_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(list_brand.get(0).get("merchant_id")) && !list_brand.get(0).get("merchant_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("mell_id", list_brand.get(0).get("merchant_id"));
//                    startActivity(MellInfoAty.class, bundle);
//                } else if (!TextUtils.isEmpty(list_brand.get(0).get("goods_id")) && !list_brand.get(0).get("goods_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("ticket_buy_id", list_brand.get(0).get("goods_id"));
//                    bundle.putInt("from", 1);
//                    startActivity(TicketGoodsDetialsAty.class, bundle);
//                } else {
//                    forShowAds(list_brand.get(0).get("desc"), list_brand.get(0).get("href"));
//                }
//            }
//        });
            Glide.with(getActivity()).load(list_brand.get(1).get("picture")).override(img_w, img_h)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop().into(three_image_center_iv);
            // TODO 暂时取消点击事件
//        three_image_center_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(list_brand.get(1).get("merchant_id")) && !list_brand.get(1).get("merchant_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("mell_id", list_brand.get(1).get("merchant_id"));
//                    startActivity(MellInfoAty.class, bundle);
//                } else if (!TextUtils.isEmpty(list_brand.get(1).get("goods_id")) && !list_brand.get(1).get("goods_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("ticket_buy_id", list_brand.get(1).get("goods_id"));
//                    bundle.putInt("from", 1);
//                    startActivity(TicketGoodsDetialsAty.class, bundle);
//                } else {
//                    forShowAds(list_brand.get(1).get("desc"), list_brand.get(1).get("href"));
//                }
//            }
//        });
            Glide.with(getActivity()).load(list_brand.get(2).get("picture")).override(img_w, img_h)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop().into(three_image_right_iv);
            // TODO 暂时取消点击事件
//        three_image_right_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(list_brand.get(2).get("merchant_id")) && !list_brand.get(2).get("merchant_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("mell_id", list_brand.get(2).get("merchant_id"));
//                    startActivity(MellInfoAty.class, bundle);
//                } else if (!TextUtils.isEmpty(list_brand.get(2).get("goods_id")) && !list_brand.get(1).get("goods_id").equals("0")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("ticket_buy_id", list_brand.get(2).get("goods_id"));
//                    bundle.putInt("from", 1);
//                    startActivity(TicketGoodsDetialsAty.class, bundle);
//                } else {
//                    forShowAds(list_brand.get(2).get("desc"), list_brand.get(2).get("href"));
//                }
//            }
//        });
            progressBar.setVisibility(View.GONE);

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
        Bundle options = new Bundle();
        options.putSerializable("mellInfo", offLineDataBean);
        startActivity(ShopMallDetailsAty.class, options);
    }

    @Override
    public void loadComplate() {
        swipeRefreshLayout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setLoadMore(false);
    }

    @Override
    public void loadMoreOver() {
        if (page > 1)
            page -= 1;
    }
}
