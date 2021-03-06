package com.txd.hzj.wjlp.minetoaty.collect.fgt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.bean.CollectOrFootpointGoods;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mainfgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/24 0024
 * 时间：下午 5:07
 * 描述：收藏、足迹，商品碎片
 */
public class CollectGoodsHzjFgt extends BaseFgt implements RacycleAllAdapter.SelectNum {

    private boolean status;
    private int dataType = 0;
    @ViewInject(R.id.collect_goods_rv)
    private RecyclerView collect_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;
    Intent intent;
    private List<CFGoodsList> data;
    private List<CFGoodsList> data2;
    private List<CFGoodsList> data3;
    private int height = 0;

    @ViewInject(R.id.collect_operation_layout)
    private LinearLayout collect_operation_layout;

    @ViewInject(R.id.super_layout)
    private SuperSwipeRefreshLayout super_layout;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private UserPst userPst;

    private UserCollectPst collectPst;

    /**
     * 分页
     */
    private int p = 1;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int allNum = 0;

    @ViewInject(R.id.collect_goods_select_all_cb)
    private CheckBox collect_goods_select_all_cb;

    public static CollectGoodsHzjFgt newInstance(boolean param1, int dataType) {
        CollectGoodsHzjFgt fragment = new CollectGoodsHzjFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intent = new Intent();
        intent.putExtra("index", 0);
        intent.setAction("sftv");
        // 布局
        collect_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        collect_goods_rv.setItemAnimator(new DefaultItemAnimator());
        collect_goods_rv.setHasFixedSize(true);
        collect_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6"))); // 分割线

        super_layout.setHeaderViewBackgroundColor(Color.WHITE);
        super_layout.setHeaderView(createHeaderView());// add headerView
        super_layout.setFooterView(createFooterView());

        super_layout.setTargetScrollWithLayout(true); // 跟随手指滑动

        super_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                frist = false;
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                if (0 == dataType) {
                    userPst.myfooter(p, "1");
                } else {
                    collectPst.collectList(p, "1");
                }
                if (collect_goods_select_all_cb.isChecked()) {
                    collect_goods_select_all_cb.setChecked(false);
                }
            }

