package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvListener;
import com.txd.hzj.wjlp.minetoAty.adapter.TricketAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：下午 3:48
 * 描述：15-4购物券使用明细
 * ===============Txunda===============
 */
public class ParticularsUsedByTricketAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 明细列表
     */
    @ViewInject(R.id.tricket_rv)
    private RecyclerView tricket_rv;

    private TricketAdapter tricketAdapter;

    private List<TricketDetailks> list;
    private ItemHeaderDecoration mDecoration;

    private int from = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (1 == from) {
            titlt_conter_tv.setText("购物券使用明细");
        } else {
            titlt_conter_tv.setText("积分明细");
        }

        tricket_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tricketAdapter = new TricketAdapter(this, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {

            }
        });
        tricket_rv.setAdapter(tricketAdapter);
        mDecoration = new ItemHeaderDecoration(this, list);
        tricket_rv.addItemDecoration(mDecoration);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_particulars_used_by_tricket;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 1);
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new TricketDetailks("2016年" + (i + 3) + "月", String.valueOf(i), true, "", "", "-1"));
            for (int j = 0; j < 3; j++) {
                list.add(new TricketDetailks("积分转余额 赠送", String.valueOf(j), false, "2017-06-23", "300.00",
                        String.valueOf(j)));
            }
        }
    }

    @Override
    protected void requestData() {

    }
}
