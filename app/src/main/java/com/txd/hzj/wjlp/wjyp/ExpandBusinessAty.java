package com.txd.hzj.wjlp.wjyp;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/17.
 */

public class ExpandBusinessAty extends BaseAty {
    private int size = 0;
    private TextView tv_title;
    private ImageView im1;
    private ImageView im2;
    private ImageView im3;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_expandbusiness;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("拓展商申请");
        im1 =  findViewById(R.id.im1);
        im2 =  findViewById(R.id.im2);
        im3 =  findViewById(R.id.im3);
        image1 =  findViewById(R.id.image1);
        image2 =  findViewById(R.id.image2);
        image3 =  findViewById(R.id.image3);
        size=getResources().getDisplayMetrics().widthPixels;
        size = size / 2 - 15;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 5;
        im1.setLayoutParams(layoutParams);
        im2.setLayoutParams(layoutParams);
        im3.setLayoutParams(layoutParams);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        image1.setLayoutParams(layoutParams);
        image2.setLayoutParams(layoutParams);
        image3.setLayoutParams(layoutParams);
    }

    @Override
    protected void requestData() {

    }

}
