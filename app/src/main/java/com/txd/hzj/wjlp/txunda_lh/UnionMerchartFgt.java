package com.txd.hzj.wjlp.txunda_lh;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * by Txunda_LH on 2018/1/22.
 */

public class UnionMerchartFgt extends BaseFgt {
    private int size = 0;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;
    @ViewInject(R.id.image1)
    private ImageView image1;
    @ViewInject(R.id.image2)
    private ImageView image2;
    @ViewInject(R.id.image3)
    private ImageView image3;

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_unionmerchart;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        size = getResources().getDisplayMetrics().widthPixels;
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
}
