package com.txd.hzj.wjlp.mainfgt;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
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
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.catchDoll.ui.activity.CatchDollMainActivity;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mainfgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.OnLineMenuGvAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellonLine.MellOnLineClassifyAty;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellonLine.WujieTopHzjAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.AuctionGoodsDetailsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.CarDetailseAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ExplosiveAreaAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ExplosiveAreaGoodsDetialsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodsInputHzjAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ThemeStreetHzjAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea.GiveAwayAreaAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.hous.HousDetailsChenAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.snatch.SnatchGoodsDetailsAty;
import com.txd.hzj.wjlp.minetoaty.setting.EditProfileAty;
import com.txd.hzj.wjlp.view.ImprovePersonalInfoDialog;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.UPMarqueeView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:05
 * 描述：线上商城
 */
public class MellonLineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {

    private boolean localShowAsd = true; // 是否由本地控制活动的显隐

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    @ViewInject(R.id.super_layouts)
    private VpSwipeRefreshLayout superSwipeRefreshLayout;
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


    @ViewInject(R.id.wujie_top_lin_layout)
    private LinearLayout wujie_top_lin_layout;

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
     * 爆款专区
     */
    @ViewInject(R.id.explosiveAreaGv)
    private GridViewForScrollView explosiveAreaGv;
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
    private GridViewForScrollView group_shopping_lv;

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
    private String left_desc = ""; // 左侧图片地址
    private String left_href = ""; // 左侧链接地址
    private String cent_desc = ""; // 中间图片地址
    private String cent_href = ""; // 中间连接地址
    private String right_desc = ""; // 右侧图片地址
    private String right_href = ""; // 右侧连接地址

    /**
     * 广告高度
     */
    private int ads_w = 0;
    private int ads_h = 0;
    /**
     * 限量购广告图
     */
    @ViewInject(R.id.ads_by_limit_buy_iv)
    private ImageView ads_by_limit_buy_iv;
    @ViewInject(R.id.explosiveAreaImg)
    private ImageView explosiveAreaImg;
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
    private List<AllGoodsBean> groupList;
    private List<AllGoodsBean> limit;
    private List<AllGoodsBean> explosiveList;
    private List<AllGoodsBean> ticket;
    private List<AllGoodsBean> per;
    private List<AllGoodsBean> countryList;
    private List<AllGoodsBean> auctionList;
    private List<AllGoodsBean> one_buyList;
    private List<AllGoodsBean> carList;
    private List<AllGoodsBean> houseList;
    /**
     * 系统消息数量
     */
    private int msg_tip = 0;
    private String lat;
    private String lng;

