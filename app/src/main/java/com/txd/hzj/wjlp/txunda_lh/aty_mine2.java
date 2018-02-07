package com.txd.hzj.wjlp.txunda_lh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.wjyp.ExpandTheMemberAty;
import com.txd.hzj.wjlp.wjyp.LMSJAty;
import com.txd.hzj.wjlp.wjyp.TZSAty;

/**
 * by Txunda_LH on 2018/1/30.
 */

public class aty_mine2 extends BaseAty {

    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 广告高度
     */
    private int ads_w = 0;
    private int ads_h = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mine2;
    }

    @OnClick({R.id.im1, R.id.im2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.im1:
                startActivity(new Intent(this, ExpandTheMemberAty.class));

                break;
            case R.id.im2:
                startActivity(new Intent(this, TZSAty.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("代理加盟");
    }

    @Override
    protected void initialized() {
        // 广告宽高
        ads_h = Settings.displayWidth * 800 / 1242;
        ads_w = Settings.displayWidth;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ads_w, ads_h);
        im1.setLayoutParams(layoutParams);
        im2.setLayoutParams(layoutParams);
    }

    @Override
    protected void requestData() {

    }
}
