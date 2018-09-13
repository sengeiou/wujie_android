package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/23.
 */

public class LMSJAty extends BaseAty {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private TextView tv1;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv9;
    private TextView tv10;
    private TextView tv_post;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_lmsj;
    }

    @Override
    protected void initialized() {
        toolbar =  findViewById(R.id.toolbar);
        collapsing_toolbar_layout =  findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout =  findViewById(R.id.app_bar_layout);
        setSupportActionBar(toolbar);
        collapsing_toolbar_layout.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

        tv1 =  findViewById(R.id.tv1);
        tv3 =  findViewById(R.id.tv3);
        tv4 =  findViewById(R.id.tv4);
        tv5 =  findViewById(R.id.tv5);
        tv9 =  findViewById(R.id.tv9);
        tv10 =  findViewById(R.id.tv10);
        tv_post =  findViewById(R.id.tv_post);
        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LMSJAty.this, ApplyPostAty.class));
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LMSJAty.this, GetTicketAty.class));
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LMSJAty.this, MyTicketCheckAty.class));

            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.setClass(LMSJAty.this, RecordAty.class);
                startActivity(intent);

            }
        });

        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LMSJAty.this, MaterialApplyAty.class));
            }
        });

        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LMSJAty.this, LowerMaterialAuditAty.class));
            }
        });
    }

    @Override
    protected void requestData() {

    }

}
