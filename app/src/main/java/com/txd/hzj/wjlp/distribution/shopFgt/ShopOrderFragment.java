package com.txd.hzj.wjlp.distribution.shopFgt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopOrderManageAdapter;

import java.util.ArrayList;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/12 0012 9:26
 * 功能描述：此类为分销订单管理fragment
 * 联系方式：无
 */
public class ShopOrderFragment extends BaseFgt {


    @ViewInject(R.id.shop_order_re_list)
    private RecyclerView shop_order_re_list;
    private ShopOrderManageAdapter adapter;
    private ArrayList list;

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
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        shop_order_re_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShopOrderManageAdapter(list, getActivity());
        shop_order_re_list.setAdapter(adapter);
    }
}
