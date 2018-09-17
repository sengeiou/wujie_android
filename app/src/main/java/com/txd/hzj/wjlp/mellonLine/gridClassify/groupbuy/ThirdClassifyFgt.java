package com.txd.hzj.wjlp.mellonLine.gridClassify.groupbuy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListDataBean;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainfgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：16:47
 * 描述：
 *
 */

public class ThirdClassifyFgt extends BaseFgt {
    private String title;

    private int height = 0;

    private String two = "";
    private String three = "";

    @ViewInject(R.id.pr_third_lv)
    private GridViewForScrollView pr_third_lv;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;

    private GroupBuyPst groupBuyPst;
    private int p = 1;
    private AllGvLvAdapter allGvLvAdapter1;

    @ViewInject(R.id.refresh_view)
    private VpSwipeRefreshLayout refresh_view;
    private int numall = 0;
    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public static ThirdClassifyFgt getFgt(String two, String three) {
        ThirdClassifyFgt subClassifyListFgt = new ThirdClassifyFgt();
        subClassifyListFgt.two = two;
        subClassifyListFgt.three = three;
        return subClassifyListFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pr_third_lv.setEmptyView(no_data_layout);
        /*pr_third_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                p = 1;
                groupBuyPst.threeList(two, p, three);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                p++;
                groupBuyPst.threeList(two, p, three);
            }
        });*/
        pr_third_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("group_buy_id", data.get(i).getGroup_buy_id());
                startActivity(GoodLuckDetailsAty.class, bundle);
            }
        });
        pr_third_lv.setEmptyView(no_data_layout);
        forUpdata();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_group_buy_third_lv;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        groupBuyPst = new GroupBuyPst(this);
    }

    @Override
    protected void requestData() {
        groupBuyPst.threeList(two, p, three);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("threeList")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, ThreeListBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(final Object t) {
                    ThreeListBean threeListBean = (ThreeListBean) t;
                    ThreeListDataBean dataBean=threeListBean.getData();
                    if (1 == p) {
                        data = dataBean.getGroup_buy_list();
                        if (!ListUtils.isEmpty(data)) {
                            allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, 8);
                            pr_third_lv.setAdapter(allGvLvAdapter1);
//                            pr_third_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
//                                @Override
//                                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                                    if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//                                        allGvLvAdapter1.setCanLoadImg(true);
//                                    }else{
//                                        allGvLvAdapter1.setCanLoadImg(false);
//                                    }
//                                }
//
//                                @Override
//                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                                }
//                            });
                        }
                        progressBar.setVisibility(View.GONE);
                        refresh_view.setRefreshing(false); // 刷新成功
                    } else {
                        data2 = dataBean.getGroup_buy_list();
                        if (!ListUtils.isEmpty(data2)) {
                            data.addAll(data2);
                            allGvLvAdapter1.notifyDataSetChanged();
                        }
                        footerImageView.setVisibility(View.VISIBLE);
                        footerProgressBar.setVisibility(View.GONE);
                        refresh_view.setLoadMore(false); // 刷新成功
                    }
                }
            });

            /*pr_third_lv.onRefreshComplete();*/
        }
    }

    /**
     * 更新数据
     */
    private void forUpdata() {
        refresh_view.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_view.setHeaderView(createHeaderView());// add headerView
        refresh_view.setTargetScrollWithLayout(true);
        refresh_view.setFooterView(createFooterView());
        refresh_view.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                groupBuyPst.threeList(two, p, three);//请求接口
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
        refresh_view.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                if (numall <= data.size()) {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    refresh_view.setLoadMore(false); // 刷新成功
                    return;
                }
                // 加载操作
                p++;
                groupBuyPst.threeList(two, p, three);
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

    private View createHeaderView() {
        View headerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_head, null);
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
        View footerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_footer, null);
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
