package com.txd.hzj.wjlp.new_wjyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;


public class InvoiceAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;


    @ViewInject(R.id.cb1)
    private CheckBox cb1;
    @ViewInject(R.id.cb2)
    private CheckBox cb2;
    @ViewInject(R.id.layout1)
    private LinearLayout linearLayout1;
    @ViewInject(R.id.layout2)
    private LinearLayout linearLayout2;
    @ViewInject(R.id.layout)
    private LinearLayout layout;
    @ViewInject(R.id.rb1)
    private RadioButton rb1;
    @ViewInject(R.id.rb2)
    private RadioButton rb2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("发票");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("完成");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_invoice;
    }

    @Override
    protected void initialized() {
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
            }
        });
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb2.setChecked(false);
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb1.setChecked(false);
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void requestData() {

    }
}
