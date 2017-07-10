package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mellOnLine.GoodLuckDetailsAty;
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
public class TicketZoonFgt extends BaseFgt {
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
                }
            }
        });
        ticket_zoon_goods_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(GoodLuckDetailsAty.class, null);
            }
        });
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
}
