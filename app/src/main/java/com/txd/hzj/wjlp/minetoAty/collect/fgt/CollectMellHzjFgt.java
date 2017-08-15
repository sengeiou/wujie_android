package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 9:58
 * 描述：收藏，商家
 * ===============Txunda===============
 */
public class CollectMellHzjFgt extends BaseFgt {
    private String status;

    @ViewInject(R.id.collect_mell_lv)
    private ListView collect_mell_lv;

    private MellListAdapter mellListAdapter;

    private List<String> mells;

    public static CollectMellHzjFgt newInstance(String param1) {
        CollectMellHzjFgt fragment = new CollectMellHzjFgt();
        fragment.status = param1;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collect_mell_lv.setAdapter(mellListAdapter);
        setStatus(status);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_mell_hzj;
    }

    @Override
    protected void initialized() {
        mells = new ArrayList<>();
        mellListAdapter = new MellListAdapter(getActivity(), mells);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void immersionInit() {

    }

    public void setStatus(String status) {
        this.status = status;
    }
}
