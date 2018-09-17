package com.txd.hzj.wjlp.mellonLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.txd.hzj.wjlp.R;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：19:17
 * 描述：
 */

public class AuctionSingUpDialog extends Dialog implements View.OnClickListener {
    private SignUpClick signUpClick;
    public EditText editText;

    public EditText getEditText() {
        return editText;
    }

    public AuctionSingUpDialog(@NonNull Context context, SignUpClick signUpClick) {
        super(context, R.style.dialog_style);
        this.signUpClick = signUpClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_auction_sign_up_layout);
        findViewById(R.id.submit_auction_price_tv).setOnClickListener(this);
        editText = findViewById(R.id.auction_price_ev);
    }

    @Override
    public void onClick(View view) {
        if (signUpClick != null) {
            signUpClick.onClick(view);
        }
    }


    public interface SignUpClick {
        void onClick(View view);
    }

}
