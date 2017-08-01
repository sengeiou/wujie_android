package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.OnLineMenuGvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.WjMellAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.AuctionCollectAty;
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
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.SnatchChenAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import static com.txd.hzj.wjlp.R.id.under_banner_menu_vp;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 9:42
 * 描述：票券区碎片
 * ===============Txunda===============
 */
public class TicketZoonFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
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
    private List<String> gv_classify;

    private String title = "";
    private int type = 0;
    private AllGvLvAdapter allGvLvAdapter1;

    private WjMellAdapter wjMellAdapter;

    private List<String> data;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.zoom_be_back_top_iv)
    private ImageView zoom_be_back_top_iv;

    @ViewInject(R.id.zooom_sc)
    private ObservableScrollView zooom_sc;

    @ViewInject(R.id.goods_menu_vp)
    private ViewPager goods_menu_vp;
    private int pageSize = 10;
    private ArrayList<View> mPagerList;
    private int curIndex = 0;

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
        } else {
            allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
        }

        if (8 == type) {
            ticket_zoon_goods_lv.setVisibility(View.VISIBLE);
            ticket_zoon_goods_gv.setVisibility(View.GONE);
            ticket_zoon_goods_lv.setAdapter(allGvLvAdapter1);
        } else {
            ticket_zoon_goods_lv.setVisibility(View.GONE);
            ticket_zoon_goods_gv.setVisibility(View.VISIBLE);
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
                startActivity(GoodLuckDetailsAty.class, null);
            }
        });
        zooom_sc.smoothScrollTo(0, 0);
        zooom_sc.setScrollViewListener(this);
        forMenu();
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
        gv_classify = new ArrayList<>();
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        gv_classify.add("巧克力");
        data = new ArrayList<>();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
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
            gridView.setAdapter(new OnLineMenuGvAdapter(getActivity(), gv_classify, i));
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
