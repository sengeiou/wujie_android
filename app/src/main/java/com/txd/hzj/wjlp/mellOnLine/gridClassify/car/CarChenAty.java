package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.mm.opensdk.utils.Log;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.CarBean;
import com.txd.hzj.wjlp.http.carbuy.CarBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;
import com.txd.hzj.wjlp.view.ATDragView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 11:06
 * 描述：汽车购(9-1汽车购)
 * ===============Txunda===============
 */
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

    private CarBuyPst carBuyPst;
    private List<String> price;

    private String min_price = "0.0001";
    private String max_price = "1000";

    private List<Integer> brandIds;
    private List<Integer> styleIds;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.ie_car);
        /*
         * 价格区间
         */
        mAtDragView.setData(price, new ATDragView.OnDragFinishedListener() {
            @Override
            public void dragFinished(int leftPostion, int rightPostion) {
                if (leftPostion < 0) {
                    leftPostion = 0;
                }
                if (rightPostion >= price.size()) {
                    rightPostion = price.size() - 1;
                }

                if (leftPostion >= price.size()) {
                    return;
                } else {
                    min_price = price.get(leftPostion);
                }
                if (min_price.equals("0")) {
                    min_price = "0.0001";
                }
                max_price = price.get(rightPostion);
                tv_price.setText(price.get(leftPostion) + "-" + price.get(rightPostion) + "万");
            }
        });
        // RecycleView设置布局样式
        initRecyclerView();
    }

    @OnClick({R.id.tv_car_screen})
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转到汽车购商品页
            case R.id.tv_car_screen:
                brandIds = new ArrayList<>();
                styleIds = new ArrayList<>();
                for (CarBean style : carBeens) {
                    if (style.isSelecet()) {
                        styleIds.add(Integer.parseInt(style.getStyle_id()));
                    }
                }
                for (CarBean brand : carBeens2) {
                    if (brand.isSelecet()) {
                        brandIds.add(Integer.parseInt(brand.getBrand_id()));
                    }
                }

                Gson gson = new Gson();
                String brand_id = gson.toJson(brandIds).replace("[", "").replace("]", "");
                String style_id = gson.toJson(styleIds).replace("[", "").replace("]", "");
                Bundle bundle = new Bundle();
                bundle.putString("min_price", min_price);
                bundle.putString("max_price", max_price);
                bundle.putString("style_id", style_id);
                bundle.putString("brand_id", brand_id);
                startActivity(CarCommodityChenAty.class, bundle);
                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_chen;
    }

    @Override
    protected void initialized() {
        carBuyPst = new CarBuyPst(this);
        titlt_conter_tv.setText("汽车购");
        carBeens = new ArrayList<>();
        carBeens2 = new ArrayList<>();
        price = new ArrayList<>();
        price.add("0");
        price.add("10");
        price.add("20");
        price.add("40");
        price.add("80");
        price.add("150");
        price.add("300");
        price.add("600");
        price.add("1000");
    }

    /**
     * RecyclerView
     */
    private void initRecyclerView() {
        /*
         * 车型选择
         */
        GridLayoutManager typeManager = new GridLayoutManager(this, 4);
        mRvCarType.setLayoutManager(typeManager);
        /*
         * 品牌选择
         */
        GridLayoutManager barndManager = new GridLayoutManager(this, 4);
        rvCarBrand.setLayoutManager(barndManager);
    }


    @Override
    protected void requestData() {
        carBuyPst.carSelect();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("carSelect")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (ToolKit.isList(data, "style_list")) {
                carBeens = GsonUtil.getObjectList(data.get("style_list"), CarBean.class);
                CarTypeChenAdapter typeAdapter = new CarTypeChenAdapter(this, 1, carBeens);
                mRvCarType.setAdapter(typeAdapter);
            }
            if (ToolKit.isList(data, "brand_list")) {
                carBeens2 = GsonUtil.getObjectList(data.get("brand_list"), CarBean.class);
                CarTypeChenAdapter gridAdapter = new CarTypeChenAdapter(this, 2, carBeens2);
                rvCarBrand.setAdapter(gridAdapter);
            }
        }
    }
}
