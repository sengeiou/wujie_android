package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
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
import com.txd.hzj.wjlp.catchDoll.adapter.GranTheRecordAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.GrabTheRecordBean;
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
 * <br>功能描述：抓中娃娃记录界面
 */
public class GrabTheRecordActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv) // 标题
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        titleView_title_tv.setText("抓中记录");
        Catcher.userCatcher(this);
    }

    /**
     * 设置展示数据
     */
    private void setData(List<GrabTheRecordBean> list) {
        List<GrabTheRecordBean> grabTheRecordBeans = list;
        if (grabTheRecordBeans == null || grabTheRecordBeans.size() == 0) {
            // 设置空数据展示视图
            showNullData(list_show_reView, list_nullData_llayout, list_nullDataImg_imgv, list_nullDataMsg_tv, R.mipmap.icon_my_select, "暂无抓中记录哦~");
        } else {
            GranTheRecordAdapter adapter = new GranTheRecordAdapter(grabTheRecordBeans, this);
            list_show_reView.setLayoutManager(new LinearLayoutManager(this));
            list_show_reView.setAdapter(adapter);
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

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (requestUrl.contains("userCatcher")) {
                JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                List<GrabTheRecordBean> tempGrabTheRecordBeans = new ArrayList<>();
                GrabTheRecordBean grabTheRecordBean;
                for (int i = 0; i < jsonArrayData.length(); i++) {
                    JSONObject jsonObject1 = jsonArrayData.getJSONObject(i);

                    grabTheRecordBean = new GrabTheRecordBean();
                    grabTheRecordBean.setHeadUrl(jsonObject1.getString("head_pic")); // 头像
                    grabTheRecordBean.setUserName(jsonObject1.getString("nickname")); // 用户名称
                    grabTheRecordBean.setTime(Long.parseLong(jsonObject1.getString("create_time"))); // 时间戳
                    grabTheRecordBean.setContent(jsonObject1.getString("details")); // 内容
                    grabTheRecordBean.setVideoUrl(jsonObject1.getString("url")); // 视频回放地址
                    RoomBean roomBean = GsonUtil.GsonToBean(jsonObject1.getString("roomBean"), RoomBean.class);
                    grabTheRecordBean.setRoomBean(roomBean); // 设置房间

                    tempGrabTheRecordBeans.add(grabTheRecordBean);
                }
                setData(tempGrabTheRecordBeans);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
