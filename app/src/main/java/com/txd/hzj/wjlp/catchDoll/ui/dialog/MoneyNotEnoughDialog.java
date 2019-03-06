package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两余额不足弹窗
 */
public class MoneyNotEnoughDialog extends BaseDialog {

    public MoneyNotEnoughDialog(@NonNull Context context) {
        super(context);
    }

    public MoneyNotEnoughDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String btnStr;
        OnClickListener onBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOnBtnClickListener(String btnStr, OnClickListener onBtnClickListener) {
            this.btnStr = btnStr;
            this.onBtnClickListener = onBtnClickListener;
            return this;
        }

        public MoneyNotEnoughDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final MoneyNotEnoughDialog moneyNotEnoughDialog = new MoneyNotEnoughDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_money_not_enough, null);

            TextView btnTv = view.findViewById(R.id.dialogmoneyNotEnough_btn_tv);
            btnTv.setText(btnStr.isEmpty() ? "购买银两" : btnStr);
            btnTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBtnClickListener != null) {
                        onBtnClickListener.onClick(moneyNotEnoughDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                    if (moneyNotEnoughDialog.isShowing()) {
                        moneyNotEnoughDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = moneyNotEnoughDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 6 * 5;
            window.setAttributes(lp);

            moneyNotEnoughDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            moneyNotEnoughDialog.setCancelable(true);
            return moneyNotEnoughDialog;

        }

    }
}
