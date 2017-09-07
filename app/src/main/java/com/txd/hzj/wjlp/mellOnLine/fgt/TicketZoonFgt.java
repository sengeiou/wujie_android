package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.DukeScrollView;
import com.ants.theantsgo.view.PullToRefreshLayout;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.bean.GroupBuyBean;
import com.txd.hzj.wjlp.bean.TwoCateListBean;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.TicketZoonAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.WjMellAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.InputGoodsDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 9:42
 * 描述：票券区碎片
 * ===============Txunda===============
 */
public class TicketZoonFgt extends BaseFgt implements DukeScrollView.ScrollViewListener {
    /**
     * 商品
     */
    @ViewInject(R.id.ticket_zoon_goods_gv)
    private GridViewForScrollView ticket_zoon_goods_gv;
    /**
     * 商品
     */
    @ViewInject(R.id.ticket_zoon_goods_lv)
    private ListViewForScrollView ticket_zoon_goods_lv;

    /**
     * GridView数据列表
     */
    private List<TwoCateListBean> gv_classify;

    private String title = "";
    private int type = 0;
    private AllGvLvAdapter allGvLvAdapter1;

    private WjMellAdapter wjMellAdapter;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.zoom_be_back_top_iv)
    private ImageView zoom_be_back_top_iv;

    @ViewInject(R.id.zooom_sc)
    private DukeScrollView zooom_sc;

    @ViewInject(R.id.goods_menu_vp)
    private ViewPager goods_menu_vp;
    private int pageSize = 10;
    private ArrayList<View> mPagerList;
    private int curIndex = 0;

    private GroupBuyPst groupBuyPst;

    private int p = 1;

    @ViewInject(R.id.refresh_view)
    private PullToRefreshLayout refresh_view;

    private boolean isLoding = true;
    private int numall = 0;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.group_ad_pic_iv)
    private ImageView group_ad_pic_iv;

    private LinearLayout.LayoutParams params;

    public static TicketZoonFgt getFgt(String title, int type) {
        TicketZoonFgt tzf = new TicketZoonFgt();
        tzf.title = title;
        tzf.type = type;
        return tzf;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (10 == type) {
            wjMellAdapter = new WjMellAdapter(getActivity());
        } else if (type != 8) {
            allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
        }

        if (8 == type) {
            ticket_zoon_goods_lv.setVisibility(View.VISIBLE);
            ticket_zoon_goods_gv.setVisibility(View.GONE);
            ticket_zoon_goods_lv.setEmptyView(no_data_layout);
        } else {
            ticket_zoon_goods_lv.setVisibility(View.GONE);
            ticket_zoon_goods_gv.setVisibility(View.VISIBLE);
            ticket_zoon_goods_gv.setEmptyView(no_data_layout);
            if (10 == type) {
                ticket_zoon_goods_gv.setAdapter(wjMellAdapter);
            } else {
                ticket_zoon_goods_gv.setAdapter(allGvLvAdapter1);
            }
        }
        ticket_zoon_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (type) {
                    case 1:// 票券区
                        startActivity(TicketGoodsDetialsAty.class, null);
                        break;
                    case 2:// 预购
                        startActivity(LimitGoodsAty.class, null);
                    case 3:// 进口馆
                        startActivity(InputGoodsDetailsAty.class, null);
                        break;
                }
            }
        });
        ticket_zoon_goods_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("group_buy_id", data.get(i).getGroup_buy_id());
                startActivity(GoodLuckDetailsAty.class, bundle);
            }
        });
        zooom_sc.smoothScrollTo(0, 0);
        zooom_sc.setScrollViewListener(this);

        forUpdata();
    }

    /**
     * 更新数据
     */
    private void forUpdata() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                forData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (numall <= data.size()) {
                    refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
                    return;
                }
                // 加载操作
                p++;
                forData();
            }
        });
    }

    @Override
    @OnClick({R.id.zoom_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.zoom_be_back_top_iv:
                zoom_be_back_top_iv.setVisibility(View.GONE);
                zooom_sc.smoothScrollTo(0, 0);
                break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_ticket_zoon;
    }

    @Override
    protected void initialized() {
        groupBuyPst = new GroupBuyPst(this);
        gv_classify = new ArrayList<>();
        data = new ArrayList<>();
        data2 = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        forData();
    }

    private void forData() {
        switch (type) {
            case 8:
                groupBuyPst.groupBuyIndex(p, title);
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("groupBuyIndex")) {
            GroupBuyBean groupBuyBean = GsonUtil.GsonToBean(jsonStr, GroupBuyBean.class);
            numall = groupBuyBean.getNums();
            if (1 == p) {
                if (isLoding) {
                    gv_classify = groupBuyBean.getData().getTwo_cate_list();
                    if (!ListUtils.isEmpty(gv_classify)) {
                        if (gv_classify.size() > 5) {
                            params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 160));
                        } else {
                            params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 80));
                        }
                        goods_menu_vp.setLayoutParams(params);
                        forMenu();
                    }
                }

                data = groupBuyBean.getData().getGroup_buy_list();

                if (!ListUtils.isEmpty(data)) {
                    allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
                    ticket_zoon_goods_lv.setAdapter(allGvLvAdapter1);
                }

                GroupBuyBean.Data.AdsBean adsBean = groupBuyBean.getData().getAds();
                if (adsBean != null) {
                    Glide.with(getActivity()).load(adsBean.getPicture())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .centerCrop()
                            .override(Settings.displayWidth, Settings.displayWidth / 2)
                            .error(R.drawable.ic_default)
                            .placeholder(R.drawable.ic_default)
                            .into(group_ad_pic_iv);
                }

                refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {
                data2 = groupBuyBean.getData().getGroup_buy_list();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    allGvLvAdapter1.notifyDataSetChanged();
                }
                refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            }
        }

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onScrollChanged(DukeScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth / 2) {
            zoom_be_back_top_iv.setVisibility(View.GONE);
        } else {
            zoom_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private void forMenu() {
        // 获取总页数
        int pageCount = (int) Math.ceil(gv_classify.size() * 1.0 / pageSize);
        // 初始化View列表
        mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout,
                    goods_menu_vp, false);
            gridView.setAdapter(new TicketZoonAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int itemPos = i + curIndex * pageSize;
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
