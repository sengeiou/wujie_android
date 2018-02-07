package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MaterialApplyAty extends BaseAty {
    private TextView tv_title;
    private TextView tv_setType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_materialapply);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_setType = (TextView) findViewById(R.id.tv_setType);
        tv_title.setText("物料申请");
        tv_setType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialApplyAty.this, TypeNumberAty.class));
            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}
