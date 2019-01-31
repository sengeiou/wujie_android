package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.PlayerPeopleAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.PlayerPeopleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：玩家列表界面
 */
public class PlayerPeopleListActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("玩家列表");
        initShowData();
    }

    @Override
    protected void requestData() {
    }

    /**
     * 设置模拟数据
     */
    private void initShowData() {
        List<PlayerPeopleBean> list = new ArrayList<>();
        PlayerPeopleBean playerPeopleBean;
        for (int i = 0; i < 20; i++) {
            playerPeopleBean = new PlayerPeopleBean();
            playerPeopleBean.set_id(i);
            playerPeopleBean.setHeaderUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            playerPeopleBean.setName("测试名称" + i);
            playerPeopleBean.setTime(System.currentTimeMillis());
            list.add(playerPeopleBean);
        }

        PlayerPeopleAdapter adapter = new PlayerPeopleAdapter(this, list);
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
