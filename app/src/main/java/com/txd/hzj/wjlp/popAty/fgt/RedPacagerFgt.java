package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.popAty.GetRedPackageAty;
import com.txd.hzj.wjlp.popAty.adapter.RedPackageAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 4:07
 * 描述：领红包
 * ===============Txunda===============
 */
public class RedPacagerFgt extends BaseFgt {

    @ViewInject(R.id.red_package_lv)
    private ListView red_package_lv;

    private RedPackageAdapter redPackageAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        red_package_lv.setAdapter(redPackageAdapter);
        red_package_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(GetRedPackageAty.class, null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_red_pacager;
    }

    @Override
    protected void initialized() {
        redPackageAdapter = new RedPackageAdapter(getActivity(),1);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
