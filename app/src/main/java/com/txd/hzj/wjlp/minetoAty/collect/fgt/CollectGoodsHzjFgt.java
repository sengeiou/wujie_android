package com.txd.hzj.wjlp.minetoAty.collect.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CollectGoodsHzjFgt extends BaseFgt {
    private static final String ARG_PARAM1 = "status";

    private String status;

    @ViewInject(R.id.collect_goods_rv)
    private RecyclerView collect_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<String> data;
    private int height=0;

    @ViewInject(R.id.collect_operation_layout)
    private LinearLayout collect_operation_layout;

    public static CollectGoodsHzjFgt newInstance(String param1) {
        CollectGoodsHzjFgt fragment = new CollectGoodsHzjFgt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString(ARG_PARAM1);
        }
        collect_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        collect_goods_rv.setItemAnimator(new DefaultItemAnimator());
        collect_goods_rv.setHasFixedSize(true);
        collect_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        collect_goods_rv.setAdapter(racycleAllAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_goods_hzj;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
        height = ToolKit.dip2px(getActivity(), 4);
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }

    public void setStatus(String status) {
        this.status = status;
        if(status.equals("完成")){
            collect_operation_layout.setVisibility(View.GONE);
            if(racycleAllAdapter!=null){
                racycleAllAdapter.setShowSelect(false);
            }
        } else {
            collect_operation_layout.setVisibility(View.VISIBLE);
            if(racycleAllAdapter!=null){
                racycleAllAdapter.setShowSelect(true);
            }
        }
        racycleAllAdapter.notifyDataSetChanged();
    }
}
