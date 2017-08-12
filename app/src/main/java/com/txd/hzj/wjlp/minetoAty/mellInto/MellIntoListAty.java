package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/11 0011
 * 时间：上午 11:41
 * 描述：推荐列表
 * ===============Txunda===============
 */
public class MellIntoListAty extends BaseAty {

    /**
     * 推荐列表
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.mell_into_lv)
    private ListView mell_into_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐列表");
    }

    @Override
    @OnClick({R.id.to_mell_into_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.to_mell_into_tv:
                startActivity(MerchantWillMoveIntoAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_into_list_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
