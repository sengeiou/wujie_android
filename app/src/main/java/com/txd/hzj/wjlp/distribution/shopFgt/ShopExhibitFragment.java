package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 创建者：Zyf
 * 功能描述：小店上货fragment
 * 联系方式：无
 */
public class ShopExhibitFragment extends BaseFgt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //积分
    TextView internal_tv;
    //代金券
    TextView cash_coupon_tv;
    //销量
    TextView sales_volume_tv;
    //价格
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
    protected void getView(View view) {
        super.getView(view);
        internal_tv=view.findViewById(R.id.internal_tv);
        cash_coupon_tv=view.findViewById(R.id.cash_coupon_tv);
        sales_volume_tv=view.findViewById(R.id.sales_volume_tv);
        price_tv=view.findViewById(R.id.price_tv);
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
                setChioceItem(0);
                break;
            case R.id.cash_coupon_tv:
                setChioceItem(1);
                break;
            case R.id.sales_volume_tv:
                setChioceItem(2);
                break;
            case R.id.price_tv:
                setChioceItem(3);
                break;
        }
    }

    private void setChioceItem(int index) {
        clearChioce();
        switch (index){
            case 0:
                internal_tv.setTextColor(Color.parseColor(redColor));
                internal_tv.setCompoundDrawables(null,null,selectId,null);
                break;
            case 1:
                cash_coupon_tv.setTextColor(Color.parseColor(redColor));
                cash_coupon_tv.setCompoundDrawables(null,null,selectId,null);
                break;
            case 2:
                sales_volume_tv.setTextColor(Color.parseColor(redColor));
                sales_volume_tv.setCompoundDrawables(null,null,selectId,null);
                break;
            case 3:
                price_tv.setTextColor(Color.parseColor(redColor));
                price_tv.setCompoundDrawables(null,null,selectId,null);
                break;
        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        internal_tv.setCompoundDrawables(null,null,unSelectId,null);
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setCompoundDrawables(null,null,unSelectId,null);
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setCompoundDrawables(null,null,unSelectId,null);
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null,null,unSelectId,null);
    }

}