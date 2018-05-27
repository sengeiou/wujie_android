package com.txd.hzj.wjlp.mellOnLine.gridClassify.groupbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshGridView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListDataBean;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：16:47
 * 描述：
 * ===============Txunda===============
 */

public class ThirdClassifyFgt extends BaseFgt {
    private String title;

    private int height = 0;

    private String two = "";
    private String three = "";

    @ViewInject(R.id.pr_third_lv)
    private PullToRefreshGridView pr_third_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;

    private GroupBuyPst groupBuyPst;
    private int p = 1;
    private AllGvLvAdapter allGvLvAdapter1;

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
        pr_third_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
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
        });
        pr_third_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("group_buy_id", data.get(i).getGroup_buy_id());
                startActivity(GoodLuckDetailsAty.class, bundle);
            }
        });
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
                            pr_third_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {
                                    if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                                        allGvLvAdapter1.setCanLoadImg(true);
                                    }else{
                                        allGvLvAdapter1.setCanLoadImg(false);
                                    }
                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                                }
                            });
                        }
                    } else {
                        data2 = dataBean.getGroup_buy_list();
                        if (!ListUtils.isEmpty(data2)) {
                            data.addAll(data2);
                            allGvLvAdapter1.notifyDataSetChanged();
                        }
                    }
                }
            });

            pr_third_lv.onRefreshComplete();
        }
    }
}
