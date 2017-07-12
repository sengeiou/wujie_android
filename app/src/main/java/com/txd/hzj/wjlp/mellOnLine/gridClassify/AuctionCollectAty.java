package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.LimitAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：下午 1:41
 * 描述：竞拍汇(8-1竞拍汇)
 * ===============Txunda===============
 */
public class AuctionCollectAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int type;
    private List<String> list;

    @ViewInject(R.id.left_tv)
    private TextView left_tv;
    @ViewInject(R.id.left_view)
    private View left_view;

    @ViewInject(R.id.righr_tv)
    private TextView right_tv;

    @ViewInject(R.id.right_view)
    private View right_view;

    @ViewInject(R.id.suction_collect_gv)
    private GridViewForScrollView suction_collect_gv;
    private LimitAdapter limitAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("竞拍汇");
        changeViewStatus(0);
        suction_collect_gv.setAdapter(limitAdapter);
        suction_collect_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(AuctionGoodsDetailsAty.class, null);
            }
        });
    }

    private void changeViewStatus(int i) {
        left_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        right_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        if (0 == i) {
            left_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        } else {
            right_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    @Override
    @OnClick({R.id.left_lin_layout, R.id.right_lin_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_lin_layout:// 左(今日拍卖)
                changeViewStatus(0);
                limitAdapter.setType(0);
                limitAdapter.notifyDataSetChanged();
                break;
            case R.id.right_lin_layout:// 右(拍卖预展)
                changeViewStatus(1);
                limitAdapter.setType(1);
                limitAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_collect;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        type = getIntent().getIntExtra("type", 0);
        limitAdapter = new LimitAdapter(list, this, 0);
    }

    @Override
    protected void requestData() {

    }

}
