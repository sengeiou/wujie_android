package com.txd.hzj.wjlp.mellOnLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：19:17
 * 描述：
 * ===============Txunda===============
 */

public class AuctionSingUpDialog extends Dialog implements View.OnClickListener {
    private SignUpClick signUpClick;

    public AuctionSingUpDialog(@NonNull Context context, SignUpClick signUpClick) {
        super(context, R.style.dialog_style);
        this.signUpClick = signUpClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_auction_sign_up_layout);
        findViewById(R.id.submit_auction_price_tv).setOnClickListener(this);
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
