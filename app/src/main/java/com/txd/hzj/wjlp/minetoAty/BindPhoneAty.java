package com.txd.hzj.wjlp.minetoAty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class BindPhoneAty extends BaseAty {
    /**
     * 下一步
     * */
    @ViewInject(R.id.btn_next)
    private Button btn_next;
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("换绑手机");
        initEvent();
    }

    private void initEvent() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BindPhoneAty.this,BindNewPhoneAty.class));
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bind_phone_li;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
