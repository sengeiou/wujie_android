package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.txd.hzj.wjlp.R;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * type 0-1-2 拓展商 拓展员 联盟商家
 * <p>
 * by Txunda_LH on 2018/1/17.
 */

public class WebViewAty extends BaseAty {

    private WebView webview;
    private TextView tv_title;
    private TextView tv_submit;
    private Intent mIntent;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_tz_webview);
        mIntent = getIntent();
        type = mIntent.getStringExtra("type");
        webview = (WebView) findViewById(R.id.webview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        switch (type) {
            case "0": {
                tv_title.setText("拓展商说明");
                tv_submit.setText("申请成为拓展商");
                break;
            }
            case "1": {
                tv_title.setText("拓展员说明");
                tv_submit.setText("申请成为拓展员");
                break;
            }
            case "2": {
                tv_title.setText("联盟商家说明");
                tv_submit.setText("申请成为联盟商家");
                break;
            }
        }
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "0": {
                        Intent intent = new Intent();
                        intent.setClass(WebViewAty.this, ExpandBusinessAty.class);
                        startActivity(intent);
                        break;
                    }
                    case "1": {


                        break;
                    }
                    case "2": {


                        break;
                    }
                }
            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return Color.BLACK;
    }
}
