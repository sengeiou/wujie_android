package com.txd.hzj.wjlp.mellOffLine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/12 0012
 * 时间：上午 9:41
 * 描述：举报商家得红包
 * ===============Txunda===============
 */
public class MellReportedAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 举报类型
     */
    @ViewInject(R.id.report_type_tv)
    private TextView report_type_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("举报商家");
    }

    @Override
    @OnClick({R.id.report_type_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.report_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title","举报类型");
                startActivityForResult(TextListAty.class,bundle,100);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_reported_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
            return;
        if(RESULT_OK == resultCode){
            if(100 == requestCode){
                String type = data.getStringExtra("type");
                report_type_tv.setText(type);
            }
        }
    }
}
