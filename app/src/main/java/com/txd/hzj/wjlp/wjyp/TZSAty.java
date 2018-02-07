package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import com.txd.hzj.wjlp.R;
import android.view.View;
import android.widget.TextView;

/**
 * by Txunda_LH on 2018/1/18.
 */

public class TZSAty extends BaseAty {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private TextView tv1;//代金券申请
    private TextView tv2;//代金券审核
    private TextView tv3;//我的代金券
    private TextView tv4;//代金券审核
    private TextView tv5;//发放记录 传type 1
    private TextView tv6;//联盟商家列表 传type 2
    private TextView tv7;//推荐商家审核
    private TextView tv8;//拓展员管理
    private TextView tv9;//物料申请
    private TextView tv10;//平台物料申请
    private TextView tv11;//下级物料申请

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_tuozhanshang);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv10 = (TextView) findViewById(R.id.tv10);
        tv11 = (TextView) findViewById(R.id.tv11);
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
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, GetTicketAty.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, TerraceTicketCheckAty.class));
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, MyTicketAty.class));
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, MyTicketCheckAty.class));

            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.setClass(TZSAty.this, RecordAty.class);
                startActivity(intent);

            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "2");
                intent.setClass(TZSAty.this, RecordAty.class);
                startActivity(intent);

            }
        });
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, MerchantCheckAty.class));
            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, PersonnelManagementAty.class));

            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, MaterialApplyAty.class));
            }
        });
        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, PlatformMaterialAuditAty.class));
            }
        });
        tv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TZSAty.this, LowerMaterialAuditAty.class));
            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}
