package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.GroupMemberAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 1:21
 * 描述：4-3参团
 * ===============Txunda===============
 */
public class CreateGroupAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 团圆列表
     */
    @ViewInject(R.id.group_member_rv)
    private RecyclerView group_member_rv;

    /**
     * 团状态
     */
    @ViewInject(R.id.group_status_tv)
    private TextView group_status_tv;

    /**
     * 一键开团，参团，团长不能操作
     */
    @ViewInject(R.id.group_operation_tv)
    private TextView group_operation_tv;
    private int status = 0;

    private GroupMemberAdapter groupMemberAdapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("参团");
        if (0 == status) {
            group_operation_tv.setText("我是团长");
            group_operation_tv.setEnabled(false);
            group_operation_tv.setBackgroundResource(R.drawable.shape_un_operation);
            group_status_tv.setText("还差一人");
        } else if (1 == status) {
            group_operation_tv.setText("我要参团");
            group_operation_tv.setEnabled(true);
            group_status_tv.setText("还差一人");
            group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
        } else {
            group_operation_tv.setText("一键开团");
            group_operation_tv.setEnabled(true);
            group_status_tv.setText("团已满");
            group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
        }

        group_member_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        group_member_rv.setHasFixedSize(true);
        group_member_rv.setItemAnimator(new DefaultItemAnimator());
        group_member_rv.setAdapter(groupMemberAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_create_group;
    }

    @Override
    protected void initialized() {
        status = getIntent().getIntExtra("status", 0);
        data = new ArrayList<>();
        groupMemberAdapter = new GroupMemberAdapter(this, data);
    }

    @Override
    protected void requestData() {

    }
}
