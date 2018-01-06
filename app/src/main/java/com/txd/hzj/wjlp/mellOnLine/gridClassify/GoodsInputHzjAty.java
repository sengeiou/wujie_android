package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.DukeScrollView;
import com.ants.theantsgo.view.PullToRefreshLayout;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.GlobalAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：上午 11:19
 * 描述：进口馆
 * ===============Txunda===============
 */
public class GoodsInputHzjAty extends BaseAty implements DukeScrollView.ScrollViewListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 进口馆商品列表
     */
    @ViewInject(R.id.global_goods_gv)
    private GridViewForScrollView global_goods_gv;

    private AllGvLvAdapter allGvAdapter;

    private List<AllGoodsBean> list;
    private List<Map<String, String>> country;
    private int type = 3;

    @ViewInject(R.id.input_be_back_top_iv)
    private ImageView input_be_back_top_iv;

    @ViewInject(R.id.input_sc)
    private DukeScrollView input_sc;
    /**
     * 上拉加载
     * 下拉刷新
     */
    @ViewInject(R.id.ptr_input_layout)
    private PullToRefreshLayout ptr_input_layout;
    /**
     * 进口馆获取数据
     */
    private CountryPst countryPst;

    private int p = 1;

    @ViewInject(R.id.input_ads_iv)
    private ImageView input_ads_iv;
    private int ads_size2 = 0;
    private int ads_size1 = 0;
    /**
     * 广告链接
     */
    private String href = "";
    /**
     * 说明
     */
    private String desc = "";
    /**
     * 全球馆ViewPager
     */
    @ViewInject(R.id.goods_menu_vp)
    private ViewPager goods_menu_vp;
    /**
     * 每页10个
     */
    private int pageSize = 10;
    /**
     * 当前页面
     */
    private int curIndex = 0;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public GoodsInputHzjAty() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("进口馆");
        // 广告图宽高
        ads_size1 = Settings.displayWidth;
        ads_size2 = Settings.displayWidth*400 /1242;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ads_size1, ads_size2);
        input_ads_iv.setLayoutParams(params);

        global_goods_gv.setEmptyView(no_data_layout);

        input_sc.smoothScrollTo(0, 0);
        input_sc.setScrollViewListener(this);
        ptr_input_layout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                countryPst.countryIndex(p);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                p++;
                countryPst.countryIndex(p);
            }
        });

        global_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", list.get(i).getGoods_id());
                bundle.putInt("from",1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });

    }

    @Override
    @OnClick({R.id.input_ads_iv, R.id.input_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.input_ads_iv:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                bundle.putString("desc", desc);
                bundle.putString("href", href);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
            case R.id.input_be_back_top_iv:
                input_sc.smoothScrollTo(0, 0);
                input_be_back_top_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_input_hzj;
    }

    @Override
    protected void initialized() {
        countryPst = new CountryPst(this);
        list = new ArrayList<>();
        country = new ArrayList<>();
        type = getIntent().getIntExtra("type", 3);
    }

    @Override
    protected void requestData() {
        countryPst.countryIndex(p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (ToolKit.isList(map, "data")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (1 == p) {
                Map<String, String> ads = JSONUtils.parseKeyAndValueToMap(data.get("ads"));
                Glide.with(this).load(ads.get("picture"))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .into(input_ads_iv);
                href = ads.get("href");
                desc = ads.get("desc");
                // 全球馆
                if (ToolKit.isList(data, "country_list")) {
                    country = JSONUtils.parseKeyAndValueToMapList(data.get("country_list"));
                    LinearLayout.LayoutParams params;
                    if (country.size() > 5) {
                        params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                ToolKit.dip2px(this, 160));
                    } else {
                        params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                ToolKit.dip2px(this, 80));
                    }
                    goods_menu_vp.setLayoutParams(params);
                    forMenu();
                }
                // 商品数据
                if (ToolKit.isList(data, "goods_list")) {
                    list = GsonUtil.getObjectList(data.get("goods_list"), AllGoodsBean.class);
                    allGvAdapter = new AllGvLvAdapter(this, list, type);
                    global_goods_gv.setAdapter(allGvAdapter);
                }
                ptr_input_layout.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {
                if (ToolKit.isList(data, "goods_list")) {
                    list.addAll(GsonUtil.getObjectList(data.get("goods_list"), AllGoodsBean.class));
                    allGvAdapter.notifyDataSetChanged();
                }
                ptr_input_layout.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            }
        } else {
            if (1 == p) {
                ptr_input_layout.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {
                ptr_input_layout.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            }
        }

    }

    @Override
    public void onScrollChanged(DukeScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth / 2) {
            input_be_back_top_iv.setVisibility(View.GONE);
        } else {
            input_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private void forMenu() {
        // 获取总页数
        int pageCount = (int) Math.ceil(country.size() * 1.0 / pageSize);
        // 初始化View列表
        /*View列表*/
        ArrayList<View> mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout,
                    goods_menu_vp, false);
            gridView.setAdapter(new GlobalAdapter(this, country, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int itemPos = i + curIndex * pageSize;
                    Bundle bundle = new Bundle();
                    bundle.putString("title", country.get(itemPos).get("country_name"));
                    bundle.putString("country_id", country.get(itemPos).get("country_id"));
                    bundle.putInt("type", type);
                    startActivity(TicketZoonAty.class, bundle);

                }
            });
            // 给ViewPager设置适配器
            goods_menu_vp.setAdapter(new ViewPagerAdapter(mPagerList));
            // 添加页面改变监听事件
            goods_menu_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
