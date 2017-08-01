package com.txd.hzj.wjlp.minetoAty.mell;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

public class MellGoodsListAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.mell_conter_goods_rv)
    private RecyclerView mell_conter_goods_rv;

    private MellGoodsAdapter mellGoodsAdapter;
    private int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("货品列表");

        mell_conter_goods_rv.setLayoutManager(new GridLayoutManager(this, 2));
        mell_conter_goods_rv.addItemDecoration(new GridDividerItemDecoration(height,
                ContextCompat.getColor(this, R.color.bg_color)));
        mell_conter_goods_rv.setHasFixedSize(true);
        mell_conter_goods_rv.setAdapter(mellGoodsAdapter);
        mellGoodsAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(QRCodeForMellGoodsAty.class, null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_goods_list;
    }

    @Override
    protected void initialized() {
        mellGoodsAdapter = new MellGoodsAdapter(this, 0);
        height = ToolKit.dip2px(this, 4);
    }

    @Override
    protected void requestData() {

    }
}
