package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * by Txunda_LH on 2018/1/17.
 */

public class ApplyAty extends BaseAty {

    private TextView tv_title;
    private TextView tv_1;
    private TextView tv_submit;
    private LinearLayout layout_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_apply);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        layout_1 = (LinearLayout) findViewById(R.id.layout_1);
        tv_title.setText("拓展商");
        if (getIntent().getStringExtra("type").equals("0")) {
            tv_1.setVisibility(View.VISIBLE);
        } else {
            layout_1.setVisibility(View.VISIBLE);
            tv_submit.setVisibility(View.VISIBLE);
        }
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApplyAty.this, PayVipAty.class));
            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}
