package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.DetilsTypeChenAdapter;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页_户型(10-2房产购)
 * ===============Txunda===============
 */

public class HousDetailsTypeChenFgt extends BaseFgt{

    @ViewInject(R.id.rv_details_type)//RecyclerView
    private RecyclerView rv_details_type;



    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hous_details_type_chen;
    }

    @Override
    protected void initialized() {


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

        GridLayoutManager manager=new GridLayoutManager(getActivity(),2);
        rv_details_type.setLayoutManager(manager);
        DetilsTypeChenAdapter adapter=new DetilsTypeChenAdapter(getActivity());
        rv_details_type.setAdapter(adapter);

    }
}
