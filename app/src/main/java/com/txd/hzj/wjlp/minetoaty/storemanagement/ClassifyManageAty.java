package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 16:13
 * 功能描述：
 */
public class ClassifyManageAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.time_select_img)
    private ImageView time_select_img;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.addClassifyTv)
    private TextView addClassifyTv;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_classify_manage;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("分类管理");
        time_select_img.setVisibility(View.VISIBLE);
        time_select_img.setImageResource(R.drawable.icon_trash);
    }

    @Override
    protected void requestData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    }

    @Override
    @OnClick({R.id.time_select_img, R.id.addClassifyTv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.time_select_img:

                break;
            case R.id.addClassifyTv:

                break;

        }
    }
}
