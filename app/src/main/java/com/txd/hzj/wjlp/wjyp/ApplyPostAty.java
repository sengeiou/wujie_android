package com.txd.hzj.wjlp.wjyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * by Txunda_LH on 2018/1/23.
 */

public class ApplyPostAty
        extends BaseAty {
    private TextView tv_title;

    private int size = 0;
    private ImageView im4;
    private ImageView image4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_applypost);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("xfte驿站升级");
        im4 = (ImageView) findViewById(R.id.im4);
        image4 = (ImageView) findViewById(R.id.image4);
        size = getResources().getDisplayMetrics().widthPixels;
        size = size / 2 - 15;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 5;
        im4.setLayoutParams(layoutParams);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        image4.setLayoutParams(layoutParams);
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}
