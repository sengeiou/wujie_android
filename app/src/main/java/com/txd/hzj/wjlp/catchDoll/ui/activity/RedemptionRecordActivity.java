package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.MoneyRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.MoneyRecordingBean;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：兑换记录
 */
public class RedemptionRecordActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;
    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    @ViewInject(R.id.list_smartRefresh_llayout) // 刷新加载
    public RefreshLayout list_smartRefresh_llayout;

    private int page = 1; // 当前页数
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.activityList_show_inc);
        titleView_title_tv.setText("兑换记录");
        list_smartRefresh_llayout.setEnableAutoLoadMore(false); // 是否启用列表惯性滑动到底部时自动加载更多
        list_smartRefresh_llayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishRefresh(500);
                page = 1; // 下拉刷新 Page = 1
                requestData();
            }
        });
        list_smartRefresh_llayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishLoadMore(500);
                page++;
                requestData();
            }
        });
    }

    @Override
    protected void requestData() {
        Catcher.userCoinLogInfo(0, page, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("userCoinLogInfo")){
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ArrayList<MoneyRecordingBean> list = JSONUtils.parseKeyAndValueToMapList(MoneyRecordingBean.class, data.get("list"));
            if (list != null && list.size()>0){
                List<MoneyRecordingBean> tempMoneyRecordingBeans = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    MoneyRecordingBean moneyRecordingBean = list.get(i);
                    moneyRecordingBean.setDesc("兑换记录");
                    moneyRecordingBean.setCreate_time(list.get(i).getCreate_time() * 1000L); // 后台回传的时间戳是秒，此处要使用毫秒，所以乘1000
                    tempMoneyRecordingBeans.add(moneyRecordingBean);
                }
                MoneyRecordingAdapter adapter = new MoneyRecordingAdapter(this, tempMoneyRecordingBeans);
                list_show_reView.setLayoutManager(new LinearLayoutManager(this));
                list_show_reView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // 添加默认分割线
                list_show_reView.setAdapter(adapter);
            }
        }

    }

    @OnClick({R.id.titleView_goback_imgv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
        }
    }
}
