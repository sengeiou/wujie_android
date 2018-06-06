package com.txd.hzj.wjlp.distribution;

import android.os.Bundle;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 我的店铺
 */
public class PersonalStores extends BaseAty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_stores);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
