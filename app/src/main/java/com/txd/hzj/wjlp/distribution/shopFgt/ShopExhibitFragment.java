package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

public class ShopExhibitFragment extends BaseFgt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //积分
    @ViewInject(R.id.internal_tv)
    TextView internal_tv;
    //代金券
    @ViewInject(R.id.cash_coupon_tv)
    TextView cash_coupon_tv;
    //销量
    @ViewInject(R.id.sales_volume_tv)
    TextView sales_volume_tv;
    //价格
    @ViewInject(R.id.price_tv)
    TextView price_tv;

    private String redColor="#ffe71f19";
    private String blgColor="#ff333333";
    private Drawable selectId;
    private Drawable unSelectId;


    public static ShopExhibitFragment newInstance(String param1, String param2) {
        ShopExhibitFragment fragment = new ShopExhibitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shop_exhibit;
    }

    @Override
    protected void initialized() {
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0,0,selectId.getMinimumWidth(),selectId.getMinimumHeight());
        unSelectId.setBounds(0,0,unSelectId.getMinimumWidth(),unSelectId.getMinimumHeight());
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }

    @Override
    @OnClick({R.id.internal_tv,R.id.cash_coupon_tv,R.id.sales_volume_tv,R.id.price_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.internal_tv:
                internal_tv.setTextColor(Color.parseColor(redColor));
                internal_tv.setCompoundDrawables(null,null,selectId,null);
                cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
                cash_coupon_tv.setCompoundDrawables(null,null,unSelectId,null);
                sales_volume_tv.setTextColor(Color.parseColor(blgColor));
                sales_volume_tv.setCompoundDrawables(null,null,unSelectId,null);
                price_tv.setTextColor(Color.parseColor(blgColor));
                price_tv.setCompoundDrawables(null,null,unSelectId,null);
                break;
            case R.id.cash_coupon_tv:
                internal_tv.setTextColor(Color.parseColor(blgColor));
                internal_tv.setCompoundDrawables(null,null,unSelectId,null);
                cash_coupon_tv.setTextColor(Color.parseColor(redColor));
                cash_coupon_tv.setCompoundDrawables(null,null,selectId,null);
                sales_volume_tv.setTextColor(Color.parseColor(blgColor));
                sales_volume_tv.setCompoundDrawables(null,null,unSelectId,null);
                price_tv.setTextColor(Color.parseColor(blgColor));
                price_tv.setCompoundDrawables(null,null,unSelectId,null);
                break;
            case R.id.sales_volume_tv:
                internal_tv.setTextColor(Color.parseColor(blgColor));
                internal_tv.setCompoundDrawables(null,null,unSelectId,null);
                cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
                cash_coupon_tv.setCompoundDrawables(null,null,unSelectId,null);
                sales_volume_tv.setTextColor(Color.parseColor(redColor));
                sales_volume_tv.setCompoundDrawables(null,null,selectId,null);
                price_tv.setTextColor(Color.parseColor(blgColor));
                price_tv.setCompoundDrawables(null,null,unSelectId,null);
                break;
            case R.id.price_tv:
                internal_tv.setTextColor(Color.parseColor(blgColor));
                internal_tv.setCompoundDrawables(null,null,unSelectId,null);
                cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
                cash_coupon_tv.setCompoundDrawables(null,null,unSelectId,null);
                sales_volume_tv.setTextColor(Color.parseColor(blgColor));
                sales_volume_tv.setCompoundDrawables(null,null,unSelectId,null);
                price_tv.setTextColor(Color.parseColor(redColor));
                price_tv.setCompoundDrawables(null,null,selectId,null);
                break;
        }
    }

}
