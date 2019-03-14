package com.txd.hzj.wjlp.minetoaty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoaty.collect.fgt.CollectBooksFgt;
import com.txd.hzj.wjlp.minetoaty.collect.fgt.CollectGoodsHzjFgt;
import com.txd.hzj.wjlp.minetoaty.collect.fgt.CollectMellHzjFgt;
import com.txd.hzj.wjlp.minetoaty.collect.fgt.CollectOffLineshopFgt;

import java.util.ArrayList;

public class FootprintAty extends BaseAty {

    /**
     * 编辑,完成
     */
    @ViewInject(R.id.foot_right_tv)
    private TextView foot_right_tv;
    /**
     * 商品
     */
    @ViewInject(R.id.foot_print_goods_tv)
    private TextView foot_print_goods_tv;
    @ViewInject(R.id.foot_print_goods_view)
    private View foot_print_goods_view;
    /**
     * 商家
     */
    @ViewInject(R.id.foot_print_mell_tv)
    private TextView foot_print_mell_tv;
    @ViewInject(R.id.foot_print_mell_view)
    private View foot_print_mell_view;
    /**
     * 书院
     */
    @ViewInject(R.id.foot_print_books_tv)
    private TextView foot_print_books_tv;
    @ViewInject(R.id.foot_print_books_view)
    private View foot_print_books_view;

    /**
     * 线下店铺
     */
    @ViewInject(R.id.foot_offline_tv)
    private TextView foot_offline_tv;
    @ViewInject(R.id.foot_offline_view)
    private View foot_offline_view;

    private FragmentChangeManager fcm;

    private ArrayList<Fragment> mFragment;

    public int selected = 0; // TODO 在Fragment中获取该界面的变量，并设置刷新
    public int index = 0; // TODO 在Fragment中获取该界面的变量，并设置刷新

    public Fragment f; // TODO 在Fragment中获取该界面的变量，并设置刷新
    public boolean status = false; // TODO 在Fragment中获取该界面的变量，并设置刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.foot_print_title_layout);
        fcm = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.foot_print_layout, mFragment);
        setTvAndViewStyle(index);
    }

    @Override
    @OnClick({R.id.title_left_layout, R.id.title_right_layout, R.id.title_right_right_layout,
            R.id.foot_right_tv,R.id.title_right_offline_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.foot_right_tv:// 编辑，完成
                f = mFragment.get(selected);
                String tv = foot_right_tv.getText().toString();
                if (tv.equals("编辑")) {
                    foot_right_tv.setText("完成");
                    status = true;
                } else {
                    foot_right_tv.setText("编辑");
                    status = false;
                }
                setNewStatus(f);
                break;
            case R.id.title_left_layout:// 商品
                index = 0;
                setTvAndViewStyle(index);
                setNewStatus(mFragment.get(selected));
                foot_right_tv.setVisibility(View.GONE);
                break;
            case R.id.title_right_layout:// 商家
                index = 1;
                setTvAndViewStyle(index);
                setNewStatus(mFragment.get(selected));
                foot_right_tv.setVisibility(View.GONE);
                break;
            case R.id.title_right_right_layout:// 书院
                index = 2;
                setTvAndViewStyle(index);
                setNewStatus(mFragment.get(selected));
                foot_right_tv.setVisibility(View.GONE);
                break;
            case R.id.title_right_offline_layout: //线下店铺
                index = 3;
                setTvAndViewStyle(index);
                setNewStatus(mFragment.get(selected));
                foot_right_tv.setVisibility(View.GONE);
                break;
        }
    }

    public void setNewStatus(Fragment f) {
        if (0 == selected) {// 商品
            ((CollectGoodsHzjFgt) f).setStatus(status);
            ((CollectGoodsHzjFgt) f).r();
        } else if (1 == selected) {// 商家
            ((CollectMellHzjFgt) f).setStatus(status);
            ((CollectMellHzjFgt) f).r();
        } else if (2 == selected){ // 应该是书院
            ((CollectBooksFgt) f).setStatus(status);
            ((CollectBooksFgt) f).r();
        }else if (3 == selected){ // 线下店铺
            ((CollectOffLineshopFgt) f).setStatus(status);
            ((CollectOffLineshopFgt) f).r();
        }
    }

    private void setTvAndViewStyle(int position) {
        selected = position;
        foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_books_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_offline_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_offline_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == position) {
            foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if ((1 == position)) {
            foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if (2==position){
            foot_print_books_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if(3==position){
            foot_offline_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_offline_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        if (position < 4) {
            fcm.setFragments(position);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_footprint_hzj;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(CollectGoodsHzjFgt.newInstance(false, 0)); // 商品足迹
        mFragment.add(CollectMellHzjFgt.newInstance(false, 0)); // 商家足迹
        mFragment.add(CollectBooksFgt.newInstance(false, 0)); // 书店足迹
        mFragment.add(CollectOffLineshopFgt.newInstance(false, 0)); // 线下足迹
    }

    @Override
    protected void requestData() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("sftv");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("sftv") && index == intent.getIntExtra("index", -1)) {
                foot_right_tv.setVisibility(View.VISIBLE);
            }
        }

    };

    public void setView(int v) {
        foot_right_tv.setVisibility(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

}
