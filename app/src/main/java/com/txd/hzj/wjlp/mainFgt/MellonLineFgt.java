package com.txd.hzj.wjlp.mainFgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.AllClassifyAty;
import com.txd.hzj.wjlp.mellOnLine.MellOnLineClassifyAty;
import com.txd.hzj.wjlp.mellOnLine.MessageAty;
import com.txd.hzj.wjlp.mellOnLine.SearchAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.AuctionCollectAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsInputHzjAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitShoppingAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ThemeStreetHzjAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.car.CarChenAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HousChenAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Integer> image;

    /**
     * 横向滑动的分类
     */
    @ViewInject(R.id.on_line_rv)
    private RecyclerView on_line_rv;
    /**
     * 分类列表
     */
    private List<String> horizontal_classify;

    /**
     * 横向滑动的分类适配器
     */
    private HorizontalAdapter horizontalAdapter;

    /**
     * GridView分类
     */
    @ViewInject(R.id.on_lin_classify_gv)
    private GridViewForScrollView on_lin_classify_gv;
    /**
     * GridView数据列表
     */
    private List<String> gv_classify;

    private GVClassifyAdapter gvClassifyAdapter;

    /**
     * 今日头条
     */
    @ViewInject(R.id.upview1)
    private UPMarqueeView upview1;
    /**
     * 误解头条数据
     */
    private List<String> data;
    /**
     * 无界头条View
     */
    private List<View> views;

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

    private List<String> data_for_gv;

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

    public MellonLineFgt() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search_title_layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        title_scan_tv.setVisibility(View.VISIBLE);
        title_classify_tv.setVisibility(View.VISIBLE);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, Settings
                .displayWidth * 2 / 3);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();
        forHorizontalItem();
        on_lin_classify_gv.setAdapter(gvClassifyAdapter);
        on_lin_classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:// 限量购
                        startActivity(LimitShoppingAty.class, null);
                        break;
                    case 1:// 票券区
                        bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putString("title", "票券区");
                        startActivity(TicketZoonAty.class, bundle);
                        break;
                    case 2:// 票券区
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
                        bundle.putString("title", "拼团购");
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
                }
            }
        });

        setView();
        upview1.setViews(views);

        onChangeTitleColor();
        purchase_gv.setAdapter(allGvLvAdapter);
        ticket_gv.setAdapter(allGvLvAdapter1);
        limit_shopping_gv.setAdapter(allGvLvAdapter2);
        import_gv.setAdapter(allGvLvAdapter3);
        auction_gv.setAdapter(allGvLvAdapter4);
        good_luck_gv.setAdapter(allGvLvAdapter5);
        car_gv.setAdapter(allGvLvAdapter6);
        house_gv.setAdapter(allGvLvAdapter7);
        group_shopping_lv.setAdapter(allGvLvAdapter8);
    }


    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setPageCount(image.size());
        online_carvouse_view.setImageListener(imageListener);
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(image.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    @OnClick({R.id.title_classify_tv, R.id.title_search_tv, R.id.message_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_classify_tv:// 分类
                startActivity(AllClassifyAty.class, null);
                break;
            case R.id.title_search_tv:// 搜索
                startActivity(SearchAty.class, null);
                break;
            case R.id.message_layout:// 消息中心
                startActivity(MessageAty.class, null);
                break;
        }
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
        on_line_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                startActivity(MellOnLineClassifyAty.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mellon_line;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        horizontal_classify = new ArrayList<>();
        gv_classify = new ArrayList<>();
        horizontal_classify.add("推荐");
        horizontal_classify.add("食品");
        horizontal_classify.add("生鲜");
        horizontal_classify.add("服饰");
        horizontal_classify.add("家居");
        horizontal_classify.add("进口");
        horizontal_classify.add("美妆");
        horizontal_classify.add("母婴");
        horizontal_classify.add("电子");
        horizontalAdapter = new HorizontalAdapter(horizontal_classify, getActivity());
        gvClassifyAdapter = new GVClassifyAdapter(getActivity(), gv_classify, 0);
        data = new ArrayList<>();
        views = new ArrayList<>();
        data.add("家人给2岁孩子喝这个，孩子智力倒退10岁!!!");
        data.add("iPhone8最感人变化成真，必须买买买买!!!!");
        data.add("简直是白菜价！日本玩家33万甩卖15万张游戏王卡");
        data.add("iPhone7价格曝光了！看完感觉我的腰子有点疼...");
        data.add("主人内疚逃命时没带够，回废墟狂挖30小时！");
        data_for_gv = new ArrayList<>();
        allGvLvAdapter = new AllGvLvAdapter(getActivity(), data, 0);
        allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, 1);
        allGvLvAdapter2 = new AllGvLvAdapter(getActivity(), data, 2);
        allGvLvAdapter3 = new AllGvLvAdapter(getActivity(), data, 3);
        allGvLvAdapter4 = new AllGvLvAdapter(getActivity(), data, 4);
        allGvLvAdapter5 = new AllGvLvAdapter(getActivity(), data, 5);
        allGvLvAdapter6 = new AllGvLvAdapter(getActivity(), data, 6);
        allGvLvAdapter7 = new AllGvLvAdapter(getActivity(), data, 7);
        allGvLvAdapter8 = new AllGvLvAdapter(getActivity(), data, 8);
    }

    @Override
    protected void requestData() {

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
        for (int i = 0; i < data.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);

            /**
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            //进行对控件赋值
            tv1.setText(data.get(i).toString());
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    private int allHeight = 0;

    /**
     * 设置标题栏颜色和滑动的高度
     */
    private void onChangeTitleColor() {
        ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                allHeight = online_carvouse_view.getHeight();
                mell_on_line_sc.setScrollViewListener(MellonLineFgt.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            search_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            search_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
        } else {
            search_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
        }
        immersionInit();
    }

}
