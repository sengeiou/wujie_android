package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.GameRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.GameRecordingBean;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.HomeFragment;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;
import com.txd.hzj.wjlp.mainfgt.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录
 */
public class GameRecordingActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;
    @ViewInject(R.id.list_smartRefresh_llayout)
    public SmartRefreshLayout list_smartRefresh_llayout;

    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    private List<GameRecordingBean> list;

    private int page = 1; // 当前分页
    private int per = 0; // 每页条数
    private GameRecordingAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("游戏记录");
    }

    @Override
    protected void requestData() {

        Catcher.getCatchersLogs(page, per, this);

        list_smartRefresh_llayout.setEnableAutoLoadMore(false); // 是否启用列表惯性滑动到底部时自动加载更多
        list_smartRefresh_llayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishRefresh(500);
                page = 1; // 下拉刷新 Page = 1
                Catcher.getCatchersLogs(page, per, GameRecordingActivity.this);
            }
        });
        list_smartRefresh_llayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                list_smartRefresh_llayout.finishLoadMore(500);
                page++;
                Catcher.getCatchersLogs(page, per, GameRecordingActivity.this);
            }
        });

    }

    private void setGameRecordingList(List<GameRecordingBean> list) {
        if (list.size() <= 0) {
            if (page <= 1) { // 第一页数据为空
                list_nullData_llayout.setVisibility(View.VISIBLE);
                list_nullDataImg_imgv.setImageResource(R.mipmap.icon_money_recording_null);
                list_nullDataMsg_tv.setText("暂无数据");
                list_smartRefresh_llayout.setVisibility(View.GONE);
            } else { // 加载更多
                showToast("没有更多数据了！");
            }
            return;
        }
        if (page <= 1) {
            this.list = list;
        } else {
            this.list.addAll(list);
        }
        adapter.notifyDataSetChanged();
        adapter = new GameRecordingAdapter(list, this);
        list_show_reView.setLayoutManager(new LinearLayoutManager(this));
        list_show_reView.setAdapter(adapter);
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

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("getCatchersLogs ============> " + jsonStr);
        if (requestUrl.contains("getCatchersLogs")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                List<GameRecordingBean> tempRecordingList = new ArrayList<>();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject gameRecordingJson = dataArray.getJSONObject(i);
                    GameRecordingBean gameRecordingBean = GsonUtil.GsonToBean(gameRecordingJson.toString(), GameRecordingBean.class);
                    tempRecordingList.add(gameRecordingBean);
                }
                setGameRecordingList(tempRecordingList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
