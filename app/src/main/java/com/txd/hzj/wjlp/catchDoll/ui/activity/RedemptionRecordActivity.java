package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.MoneyRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.MoneyRecordingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：兑换记录
 */
public class RedemptionRecordActivity extends BaseAty {

//    @ViewInject(R.id.titleView_goback_imgv)

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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("兑换记录");
    }

    @Override
    protected void requestData() {
        List<MoneyRecordingBean> list = new ArrayList<>();
        MoneyRecordingBean moneyRecordingBean;
        for (int i = 0; i < 20; i++) {
            moneyRecordingBean = new MoneyRecordingBean();
            moneyRecordingBean.setContent("兑换银两");
            moneyRecordingBean.setTime(1545815792 * 1000L);
            moneyRecordingBean.setPrice((i + 1) * 10);
            list.add(moneyRecordingBean);
        }
        MoneyRecordingAdapter adapter = new MoneyRecordingAdapter(this, list, 0);
        list_show_reView.setLayoutManager(new LinearLayoutManager(this));
        list_show_reView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // 添加默认分割线
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
}