    /**
     * 各个广告模块，初始隐藏
     */
    @ViewInject(R.id.limitBuy_llayout)
    private LinearLayout limitBuy_llayout;
    @ViewInject(R.id.explosiveAreaLayout)
    private LinearLayout explosiveAreaLayout;
    @ViewInject(R.id.groupBuy_llayout)
    private LinearLayout groupBuy_llayout;
    @ViewInject(R.id.ticketBuy_llayout)
    private LinearLayout ticketBuy_llayout;
    @ViewInject(R.id.pre_llayout)
    private LinearLayout pre_llayout;
    @ViewInject(R.id.country_llayout)
    private LinearLayout country_llayout;
    @ViewInject(R.id.auction_llayout)
    private LinearLayout auction_llayout;
    @ViewInject(R.id.oneBuy_llayout)
    private LinearLayout oneBuy_llayout;
    @ViewInject(R.id.car_llayout)
    private LinearLayout car_llayout;
    @ViewInject(R.id.house_llayout)
    private LinearLayout house_llayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search_title_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        title_scan_tv.setVisibility(View.VISIBLE);
        title_classify_tv.setVisibility(View.VISIBLE);
        // 轮播图高度
        allHeight = Settings.displayWidth * 2 / 3;
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
        ads_h = Settings.displayWidth * 300 / 1242;
        ads_w = Settings.displayWidth;
        LinearLayout.LayoutParams adsParam = new LinearLayout.LayoutParams(ads_w, ads_h);
        ads_by_limit_buy_iv.setLayoutParams(adsParam);
        explosiveAreaImg.setLayoutParams(adsParam);
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
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout, under_banner_menu_vp, false);
            //设置adapter  给布局填充控件（限量购及票卷区图标）
            gridView.setAdapter(new OnLineMenuGvAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = i + curIndex * pageSize;
                    switch (pos) {
                        case 0:// 限量购
//                            startActivity(SearchBluetoothAty.class, null); // 跳转到蓝牙连接界面
                            showToast("开发中，敬请期待");
//                                                        startActivity(LimitShoppingAty.class, null);
                            break;
                        case 1:// TODO 票券区 现在为抓娃娃功能入口
//                            showToast("开发中，敬请期待");
                            if (!Config.isLogin()) {
                                startActivity(LoginAty.class, null);
                            } else {
                                startActivity(CatchDollMainActivity.class, null);
                            }
                            //                            bundle = new Bundle();
                            //                            bundle.putInt("type", 1);
                            //                            bundle.putString("title", "票券区");
                            //                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 2:// 拼团购
                            bundle = new Bundle();
                            bundle.putInt("type", 8);
                            bundle.putString("title", "拼单购");
                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 3:// 主题街
                            startActivity(ThemeStreetHzjAty.class, null);
                            break;
                        case 4:// 无界预购
                            //                            bundle = new Bundle();
                            //                            bundle.putInt("type", 2);
                            //                            bundle.putString("title", "无界预购");
                            //                            startActivity(TicketZoonAty.class, bundle);
                            if (!Config.isLogin()) {
                                startActivity(LoginAty.class, null);
                            } else {
                                bundle = new Bundle();
                                bundle.putInt("from", 6);
                                bundle.putString("desc", "集碎片");
                                bundle.putString("href", Config.SHARE_URL + "Splicing/index/");
                                startActivity(NoticeDetailsAty.class, bundle);
                            }
                            break;
                        case 5:// 进口馆
                            bundle = new Bundle();
                            bundle.putInt("type", 3);
                            bundle.putString("title", "进口馆");
                            startActivity(GoodsInputHzjAty.class, bundle);
                            break;
                        case 6:// 爆款专区
//                            showToast("开发中，敬请期待");
                            //                            bundle = new Bundle();
                            //                            bundle.putInt("type", 3);
                            //                            bundle.putString("title", "比价购");
                            //                            startActivity(AuctionCollectAty.class, bundle);
                            if (!Config.isLogin()) {
                                startActivity(LoginAty.class, null);
                            } else {
                                startActivity(ExplosiveAreaAty.class, null);
                            }
                            break;
                        case 7://积分商店
                            bundle = new Bundle();
                            bundle.putInt("type", 10);
                            bundle.putString("title", "积分商店");
                            startActivity(TicketZoonAty.class, bundle);
                            break;
                        case 8://赠品专区  之前是房产购
                            //                            showToast("开发中，敬请期待");
                            if (!Config.isLogin()) {
                                startActivity(LoginAty.class, null);
                            } else {
                                startActivity(GiveAwayAreaAty.class, null);
                            }
                            //                            startActivity(HousChenAty.class, null);
                            break;
                        case 9://紫薇斗数    一元夺宝
//                            showToast("开发中，敬请期待");
                            //                            startActivity(SnatchChenAty.class, null);
                            if (!Config.isLogin()) {
                                startActivity(LoginAty.class, null);
                            } else {
                                bundle = new Bundle();
                                bundle.putBoolean("isShowTitle",true);
                                bundle.putString("title", "紫薇斗数");
                                bundle.putString("url", Config.SHARE_URL + "Divination/Index/index");
                                startActivity(WebViewAty.class, bundle);
                            }
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
                bundle = new Bundle();
                bundle.putString("limit_buy_id", limit.get(i).getLimit_buy_id());
                bundle.putInt("type", 0);
                startActivity(LimitGoodsAty.class, bundle);
            }
        });
        // 爆款专区
        explosiveAreaGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("ticket_buy_id", explosiveList.get(i).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(ExplosiveAreaGoodsDetialsAty.class, bundle);
            }
        });
        // 票券区

        ticket_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("ticket_buy_id", ticket.get(i).getTicket_buy_id());
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });

        // 无界预购
        limit_shopping_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("limit_buy_id", per.get(i).getPre_buy_id());
                bundle.putInt("type", 2);
                startActivity(LimitGoodsAty.class, bundle);
            }
        });

        // 进口馆

        import_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("ticket_buy_id", countryList.get(i).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });
        // 竞拍汇
        auction_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("auction_id", auctionList.get(i).getAuction_id());
                startActivity(AuctionGoodsDetailsAty.class, bundle);
            }
        });
        // 一元夺宝
        good_luck_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("one_buy_id", one_buyList.get(i).getOne_buy_id());
                startActivity(SnatchGoodsDetailsAty.class, bundle);
            }
        });
        // 汽车购
        car_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("car_id", carList.get(i).getCar_id());
                startActivity(CarDetailseAty.class, bundle);
            }
        });
        // 房产购

        house_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("house_id", houseList.get(i).getHouse_id());
                startActivity(HousDetailsChenAty.class, bundle);
            }
        });
        // 拼团区
        group_shopping_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("group_buy_id", groupList.get(i).getGroup_buy_id());
                startActivity(GoodLuckDetailsAty.class, bundle);
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
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(image.get(position).get("merchant_id")) && !image.get(position).get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", image.get(position).get("merchant_id"));
                        if (!image.get(position).get("merchant_id").equals("")) {
                            startActivity(MellInfoAty.class, bundle);
                        }
                    } else if (!TextUtils.isEmpty(image.get(position).get("goods_id")) && !image.get(position).get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", image.get(position).get("goods_id"));
                        bundle.putInt("from", 1);
                        if (!image.get(position).get("goods_id").equals("")) {
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        }
                    } else {
                        forShowAds(image.get(position).get("desc"), image.get(position).get("href"));
                    }
                }
            });
        }
    };

    @Override
    @OnClick({R.id.wujie_top_lin_layout, R.id.on_line_be_back_top_iv,
            R.id.ads_by_limit_buy_iv, R.id.explosiveAreaImg, R.id.ticket_buy_ads_iv,
            R.id.pre_buy_ads_iv, R.id.country_ads_iv,
            R.id.auction_ads_iv, R.id.one_buy_ads_iv,
            R.id.car_ads_iv, R.id.house_ads_iv
    })
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.wujie_top_lin_layout:// 无界头条
                startActivity(WujieTopHzjAty.class, null);
                break;
            case R.id.on_line_be_back_top_iv:
                mell_on_line_sc.smoothScrollTo(0, 0);
                break;
            case R.id.three_image_left_iv://左边
                forShowAds(left_desc, left_href);
                break;
            case R.id.three_image_center_iv://中间
                forShowAds(cent_desc, cent_href);
                break;
            case R.id.three_image_right_iv://右边
                forShowAds(right_desc, right_href);
                break;
            case R.id.ads_by_limit_buy_iv://限量购
                forShowAds(limit_desc, limit_href);
                break;
            case R.id.explosiveAreaImg://爆款专区
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
        if (limit_href.isEmpty() || limit_href.equals("")) {
            return;
        }
        bundle = new Bundle();
        bundle.putString("desc", limit_desc);
        bundle.putString("url", limit_href);
        //        bundle.putInt("from", 2);
        startActivity(WebViewAty.class, bundle);
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
        gv_classify.add("抓娃娃");
        gv_classify.add("拼单购");
        gv_classify.add("主题街");
        gv_classify.add("集碎片");
        gv_classify.add("进口馆");
        gv_classify.add("爆款专区");
        gv_classify.add("积分商店");
        gv_classify.add("赠品专区");
        gv_classify.add("紫薇斗数");

        groupList = new ArrayList<>();
        limit = new ArrayList<>();
        explosiveList = new ArrayList<>();
        ticket = new ArrayList<>();
        per = new ArrayList<>();
        countryList = new ArrayList<>();
        auctionList = new ArrayList<>();
        one_buyList = new ArrayList<>();
        carList = new ArrayList<>();
        houseList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (horizontalAdapter != null) {
            horizontalAdapter.setSelected(0);
            horizontalAdapter.notifyDataSetChanged();
        }
        lat = DemoApplication.getInstance().getLocInfo().get("lat");
        lng = DemoApplication.getInstance().getLocInfo().get("lon");
        indexPst.index(lng, lat);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (null != upview1 && upview1.isFlipping()) {
            upview1.stopFlipping();
            upview1.removeAllViews();
        }
    }

    @Override
    protected void requestData() {
        superSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        superSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        superSwipeRefreshLayout.setTargetScrollWithLayout(true);

        superSwipeRefreshLayout.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                indexPst.index(lng, lat);
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        try {
            removeProgressDialog();
        } catch (IllegalArgumentException e) {
        }
        superSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("index")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String message = jsonObject.getString("message");

                // 设置界面广告显示开关
                Map<String, String> dataASD = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (localShowAsd || dataASD.get("activity_status").equals("1")) { // 如果活动页开启，则显示相应广告
                    under_banner_menu_vp.setVisibility(View.VISIBLE);
//                    limitBuy_llayout.setVisibility(View.VISIBLE);
                    explosiveAreaLayout.setVisibility(View.VISIBLE);
                    groupBuy_llayout.setVisibility(View.VISIBLE);
//                    ticketBuy_llayout.setVisibility(View.VISIBLE);
//                    pre_llayout.setVisibility(View.VISIBLE);
                    country_llayout.setVisibility(View.VISIBLE);
//                    auction_llayout.setVisibility(View.VISIBLE);
//                    oneBuy_llayout.setVisibility(View.VISIBLE);
//                    car_llayout.setVisibility(View.VISIBLE);
//                    house_llayout.setVisibility(View.VISIBLE);
                } else {
                    under_banner_menu_vp.setVisibility(View.GONE);
                    limitBuy_llayout.setVisibility(View.GONE);
                    explosiveAreaLayout.setVisibility(View.GONE);
                    groupBuy_llayout.setVisibility(View.GONE);
                    ticketBuy_llayout.setVisibility(View.GONE);
                    pre_llayout.setVisibility(View.GONE);
                    country_llayout.setVisibility(View.GONE);
                    auction_llayout.setVisibility(View.GONE);
                    oneBuy_llayout.setVisibility(View.GONE);
                    car_llayout.setVisibility(View.GONE);
                    house_llayout.setVisibility(View.GONE);
                }

                // 获取回传的Data值
                JSONObject jsonData = jsonObject.getJSONObject("data");
                // 获取个人所在地址填写状态 0是未填写 1是已填写
                if (!Config.getToken().equals("") && jsonData.getString("city_status").equals("0")) {

                    String return_ticket = jsonData.has("return_ticket") ? jsonData.getString("return_ticket") : "100";
                    String return_ticket_desc = jsonData.has("return_ticket_desc") ? jsonData.getString("return_ticket_desc") : "完善个人资料就可以获得100代金券！";
                    String ticket_first_desc = jsonData.has("ticket_first_desc") ? jsonData.getString("ticket_first_desc") : "无界商城可通用";
                    String ticket_second_desc = jsonData.has("ticket_second_desc") ? jsonData.getString("ticket_second_desc") : "全场通用，可在[我的]中进行查看";

                    new ImprovePersonalInfoDialog.Builder(getActivity())
                            .setReturnTicketStr(return_ticket_desc)
                            .setNumberStr(return_ticket)
                            .setText1Str(ticket_first_desc)
                            .setText2Str(ticket_second_desc)
                            .setOnGoImproveBtnClickListener("立即完善", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Config.getToken().equals("")) {
                                        Intent intent = new Intent();
                                        intent.setClass(getActivity(), LoginAty.class);
                                        getActivity().startActivity(intent);
                                    } else {
                                        Intent intent = new Intent();
                                        intent.setClass(getActivity(), EditProfileAty.class);
                                        getActivity().startActivity(intent);
                                    }
                                }
                            }).create().show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                updata.clear();
                views.clear();
                updata = JSONUtils.parseKeyAndValueToMapList(data.get("headlines"));
                setView();
                upview1.setViews(views);
            }
            // 三个活动图片
            threeAdsInfo(data);
            // 限量购
            forLimit(data);
            // 爆款专区
            forExplosiveArea(data);
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

        superSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    // 拼团购
    private void forGroup(Map<String, String> data) {
        Map<String, String> group_buy = JSONUtils.parseKeyAndValueToMap(data.get("group_buy"));
        final Map<String, String> group_buy_ads = JSONUtils.parseKeyAndValueToMap(group_buy.get("ads"));

        if (ToolKit.isList(group_buy, "goodsList")) {
            groupList = GsonUtil.getObjectList(group_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter8 = new AllGvLvAdapter(getActivity(), groupList, 8);
            group_shopping_lv.setAdapter(allGvLvAdapter8);
        }
        if (group_buy_ads != null) {
            Glide.with(getActivity()).load(group_buy_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(group_buy_ads_iv);
            group_buy_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(group_buy_ads.get("merchant_id")) && !group_buy_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", group_buy_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(group_buy_ads.get("goods_id")) && !group_buy_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", group_buy_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(group_desc, group_href);
                    }

                }
            });
            group_href = group_buy_ads.get("href");
            group_desc = group_buy_ads.get("desc");
        }
    }

    // 房产购
    private void forHouse(Map<String, String> data) {
        Map<String, String> house_buy = JSONUtils.parseKeyAndValueToMap(data.get("house"));
        final Map<String, String> house_ads = JSONUtils.parseKeyAndValueToMap(house_buy.get("ads"));
        if (house_ads != null) {
            Glide.with(getActivity()).load(house_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(house_ads_iv);
            house_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(house_ads.get("merchant_id")) && !house_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", house_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(house_ads.get("goods_id")) && !house_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", house_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(house_desc, house_href);

                    }
                }
            });
            house_href = house_ads.get("href");
            house_desc = house_ads.get("desc");
        }
        if (ToolKit.isList(house_buy, "goodsList")) {
            houseList = GsonUtil.getObjectList(house_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter7 = new AllGvLvAdapter(getActivity(), houseList, 7);
            house_gv.setAdapter(allGvLvAdapter7);
        }
    }

    private void forCar(Map<String, String> data) {
        Map<String, String> car_buy = JSONUtils.parseKeyAndValueToMap(data.get("car"));
        final Map<String, String> car_ads = JSONUtils.parseKeyAndValueToMap(car_buy.get("ads"));
        if (car_ads != null) {
            Glide.with(getActivity()).load(car_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(car_ads_iv);
            car_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(car_ads.get("merchant_id")) && !car_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", car_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(car_ads.get("goods_id")) && !car_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", car_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(car_desc, car_href);
                    }
                }
            });
            car_href = car_ads.get("href");
            car_desc = car_ads.get("desc");
        }
        if (ToolKit.isList(car_buy, "goodsList")) {
            carList = GsonUtil.getObjectList(car_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter6 = new AllGvLvAdapter(getActivity(), carList, 6);
            car_gv.setAdapter(allGvLvAdapter6);
        }
    }

    // 积分夺宝
    private void forOneBuy(Map<String, String> data) {
        Map<String, String> one_buy = JSONUtils.parseKeyAndValueToMap(data.get("one_buy"));
        final Map<String, String> one_buy_ads = JSONUtils.parseKeyAndValueToMap(one_buy.get("ads"));

        if (ToolKit.isList(one_buy, "goodsList")) {
            one_buyList = GsonUtil.getObjectList(one_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter5 = new AllGvLvAdapter(getActivity(), one_buyList, 5);
            good_luck_gv.setAdapter(allGvLvAdapter5);
        }
        if (one_buy_ads != null) {
            Glide.with(getActivity()).load(one_buy_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(one_buy_ads_iv);
            one_buy_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(one_buy_ads.get("merchant_id")) && !one_buy_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", one_buy_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(one_buy_ads.get("goods_id")) && !one_buy_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", one_buy_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(one_buy_desc, one_buy_href);
                    }
                }
            });
            one_buy_href = one_buy_ads.get("href");
            one_buy_desc = one_buy_ads.get("desc");
        }
    }

    // 竞拍汇
    private void forAuction(Map<String, String> data) {
        Map<String, String> auction = JSONUtils.parseKeyAndValueToMap(data.get("auction"));
        final Map<String, String> auction_ads = JSONUtils.parseKeyAndValueToMap(auction.get("ads"));

        if (ToolKit.isList(auction, "goodsList")) {
            auctionList = GsonUtil.getObjectList(auction.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter4 = new AllGvLvAdapter(getActivity(), auctionList, 4);
            auction_gv.setAdapter(allGvLvAdapter4);
        }
        if (auction_ads != null) {
            Glide.with(getActivity()).load(auction_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(auction_ads_iv);
            auction_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(auction_ads.get("merchant_id")) && !auction_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", auction_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(auction_ads.get("goods_id")) && !auction_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", auction_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(auction_desc, auction_href);
                    }
                }
            });
            auction_href = auction_ads.get("href");
            auction_desc = auction_ads.get("desc");
        }
    }

    // 进口馆
    private void forCountry(Map<String, String> data) {
        ObserTool.gainInstance().dealData(data.get("country"), new ObserTool.Listener() {
            @Override
            public void returneData(Map<String, String> map) {
                Map<String, String> country = map;
                final Map<String, String> country_ads = JSONUtils.parseKeyAndValueToMap(country.get("ads"));

                if (ToolKit.isList(country, "goodsList")) {
                    countryList = GsonUtil.getObjectList(country.get("goodsList"), AllGoodsBean.class);
                    AllGvLvAdapter allGvLvAdapter3 = new AllGvLvAdapter(getActivity(), countryList, 3);
                    import_gv.setAdapter(allGvLvAdapter3);
                }
                if (country_ads != null) {
                    Glide.with(getActivity()).load(country_ads.get("picture"))
                            .override(ads_w, ads_h)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.mipmap.icon_200)
                            .placeholder(R.mipmap.icon_200)
                            .centerCrop()
                            .into(country_ads_iv);
                    country_ads_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(country_ads.get("merchant_id")) && !country_ads.get("merchant_id").equals("0")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("mell_id", country_ads.get("merchant_id"));
                                startActivity(MellInfoAty.class, bundle);
                            } else if (!TextUtils.isEmpty(country_ads.get("goods_id")) && !country_ads.get("goods_id").equals("0")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("ticket_buy_id", country_ads.get("goods_id"));
                                bundle.putInt("from", 1);
                                startActivity(TicketGoodsDetialsAty.class, bundle);
                            } else {
                                forShowAds(country_desc, country_href);
                            }
                        }
                    });

                    country_href = country_ads.get("href");
                    country_desc = country_ads.get("desc");
                }
            }
        });
    }

    // 无界预购
    private void orPre(Map<String, String> data) {
        Map<String, String> pre_buy = JSONUtils.parseKeyAndValueToMap(data.get("pre_buy"));
        final Map<String, String> pre_ads = JSONUtils.parseKeyAndValueToMap(pre_buy.get("ads"));
        if (ToolKit.isList(pre_buy, "goodsList")) {
            per = GsonUtil.getObjectList(pre_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter2 = new AllGvLvAdapter(getActivity(), per, 2);
            limit_shopping_gv.setAdapter(allGvLvAdapter2);
        }
        if (pre_ads != null) {
            Glide.with(getActivity()).load(pre_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(pre_buy_ads_iv);
            pre_buy_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(pre_ads.get("merchant_id")) && !pre_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", pre_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(pre_ads.get("goods_id")) && !pre_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", pre_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(pre_desc, pre_href);
                    }
                }
            });
            pre_href = pre_ads.get("href");
            pre_desc = pre_ads.get("desc");
        }
    }

    /**
     * 票券区
     *
     * @param data 数据
     */
    private void forTicket(Map<String, String> data) {
        Map<String, String> ticket_buy = JSONUtils.parseKeyAndValueToMap(data.get("ticket_buy"));
        if (ToolKit.isList(ticket_buy, "goodsList")) {
            ticket = GsonUtil.getObjectList(ticket_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), ticket, 1);
            ticket_gv.setAdapter(allGvLvAdapter1);
        }
        final Map<String, String> ticket_ads = JSONUtils.parseKeyAndValueToMap(ticket_buy.get("ads"));
        if (ticket_ads != null) {
            Glide.with(getActivity()).load(ticket_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(ticket_buy_ads_iv);
            ticket_buy_ads_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(ticket_ads.get("merchant_id")) && !ticket_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", ticket_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(ticket_ads.get("goods_id")) && !ticket_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", ticket_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(ticket_desc, ticket_href);
                    }
                }
            });
            ticket_href = ticket_ads.get("href");
            ticket_desc = ticket_ads.get("desc");
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
        final Map<String, String> limit_ads = JSONUtils.parseKeyAndValueToMap(limit_buy.get("ads"));
        if (ToolKit.isList(limit_buy, "goodsList")) {
            limit = GsonUtil.getObjectList(limit_buy.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter = new AllGvLvAdapter(getActivity(), limit, 0);
            purchase_gv.setAdapter(allGvLvAdapter);
        }
        if (limit_ads != null) {
            Glide.with(getActivity()).load(limit_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(ads_by_limit_buy_iv);
            ads_by_limit_buy_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(limit_ads.get("merchant_id")) && !limit_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", limit_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(limit_ads.get("goods_id")) && !limit_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", limit_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(limit_desc, limit_href);
                    }

                }
            });
            limit_href = limit_ads.get("href");
            limit_desc = limit_ads.get("desc");
        }
    }

    /**
     * 爆款专区
     *
     * @param data 数据
     */
    private void forExplosiveArea(Map<String, String> data) {
        Map<String, String> hot_goods = JSONUtils.parseKeyAndValueToMap(data.get("hot_goods"));
        final Map<String, String> limit_ads = JSONUtils.parseKeyAndValueToMap(hot_goods.get("ads"));
        if (ToolKit.isList(hot_goods, "goodsList")) {
            explosiveList = GsonUtil.getObjectList(hot_goods.get("goodsList"), AllGoodsBean.class);
            AllGvLvAdapter allGvLvAdapter = new AllGvLvAdapter(getActivity(), explosiveList, 9);
            explosiveAreaGv.setAdapter(allGvLvAdapter);
        }
        if (limit_ads != null) {
            Glide.with(getActivity()).load(limit_ads.get("picture"))
                    .override(ads_w, ads_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(explosiveAreaImg);
            explosiveAreaImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(limit_ads.get("merchant_id")) && !limit_ads.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", limit_ads.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(limit_ads.get("goods_id")) && !limit_ads.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", limit_ads.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(ExplosiveAreaGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(limit_desc, limit_href);
                    }

                }
            });
            limit_href = limit_ads.get("href");
            limit_desc = limit_ads.get("desc");
        }
    }

    /**
     * 消息
     *
     * @param data 数据
     */
    private void forMes(Map<String, String> data) {
        // 消息数量
        try {
            msg_tip = Integer.parseInt(data.get("msg_tip"));
        } catch (NumberFormatException e) {
            msg_tip = 0;
        }

        int hxNum = (new MainAty()).getUnreadMsgCountTotal();

        showOrHindNum(hxNum);
    }

    public void showOrHindNum(int hxNum) {
        int all = msg_tip + hxNum;
        // 根据数量设置消息数量气泡是否显示
        if (all <= 0) {
            message_num_tv.setVisibility(View.GONE);
        } else {
            message_num_tv.setVisibility(View.VISIBLE);
            message_num_tv.setText(String.valueOf(all));
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
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("cate_id", horizontal_classify.get(position).get("cate_id"));
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
        final Map<String, String> brand = JSONUtils.parseKeyAndValueToMap(three_img.get("brand"));
        if (brand != null) {
            Glide.with(getActivity()).load(brand.get("picture"))
                    .override(img_w, img_h)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.icon_200)
                    .placeholder(R.mipmap.icon_200)
                    .centerCrop()
                    .into(three_image_left_iv);
            left_desc = brand.get("desc");
            left_href = brand.get("href");
            three_image_left_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(brand.get("merchant_id")) && !brand.get("merchant_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", brand.get("merchant_id"));
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(brand.get("goods_id")) && !brand.get("goods_id").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", brand.get("goods_id"));
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        forShowAds(left_desc, left_href);
                    }

                }
            });

        }
        // 中国制造(中间)
        final Map<String, String> china = JSONUtils.parseKeyAndValueToMap(three_img.get("china"));
        Glide.with(getActivity()).load(china.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200)
                .centerCrop()
                .into(three_image_center_iv);
        // TODO 暂时将点击事件取消
        three_image_center_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(china.get("merchant_id")) && !china.get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", china.get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty(china.get("goods_id")) && !china.get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", china.get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    forShowAds(cent_desc, cent_href);
                }

            }
        });
        cent_desc = china.get("desc");
        cent_href = china.get("href");

        // 科技前沿(右边)
        final Map<String, String> science = JSONUtils.parseKeyAndValueToMap(three_img.get("science"));
        Glide.with(getActivity()).load(science.get("picture"))
                .override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200)
                .centerCrop()
                .into(three_image_right_iv);
        // TODO 暂时将点击事件取消
        three_image_right_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(science.get("merchant_id")) && !science.get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", science.get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty(science.get("goods_id")) && !science.get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", science.get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    forShowAds(right_desc, right_href);
                }

            }
        });
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
        if (updata.size() % 2 == 0) {
            for (int i = 0; i < updata.size(); i = i + 2) {
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.iten_wj_top_view, null);
                //初始化布局的控件
                TextView tv1 = moreView.findViewById(R.id.top_tv1);
                //进行对控件赋值
                tv1.setText(updata.get(i).get("title"));
                tv1.append("\n" + updata.get(i + 1).get("title"));

                //添加到循环滚动数组里面去
                views.add(moreView);
            }
        } else {
            for (int i = 0; i < updata.size(); i = i + 2) {
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.iten_wj_top_view, null);
                //初始化布局的控件
                TextView tv1 = moreView.findViewById(R.id.top_tv1);
                //进行对控件赋值
                if (i != updata.size() - 1) {
                    tv1.setText(updata.get(i).get("title"));
                    tv1.append("\n" + updata.get(i + 1).get("title"));
                } else {
                    tv1.setText(updata.get(i).get("title"));
                }

                //添加到循环滚动数组里面去
                views.add(moreView);
            }
        }

    }

    /**
     * x&y 分别为x轴与y轴变化后的位置距离
     */
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            search_title_layout.setBackgroundColor(Color.TRANSPARENT);// AGB由相关工具获得，或者美工提供
            on_line_be_back_top_iv.setVisibility(View.GONE);
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            search_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
            on_line_be_back_top_iv.setVisibility(View.GONE);
        } else {
            search_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
            //显示回到顶部的图标
            on_line_be_back_top_iv.setVisibility(View.VISIBLE);
        }
        immersionInit();
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

}
