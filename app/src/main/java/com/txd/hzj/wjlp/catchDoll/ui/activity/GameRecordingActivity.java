package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.GameRecordingAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.GameRecordingBean;

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

    private List<GameRecordingBean> list;

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
        list = new ArrayList<>();
        GameRecordingBean gameRecordingBean;
        for (int i = 0; i < 20; i++) {
            gameRecordingBean = new GameRecordingBean();
            gameRecordingBean.setHeaderUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            gameRecordingBean.setName("巴拉巴拉不知道放啥");
            gameRecordingBean.setTime(1546481642 * 1000L);
            gameRecordingBean.setType(i % 2);
            list.add(gameRecordingBean);
        }
        GameRecordingAdapter adapter = new GameRecordingAdapter(list, this);
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
}
