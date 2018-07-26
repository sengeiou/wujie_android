package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
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
import com.txd.hzj.wjlp.minetoAty.FootprintAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 9:58
 * 描述：收藏、足迹，商家
 */
public class CollectMellHzjFgt extends BaseFgt implements MellListAdapter.ForSelectNum {

    private boolean status;
    /**
     * 数据类型
     * 0.足迹
     * 1.收藏
     */
    private int dataType = 0;

    @ViewInject(R.id.super_layouts)
    private SuperSwipeRefreshLayout super_layouts;
    @ViewInject(R.id.collect_mell_lv)
    private ListView collect_mell_lv;
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
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private UserCollectPst collectPst;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int p = 1;
    private int allNum = 0;
    Intent intent;
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

        intent = new Intent();
        intent.setAction("sftv");
        intent.putExtra("index", 1);
        collect_mell_lv.setEmptyView(no_data_layout);

        super_layouts.setHeaderViewBackgroundColor(Color.WHITE);
        super_layouts.setHeaderView(createHeaderView());// add headerView
        super_layouts.setFooterView(createFooterView());
        super_layouts.setTargetScrollWithLayout(true); // 跟随手指滑动
        super_layouts.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                if (0 == dataType) {
                    userPst.myfooter(p, "2");
                } else {
                    collectPst.collectList(p, "2");
                }
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        super_layouts.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                if (allNum >= mells.size()) {
                    p++;
                    if (0 == dataType) {
                        userPst.myfooter(p, "2");
                    } else {
                        collectPst.collectList(p, "2");
                    }
                } else {
                    super_layouts.setLoadMore(false);
                }
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });
    /*    collect_mell_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });*/

    }

    @Override
    @OnClick({R.id.cart_select_all_cb, R.id.operation_goods_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cart_select_all_cb:// 全选，取消全选
                boolean select = cart_select_all_cb.isChecked();
                if (0 == dataType) {
                    for (FootMellsBan ft : mellsFoot) {
                        ft.setSelect(select);
                    }
                } else {
                    for (MellInfoList ms : mells) {
                        ms.setSelect(select);
                    }
                }
                mellListAdapter.notifyDataSetChanged();
                break;
            case R.id.operation_goods_tv://删除
                if (0 == dataType) {
                    ArrayList<String> ids = new ArrayList<>();
                    mellsFoot3 = new ArrayList<>();
                    for (FootMellsBan ms : mellsFoot) {
                        if (ms.isSelect()) {
                            ids.add(ms.getFooter_id());
                            mellsFoot3.add(ms);
                        }
                    }
                    String collect_ids = ListUtils.join(ids).replace("[", "").replace("]", "");
                    userPst.delFooter(collect_ids);
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
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (0 == dataType) {
//            userPst.myfooter(p, "2");
//        } else {
//            collectPst.collectList(p, "2");
//        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    public void r() {
        if (0 == dataType) {
            userPst.myfooter(p, "2");
        } else {
            collectPst.collectList(p, "2");
        }
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

        L.e("=====onError========" + error.toString());

        if (requestUrl.contains("myfooter") || requestUrl.contains("collectList")) {
            removeContent();
            removeDialog();
        } else {
            super.onError(requestUrl, error);
        }
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        super_layouts.setLoadMore(false);
        super_layouts.setRefreshing(false);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("myfooter")) {
//            ((FootprintAty )getActivity()).setView(View.VISIBLE);
            getActivity().sendBroadcast(intent);
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
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            super_layouts.setLoadMore(false);
            super_layouts.setRefreshing(false);
            setStatus(status);
            return;
        }
        if (requestUrl.contains("collectList")) {

            getActivity().sendBroadcast(intent);
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
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            super_layouts.setLoadMore(false);
            super_layouts.setRefreshing(false);
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
        if (requestUrl.contains("delFooter")) {
            mellsFoot.removeAll(mellsFoot3);
            allNum -= mellsFoot3.size();
            mellListAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(mellsFoot)) {
                operation_mell_collect_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void selectNum(int num) {
        if (0 == dataType) {// 足迹
            if (num >= mellsFoot.size()) {// 全部选中
                cart_select_all_cb.setChecked(true);
            } else {// 非全部选中
                cart_select_all_cb.setChecked(false);
            }
        } else {// 收藏
            if (num >= mells.size()) {// 全部选中
                cart_select_all_cb.setChecked(true);
            } else {// 非全部选中
                cart_select_all_cb.setChecked(false);
            }
        }

    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(super_layouts.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(super_layouts.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
