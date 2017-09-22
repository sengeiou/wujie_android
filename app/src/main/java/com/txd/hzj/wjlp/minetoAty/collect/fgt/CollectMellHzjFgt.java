package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CollectOrFootpointMell;
import com.txd.hzj.wjlp.bean.MellInfoList;
import com.txd.hzj.wjlp.bean.footPoint.FootMellsBan;
import com.txd.hzj.wjlp.bean.footPoint.FootPointBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
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
public class CollectMellHzjFgt extends BaseFgt implements MellListAdapter.ForSelectNum {
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

    private List<FootMellsBan> mellsFoot;
    private List<FootMellsBan> mellsFoot2;
    private List<FootMellsBan> mellsFoot3;
    private List<MellInfoList> mells;
    private List<MellInfoList> mells2;
    private List<MellInfoList> mells3;

    /**
     * 全选删除布局
     */
    @ViewInject(R.id.operation_mell_collect_layout)
    private LinearLayout operation_mell_collect_layout;

    private UserPst userPst;

    private UserCollectPst collectPst;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int p = 1;
    private int allNum = 0;

    @ViewInject(R.id.cart_select_all_cb)
    private CheckBox cart_select_all_cb;

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
                if (0 == dataType) {
                    userPst.myfooter(p, "2");
                } else {
                    collectPst.collectList(p, "2");
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allNum >= mells.size()) {
                    p++;
                    if (0 == dataType) {
                        userPst.myfooter(p, "2");
                    } else {
                        collectPst.collectList(p, "2");
                    }
                } else {
                    collect_mell_lv.onRefreshComplete();
                }
            }
        });

    }

    @Override
    @OnClick({R.id.cart_select_all_cb, R.id.operation_goods_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cart_select_all_cb:// 全选，取消全选
                boolean select = cart_select_all_cb.isChecked();
                for (MellInfoList ms : mells) {
                    ms.setSelect(select);
                }
                mellListAdapter.notifyDataSetChanged();
                break;
            case R.id.operation_goods_tv://删除
                if (0 == dataType) {

                } else {
                    ArrayList<String> ids = new ArrayList<>();
                    mells3 = new ArrayList<>();
                    for (MellInfoList ms : mells) {
                        if (ms.isSelect()) {
                            ids.add(ms.getCollect_id());
                            mells3.add(ms);
                        }
                    }
                    String collect_ids = ListUtils.join(ids);
                    L.e("=====List转Json", collect_ids);
                    collectPst.delCollect(collect_ids);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_mell_hzj;
    }

    @Override
    protected void initialized() {
        mells = new ArrayList<>();
        mells2 = new ArrayList<>();
        mellsFoot = new ArrayList<>();
        mellsFoot2 = new ArrayList<>();
        userPst = new UserPst(this);
        collectPst = new UserCollectPst(this);
        L.e("====data22222=====", String.valueOf(dataType));
    }

    @Override
    protected void requestData() {
        if (0 == dataType) {
            userPst.myfooter(p, "2");
        } else {
            collectPst.collectList(p, "2");
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
        if (requestUrl.contains("myfooter") || requestUrl.contains("collectList")) {
            removeContent();
            removeDialog();
        } else {
            super.onError(requestUrl, error);
        }
        collect_mell_lv.onRefreshComplete();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("myfooter")) {
            FootPointBean foot = GsonUtil.GsonToBean(jsonStr, FootPointBean.class);
            allNum = foot.getNums();
            if (1 == p) {
                mellsFoot = foot.getData();
                if (!ListUtils.isEmpty(mellsFoot)) {
                    mellListAdapter = new MellListAdapter(getActivity(), mellsFoot, 1);
                    collect_mell_lv.setAdapter(mellListAdapter);
                    // 设置选中数量的监听
                    mellListAdapter.setForSelectNum(this);
                }
            } else {
                mellsFoot2 = foot.getData();
                if (!ListUtils.isEmpty(mellsFoot2)) {
                    mellsFoot.addAll(mellsFoot2);
                    mellListAdapter.notifyDataSetChanged();
                }
            }
            collect_mell_lv.onRefreshComplete();
            setStatus(status);
            return;
        }
        if (requestUrl.contains("collectList")) {
            CollectOrFootpointMell mell = GsonUtil.GsonToBean(jsonStr, CollectOrFootpointMell.class);
            allNum = mell.getNums();
            if (1 == p) {
                mells = mell.getData();
                if (!ListUtils.isEmpty(mells)) {
                    mellListAdapter = new MellListAdapter(getActivity(), mells);
                    collect_mell_lv.setAdapter(mellListAdapter);
                    // 设置选中数量的监听
                    mellListAdapter.setForSelectNum(this);
                }
            } else {
                mells2 = mell.getData();
                if (!ListUtils.isEmpty(mells2)) {
                    mells.addAll(mells2);
                    mellListAdapter.notifyDataSetChanged();
                }
            }
            collect_mell_lv.onRefreshComplete();
            setStatus(status);
            return;
        }
        if (requestUrl.contains("delCollect")) {
            mells.removeAll(mells3);
            allNum -= mells3.size();
            mellListAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(mells)) {
                operation_mell_collect_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void selectNum(int num) {
        if (num >= mells.size()) {// 全部选中
            cart_select_all_cb.setChecked(true);
        } else {// 非全部选中
            cart_select_all_cb.setChecked(false);
        }
    }
}
