package com.txd.hzj.wjlp.distribution.shopFgt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopPersonAdapter;

import java.util.ArrayList;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/13 0013 15:51
 * 功能描述：顾客管理店主页面
 * 联系方式：无
 */
public class ShopPersonShopFreagment extends BaseFgt {


    private ShopPersonAdapter adapter;
    private ArrayList list;
    @ViewInject(R.id.shop_person_relist)
    private RecyclerView reList;

    public static ShopPersonShopFreagment newInstance(int prage) {
        ShopPersonShopFreagment freagment = new ShopPersonShopFreagment();
        Bundle bundle = new Bundle();
        bundle.putInt("prage", prage);
        freagment.setArguments(bundle);
        return freagment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_person_fragment;
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
        reList.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        adapter = new ShopPersonAdapter(list, getActivity());
        reList.setAdapter(adapter);
    }
}
