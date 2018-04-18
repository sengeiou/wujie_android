package com.txd.hzj.wjlp.minetoAty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CollectBooks;
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectBooksFgt;
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectGoodsHzjFgt;
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectMellHzjFgt;

import java.util.ArrayList;

import static com.txd.hzj.wjlp.R.id.left_goods_tv;
import static com.txd.hzj.wjlp.R.id.middle_mell_tv;
import static com.txd.hzj.wjlp.R.id.right_books_tv;

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
     * 商家
     */
    @ViewInject(R.id.foot_print_books_tv)
    private TextView foot_print_books_tv;
    @ViewInject(R.id.foot_print_books_view)
    private View foot_print_books_view;

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
            R.id.foot_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
        }
    }

    public void setNewStatus(Fragment f) {
        if (0 == selected) {// 商品
            ((CollectGoodsHzjFgt) f).setStatus(status);
            ((CollectGoodsHzjFgt) f).r();
        } else if (1 == selected) {// 商家
            ((CollectMellHzjFgt) f).setStatus(status);
            ((CollectMellHzjFgt) f).r();
        } else { // 应该是书院
            ((CollectBooksFgt) f).setStatus(status);
            ((CollectBooksFgt) f).r();
        }
    }

    private void setTvAndViewStyle(int position) {
        selected = position;
        foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_books_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == position) {
            foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if ((1 == position)) {
            foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            foot_print_books_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        if (position < 3)
            fcm.setFragments(position);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_footprint_hzj;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(CollectGoodsHzjFgt.newInstance(false, 0));
        mFragment.add(CollectMellHzjFgt.newInstance(false, 0));
        mFragment.add(CollectBooksFgt.newInstance(false, 0));
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
