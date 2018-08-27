package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.util.ListUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.footPoint.OffLineCollectBean;
import com.txd.hzj.wjlp.bean.footPoint.OfflineFootBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOffLine.ShopMallDetailsAty;
import com.txd.hzj.wjlp.mellOffLine.adapter.OffLineFooterAndCollectAdapter;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/21 16:29
 * 功能描述：线下店铺收藏
 */
public class CollectOffLineshopFgt extends BaseFgt {

    private boolean status;
    /**
     * 数据类型
     * 0.足迹
     * 1.收藏
     */
    private int dataType = 1;

    @ViewInject(R.id.vpSwipeRefreshLayout)
    private SuperSwipeRefreshLayout mVpSwipeRefreshLayout;

    @ViewInject(R.id.mell_lv)
    private ListView mell_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;


    @ViewInject(R.id.collect_operation_layout)
    private LinearLayout collect_operation_layout;

    @ViewInject(R.id.collect_select_all_cb)
    private CheckBox collect_select_all_cb;


    private List<OffLineCollectBean.DataBean> mells;

    private List<OffLineCollectBean.DataBean> mells2;


    private List<OfflineFootBean.DataBean> footerList;

    private List<OfflineFootBean.DataBean> footerList2;

    private OffLineFooterAndCollectAdapter mlAdapter;

    private UserPst userPst;
    private UserCollectPst collectPst;
    private int p = 1;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private Intent intent;

    private List<Boolean> selectList=new ArrayList<>();
    private List<Boolean> mlAdapterSelectList;
    private ArrayList<Boolean> selectList2;
    private int allNum = 0;


    public static CollectOffLineshopFgt newInstance(boolean param1, int dataType) {
        CollectOffLineshopFgt fragment = new CollectOffLineshopFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_offlineshop;
    }

