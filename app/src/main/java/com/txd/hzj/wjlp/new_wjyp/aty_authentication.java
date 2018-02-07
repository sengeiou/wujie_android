package com.txd.hzj.wjlp.new_wjyp;

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
 * 什么坑爹的认证
 *   2017/11/25.
 */

public class aty_authentication extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;
    @ViewInject(R.id.switchmultibutton)
    private SwitchMultiButton switchmultibutton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("认证");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_authentication;
    }

    @Override
    protected void initialized() {
        fragments = new ArrayList<>();
        fragment1 f1 = new fragment1();
        fragment2 f2 = new fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("auth_status", getIntent().getStringExtra("auth_status"));
        bundle.putString("comp_auth_status", getIntent().getStringExtra("comp_auth_status"));
        f1.setArguments(bundle);
        f2.setArguments(bundle);
        fragments.add(f1);
        fragments.add(f2);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.feamelayout,
                fragments);
        fragmentChangeManager.setFragments(0);
    }


    @Override
    protected void requestData() {
        switchmultibutton.setText("个人", "企业").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (tabText) {
                    case "个人":
                        fragmentChangeManager.setFragments(0);
                        break;
                    case "企业":
                        fragmentChangeManager.setFragments(1);
                        break;
                }
            }
        });
    }
}

