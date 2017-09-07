package com.txd.hzj.wjlp.mellOnLine.gridClassify.groupbuy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.bean.groupbuy.GroupThirdList;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.mellOnLine.fgt.SubClassifyListFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

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
    private PullToRefreshListView pr_third_lv;

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
        pr_third_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                groupBuyPst.threeList(two, p, three);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                groupBuyPst.threeList(two, p, three);
            }
        });
        pr_third_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("group_buy_id", data.get(i - 1).getGroup_buy_id());
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
            Map<String,String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String,String> datajson = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (1 == p) {
                data = GsonUtil.getObjectList(datajson.get("group_buy_list"),AllGoodsBean.class);
                if (!ListUtils.isEmpty(data)) {
                    allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, 8);
                    pr_third_lv.setAdapter(allGvLvAdapter1);
                }
            } else {
                data2 = GsonUtil.getObjectList(datajson.get("group_buy_list"),AllGoodsBean.class);
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    allGvLvAdapter1.notifyDataSetChanged();
                }
            }
            pr_third_lv.onRefreshComplete();
        }
    }
}
