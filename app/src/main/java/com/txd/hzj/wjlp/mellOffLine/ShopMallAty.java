package com.txd.hzj.wjlp.mellOffLine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.offline.OffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.mainFgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/16 14:56
 * 功能描述：线下店铺店铺列表页
 */
public class ShopMallAty extends BaseAty {

    @ViewInject(R.id.super_offline_layout)
    private VpSwipeRefreshLayout super_offline_layout;

    @ViewInject(R.id.title_tv)
    private TextView title_tv;


    @ViewInject(R.id.titles_tab_layout)
    private TabLayout titles_tab_layout;

    @ViewInject(R.id.mell_near_by_lv)
    private ListViewForScrollView mell_near_by_lv;


    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;


    private String mTitle;
    private String top_cate="";
    private String little_cate="";

    private int page = 1;
    private MellNearByHzjAdapter mellNearByHzjAdapter;
    private List<OffLineBean.NumsBean> mNumsBeans;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_mall;
    }

    @Override
    protected void initialized() {
        mTitle = getIntent().getStringExtra("menu_title");
        top_cate = getIntent().getStringExtra("top_cate");
        if (!TextUtils.isEmpty(mTitle)) {
            title_tv.setText(mTitle);
        }
    }

    private void postUrl(String top_cate, String little_cate) {
        Map<String, String> locMap = DemoApplication.getInstance().getLocInfo();
        if (locMap != null) {
            OfflineStore.offlineStoreList(locMap.get("lon"), locMap.get("lat"), page, "", top_cate, little_cate, this);
        } else {
            OfflineStore.offlineStoreList("", "", page, "", top_cate, little_cate, this);
        }
    }

    @Override
    protected void requestData() {
        postUrl(top_cate, little_cate);
        if (null == mellNearByHzjAdapter) {
            mellNearByHzjAdapter = new MellNearByHzjAdapter(this);
            mell_near_by_lv.setAdapter(mellNearByHzjAdapter);
            mell_near_by_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OffLineDataBean offLineDataBea = mellNearByHzjAdapter.getItem(position);
                    Bundle options = new Bundle();
                    options.putSerializable("mellInfo", offLineDataBea);
                    startActivity(ShopMallDetailsAty.class, options);
                }
            });
        }

        titles_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page = 1;
                int position = tab.getPosition();
                if (mNumsBeans.size() > 0) {
                    little_cate=mNumsBeans.get(position).getRec_type_id();
                    postUrl(top_cate, little_cate);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        super_offline_layout.setHeaderViewBackgroundColor(Color.WHITE);
        super_offline_layout.setHeaderView(createHeaderView());// add headerView
        super_offline_layout.setTargetScrollWithLayout(true);
        super_offline_layout.setFooterView(createFooterView());

        super_offline_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                page=1;
                postUrl(top_cate,little_cate);
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


        super_offline_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                page++;
                postUrl(top_cate,little_cate);
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
        if (requestUrl.contains("offlineStoreList")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, OffLineBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    OffLineBean offLineBean = (OffLineBean) t;
                    if ("1".equals(offLineBean.getCode())) {
                        if (page == 1) {
                            mellNearByHzjAdapter.getList().clear();
                        }
                        if (offLineBean.getData().size() > 0) {
                            mellNearByHzjAdapter.getList().addAll(offLineBean.getData());
                            mellNearByHzjAdapter.notifyDataSetChanged();
                        }
                        mNumsBeans = offLineBean.getNums();
                        if (mNumsBeans.size() > 0) {
                            for (OffLineBean.NumsBean bean : mNumsBeans) {
                                titles_tab_layout.addTab(titles_tab_layout.newTab().setText(bean.getType()));
                            }
                        }
                    }
                }
            });
        }
        loadComplate();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        loadComplate();
    }

    private void loadComplate() {
        super_offline_layout.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        super_offline_layout.setLoadMore(false);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(super_offline_layout.getContext()).inflate(R.layout.layout_head, null);
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
        View footerView = LayoutInflater.from(super_offline_layout.getContext()).inflate(R.layout.layout_footer, super_offline_layout,false);
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
