package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.mm.opensdk.utils.Log;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.CarBean;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;
import com.txd.hzj.wjlp.view.ATDragView;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 11:06
 * 描述：汽车购(9-1汽车购)
 * ===============Txunda===============
 */
//ShapedImageView

public class CarChenAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.at_dragView)//价格筛选
    private ATDragView mAtDragView;
    @ViewInject(R.id.rv_car_type)//车型
    private RecyclerView mRvCarType;
    @ViewInject(R.id.rv_car_brand)//品牌选择
    private RecyclerView rvCarBrand;

    private List<CarBean> carBeens;
    private List<CarBean> carBeens2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.ie_car);
        initRecyclerView();//RecyclerView
    }

    @OnClick({R.id.tv_car_screen})
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转到汽车购商品页
            case R.id.tv_car_screen:
                startActivity(CarCommodityChenAty.class, null);
                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_chen;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("汽车购");
        initAtDrag();//选择价格

        carBeens = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            carBeens.add(new CarBean(false, R.drawable.icon_car_type_mvp_b, R.drawable.icon_car_type_mvp, "MVP"));
        }
        carBeens2 = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            carBeens2.add(new CarBean(false, R.drawable.icon_car_type_mvp_b, R.drawable.icon_car_type_mvp, "宝马"));
        }

    }

    private void initAtDrag() {

    }

    /**
     * RecyclerView
     */
    private void initRecyclerView() {

        /*
         * 车型选择
         */
        GridLayoutManager typeManager = new GridLayoutManager(this, 5);
        mRvCarType.setLayoutManager(typeManager);
        CarTypeChenAdapter typeAdapter = new CarTypeChenAdapter(this, 1, carBeens);
        mRvCarType.setAdapter(typeAdapter);

        /*
         * 品牌选择
         */
        GridLayoutManager barndManager = new GridLayoutManager(this, 4);
        rvCarBrand.setLayoutManager(barndManager);
        CarTypeChenAdapter gridAdapter = new CarTypeChenAdapter(this, 2, carBeens2);
        rvCarBrand.setAdapter(gridAdapter);
    }

    /**
     * 价格筛选
     */
    @Override
    protected void requestData() {
        List<String> data = new ArrayList<>();
        data.add("0");
        data.add("10");
        data.add("20");
        data.add("40");
        data.add("80");
        data.add("150");
        data.add("300");
        data.add("600");
        data.add("1000");
        mAtDragView.setData(data, new ATDragView.OnDragFinishedListener() {
            @Override
            public void dragFinished(int leftPostion, int rightPostion) {
                Log.e("==========返回数据==========", "回调数据Left-->" + leftPostion + "--Right-->" + rightPostion);
            }
        });

    }
}
