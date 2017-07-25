package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.adapter.PastAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：下午 2:37
 * 描述：往期揭晓
 * ===============Txunda===============
 */
public class PastFgt extends BaseFgt {

    @ViewInject(R.id.past_rv)
    private RecyclerView past_rv;

    private PastAdapter pastAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        past_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        past_rv.setAdapter(pastAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_past;
    }

    @Override
    protected void initialized() {
        pastAdapter = new PastAdapter(getActivity());
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
