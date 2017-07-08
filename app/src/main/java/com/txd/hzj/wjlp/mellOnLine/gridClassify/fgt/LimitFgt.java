package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.LimitAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:33
 * 描述：抢购碎片
 * ===============Txunda===============
 */
public class LimitFgt extends BaseFgt {
    private int type;

    /**
     * 倒计时
     */
    @ViewInject(R.id.limit_count_down_view)
    private CountdownView limit_count_down_view;
    @ViewInject(R.id.limit_gv)
    private GridViewForScrollView limit_gv;

    private LimitAdapter limiAdapter;
    private List<String> list;

    public LimitFgt() {
    }

    public static LimitFgt getFgt(int type) {
        LimitFgt limitFgt = new LimitFgt();
        limitFgt.type = type;
        return limitFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        limit_count_down_view.setTag("limit");
        limit_count_down_view.start(5 * 60 * 60 * 1000);
        limit_gv.setAdapter(limiAdapter);
        limit_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(LimitGoodsAty.class, null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_limit;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        limiAdapter = new LimitAdapter(list, getActivity(), type);

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
