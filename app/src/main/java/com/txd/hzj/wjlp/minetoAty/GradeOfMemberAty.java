package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.listener.ItemClickForRecyclerView;
import com.txd.hzj.wjlp.minetoAty.adapter.GrowthValueAdapter;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：上午 10:19
 * 描述：会员
 * ===============Txunda===============
 */
public class GradeOfMemberAty extends BaseAty {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    @ViewInject(R.id.app_bar_layout)
    private AppBarLayout app_bar_layout;

    @ViewInject(R.id.growth_value_rv)
    private RecyclerView growth_value_rv;

    private List<String> list;
    private GrowthValueAdapter gvAdapter;

    /**
     * 金牌会员
     */
    @ViewInject(R.id.grade_of_member_tv)
    private TextView grade_of_member_tv;

    /**
     * 普通用户
     */
    @ViewInject(R.id.member_type_tv)
    private TextView member_type_tv;

    /**
     * 成长值(消费金额)
     */
    @ViewInject(R.id.member_growth_value_tv)
    private TextView member_growth_value_tv;

    @ViewInject(R.id.member_title_tv)
    private TextView member_title_tv;

    @ViewInject(R.id.details_for_sb_tv)
    private TextView details_for_sb_tv;

    private int from = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsing_toolbar_layout.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

        if (0 == from) {
            grade_of_member_tv.setVisibility(View.VISIBLE);
            member_type_tv.setVisibility(View.GONE);
            member_growth_value_tv.setText("2017年度成长值:88");
            member_title_tv.setText("会员成长");
            details_for_sb_tv.setVisibility(View.VISIBLE);
        } else {
            grade_of_member_tv.setVisibility(View.GONE);
            member_type_tv.setVisibility(View.VISIBLE);
            member_growth_value_tv.setText("2018年08月10日到期");
            member_title_tv.setText("会员等级");
            details_for_sb_tv.setVisibility(View.GONE);
        }

        growth_value_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        growth_value_rv.setHasFixedSize(true);
        growth_value_rv.setAdapter(gvAdapter);
        // 会员等级跳页
        if (1 == from) {
            gvAdapter.setItemClickForRecyclerView(new ItemClickForRecyclerView() {
                @Override
                public void OnItemClick(View v, int pos) {

                    L.e("=====点击下标=====", String.valueOf(pos));

                    if (2 <= pos) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("order_type", 0);
                        startActivity(PayForAppAty.class, bundle);
                    }
                }
            });
        }
    }

    @Override
    @OnClick({R.id.details_for_sb_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.details_for_sb_tv:// 成长值
                if (0 == from) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("from", 4);
                    startActivity(ParticularsUsedByTricketAty.class, bundle);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_grade_of_member;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 0);
        list = new ArrayList<>();
        gvAdapter = new GrowthValueAdapter(this, list, from);
    }

    @Override
    protected void requestData() {

    }
}
