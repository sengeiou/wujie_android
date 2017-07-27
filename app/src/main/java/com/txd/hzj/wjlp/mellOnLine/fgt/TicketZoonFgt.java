package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.InputGoodsDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;

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
public class TicketZoonFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 分类
     */
    @ViewInject(R.id.ticket_zoon_gv)
    private GridViewForScrollView ticket_zoon_gv;
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

    private GVClassifyAdapter gvClassifyAdapter;
    /**
     * GridView数据列表
     */
    private List<String> gv_classify;

    private String title = "";
    private int type = 0;
    private AllGvLvAdapter allGvLvAdapter1;
    private List<String> data;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.zoom_be_back_top_iv)
    private ImageView zoom_be_back_top_iv;

    @ViewInject(R.id.zooom_sc)
    private ObservableScrollView zooom_sc;

    public static TicketZoonFgt getFgt(String title, int type) {
        TicketZoonFgt tzf = new TicketZoonFgt();
        tzf.title = title;
        tzf.type = type;
        return tzf;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ticket_zoon_gv.setAdapter(gvClassifyAdapter);
        if (8 == type) {
            ticket_zoon_goods_lv.setVisibility(View.VISIBLE);
            ticket_zoon_goods_gv.setVisibility(View.GONE);
            ticket_zoon_goods_lv.setAdapter(allGvLvAdapter1);
        } else {
            ticket_zoon_goods_lv.setVisibility(View.GONE);
            ticket_zoon_goods_gv.setVisibility(View.VISIBLE);
            ticket_zoon_goods_gv.setAdapter(allGvLvAdapter1);
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
        data = new ArrayList<>();
        gvClassifyAdapter = new GVClassifyAdapter(getActivity(), gv_classify, 1);
        allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
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
}
