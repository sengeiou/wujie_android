package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/20.
 */

public class EditPresMangAty extends BaseAty {
    private TextView tv_title;
    private TextView tv_choose;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_editpresmang;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_choose =  findViewById(R.id.tv_choose);
        tv_title.setText("编辑拓展员");
        tv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPresMangAty.this, ChoosePresMangAty.class));
            }
        });
    }

    @Override
    protected void requestData() {

    }

}
