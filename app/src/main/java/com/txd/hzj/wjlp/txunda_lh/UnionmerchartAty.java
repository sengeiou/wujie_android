package com.txd.hzj.wjlp.txunda_lh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    @ViewInject(R.id.layout)
    private LinearLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("联盟商家推荐");
    }

    private String type;
    private String str;
    private Map<String, String> data;
    private Map<String, String> map;
    private Bundle bundle;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_unionmerchant;
    }

    @Override
    protected void initialized() {
        fragments = new ArrayList<>();
        f1 = new UnionMerchartFgt();
        f2 = new WujiePostFgt();
        type = getIntent().getStringExtra("type");
        if (type.equals("2")) {
            str = getIntent().getStringExtra("data");
            data = mapStringToMap(str);
            bundle = new Bundle();
            bundle.putString("type", type);
//            bundle.putString("data", str);
            map = new HashMap<String, String>();
            for (String str : data.keySet()) {
                map.put(str.trim(), data.get(str));
            }
            bundle.putString("id", map.get("recommending_id"));
            if (map.get("type").equals("1")) {
                f1.setArguments(bundle);
            } else {
                f2.setArguments(bundle);
            }
        }
        fragments.add(f1);
        fragments.add(f2);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.feamelayout,
                fragments);
        if (type.equals("1")) {
            fragmentChangeManager.setFragments(0);
        } else {
            if (map.get("type").equals("1")) {
                fragmentChangeManager.setFragments(0);
            } else {
                fragmentChangeManager.setFragments(1);
            }
        }
    }

    public Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            map.put(key.trim(), value);
        }
        return map;
    }

    @Override
    protected void requestData() {
        switchmultibutton.setText("联盟商家", "无界驿店");
        if (type.equals("1")) {
            switchmultibutton.setEnabled(true);
            switchmultibutton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
                @Override
                public void onSwitch(int position, String tabText) {
                    switch (tabText) {
                        case "联盟商家":
                            fragmentChangeManager.setFragments(0);
                            break;
                        case "无界驿店":
                            fragmentChangeManager.setFragments(1);
                            break;
                    }
                }

            });
        } else {
            layout.setVisibility(View.GONE);
        }


    }
}
