package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.DetilsTypeChenAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页_户型(10-2房产购)
 * ===============Txunda===============
 */

public class HousDetailsTypeChenFgt extends BaseFgt {

    @ViewInject(R.id.rv_details_type)//RecyclerView
    private RecyclerView rv_details_type;

    @ViewInject(R.id.hx_be_back_top_iv)
    private ImageView hx_be_back_top_iv;
    private int height = 0;

    @Override
    protected void immersionInit() {

    }

    @Override
    @OnClick({R.id.hx_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hx_be_back_top_iv:
                rv_details_type.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hous_details_type_chen;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);

    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();//RecyclerView初始化
    }

    /**
     * RecyclerView初始化
     */
    private void initRecyclerView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv_details_type.setLayoutManager(manager);
        rv_details_type.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        DetilsTypeChenAdapter adapter = new DetilsTypeChenAdapter(getActivity());
        rv_details_type.setAdapter(adapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    hx_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    hx_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });

    }
}
