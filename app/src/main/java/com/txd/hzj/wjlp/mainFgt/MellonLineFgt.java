package com.txd.hzj.wjlp.mainFgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.OnLineMenuGvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOnLine.MellOnLineClassifyAty;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.WujieTopHzjAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.AuctionCollectAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.AuctionGoodsDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.CarDetailseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsInputHzjAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.InputGoodsDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitShoppingAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ThemeStreetHzjAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.car.CarChenAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HousChenAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HousDetailsChenAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.SnatchChenAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.SnatchGoodsDetailsAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:05
 * 描述：线上商城
 * ===============Txunda===============
 */
public class MellonLineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {


    @ViewInject(R.id.search_title_layout)
    private RelativeLayout search_title_layout;

    /**
     * 扫一扫
     */
    @ViewInject(R.id.title_scan_tv)
    public TextView title_scan_tv;
    /**
     * 分类
     */
    @ViewInject(R.id.title_classify_tv)
    public TextView title_classify_tv;
    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Map<String, String>> image;

    /**
     * 横向滑动的分类
     */
    @ViewInject(R.id.on_line_rv)
    private RecyclerView on_line_rv;
    /**
     * 分类列表
     */
    private List<Map<String, String>> horizontal_classify;

    /**
     * 横向滑动的分类适配器
     */
    private HorizontalAdapter horizontalAdapter;

    /**
     * GridView数据列表
     */
    private List<String> gv_classify;

    /**
     * 今日头条
     */
    @ViewInject(R.id.upview1)
    private UPMarqueeView upview1;
    /**
     * 无界头条View
     */
    private List<View> views;
    private List<Map<String, String>> updata;

    @ViewInject(R.id.mell_on_line_sc)
    private ObservableScrollView mell_on_line_sc;

    /**
     * 限量购
     */
    @ViewInject(R.id.purchase_gv)
    private GridViewForScrollView purchase_gv;
    /**
     * 票券区
     */
    @ViewInject(R.id.ticket_gv)
    private GridViewForScrollView ticket_gv;
    /**
     * 无界优购
     */
    @ViewInject(R.id.limit_shopping_gv)
    private GridViewForScrollView limit_shopping_gv;
    /**
     * 进口馆
     */
    @ViewInject(R.id.import_gv)
    private GridViewForScrollView import_gv;
    /**
     * 竞拍汇
     */
    @ViewInject(R.id.auction_gv)
    private GridViewForScrollView auction_gv;
    /**
     * 一元夺宝
     */
    @ViewInject(R.id.good_luck_gv)
    private GridViewForScrollView good_luck_gv;
    /**
     * 汽车购
     */
    @ViewInject(R.id.car_gv)
    private GridViewForScrollView car_gv;
    /**
     * 房产购
     */
    @ViewInject(R.id.house_gv)
    private GridViewForScrollView house_gv;
    /**
     * 拼团区
     */
    @ViewInject(R.id.group_shopping_lv)
    private ListViewForScrollView group_shopping_lv;

    private AllGvLvAdapter allGvLvAdapter;
    private AllGvLvAdapter allGvLvAdapter1;
    private AllGvLvAdapter allGvLvAdapter2;
    private AllGvLvAdapter allGvLvAdapter3;
    private AllGvLvAdapter allGvLvAdapter4;
    private AllGvLvAdapter allGvLvAdapter5;
    private AllGvLvAdapter allGvLvAdapter6;
    private AllGvLvAdapter allGvLvAdapter7;
    private AllGvLvAdapter allGvLvAdapter8;
    private Bundle bundle;

    /**
     * 轮播图高度
     */
    private int allHeight = 0;

    @ViewInject(R.id.on_line_be_back_top_iv)
    private ImageView on_line_be_back_top_iv;

    /**
     * 轮图下方的分类
     */
    @ViewInject(R.id.under_banner_menu_vp)
    private ViewPager under_banner_menu_vp;


    private int pageSize = 10;
    /**
     * 当前选中的第几页
     */
    private int curIndex = 0;

    private ViewPagerAdapter viewPagerAdapter;

    private IndexPst indexPst;
    /**
     * 消息数量
     */
    @ViewInject(R.id.message_num_tv)
    private TextView message_num_tv;

