package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.welfare.WelfarePst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.popAty.GetRedPackageAty;
import com.txd.hzj.wjlp.popAty.adapter.RedPackageAdapter;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 4:07
 * 描述：领红包
 * ===============Txunda===============
 */
public class RedPacagerFgt extends BaseFgt {

    @ViewInject(R.id.red_package_lv)
    private PullToRefreshListView red_package_lv;

    private WelfarePst welfarePst;

    private RedPackageAdapter redPackageAdapter;
    private int p = 1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.mell_noty_up_view)
    private UPMarqueeView mell_noty_up_view;
    private List<Map<String, String>> announce;
    /**
     * xfte头条View
     */
    private List<View> views;

    private List<Map<String, String>> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        red_package_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                welfarePst.faceList(p, 0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                welfarePst.faceList(p, 0);
            }
        });


        red_package_lv.setEmptyView(no_data_layout);
        red_package_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!TextUtils.isEmpty( list.get(i - 1). get("merchant_id")) && ! list.get(i - 1).get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id",  list.get(i - 1).get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty( list.get(i - 1).get("goods_id")) && ! list.get(i - 1).get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id",  list.get(i - 1).get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("bonus_id", list.get(i - 1).get("bonus_id"));
                    startActivity(GetRedPackageAty.class, bundle);
                }

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_red_pacager;
    }

    @Override
    protected void initialized() {
        welfarePst = new WelfarePst(this);
        announce = new ArrayList<>();
        list = new ArrayList<>();
        views = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        welfarePst.faceList(p, 1);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("faceList")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    if (ToolKit.isList(data, "announce")) {
                        announce = JSONUtils.parseKeyAndValueToMapList(data.get("announce"));
                        setView();
                        mell_noty_up_view.setViews(views);
                    }
                    if (ToolKit.isList(data, "list")) {
                        list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                        redPackageAdapter = new RedPackageAdapter(getActivity(), 1, list);
                        red_package_lv.setAdapter(redPackageAdapter);
                    }
                } else {
                    if (ToolKit.isList(data, "list")) {
                        List<Map<String, String>> lsit2 = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                        list.addAll(lsit2);
                        redPackageAdapter.notifyDataSetChanged();
                    }
                }
            }
            red_package_lv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        red_package_lv.onRefreshComplete();
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < announce.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            tv1.setTextColor(ContextCompat.getColor(getActivity(), R.color.app_text_color));
            tv1.setGravity(Gravity.CENTER_VERTICAL);
            tv1.setTextSize(14);
            /*
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            //进行对控件赋值
            tv1.setText(announce.get(i).get("title"));
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }
}
