package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.DateTool;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.DukeScrollView;
import com.ants.theantsgo.view.PullToRefreshLayout;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.auction.AuctionList;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.LimitAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:33
 * 描述：抢购碎片
 * ===============Txunda===============
 */
public class LimitFgt extends BaseFgt implements DukeScrollView.ScrollViewListener {

    private int type;
    private String stage_id = "";
    private String str = "";

    /**
     * 倒计时
     */
    @ViewInject(R.id.limit_count_down_view)
    private CountdownView limit_count_down_view;
    @ViewInject(R.id.limit_gv)
    private GridViewForScrollView limit_gv;

    private LimitAdapter limiAdapter;
    private List<AuctionList> list;
    private List<AuctionList> list2;

    /**
     * 可以监听滚动距离的ScrollView
     */
    @ViewInject(R.id.fgt_limit_sc)
    private DukeScrollView fgt_limit_sc;

    @ViewInject(R.id.top_ad_iv)
    private ImageView top_ad_iv;
    /**
     * 回到顶部
     */
    @ViewInject(R.id.to_be_back_iv)
    private ImageView to_be_back_iv;
    private int height = 0;

    private LimitBuyPst limitBuyPst;
    private int p = 1;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 刷新控件
     */
    @ViewInject(R.id.ptr_layout)
    private PullToRefreshLayout refresh_view;
    private int numall = -1;

    @ViewInject(R.id.limit_status_tv)
    private TextView limit_status_tv;
    private int operation_select = -1;
    private String href = "";
    private String desc = "";

    public LimitFgt() {
    }

    public static LimitFgt getFgt(String stage_id, int type) {
        LimitFgt limitFgt = new LimitFgt();
        limitFgt.stage_id = stage_id;
        limitFgt.type = type;
        return limitFgt;
    }

    public static LimitFgt getFgt(String stage_id, String s) {
        LimitFgt limitFgt = new LimitFgt();
        limitFgt.stage_id = stage_id;
        limitFgt.str = s;
        return limitFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        limit_gv.setEmptyView(no_data_layout);
        limit_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("limit_buy_id", list.get(i).getLimit_buy_id());
                bundle.putInt("type", 0);
                startActivity(LimitGoodsAty.class, bundle);
            }
        });
        height = Settings.displayWidth * 400 / 1242;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, height);
        top_ad_iv.setLayoutParams(params);
        fgt_limit_sc.setScrollViewListener(this);
        fgt_limit_sc.smoothScrollTo(0, 0);
        forUpdata();
        limit_status_tv.setText(str);
//        if (-1 == type) {
//            limit_status_tv.setText("本场已结束");
//        } else if (0 == type) {
//            limit_status_tv.setText("距本场结束");
//        } else {
//            limit_status_tv.setText("距本场开始");
//        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            try {
                fgt_limit_sc.smoothScrollTo(0, 0);
            } catch (NullPointerException e) {
                L.e("限量购");
            }
        }
    }

    @Override
    @OnClick({R.id.top_ad_iv, R.id.to_be_back_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.top_ad_iv:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                bundle.putString("desc", desc);
                bundle.putString("href", href);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
            case R.id.to_be_back_iv:
                fgt_limit_sc.smoothScrollTo(0, 0);
                to_be_back_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_limit;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        limitBuyPst = new LimitBuyPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        limitBuyPst.limitBuyIndex(p, stage_id);
    }

    @Override
    protected void immersionInit() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        numall = Integer.parseInt(map.get("nums"));
        if (requestUrl.contains("limitBuyIndex")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            // 当前时间
            String now = DateTool.getUserDate("HH:mm");
            // 结束时间
            String end;
            if (1 == type) {
                end = data.get("start_time");
            } else {
                end = data.get("end_time");
            }
            // 两个时间的差值
            String interval = DateTool.getTwoHour(end, now);
            // 将差值转成秒
            double temp = Double.parseDouble(interval) * 60 * 60;
            // 将秒转成毫秒
            long difference = (long) (temp * 1000);
            // 设置倒计时的Tag
            limit_count_down_view.setTag("limit" + stage_id);
            // 设置倒计时的时长
            limit_count_down_view.start(difference);
            if (1 == p) {
                Map<String, String> ads = JSONUtils.parseKeyAndValueToMap(data.get("ads"));
                Glide.with(getActivity()).load(ads.get("picture"))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
//                        .centerCrop()
//                        .override(Settings.displayWidth, height)
                        .into(top_ad_iv);
                desc = ads.get("desc");
                href = ads.get("href");
                if (ToolKit.isList(data, "limitBuyList")) {
                    list = GsonUtil.getObjectList(data.get("limitBuyList"), AuctionList.class);
                    limiAdapter = new LimitAdapter(list, getActivity(), type, 0);
                    limit_gv.setAdapter(limiAdapter);
                    limiAdapter.setTvClick(new AdapterTextViewClickListener() {
                        @Override
                        public void onTextViewClick(View v, int position) {
                            if (!Config.isLogin()) {
                                toLogin();
                                return;
                            }
                            if (list.get(position).getIs_remind().equals("1")) {
                                showRightTip("您已设置过提醒");
                                return;
                            }
                            operation_select = position;
                            limitBuyPst.remindMe(list.get(position).getLimit_buy_id());
                        }
                    });
                }
                refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {

                if (ToolKit.isList(data, "limitBuyList")) {
                    list2 = GsonUtil.getObjectList(data.get("limitBuyList"), AuctionList.class);
                    list.addAll(list2);
                    limiAdapter.notifyDataSetChanged();
                }

                refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            }
            return;
        }
        if (requestUrl.contains("remindMe")) {
            showRightTip("设置成功");
            if (operation_select >= 0) {
                list.get(operation_select).setIs_remind("1");
                limiAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新数据
     */
    private void forUpdata() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                limitBuyPst.limitBuyIndex(p, stage_id);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (numall <= list.size()) {
                    refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
                    return;
                }
                // 加载操作
                p++;
                limitBuyPst.limitBuyIndex(p, stage_id);
            }
        });
    }

    @Override
    public void onScrollChanged(DukeScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < height) {
            to_be_back_iv.setVisibility(View.GONE);
        } else {
            to_be_back_iv.setVisibility(View.VISIBLE);
        }
    }
}
