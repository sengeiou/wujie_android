package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarCommodityChenAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 17.27
 * 描述：汽车购(9-2汽车购)
 * ===============Txunda===============
 */
public class CarCommodityChenAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.rv_car_commodity)//RecyclerView
    private RecyclerView rv_car_commodity;
    private int height = 0;

    @ViewInject(R.id.car_be_back_top_iv)
    private ImageView car_be_back_top_iv;
    private GridLayoutManager barndManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_commodity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
        barndManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    car_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    car_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });

    }

    @Override
    @OnClick({R.id.car_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.car_be_back_top_iv:
                car_be_back_top_iv.setVisibility(View.GONE);
                rv_car_commodity.scrollToPosition(0);
                break;
        }
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("汽车购");
        height = ToolKit.dip2px(this, 4);
        initRecycler();//RecyclerView初始化
    }


    @Override
    protected void requestData() {

    }

    /**
     * RecyclerView初始化
     */
    private void initRecycler() {
        barndManager = new GridLayoutManager(this, 2);
        rv_car_commodity.setLayoutManager(barndManager);
        rv_car_commodity.setHasFixedSize(true);
        rv_car_commodity.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        CarCommodityChenAdapter gridAdapter = new CarCommodityChenAdapter(this);
        rv_car_commodity.setAdapter(gridAdapter);
    }
}
