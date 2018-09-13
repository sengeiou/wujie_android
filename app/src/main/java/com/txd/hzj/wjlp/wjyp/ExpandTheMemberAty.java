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
 * by Txunda_LH on 2018/1/22.
 */

public class ExpandTheMemberAty extends BaseAty {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private TextView tv1;//代金券申请
    private TextView tv4;//代金券审核
    private TextView tv5;//发放记录 传type 1
    private TextView tv6;//联盟商家列表 传type 2
    private TextView tv7;//推荐商家审核
    private TextView tv9;//物料申请
    private TextView tv10;//下级物料申请


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_expandthemember;
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
        tv4 =  findViewById(R.id.tv4);
        tv5 =  findViewById(R.id.tv5);
        tv6 =  findViewById(R.id.tv6);
        tv7 =  findViewById(R.id.tv7);
        tv9 =  findViewById(R.id.tv9);
        tv10 =  findViewById(R.id.tv10);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandTheMemberAty.this, GetTicketAty.class));
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandTheMemberAty.this, MyTicketCheckAty.class));

            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.setClass(ExpandTheMemberAty.this, RecordAty.class);
                startActivity(intent);

            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "2");
                intent.setClass(ExpandTheMemberAty.this, RecordAty.class);
                startActivity(intent);

            }
        });
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandTheMemberAty.this, MerchantCheckAty.class));
            }
        });

        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandTheMemberAty.this, MaterialApplyAty.class));
            }
        });

        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandTheMemberAty.this, LowerMaterialAuditAty.class));
            }
        });
    }

    @Override
    protected void requestData() {

    }

}
