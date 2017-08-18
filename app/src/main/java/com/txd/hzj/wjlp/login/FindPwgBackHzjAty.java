package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.tools.RegexUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：上午 9:20
 * 描述：找回密码
 * ===============Txunda===============
 */
public class FindPwgBackHzjAty extends BaseAty {
    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("重置登录密码");
    }

    @Override
    @OnClick({R.id.to_next_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_next_tv:// 下一步
                String phone = phone_ev.getText().toString();
                if (!RegexUtils.checkPhone(phone)) {
                    onErrorTip("请检查手机号");
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                startActivity(ReSetPwdAty.class, bundle);
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_find_pwg_back_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
