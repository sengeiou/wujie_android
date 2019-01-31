package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.MoneyRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.bean.MoneyRecordingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两变动记录Fragment
 */
@SuppressLint("ValidFragment")
public class MoneyRecordingFragment extends BaseFgt {

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

    private int type; // 0:获得 1:消耗

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
        activityList_show_inc.setVisibility(View.GONE);
        setListData();
    }

    @Override
    protected void immersionInit() {
    }

    private void setListData() {

        List<MoneyRecordingBean> list = new ArrayList<>();
        MoneyRecordingBean moneyRecordingBean;
        for (int i = 0; i < 20; i++) {
            moneyRecordingBean = new MoneyRecordingBean();
            moneyRecordingBean.setContent(type == 0 ? "签到领银两" : "房间游戏消耗");
            moneyRecordingBean.setTime(1545815792 * 1000L);
            moneyRecordingBean.setPrice((i + 1) * 10);
            list.add(moneyRecordingBean);
        }
        list = type == 0 ? null : list;
        // 设置空数据显示的界面
        if (list == null || list.size() <= 0) {
            showNullData(list_show_reView, list_nullData_llayout, list_nullDataImg_imgv, list_nullDataMsg_tv, R.mipmap.icon_money_recording_null, "暂无银两记录哦~");
        }
        MoneyRecordingAdapter adapter = new MoneyRecordingAdapter(getActivity(), list, type);
        list_show_reView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_show_reView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)); // 添加默认分割线
        list_show_reView.setAdapter(adapter);

    }

}
