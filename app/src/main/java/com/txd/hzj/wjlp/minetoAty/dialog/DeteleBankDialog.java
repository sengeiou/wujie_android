package com.txd.hzj.wjlp.minetoAty.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：19:31
 * 描述：
 * ===============Txunda===============
 */

public class DeteleBankDialog extends Dialog implements View.OnClickListener {
    private ClickListener clickListener;

    public DeteleBankDialog(@NonNull Context context, ClickListener clickListener) {
        super(context, R.style.dialog_style);
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_bank_layout);
        findViewById(R.id.delelte_bank_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onClick(view);
        }
    }

    public interface ClickListener {
        void onClick(View view);
    }

}
