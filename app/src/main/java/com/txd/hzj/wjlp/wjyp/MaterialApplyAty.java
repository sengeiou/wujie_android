package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MaterialApplyAty extends BaseAty {
    private TextView tv_title;
    private TextView tv_setType;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_materialapply;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_setType =  findViewById(R.id.tv_setType);
        tv_title.setText("物料申请");
        tv_setType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialApplyAty.this, TypeNumberAty.class));
            }
        });
    }

    @Override
    protected void requestData() {

    }

}
