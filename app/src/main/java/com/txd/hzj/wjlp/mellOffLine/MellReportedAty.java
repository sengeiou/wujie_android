package com.txd.hzj.wjlp.mellOffLine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
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

    private String merchant_id = "";

    private MerchantPst merchantPst;
    private String report_type_id = "";

    @ViewInject(R.id.report_content_tv)
    private EditText report_content_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("举报商家");
    }

    @Override
    @OnClick({R.id.report_type_layout, R.id.report_mell_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.report_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "举报类型");
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
            case R.id.report_mell_tv:
                if (!Config.isLogin()) {
                    toLogin();
                }
                String report_content = report_content_tv.getText().toString().trim();
                merchantPst.report(report_type_id, report_content, merchant_id);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_reported_hzj;
    }

    @Override
    protected void initialized() {
        merchant_id = getIntent().getStringExtra("merchant_id");
        merchantPst = new MerchantPst(this);
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("report")) {
            showRightTip("举报成功");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                String type = data.getStringExtra("type");
                report_type_tv.setText(type);
                report_type_id = data.getStringExtra("report_type_id");
            }
        }
    }
}
