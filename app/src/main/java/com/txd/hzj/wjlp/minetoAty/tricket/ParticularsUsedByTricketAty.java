package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.minetoAty.adapter.StickyExampleAdapter;

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

    @ViewInject(R.id.tv_sticky_header_view)
    private TextView tv_sticky_header_view;

    private List<TricketDetailks> list;

    private StickyExampleAdapter stickyExampleAdapter;

    /**
     * 1.购物券使用明细
     * 2.积分明细
     * 3.余额明细
     * 4.成长值明细
     */
    private int from = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (1 == from) {
            titlt_conter_tv.setText("购物券使用明细");
        } else if (2 == from) {
            titlt_conter_tv.setText("积分明细");
        } else if(3 == from){
            titlt_conter_tv.setText("余额明细");
        } else {
            titlt_conter_tv.setText("成长值明细");
        }

        tricket_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tricket_rv.setAdapter(stickyExampleAdapter);

        tricket_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tv_sticky_header_view.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, tv_sticky_header_view.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tv_sticky_header_view.getMeasuredHeight();

                    if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tv_sticky_header_view.setTranslationY(dealtY);
                        } else {
                            tv_sticky_header_view.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
                        tv_sticky_header_view.setTranslationY(0);
                    }
                }

            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_particulars_used_by_tricket;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 1);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < 5) {
                list.add(new TricketDetailks(
                        "2017-06", "积分转余额 赠送", "2017-06-23", "+300"));
            } else if (i < 10) {
                list.add(new TricketDetailks(
                        "2017-07", "积分转余额 赠送", "2017-07-23", "+500"));
            } else if (i < 15) {
                list.add(new TricketDetailks(
                        "2017-08", "积分转余额 赠送", "2017-08-23", "+700"));
            }
        }
        stickyExampleAdapter = new StickyExampleAdapter(this, list);
    }

    @Override
    protected void requestData() {

    }
}
