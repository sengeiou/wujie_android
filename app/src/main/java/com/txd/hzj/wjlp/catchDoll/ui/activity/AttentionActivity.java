package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.RoomListAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：关注界面
 */
public class AttentionActivity extends BaseAty implements RoomListAdapter.OnDeleteClickListener {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.titleView_edit_tv)
    public TextView titleView_edit_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    private int type; // 展示的界面信息 1：关注 2：收藏
    List<RoomBean> roomList;
    boolean isEdit = false; // 是否是编辑状态
    private RoomListAdapter adapter;

    private List<RoomBean> adapterList; // adapter删除回传的List
    private int adapterPosition; // adapter删除回传的position

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.activityList_show_inc);
    }

    @Override
    protected void requestData() {

        Intent intent = getIntent();
        type = intent.getExtras().getInt("type");

        titleView_title_tv.setText(type == 1 ? "关注" : "收藏"); // 1：关注 2：收藏
        titleView_edit_tv.setVisibility(View.VISIBLE);
        titleView_edit_tv.setText("编辑");

        Catcher.getCatcherAttentionList(1, this);
    }

    /**
     * 设置房间列表
     */
    private void setRoomData(List<RoomBean> roomList) {
        this.roomList = roomList;
        if (roomList == null || roomList.size() <= 0) {
            showNullData(list_show_reView, list_nullData_llayout, list_nullDataImg_imgv, list_nullDataMsg_tv, R.mipmap.icon_attention_null, "暂无关注记录哦~");
        } else {
            adapter = new RoomListAdapter(roomList, this);
            adapter.setOnDeleteClickListener(this);
            list_show_reView.setLayoutManager(new GridLayoutManager(this, 2));
            list_show_reView.setAdapter(adapter);
        }
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.titleView_edit_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.titleView_edit_tv:
                isEdit = !isEdit;
                if (isEdit) {
                    titleView_edit_tv.setText("完成");
                } else {
                    titleView_edit_tv.setText("编辑");
                }
                if (adapter != null) {
                    adapter.setEdit(isEdit);
                }
                break;
        }
    }

    @Override
    public void delete(final List<RoomBean> list, final int position) {
        this.adapterList = list;
        this.adapterPosition = position;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除关注的房间吗？");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Catcher.getCatcherAttention(Integer.parseInt(list.get(position).getId()), 0, AttentionActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            if (requestUrl.contains("getCatcherAttentionList")) { // 获取关注列表
                JSONArray data = jsonObject.getJSONArray("data");
                List<RoomBean> tempList = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject1 = data.getJSONObject(i);
                    RoomBean roomBean = GsonUtil.GsonToBean(jsonObject1.toString(), RoomBean.class);
                    tempList.add(roomBean);
                }
                setRoomData(tempList);
            }

            if (requestUrl.contains("getCatcherAttention")) {
                adapterList.remove(adapterPosition);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
