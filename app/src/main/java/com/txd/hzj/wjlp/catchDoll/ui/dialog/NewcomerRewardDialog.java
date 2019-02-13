package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
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

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：新人奖励Dialog
 */
public class NewcomerRewardDialog extends Dialog {

    public NewcomerRewardDialog(@NonNull Context context) {
        super(context);
    }

    public NewcomerRewardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String btnStr;
        private String moneyStr;
        private OnClickListener onBtnClickListener;
        private boolean cancelable = false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOnClickListener(String btnStr, OnClickListener onBtnClickListener) {
            this.btnStr = btnStr;
            this.onBtnClickListener = onBtnClickListener;
            return this;
        }

        public Builder setMoneyStr(String moneyStr) {
            this.moneyStr = moneyStr;
            return this;
        }

        public NewcomerRewardDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final NewcomerRewardDialog appUpgradeDialog = new NewcomerRewardDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_newcomer_reward, null);
            TextView dialogNewcomer_moneyAfter_tv = view.findViewById(R.id.dialogNewcomer_moneyAfter_tv);
            TextView dialogNewcomer_moneyBefore_tv = view.findViewById(R.id.dialogNewcomer_moneyBefore_tv);
            TextView dialogNewcomer_receive_tv = view.findViewById(R.id.dialogNewcomer_receive_tv);

            dialogNewcomer_moneyAfter_tv.setText(moneyStr);
            dialogNewcomer_moneyBefore_tv.setText(moneyStr);
            // 如果左侧的按钮文字不为null则显示按钮
            if (btnStr != null) {
                // 如果字符串不为空字符串，则设置指定的字符串，否则直接显示原始字符串
                if (!btnStr.isEmpty()) {
                    dialogNewcomer_receive_tv.setText(btnStr);
                }
                // 如果点击事件监听不为空，则直接设置其点击的监听事件
                if (onBtnClickListener != null) {
                    dialogNewcomer_receive_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBtnClickListener.onClick(appUpgradeDialog, DialogInterface.BUTTON_NEGATIVE);
                            if (appUpgradeDialog.isShowing()) {
                                appUpgradeDialog.dismiss();
                            }
                        }
                    });
                } else { // 否则，如果为null则直接隐藏该按钮
                    dialogNewcomer_receive_tv.setVisibility(View.GONE);
                }
            }

            int screenWidth = Config.getScreenWidth((Activity) context);
            int screenHeight = Config.getScreenHeight((Activity) context);
            Window window = appUpgradeDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 6 * 5;
            lp.height = screenHeight / 2 * 1;
            window.setAttributes(lp);

            appUpgradeDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            appUpgradeDialog.setCancelable(cancelable);
            return appUpgradeDialog;
        }

    }

}
