package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarCommodityChenAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 17.27
 * 描述：汽车购(9-2汽车购)
 * ===============Txunda===============
 */
public class CarCommodityChenAty extends BaseAty{
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.rv_car_commodity)//RecyclerView
    private RecyclerView rv_car_commodity;
    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_commodity;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("汽车购");
        initRecycler();//RecyclerView初始化
    }



    @Override
    protected void requestData() {

    }

    /**
     * RecyclerView初始化
     */
    private void initRecycler() {
        GridLayoutManager barndManager=new GridLayoutManager(this,2);
        rv_car_commodity.setLayoutManager(barndManager);
        CarCommodityChenAdapter gridAdapter=new CarCommodityChenAdapter(this);
        rv_car_commodity.setAdapter(gridAdapter);

    }
}
