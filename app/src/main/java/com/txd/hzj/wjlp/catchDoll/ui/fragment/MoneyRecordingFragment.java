package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.adapter.MoneyRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.MoneyRecordingBean;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两变动记录Fragment
 */
@SuppressLint("ValidFragment")
public class MoneyRecordingFragment extends BaseFgt {

    @ViewInject(R.id.list_smartRefresh_llayout)
    public SmartRefreshLayout list_smartRefresh_llayout;
    @ViewInject(R.id.activityList_show_inc)
    public RelativeLayout activityList_show_inc;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    List<MoneyRecordingBean> list;

    private int type; // 1:获得 2:消耗
    private int page = 1; // 当前页数

    @SuppressLint("ValidFragment")
    public MoneyRecordingFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        Catcher.userCoinLogInfo(type, page, this);

        activityList_show_inc.setVisibility(View.GONE);

        list_smartRefresh_llayout.setEnableAutoLoadMore(false); // 是否启用列表惯性滑动到底部时自动加载更多
        list_smartRefresh_llayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishRefresh(500);
                page = 1; // 下拉刷新 Page = 1
                Catcher.userCoinLogInfo(type, page, MoneyRecordingFragment.this);
            }
        });
        list_smartRefresh_llayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishLoadMore(500);
                page++;
                Catcher.userCoinLogInfo(type, page, MoneyRecordingFragment.this);
            }
        });

    }

    @Override
    protected void immersionInit() {
    }

    private void setListData(List<MoneyRecordingBean> list) {
        if (page <= 1) { // 第一页
            this.list = new ArrayList<>();
            this.list = list;
        } else {
            this.list.addAll(list);
        }
        // 设置空数据显示的界面
        if (this.list.size() <= 0) {
            showNullData(list_nullData_llayout, list_nullData_llayout, list_nullDataImg_imgv, list_nullDataMsg_tv, R.mipmap.icon_money_recording_null, "暂无银两记录哦~");
            return;
        }
        MoneyRecordingAdapter adapter = new MoneyRecordingAdapter(getActivity(), list);
        list_show_reView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_show_reView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)); // 添加默认分割线
        list_show_reView.setAdapter(adapter);

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            if (requestUrl.contains("userCoinLogInfo")) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray listArray = data.getJSONArray("list");
                List<MoneyRecordingBean> tempMoneyRecordingBeans = new ArrayList<>();
                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject jsonObject1 = listArray.getJSONObject(i);
                    MoneyRecordingBean moneyRecordingBean = GsonUtil.GsonToBean(jsonObject1.toString(), MoneyRecordingBean.class);
                    moneyRecordingBean.setCreate_time(moneyRecordingBean.getCreate_time() * 1000L); // 后台回传的时间戳是秒，此处要使用毫秒，所以乘1000
                    tempMoneyRecordingBeans.add(moneyRecordingBean);
                }
                setListData(tempMoneyRecordingBeans);

            }

            setListData(new ArrayList<MoneyRecordingBean>());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