    /**
     * 品牌图(左边)
     */
    @ViewInject(R.id.three_image_left_iv)
    private ImageView three_image_left_iv;
    /**
     * 中国制造(中间)
     */
    @ViewInject(R.id.three_image_center_iv)
    private ImageView three_image_center_iv;
    /**
     * 科技前沿(右边)
     */
    @ViewInject(R.id.three_image_right_iv)
    private ImageView three_image_right_iv;

    private int img_w = 0;
    private int img_h = 0;
    private String left_desc = "";
    private String left_href = "";
    private String cent_desc = "";
    private String cent_href = "";
    private String right_desc = "";
    private String right_href = "";


    /**
     * 广告高度
     */
    private int ads_h = 0;
    /**
     * 限量购广告图
     */
    @ViewInject(R.id.ads_by_limit_buy_iv)
    private ImageView ads_by_limit_buy_iv;
    /**
     * 限量购href
     */
    private String limit_href = "";
    /**
     * 限量购desc
     */
    private String limit_desc = "";
    /**
     * 票券区
     */
    @ViewInject(R.id.ticket_buy_ads_iv)
    private ImageView ticket_buy_ads_iv;
    private String ticket_href = "";
    private String ticket_desc = "";
    /**
     * 限量购
     */
    @ViewInject(R.id.pre_buy_ads_iv)
    private ImageView pre_buy_ads_iv;
    private String pre_href = "";
    private String pre_desc = "";
    /**
     * 进口馆
     */
    @ViewInject(R.id.country_ads_iv)
    private ImageView country_ads_iv;
    private String country_href = "";
    private String country_desc = "";
    /**
     * 竞拍汇
     */
    @ViewInject(R.id.auction_ads_iv)
    private ImageView auction_ads_iv;
    private String auction_href = "";
    private String auction_desc = "";
    /**
     * 一元夺宝(积分夺宝)
     */
    @ViewInject(R.id.one_buy_ads_iv)
    private ImageView one_buy_ads_iv;
    private String one_buy_href = "";
    private String one_buy_desc = "";
    /**
     * 汽车购
     */
    @ViewInject(R.id.car_ads_iv)
    private ImageView car_ads_iv;
    private String car_href = "";
    private String car_desc = "";
    /**
     * 汽车购
     */
    @ViewInject(R.id.house_ads_iv)
    private ImageView house_ads_iv;
    private String house_href = "";
    private String house_desc = "";
    /**
     * 团购
     */
    @ViewInject(R.id.group_buy_ads_iv)
    private ImageView group_buy_ads_iv;
    private String group_href = "";
    private String group_desc = "";


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search_title_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        title_scan_tv.setVisibility(View.VISIBLE);
        title_classify_tv.setVisibility(View.VISIBLE);
        // 轮播图高度
        allHeight = Settings.displayWidth * 3 / 5;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);

        // 设置三张广告图的宽高
        img_w = Settings.displayWidth / 3;
        img_h = Settings.displayWidth * 5 / 14;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(img_w, img_h);
        three_image_left_iv.setLayoutParams(params);
        three_image_center_iv.setLayoutParams(params);
        three_image_right_iv.setLayoutParams(params);
        // 广告宽高
        ads_h = Settings.displayWidth / 2;
        LinearLayout.LayoutParams adsParam = new LinearLayout.LayoutParams(Settings.displayWidth, ads_h);
        ads_by_limit_buy_iv.setLayoutParams(adsParam);
        ticket_buy_ads_iv.setLayoutParams(adsParam);
        pre_buy_ads_iv.setLayoutParams(adsParam);
        auction_ads_iv.setLayoutParams(adsParam);
        country_ads_iv.setLayoutParams(adsParam);
        one_buy_ads_iv.setLayoutParams(adsParam);
        car_ads_iv.setLayoutParams(adsParam);
        house_ads_iv.setLayoutParams(adsParam);
        group_buy_ads_iv.setLayoutParams(adsParam);


        forHorizontalItem();

        forMenu();

        mell_on_line_sc.setScrollViewListener(MellonLineFgt.this);
        goodsAdapter();

    }

    private void forMenu() {
        // 获取总页数
        int pageCount = (int) Math.ceil(gv_classify.size() * 1.0 / pageSize);
        // 初始化View列表
        ArrayList<View> mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout,
                    under_banner_menu_vp, false);
            gridView.setAdapter(new OnLineMenuGvAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = i + curIndex * pageSize;
                    switch (pos) {
                        case 0:// 限量购
                            startActivity(LimitShoppingAty.class, null);
                            break;
                        case 1:// 票券区
                            bundle = new Bundle();
                            bundle.putInt("type", 1);
                            bundle.putString("title", "票券区");
                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 2:// 拼团购
                            bundle = new Bundle();
                            bundle.putInt("type", 8);
                            bundle.putString("title", "拼团购");
                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 3:// 主题街
                            startActivity(ThemeStreetHzjAty.class, null);
                            break;
                        case 4:// 无界预购
                            bundle = new Bundle();
                            bundle.putInt("type", 2);
                            bundle.putString("title", "无界预购");
                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 5:// 进口馆
                            bundle = new Bundle();
                            bundle.putInt("type", 3);
                            bundle.putString("title", "进口馆");
                            startActivity(GoodsInputHzjAty.class, bundle);
                            break;
                        case 6:// 竞拍汇
                            bundle = new Bundle();
                            bundle.putInt("type", 3);
                            bundle.putString("title", "竞拍汇");
                            startActivity(AuctionCollectAty.class, bundle);
                            break;
                        case 7://汽车购
                            startActivity(CarChenAty.class, null);
                            break;
                        case 8://房产购
                            startActivity(HousChenAty.class, null);
                            break;
                        case 9://一元夺宝
                            startActivity(SnatchChenAty.class, null);
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
     * 首页下方列表适配器
     */
    private void goodsAdapter() {
        // 限量购
        purchase_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(LimitGoodsAty.class, null);
            }
        });
        // 票券区

        ticket_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(TicketGoodsDetialsAty.class, null);
            }
        });

        // 无界预购
        limit_shopping_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(LimitGoodsAty.class, null);
            }
        });

        // 进口馆

        import_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(InputGoodsDetailsAty.class, null);
            }
        });
        // 竞拍汇
        auction_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(AuctionGoodsDetailsAty.class, null);
            }
        });
        // 一元夺宝
        good_luck_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(SnatchGoodsDetailsAty.class, null);
            }
        });
        // 汽车购
        car_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(CarDetailseAty.class, null);
            }
        });
        // 房产购

        house_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(HousDetailsChenAty.class, null);
            }
        });
        // 拼团区
        group_shopping_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(GoodLuckDetailsAty.class, null);
            }
        });
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
            Glide.with(getActivity()).load(image.get(position).get("picture"))
                    .override(Settings.displayWidth, allHeight)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .centerCrop()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forShowAds(image.get(position).get("desc"), image.get(position).get("href"));
                }
            });
        }
    };

    @Override
    @OnClick({R.id.wujie_top_lin_layout, R.id.on_line_be_back_top_iv, R.id.three_image_left_iv,
            R.id.three_image_center_iv, R.id.three_image_right_iv, R.id.ads_by_limit_buy_iv,
            R.id.ticket_buy_ads_iv, R.id.pre_buy_ads_iv, R.id.country_ads_iv, R.id.auction_ads_iv,
            R.id.one_buy_ads_iv, R.id.car_ads_iv, R.id.house_ads_iv, R.id.group_buy_ads_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.wujie_top_lin_layout:// 无界头条
                startActivity(WujieTopHzjAty.class, null);
                break;
            case R.id.on_line_be_back_top_iv:// 无界头条
                mell_on_line_sc.smoothScrollTo(0, 0);
                break;
            case R.id.three_image_left_iv://左边
                forShowAds(left_desc, left_href);
                break;
            case R.id.three_image_center_iv://中间
                forShowAds(cent_desc, cent_href);
                break;
            case R.id.three_image_right_iv://右边
                forShowAds(right_desc, right_desc);
                break;
            case R.id.ads_by_limit_buy_iv://限量购
                forShowAds(limit_desc, limit_href);
                break;
            case R.id.ticket_buy_ads_iv:// 票券区
                forShowAds(ticket_desc, ticket_href);
                break;
            case R.id.pre_buy_ads_iv:// 无界预购
                forShowAds(pre_desc, pre_href);
                break;
            case R.id.country_ads_iv:// 进口馆
                forShowAds(country_desc, country_href);
                break;
            case R.id.auction_ads_iv:// 竞拍汇
                forShowAds(auction_desc, auction_href);
                break;
            case R.id.one_buy_ads_iv:// 一元夺宝(积分夺宝)
                forShowAds(one_buy_desc, one_buy_href);
                break;
            case R.id.car_ads_iv:// 汽车购
                forShowAds(car_desc, car_href);
                break;
            case R.id.house_ads_iv:// 房产购
                forShowAds(house_desc, house_href);
                break;
            case R.id.group_buy_ads_iv:// 团购
                forShowAds(group_desc, group_href);
                break;
        }
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

    /**
     * 横向滑动的分类菜单
     */
    private void forHorizontalItem() {
        // 设置布局方式
        on_line_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        // 默认分割线
        on_line_rv.setItemAnimator(new DefaultItemAnimator());
        on_line_rv.setHasFixedSize(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mellon_line;
    }

    @Override
    protected void initialized() {
        indexPst = new IndexPst(this);
        image = new ArrayList<>();
        horizontal_classify = new ArrayList<>();
        gv_classify = new ArrayList<>();

        // 头条
        updata = new ArrayList<>();
        views = new ArrayList<>();
        gv_classify.add("限量购");
        gv_classify.add("票券区");
        gv_classify.add("拼团购");
        gv_classify.add("主题街");
        gv_classify.add("无界预购");
        gv_classify.add("进口馆");
        gv_classify.add("竞拍汇");
        gv_classify.add("汽车购");
        gv_classify.add("房产购");
        gv_classify.add("积分夺宝");
    }

    @Override
    protected void requestData() {
        indexPst.index();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("index")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            // 消息
            forMes(data);

            // 轮播图
            if (ToolKit.isList(data, "index_banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("index_banner"));
                forBanner();
            }
            // 横向菜单
            if (ToolKit.isList(data, "top_nav")) {
                forHorizontalMenu(data);
            }
            // 无界头条
            if (ToolKit.isList(data, "headlines")) {
                updata = JSONUtils.parseKeyAndValueToMapList(data.get("headlines"));
                setView();
                upview1.setViews(views);
            }
            // 三个活动图片
            threeAdsInfo(data);
            // 限量购
            forLimit(data);
            // 票券区
            forTicket(data);
            // 无界预购
            orPre(data);
            // 进口馆
            forCountry(data);
            // 竞拍汇
            forAuction(data);
            // 积分夺宝
            forOneBuy(data);
            // 汽车购
            forCar(data);
            // 房产购
            forHouse(data);
            // 拼团购
            forGroup(data);


        }
    }

    // 拼团购
    private void forGroup(Map<String, String> data) {
        Map<String, String> group_buy = JSONUtils.parseKeyAndValueToMap(data.get("group_buy"));
        Map<String, String> group_buy_ads = JSONUtils.parseKeyAndValueToMap(group_buy.get("ads"));
        Glide.with(getActivity()).load(group_buy_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(group_buy_ads_iv);
        group_href = group_buy_ads.get("href");
        group_desc = group_buy_ads.get("desc");

        if (ToolKit.isList(group_buy, "goodsList")) {
            List<AllGoodsBean> groupList = GsonUtil.getObjectList(group_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter8 = new AllGvLvAdapter(getActivity(), groupList, 8);
            group_shopping_lv.setAdapter(allGvLvAdapter8);
        }
    }

    // 房产购
    private void forHouse(Map<String, String> data) {
        Map<String, String> house_buy = JSONUtils.parseKeyAndValueToMap(data.get("house"));
        Map<String, String> house_ads = JSONUtils.parseKeyAndValueToMap(house_buy.get("ads"));

        Glide.with(getActivity()).load(house_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(house_ads_iv);
        house_href = house_ads.get("href");
        house_desc = house_ads.get("desc");

        if (ToolKit.isList(house_buy, "goodsList")) {
            List<AllGoodsBean> houseList = GsonUtil.getObjectList(house_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter7 = new AllGvLvAdapter(getActivity(), houseList, 7);
            house_gv.setAdapter(allGvLvAdapter7);
        }
    }

    private void forCar(Map<String, String> data) {
        Map<String, String> car_buy = JSONUtils.parseKeyAndValueToMap(data.get("car"));
        Map<String, String> car_ads = JSONUtils.parseKeyAndValueToMap(car_buy.get("ads"));

        Glide.with(getActivity()).load(car_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(car_ads_iv);
        car_href = car_ads.get("href");
        car_desc = car_ads.get("desc");

        if (ToolKit.isList(car_buy, "goods_list")) {
            L.e("=====汽车购=====", car_buy.get("goodsList"));
            List<AllGoodsBean> carList = GsonUtil.getObjectList(car_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter6 = new AllGvLvAdapter(getActivity(), carList, 6);
            car_gv.setAdapter(allGvLvAdapter6);
        }
    }

    // 积分夺宝
    private void forOneBuy(Map<String, String> data) {
        Map<String, String> one_buy = JSONUtils.parseKeyAndValueToMap(data.get("auction"));
        Map<String, String> one_buy_ads = JSONUtils.parseKeyAndValueToMap(one_buy.get("ads"));

        Glide.with(getActivity()).load(one_buy_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(one_buy_ads_iv);
        one_buy_href = one_buy_ads.get("href");
        one_buy_desc = one_buy_ads.get("desc");

        if (ToolKit.isList(one_buy, "goodsList")) {
            List<AllGoodsBean> one_buyList = GsonUtil.getObjectList(one_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter5 = new AllGvLvAdapter(getActivity(), one_buyList, 5);
            good_luck_gv.setAdapter(allGvLvAdapter5);
        }
    }

    // 竞拍汇
    private void forAuction(Map<String, String> data) {
        Map<String, String> auction = JSONUtils.parseKeyAndValueToMap(data.get("auction"));
        Map<String, String> auction_ads = JSONUtils.parseKeyAndValueToMap(auction.get("ads"));

        Glide.with(getActivity()).load(auction_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(auction_ads_iv);
        auction_href = auction_ads.get("href");
        auction_desc = auction_ads.get("desc");

        if (ToolKit.isList(auction, "goodsList")) {
            List<AllGoodsBean> auctionList = GsonUtil.getObjectList(auction.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter4 = new AllGvLvAdapter(getActivity(), auctionList, 4);
            auction_gv.setAdapter(allGvLvAdapter4);
        }
    }

    // 进口馆
    private void forCountry(Map<String, String> data) {
        Map<String, String> country = JSONUtils.parseKeyAndValueToMap(data.get("country"));
        Map<String, String> country_ads = JSONUtils.parseKeyAndValueToMap(country.get("ads"));

        Glide.with(getActivity()).load(country_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(country_ads_iv);
        country_href = country_ads.get("href");
        country_desc = country_ads.get("desc");

        if (ToolKit.isList(country, "goodsList")) {
            List<AllGoodsBean> countryList = GsonUtil.getObjectList(country.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter3 = new AllGvLvAdapter(getActivity(), countryList, 3);
            import_gv.setAdapter(allGvLvAdapter3);
        }
    }

    // 无界预购
    private void orPre(Map<String, String> data) {
        Map<String, String> pre_buy = JSONUtils.parseKeyAndValueToMap(data.get("pre_buy"));
        Map<String, String> pre_ads = JSONUtils.parseKeyAndValueToMap(pre_buy.get("ads"));

        Glide.with(getActivity()).load(pre_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(pre_buy_ads_iv);
        pre_href = pre_ads.get("href");
        pre_desc = pre_ads.get("desc");

        if (ToolKit.isList(pre_buy, "goodsList")) {
            List<AllGoodsBean> per = GsonUtil.getObjectList(pre_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter2 = new AllGvLvAdapter(getActivity(), per, 2);
            limit_shopping_gv.setAdapter(allGvLvAdapter2);
        }
    }

    /**
     * 票券区
     *
     * @param data 数据
     */
    private void forTicket(Map<String, String> data) {
        Map<String, String> ticket_buy = JSONUtils.parseKeyAndValueToMap(data.get("ticket_buy"));
        Map<String, String> ticket_ads = JSONUtils.parseKeyAndValueToMap(ticket_buy.get("ads"));

        Glide.with(getActivity()).load(ticket_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(ticket_buy_ads_iv);
        ticket_href = ticket_ads.get("href");
        ticket_desc = ticket_ads.get("desc");

        if (ToolKit.isList(ticket_buy, "goodsList")) {
            List<AllGoodsBean> ticket = GsonUtil.getObjectList(ticket_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), ticket, 1);
            ticket_gv.setAdapter(allGvLvAdapter1);
        }
    }

    /**
     * 限量购
     *
     * @param data 数据
     */
    private void forLimit(Map<String, String> data) {
        // 限量购
        Map<String, String> limit_buy = JSONUtils.parseKeyAndValueToMap(data.get("limit_buy"));
        Map<String, String> limit_ads = JSONUtils.parseKeyAndValueToMap(limit_buy.get("ads"));

        Glide.with(getActivity()).load(limit_ads.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(ads_by_limit_buy_iv);
        limit_href = limit_ads.get("href");
        limit_desc = limit_ads.get("desc");

        if (ToolKit.isList(limit_buy, "goodsList")) {
            List<AllGoodsBean> limit = GsonUtil.getObjectList(limit_buy.get("goodsList"), AllGoodsBean.class);
            allGvLvAdapter = new AllGvLvAdapter(getActivity(), limit, 0);
            purchase_gv.setAdapter(allGvLvAdapter);
        }
    }

    /**
     * 消息
     *
     * @param data 数据
     */
    private void forMes(Map<String, String> data) {
        // 消息数量
        int msg_tip;
        try {
            msg_tip = Integer.parseInt(data.get("msg_tip"));
        } catch (NumberFormatException e) {
            msg_tip = 0;
        }
        msg_tip += (new MainAty()).getUnreadMsgCountTotal();
        // 根据数量设置消息数量气泡是否显示
        if (msg_tip <= 0) {
            message_num_tv.setVisibility(View.GONE);
        } else {
            message_num_tv.setVisibility(View.VISIBLE);
            message_num_tv.setText(String.valueOf(msg_tip));
        }
    }


    /**
     * 横向菜单
     *
     * @param data 数据
     */
    private void forHorizontalMenu(Map<String, String> data) {
        horizontal_classify = JSONUtils.parseKeyAndValueToMapList(data.get("top_nav"));
        horizontalAdapter = new HorizontalAdapter(horizontal_classify, getActivity());
        on_line_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
                if (position <= 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                startActivity(MellOnLineClassifyAty.class, bundle);
            }
        });
    }

    /**
     * 三个活动的广告详情
     *
     * @param data 数据
     */
    private void threeAdsInfo(Map<String, String> data) {
        Map<String, String> three_img = JSONUtils.parseKeyAndValueToMap(data.get("three_img"));
        // 品牌图(左边)
        Map<String, String> brand = JSONUtils.parseKeyAndValueToMap(three_img.get("brand"));
        Glide.with(getActivity()).load(brand.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(three_image_left_iv);
        left_desc = brand.get("desc");
        left_href = brand.get("href");

        // 中国制造(中间)
        Map<String, String> china = JSONUtils.parseKeyAndValueToMap(three_img.get("china"));
        Glide.with(getActivity()).load(china.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(three_image_center_iv);

        cent_desc = china.get("desc");
        cent_href = china.get("href");

        // 科技前沿(右边)
        Map<String, String> science = JSONUtils.parseKeyAndValueToMap(three_img.get("science"));
        Glide.with(getActivity()).load(science.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(three_image_right_iv);
        right_desc = science.get("desc");
        right_href = science.get("href");
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.search_title_layout);
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < updata.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                    R.layout.iten_wj_top_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.top_tv1);
            //初始化布局的控件
            TextView tv2 = moreView.findViewById(R.id.top_tv2);

            /*
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    bundle = new Bundle();
//                    bundle.putString("headlines_id",updata.get(i).get("headlines_id"));
//                    bundle.putInt("from", 1);
//                    startActivity(NoticeDetailsAty.class, bundle);

                }
            });
            //进行对控件赋值
            tv1.setText(updata.get(i).get("title"));

            if (updata.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(updata.get(i + 1).get("title"));
            } else {
                tv2.setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            search_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
            on_line_be_back_top_iv.setVisibility(View.GONE);
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            search_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
            on_line_be_back_top_iv.setVisibility(View.GONE);
        } else {
            search_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
            on_line_be_back_top_iv.setVisibility(View.VISIBLE);
        }
        immersionInit();
    }

}
