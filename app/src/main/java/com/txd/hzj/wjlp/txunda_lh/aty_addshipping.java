package com.txd.hzj.wjlp.txunda_lh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.txunda_lh.http.AfterSale;

/**
 * by Txunda_LH on 2017/12/12.
 */

public class aty_addshipping extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private String id;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.et_number)
    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("运单信息");
    }

    @OnClick({R.id.layout, R.id.submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择快递");
                startActivityForResult(TextListAty.class, bundle, 888);
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(id)) {
                    showToast("请选择快递！");
                    return;
                }
                if (TextUtils.isEmpty(et_number.getText().toString())) {
                    showToast("请输入快递单号！");
                    return;
                }
                AfterSale.addShipping(id, et_number.getText().toString(), getIntent().getStringExtra("id"), this);
                showProgressDialog();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_addshipping;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addShipping")) {
            showToast("添加成功！");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888) {
            id = data.getStringExtra("id");
            tv_name.setText(data.getStringExtra("express"));

        }
    }
}