            @Override
            public void onPullDistance(int distance) {
            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });

        super_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

            @Override
            public void onLoadMore() {
                frist = false;
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);

                p++;
                if (0 == dataType) {
                    userPst.myfooter(p, "1");
                } else {
                    collectPst.collectList(p, "1");
                }
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }

            @Override
            public void onPushDistance(int distance) {

            }

        });
    }


    @Override
    @OnClick({R.id.collect_goods_select_all_cb, R.id.operation_goods_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.collect_goods_select_all_cb:// 全选，取消全选
                boolean select = collect_goods_select_all_cb.isChecked();
                for (CFGoodsList cg : data) {
                    cg.setIsSelect(select);
                }
                if (null != racycleAllAdapter) {
                    //                if (racycleAllAdapter != null) {
                    racycleAllAdapter.notifyDataSetChanged();
                } else {
                    racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
                    racycleAllAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.operation_goods_tv:

                L.e("wang", "======>>>>>>>>>operation_goods_tv，删除按钮");

                List<String> ids;
                if (0 == dataType) { // 足迹
                    ids = new ArrayList<>();
                    data3 = new ArrayList<>();
                    for (CFGoodsList cg : data) {
                        if (cg.getIsSelect()) {
                            ids.add(cg.getFooter_id());
                            data3.add(cg);
                        }
                    }
                    String collect_ids = ListUtils.join(ids).replace("[", "").replace("]", "");
                    userPst.delFooter(collect_ids);
                } else {// 收藏
                    ids = new ArrayList<>();
                    data3 = new ArrayList<>();
                    for (CFGoodsList cg : data) {
                        if (cg.getIsSelect()) {
                            ids.add(cg.getCollect_id());
                            data3.add(cg);
                        }
                    }
                    String collect_ids = ListUtils.join(ids);
                    collectPst.delCollect(collect_ids);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_goods_hzj;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        userPst = new UserPst(this);
        collectPst = new UserCollectPst(this);
        height = ToolKit.dip2px(getActivity(), 4);
    }

    @Override
    public void onResume() {
        super.onResume();
        r();
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        removeProgressDialog();
        loadComplate();
        if (requestUrl.contains("myfooter") || requestUrl.contains("collectList")) {
        } else {
            super.onError(requestUrl, error);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();
        loadComplate();

        if (requestUrl.contains("myfooter")) { // 足迹
            L.e("=====myfooter=====", jsonStr);

            getActivity().sendBroadcast(intent);
            //            ((FootprintAty)getActivity()).setView(View.VISIBLE);

            CollectOrFootpointGoods goods = GsonUtil.GsonToBean(jsonStr, CollectOrFootpointGoods.class);
            allNum = goods.getData().size();

            L.e("wang", allNum + "\t" + p);

            if (allNum > 0) {
                super_layout.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                super_layout.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            if (1 == p) {
                this.data = goods.getData();

                if (!ListUtils.isEmpty(this.data)) {
                    // 适配器初始化
                    racycleAllAdapter = new RacycleAllAdapter(getActivity(), this.data);
                    collect_goods_rv.setAdapter(racycleAllAdapter);
                    racycleAllAdapter.setSelectNum(this);
                    racycleAllAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ticket_buy_id", CollectGoodsHzjFgt.this.data.get(position).getGoods_id());
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        }
                    });
                }
                if (!frist) {
                    super_layout.setRefreshing(false);
                    super_layout.setLoadMore(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                data2 = goods.getData();
                //                super_layout.
                super_layout.setVisibility(this.data.size() > 0 ? View.VISIBLE : View.GONE);
                no_data_layout.setVisibility(this.data.size() == 0 ? View.VISIBLE : View.GONE);
                if (!ListUtils.isEmpty(data2)) {
                    this.data.addAll(data2);
                    racycleAllAdapter.notifyDataSetChanged();
                }
            }
            super_layout.setRefreshing(false);
            super_layout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            setStatus(status);
            return;
        }
        if (requestUrl.contains("collectList")) {// 收藏
            getActivity().sendBroadcast(intent);
            CollectOrFootpointGoods goods = GsonUtil.GsonToBean(jsonStr, CollectOrFootpointGoods.class);
            allNum = goods.getNums();
            if (allNum > 0) {
                super_layout.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                super_layout.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            if (1 == p) {
                data = goods.getData();
                if (!ListUtils.isEmpty(data)) {
                    // 适配器初始化
                    racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
                    collect_goods_rv.setAdapter(racycleAllAdapter);
                    racycleAllAdapter.setSelectNum(this);
                    racycleAllAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ticket_buy_id", data.get(position).getGoods_id());
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        }
                    });
                }
                if (!frist) {
                    super_layout.setRefreshing(false);
                    super_layout.setLoadMore(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                data2 = goods.getData();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    racycleAllAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                super_layout.setLoadMore(false);
            }
            setStatus(status);
            return;
        }
        if (requestUrl.contains("delCollect")) {
            data.removeAll(data3);
            allNum -= data3.size();
            racycleAllAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(data)) {
                collect_operation_layout.setVisibility(View.GONE);
                super_layout.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            }
            return;
        }
        if (requestUrl.contains("delFooter")) {
            data.removeAll(data3);
            allNum -= data3.size();
            racycleAllAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(data)) {
                collect_operation_layout.setVisibility(View.GONE);
                super_layout.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            }
        }
    }

    private void loadComplate() {
        super_layout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        super_layout.setLoadMore(false);
    }

    @Override
    protected void immersionInit() {

    }

    public void r() {
        if (0 == dataType) {
            userPst.myfooter(p, "1");
        } else {
            collectPst.collectList(p, "1");
        }
    }

    public void setStatus(boolean show) {
        this.status = show;
        if (!show) {
            super_layout.setEnabled(true);
            collect_operation_layout.setVisibility(View.GONE);
            if (racycleAllAdapter != null) {
                racycleAllAdapter.setShowSelect(false);
            }
        } else {
            super_layout.setEnabled(false);
            collect_operation_layout.setVisibility(View.VISIBLE);
            if (racycleAllAdapter != null) {
                racycleAllAdapter.setShowSelect(true);
            }
        }
        if (racycleAllAdapter != null) {
            racycleAllAdapter.notifyDataSetChanged();
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(super_layout.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(super_layout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    @Override
    public void selectNum(int num) {
        if (num >= data.size()) {// 全部选择
            collect_goods_select_all_cb.setChecked(true);
        } else {// 未全部选择
            collect_goods_select_all_cb.setChecked(false);
        }
    }
}
