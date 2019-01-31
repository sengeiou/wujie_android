package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.AppealAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.AppealBean;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;

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

        list = new ArrayList<>();
        AppealBean appealBean;
        for (int i = 0; i < 7; i++) {
            appealBean = new AppealBean();
            appealBean.setCauseStr("申诉问题" + (i + 1));
            appealBean.setChecked(false);
            list.add(appealBean);
        }
        AppealAdapter adapter = new AppealAdapter(this, list);
        adapter.setOnItemSelectListener(this);
        appeal_list_nRlView.setLayoutManager(new LinearLayoutManager(this));
        appeal_list_nRlView.setAdapter(adapter);
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
                showToast("先别着急提交，后台还没开发完呢！！");
                break;
        }
    }

    @Override
    public void selectItem(int position) {
        showToast("您选择了 " + list.get(position).getCauseStr() + " 项");
    }
}
