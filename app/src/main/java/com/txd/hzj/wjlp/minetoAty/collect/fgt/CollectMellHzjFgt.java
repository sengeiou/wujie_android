package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CollectOrFootpointMell;
import com.txd.hzj.wjlp.bean.MellInfoList;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 9:58
 * 描述：收藏、足迹，商家
 * ===============Txunda===============
 */
public class CollectMellHzjFgt extends BaseFgt {
    private boolean status;
    /**
     * 数据类型
     * 0.足迹
     * 1.收藏
     */
    private int dataType = 0;

    @ViewInject(R.id.collect_mell_lv)
    private PullToRefreshListView collect_mell_lv;

    private MellListAdapter mellListAdapter;

    private List<MellInfoList> mells;
    private List<MellInfoList> mells2;

    /**
     * 全选删除布局
     */
    @ViewInject(R.id.operation_mell_collect_layout)
    private LinearLayout operation_mell_collect_layout;

    private UserPst userPst;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int p = 1;
    private int allNum = 0;

    public static CollectMellHzjFgt newInstance(boolean param1, int dataType) {
        CollectMellHzjFgt fragment = new CollectMellHzjFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collect_mell_lv.setEmptyView(no_data_layout);

        collect_mell_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                userPst.myfooter(p, "2");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allNum >= mells.size()) {
                    p++;
                    userPst.myfooter(p, "2");
                } else {
                    collect_mell_lv.onRefreshComplete();
                }
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_mell_hzj;
    }

    @Override
    protected void initialized() {
        mells = new ArrayList<>();
        mells2 = new ArrayList<>();
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {
        if (0 == dataType) {
            userPst.myfooter(p, "2");
        }
    }

    @Override
    protected void immersionInit() {

    }

    public void setStatus(boolean status) {
        this.status = status;
        if (!status) {
            operation_mell_collect_layout.setVisibility(View.GONE);
            if (mellListAdapter != null) {
                mellListAdapter.setShowSelect(false);
            }
        } else {
            operation_mell_collect_layout.setVisibility(View.VISIBLE);
            if (mellListAdapter != null) {
                mellListAdapter.setShowSelect(true);
            }
        }
        if (mellListAdapter != null)
            mellListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("myfooter")) {
            CollectOrFootpointMell mell = GsonUtil.GsonToBean(jsonStr, CollectOrFootpointMell.class);
            allNum = mell.getNums();
            if (1 == p) {
                mells = mell.getData();
                mellListAdapter = new MellListAdapter(getActivity(), mells);
                collect_mell_lv.setAdapter(mellListAdapter);
            } else {
                mells2 = mell.getData();
                mells.addAll(mells2);
                mellListAdapter.notifyDataSetChanged();
            }
            collect_mell_lv.onRefreshComplete();
        }
    }
}
