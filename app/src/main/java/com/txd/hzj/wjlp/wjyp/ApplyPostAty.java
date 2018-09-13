package com.txd.hzj.wjlp.wjyp;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/23.
 */

public class ApplyPostAty extends BaseAty {
    private TextView tv_title;

    private int size = 0;
    private ImageView im4;
    private ImageView image4;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_applypost;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("无界驿站升级");
        im4 =  findViewById(R.id.im4);
        image4 =  findViewById(R.id.image4);
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
    protected void requestData() {

    }

}
