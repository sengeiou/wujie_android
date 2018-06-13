package com.txd.hzj.wjlp.distribution.shopAty;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopPersonAdapter;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopPersonFreagment;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopPersonShopFreagment;
import com.txd.hzj.wjlp.view.MyShopTitleView;

import java.util.ArrayList;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 14:36
 * 功能描述：顾客管理页面
 * 联系方式：无
 */
public class ShopPersonManage extends BaseAty implements View.OnClickListener {

    private MyShopTitleView titleView;

    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_person_manage;
    }

    @Override
    protected void initialized() {

        findViewById(R.id.titlt_conter_tv).setVisibility(View.GONE);
        titleView = findViewById(R.id.mytitle_tv);
        titleView.setVisibility(View.VISIBLE);
        titleView.setLeftName("店主身份");
        titleView.setRightName("普通顾客");
        titleView.setleftListener(new MyShopTitleView.LeftContent() {
            @Override
            public void getLeft(String string) {
                fragmentChangeManager.setFragments(Integer.valueOf(string));
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    @Override
    protected void requestData() {
        fragments = new ArrayList();
        fragments.add(0, new ShopPersonShopFreagment());
        fragments.add(1, new ShopPersonFreagment());


        fragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.shop_person_frm, fragments);

    }
}
