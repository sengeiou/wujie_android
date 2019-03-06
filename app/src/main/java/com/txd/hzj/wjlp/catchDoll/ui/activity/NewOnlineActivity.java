package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.RoomListAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;
import com.txd.hzj.wjlp.catchDoll.ui.wrapper.EnterRoom;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import java.util.List;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：type: 1新品上线 2高价精品 3只爱娃娃 4美女专场 5实用专区
 */
public class NewOnlineActivity extends BaseAty {

    @ViewInject(R.id.list_smartRefresh_llayout) // 刷新加载
    public RefreshLayout list_smartRefresh_llayout;

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    List<RoomBean> roomList;
    private int type; // 数据加载类别：1新品上线 2高价精品 3只爱娃娃 4美女专场 5实用专区
    private String mClumn;
    private String mStatus;

    private Context mContext;
    private String inRoomNumber;
    private String inRoomMac;

    private int page = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.activityList_show_inc);
        mClumn = getIntent().getStringExtra("clumn");
        mStatus = getIntent().getStringExtra("status");
        type = getIntent().getExtras().getInt("type");
        switch (type) {
            case 1:
                titleView_title_tv.setText("最新上线");
                break;
            case 2:
                titleView_title_tv.setText("高价精品");
                break;
            case 3:
                titleView_title_tv.setText("只爱娃娃");
                break;
            case 4:
                titleView_title_tv.setText("美女专场");
                break;
            case 5:
                titleView_title_tv.setText("实用专区");
                break;
        }

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
        Catcher.catcherFilter(mClumn,mStatus,page,this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("catcherFilter")){
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            roomList = JSONUtils.parseKeyAndValueToMapList(RoomBean.class, data.get("list"));
            /**
             * 设置房间列表
             */
            if (roomList != null && roomList.size()>0){
                RoomListAdapter adapter = new RoomListAdapter(roomList, this);
                list_show_reView.setLayoutManager(new GridLayoutManager(this, 2));
                //        list_show_reView.setNestedScrollingEnabled(false); // 重新设置外层Scroll滑动阻尼效果
                list_show_reView.setAdapter(adapter);
                adapter.setOnRoomItemClickListener(new RoomListAdapter.OnRoomItemClickListener() {
                    @Override
                    public void onRoomItemClick(int position) {
                        RoomBean roomBean = roomList.get(position);
                        inRoomNumber = roomBean.getId(); // 点击房间的房间号
                        inRoomMac = roomBean.getMac(); // 点击房间的Mac地址
                        if (inRoomNumber.isEmpty()) {
                            showToast("房间异常，未获取到房间号！");
                            return;
                        }
                        if (inRoomMac.isEmpty()) {
                            showToast("房间异常，未获取到房间MAC地址！");
                            return;
                        }
                        Catcher.enterRoom(inRoomNumber, NewOnlineActivity.this);
                    }
                });
            }
            return;
        }
        if (requestUrl.contains("enterRoom")) {
            if (JSONUtils.parseKeyAndValueToMap(map.get("data")).get("status").equals("0")) {
                showProgressDialog("正在连接房间，请稍后....");
               new EnterRoom(inRoomNumber,inRoomMac,(BaseAty) mContext);
            } else {
                showToast(map.get("message"));
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
