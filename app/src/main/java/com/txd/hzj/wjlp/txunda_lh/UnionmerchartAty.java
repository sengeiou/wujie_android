package com.txd.hzj.wjlp.txunda_lh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;

/**
 * by Txunda_LH on 2018/1/22.
 */

public class UnionmerchartAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;
    @ViewInject(R.id.switchmultibutton)
    private SwitchMultiButton switchmultibutton;
    UnionMerchartFgt f1;
    WujiePostFgt f2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("申请填写");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_unionmerchant;
    }

    @Override
    protected void initialized() {
        fragments = new ArrayList<>();
        f1 = new UnionMerchartFgt();
        f2 = new WujiePostFgt();
        fragments.add(f1);
        fragments.add(f2);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.feamelayout,
                fragments);
        fragmentChangeManager.setFragments(0);
    }

    @Override
    protected void requestData() {
        switchmultibutton.setText("联盟商家", "无界驿站").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (tabText) {
                    case "联盟商家":
                        fragmentChangeManager.setFragments(0);
                        break;
                    case "无界驿站":
                        fragmentChangeManager.setFragments(1);
                        break;
                }
            }
        });
    }
}
