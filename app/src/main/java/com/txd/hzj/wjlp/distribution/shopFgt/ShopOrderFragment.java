package com.txd.hzj.wjlp.distribution.shopFgt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/12 0012 9:26
 * 功能描述：此类为分销订单管理fragment
 * 联系方式：无
 */
public class ShopOrderFragment extends BaseFgt {


    private View view;
    private TextView text;
    private String string;

    public static ShopOrderFragment newInstance(String string) {
        ShopOrderFragment fragment = new ShopOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("String", string);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.shop_order_frg;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
