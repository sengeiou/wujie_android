package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.auction.AuctionList;
import com.txd.hzj.wjlp.mellOnLine.adapter.LimitAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;

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
public class LimitFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    private int type;

    /**
     * 倒计时
     */
    @ViewInject(R.id.limit_count_down_view)
    private CountdownView limit_count_down_view;
    @ViewInject(R.id.limit_gv)
    private GridViewForScrollView limit_gv;

    private LimitAdapter limiAdapter;
    private List<AuctionList> list;

    /**
     * 可以监听滚动距离的ScrollView
     */
    @ViewInject(R.id.fgt_limit_sc)
    private ObservableScrollView fgt_limit_sc;

    @ViewInject(R.id.top_ad_iv)
    private ImageView top_ad_iv;
    /**
     * 回到顶部
     */
    @ViewInject(R.id.to_be_back_iv)
    private ImageView to_be_back_iv;
    private int height = 0;

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
        height = Settings.displayWidth / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, height);
        top_ad_iv.setLayoutParams(params);
        fgt_limit_sc.setScrollViewListener(this);
        fgt_limit_sc.smoothScrollTo(0, 0);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            try {
                fgt_limit_sc.smoothScrollTo(0,0);
            } catch (NullPointerException e){

            }
        }
    }

    @Override
    @OnClick({R.id.to_be_back_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_be_back_iv:
                fgt_limit_sc.smoothScrollTo(0, 0);
                to_be_back_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_limit;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        limiAdapter = new LimitAdapter(list, getActivity(), type, 0);

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < height) {
            to_be_back_iv.setVisibility(View.GONE);
        } else {
            to_be_back_iv.setVisibility(View.VISIBLE);
        }
    }
}
