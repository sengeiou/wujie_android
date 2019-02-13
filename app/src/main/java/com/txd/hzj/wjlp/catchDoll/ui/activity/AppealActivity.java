package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.AppealAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.AppealBean;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class AppealActivity extends BaseAty implements AppealAdapter.OnItemSelectListener {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.appeal_list_nRlView)
    public NoScrollRecyclerView appeal_list_nRlView;

    private List<AppealBean> list;
    private AppealAdapter adapter;

    private AppealBean selectAppeal = null; // 选中的原因对象

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_appeal;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("申诉原因");
    }

    @Override
    protected void requestData() {

        Catcher.appealApply(this);

    }

    @OnClick({R.id.titleView_goback_imgv, R.id.appeal_submit_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.appeal_submit_tv:
                if (selectAppeal != null) {
                    showToast("您选中了 " + selectAppeal.getCauseStr() + " 项");
                } else {
                    showToast("请选择之后再提交");
                }
                break;
        }
    }

    @Override
    public void selectItem(int position) {
        selectAppeal = list.get(position);
    }

    private void setAppealList(List<AppealBean> list) {
        if (list != null && list.size() > 0) {
            this.list = list;
            adapter = new AppealAdapter(this, list);
            adapter.setOnItemSelectListener(this);
            appeal_list_nRlView.setLayoutManager(new LinearLayoutManager(this));
            appeal_list_nRlView.setAdapter(adapter);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("appealApply")) { // 获取申诉原因列表
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray listJsonArray = jsonObject.getJSONArray("data");
                List<AppealBean> tempList = new ArrayList<>();
                AppealBean appealBean;
                for (int i = 0; i < listJsonArray.length(); i++) {
                    appealBean = new AppealBean();
                    JSONObject jsonObject1 = listJsonArray.getJSONObject(i);
                    appealBean.setId(Integer.parseInt(jsonObject1.getString("k")));
                    appealBean.setCauseStr(jsonObject1.getString("value"));
                    appealBean.setChecked(false);
                    tempList.add(appealBean);
                }
                setAppealList(tempList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
