package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.adapter.PastAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：下午 2:37
 * 描述：往期揭晓
 */
public class PastFgt extends BaseFgt {

    @ViewInject(R.id.past_rv)
    private RecyclerView past_rv;

    private PastAdapter pastAdapter;

    private String outTime_log = "";

    private List<Map<String, String>> outTime;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public static PastFgt getFgt(String outTime_log) {
        PastFgt pastFgt = new PastFgt();
        pastFgt.outTime_log = outTime_log;
        return pastFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        past_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        outTime = JSONUtils.parseKeyAndValueToMapList(outTime_log);
        if (!ListUtils.isEmpty(outTime)) {
            no_data_layout.setVisibility(View.GONE);
            past_rv.setVisibility(View.VISIBLE);
            pastAdapter = new PastAdapter(getActivity(), outTime);
            past_rv.setAdapter(pastAdapter);
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
            past_rv.setVisibility(View.GONE);
        }


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_past;
    }

    @Override
    protected void initialized() {
        outTime = new ArrayList<>();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
