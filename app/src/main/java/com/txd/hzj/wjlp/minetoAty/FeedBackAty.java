package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：上午 9:24
 * 描述：意见反馈
 * ===============Txunda===============
 */
public class FeedBackAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.feed_back_rv)
    private RecyclerView feed_back_rv;
    private List<String> horizontal_list;
    private HorizontalAdapter horizontalAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("意见反馈");

        // 设置布局方式
        feed_back_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 默认动画
        feed_back_rv.setItemAnimator(new DefaultItemAnimator());
        feed_back_rv.setHasFixedSize(true);
        feed_back_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_feed_back;
    }

    @Override
    protected void initialized() {
        horizontal_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            horizontal_list.add("问题类型");
        }
        horizontalAdapter = new HorizontalAdapter(horizontal_list, this);
    }

    @Override
    protected void requestData() {

    }
}
