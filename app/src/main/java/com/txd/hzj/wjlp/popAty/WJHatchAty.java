package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.popAty.adapter.RedPackageAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 7:51
 * 描述：上市孵化
 * ===============Txunda===============
 */
public class WJHatchAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.hatch_lv)
    private ListViewForScrollView hatch_lv;

    private RedPackageAdapter redPackageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("上市孵化");
        hatch_lv.setAdapter(redPackageAdapter);
        hatch_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(HatchDetailsAty.class, null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_wjhatch_hzj;
    }

    @Override
    protected void initialized() {
        redPackageAdapter = new RedPackageAdapter(this);
    }

    @Override
    protected void requestData() {

    }
}
