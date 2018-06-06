package com.txd.hzj.wjlp.distribution.shopmanage;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 小店上货
 */
public class ShopExhibit extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_for_store;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("小店上货");
    }

    @Override
    protected void requestData() {

    }
}
