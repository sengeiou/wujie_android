package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.opensdk.utils.Log;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;
import com.txd.hzj.wjlp.view.ATDragView;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 11:06
 * 描述：汽车购(9-1汽车购)
 * ===============Txunda===============
 */
//ShapedImageView

public class CarChenAty extends BaseAty{


    @ViewInject(R.id.at_dragView)//价格筛选
    private ATDragView mAtDragView;


    @ViewInject(R.id.rv_car_type)//车型
    private RecyclerView mRvCarType;

    @ViewInject(R.id.rv_car_brand)//品牌选择
    private  RecyclerView rvCarBrand;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_chen;
    }

    @Override
    protected void initialized() {
        initRecyclerView();//RecyclerView
        
        initAtDrag();//选择价格


    }

    private void initAtDrag() {

    }

    /**
     * RecyclerView
     */
    private void initRecyclerView() {

        /**
         * 车型选择
         */
        GridLayoutManager typeManager=new GridLayoutManager(this,4);
//        typeManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvCarType.setLayoutManager(typeManager);
        CarTypeChenAdapter typeAdapter=new CarTypeChenAdapter(this,1);
        mRvCarType.setAdapter(typeAdapter);

        /**
         * 品牌选择
         */
        GridLayoutManager barndManager=new GridLayoutManager(this,4);
        rvCarBrand.setLayoutManager(barndManager);
        CarTypeChenAdapter gridAdapter=new CarTypeChenAdapter(this,2);
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
        data.add("15");
        data.add("20");
        data.add("25");
        data.add("40");
        data.add("60");
        data.add("80");
        data.add("150");
        mAtDragView.setData(data, new ATDragView.OnDragFinishedListener() {
            @Override
            public void dragFinished(int leftPostion, int rightPostion) {
                Log.e("==========返回数据==========","回调数据Left-->" + leftPostion + "--Right-->" + rightPostion);
            }
        });

    }
}