    @Override
    protected void initialized() {
        mells = new ArrayList<>();
        footerList=new ArrayList<>();
        userPst = new UserPst(this);
        collectPst = new UserCollectPst(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void r() {
        if (0 == dataType) {
            userPst.myfooter(p, "5");
        } else {
            collectPst.collectList(p, "5");
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (!status) {
            mVpSwipeRefreshLayout.setEnabled(true);
            mVpSwipeRefreshLayout.setRefreshing(false);
            mVpSwipeRefreshLayout.setLoadMore(false);
            collect_operation_layout.setVisibility(View.GONE);
            if (mlAdapter != null) {
                mlAdapter.setShowSelect(false);
                mlAdapter.notifyDataSetChanged();
            }
        } else {
            mVpSwipeRefreshLayout.setEnabled(false);
            collect_operation_layout.setVisibility(View.VISIBLE);
            if (mlAdapter != null) {
                mlAdapter.setShowSelect(true);
                mlAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }


    @Override
    @OnClick({R.id.collect_select_all_cb, R.id.operation_tv})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.collect_select_all_cb) {
            boolean checked = collect_select_all_cb.isChecked();
            selectList.clear();
            if (0==dataType){
                if (footerList.size()>0){
                    for (int i = 0; i < footerList.size(); i++) {
                        selectList.add(checked);
                    }
                    if (null!=mlAdapter) {
                        mlAdapter.setSelectItem(selectList);
                        mlAdapter.notifyDataSetChanged();
                    }
                }
            }else if (1==dataType){
                if (mells.size()>0){
                    for (int i = 0; i < mells.size(); i++) {
                        selectList.add(checked);
                    }
                    if (null!=mlAdapter) {
                        mlAdapter.setSelectItem(selectList);
                        mlAdapter.notifyDataSetChanged();
                    }
                }
            }

        }
        if (id == R.id.operation_tv) {
            if (0==dataType){
                ArrayList<String> ids = new ArrayList<>();
                mlAdapterSelectList = mlAdapter.getSelectList();
                footerList2 = new ArrayList<>();
                selectList2=new ArrayList<>();
                for (int i = 0; i < footerList.size(); i++) {
                    if (mlAdapterSelectList.get(i)){
                        ids.add(footerList.get(i).getFooter_id());
                        footerList2.add(footerList.get(i));
                        selectList2.add(mlAdapterSelectList.get(i));
                    }
                }
                String collect_ids = ListUtils.join(ids);

                userPst.delFooter(collect_ids);
            }else {
                ArrayList<String> ids = new ArrayList<>();
                mlAdapterSelectList = mlAdapter.getSelectList();
                mells2 = new ArrayList<>();
                selectList2=new ArrayList<>();
                for (int i = 0; i < mells.size(); i++) {
                    if (mlAdapterSelectList.get(i)){
                        ids.add(mells.get(i).getCollect_id());
                        mells2.add(mells.get(i));
                        selectList2.add(mlAdapterSelectList.get(i));
                    }
                }
                String collect_ids = ListUtils.join(ids);
                collectPst.delCollect(collect_ids);
            }

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        intent = new Intent();
        intent.setAction("sftv");
        intent.putExtra("index", 3);

        mVpSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        mVpSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        mVpSwipeRefreshLayout.setTargetScrollWithLayout(true);
        mVpSwipeRefreshLayout.setFooterView(createFooterView());

        mVpSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                r();
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


        mVpSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                r();
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




    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("collectList")) {
            getActivity().sendBroadcast(intent);
            OffLineCollectBean offLineCollectBean = JSONObject.parseObject(jsonStr, OffLineCollectBean.class);
            if ("1".equals(offLineCollectBean.getCode())){
                List<OffLineCollectBean.DataBean> data = offLineCollectBean.getData();
                allNum = Integer.parseInt(offLineCollectBean.getNums());
                if (data.size()>0 || mells.size()>0){
                    mell_lv.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                    if (p == 1) {
                        mells.clear();
                    }
                    mells.addAll(data);
                    mlAdapter = new OffLineFooterAndCollectAdapter(getActivity(), mells,dataType);
                    selectList.clear();
                    if (mells.size()>0){
                        for (int i = 0; i < mells.size(); i++) {
                            selectList.add(false);
                        }
                    }
                    mlAdapter.setSelectItem(selectList);
                    mlAdapter.setOnItemClickListener(new OffLineFooterAndCollectAdapter.OnItemClickListener() {
                        @Override
                        public void itemClick(int position) {
                            Intent intent = new Intent(getActivity(), ShopMallDetailsAty.class);
                            Bundle options = new Bundle();
                            OffLineCollectBean.DataBean dataBean = mells.get(position);
                            OffLineDataBean offLineDataBea = new OffLineDataBean();
                            offLineDataBea.setS_id(dataBean.getS_id());
                            offLineDataBea.setMerchant_name(dataBean.getMerchant_name());
                            offLineDataBea.setMerchant_desc(dataBean.getMerchant_desc());
                            offLineDataBea.setLogo(dataBean.getLogo());
                            offLineDataBea.setScore(dataBean.getScore());
                            offLineDataBea.setMonths_order(dataBean.getMonths_order());
                            offLineDataBea.setDistance("");
                            Map<String, String> locInfo = DemoApplication.getInstance().getLocInfo();
                            offLineDataBea.setLat(locInfo.containsKey("lat") ? locInfo.get("lat") : "");
                            offLineDataBea.setLng(locInfo.containsKey("lon") ? locInfo.get("lon") : "");
                            offLineDataBea.setProportion("");
                            offLineDataBea.setShow(false);
                            offLineDataBea.setTicket(null);
                            offLineDataBea.setUser_id("");
                            options.putSerializable("mellInfo", offLineDataBea);
                            intent.putExtras(options);
                            startActivity(intent);
                        }
                    });

                    mlAdapter.setForSelectNum(new OffLineFooterAndCollectAdapter.ForSelectNum() {
                        @Override
                        public void selectNum(int num) {
                            if (num >= mells.size()) {// 全部选中
                                collect_select_all_cb.setChecked(true);
                            } else {// 非全部选中
                                collect_select_all_cb.setChecked(false);
                            }
                        }
                    });
                    mell_lv.setAdapter(mlAdapter);
                }else {
                    mell_lv.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
            }

            setStatus(status);
        }

        if (requestUrl.contains("myfooter")){
            getActivity().sendBroadcast(intent);
            OfflineFootBean offlineFootBean = JSONObject.parseObject(jsonStr, OfflineFootBean.class);
            if ("1".equals(offlineFootBean.getCode())){
                List<OfflineFootBean.DataBean> data = offlineFootBean.getData();
                allNum = Integer.parseInt(offlineFootBean.getNums());
                if (data.size()>0 || footerList.size()>0){
                    mell_lv.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                    if (p == 1) {
                        footerList.clear();
                    }
                    footerList.addAll(data);
                    mlAdapter = new OffLineFooterAndCollectAdapter(getActivity(), footerList);
                    selectList.clear();
                    if (footerList.size()>0){
                        for (int i = 0; i < footerList.size(); i++) {
                            selectList.add(false);
                        }
                    }
                    mlAdapter.setSelectItem(selectList);
                    mlAdapter.setOnItemClickListener(new OffLineFooterAndCollectAdapter.OnItemClickListener() {
                        @Override
                        public void itemClick(int position) {
                            Intent intent = new Intent(getActivity(), ShopMallDetailsAty.class);
                            Bundle options = new Bundle();
                            OfflineFootBean.DataBean dataBean = footerList.get(position);
                            OffLineDataBean offLineDataBea = new OffLineDataBean();
                            offLineDataBea.setS_id(dataBean.getMerchant_id());
                            offLineDataBea.setMerchant_name(dataBean.getMerchant_name());
                            offLineDataBea.setMerchant_desc(dataBean.getMerchant_desc());
                            offLineDataBea.setLogo(dataBean.getLogo());
                            offLineDataBea.setScore(dataBean.getScore());
                            offLineDataBea.setMonths_order(dataBean.getMonths_order());
                            offLineDataBea.setDistance("");
                            Map<String, String> locInfo = DemoApplication.getInstance().getLocInfo();
                            offLineDataBea.setLat(locInfo.containsKey("lat") ? locInfo.get("lat") : "");
                            offLineDataBea.setLng(locInfo.containsKey("lon") ? locInfo.get("lon") : "");
                            offLineDataBea.setProportion("");
                            offLineDataBea.setShow(false);
                            offLineDataBea.setTicket(null);
                            offLineDataBea.setUser_id("");
                            options.putSerializable("mellInfo", offLineDataBea);
                            intent.putExtras(options);
                            startActivity(intent);
                        }
                    });

                    mlAdapter.setForSelectNum(new OffLineFooterAndCollectAdapter.ForSelectNum() {
                        @Override
                        public void selectNum(int num) {
                            if (num >= footerList.size()) {// 全部选中
                                collect_select_all_cb.setChecked(true);
                            } else {// 非全部选中
                                collect_select_all_cb.setChecked(false);
                            }
                        }
                    });
                    mell_lv.setAdapter(mlAdapter);
                }else {
                    mell_lv.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
            }

            setStatus(status);
        }

        if (requestUrl.contains("delCollect")){
            mells.removeAll(mells2);
            allNum -= mells2.size();
            mlAdapterSelectList.removeAll(selectList2);
            mlAdapter.setSelectItem(mlAdapterSelectList);
            mlAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(mells)) {
                collect_operation_layout.setVisibility(View.GONE);
                mell_lv.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
        }


        if (requestUrl.contains("delFooter")){
            footerList.removeAll(footerList2);
            allNum -= footerList2.size();
            mlAdapterSelectList.removeAll(selectList2);
            mlAdapter.setSelectItem(mlAdapterSelectList);
            mlAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(footerList)) {
                collect_operation_layout.setVisibility(View.GONE);
                mell_lv.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
        }
        removeProgressDialog();
        loadComplate();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        removeProgressDialog();
        if (collect_operation_layout.getVisibility()==View.VISIBLE){
            collect_operation_layout.setVisibility(View.GONE);
        }
        loadComplate();
        if (requestUrl.contains("myfooter") || requestUrl.contains("collectList")) {
        } else {
            super.onError(requestUrl, error);
        }
    }

    private void loadComplate() {
        mVpSwipeRefreshLayout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        mVpSwipeRefreshLayout.setLoadMore(false);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mVpSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, mVpSwipeRefreshLayout, false);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(mVpSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, mVpSwipeRefreshLayout, false);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }


}
