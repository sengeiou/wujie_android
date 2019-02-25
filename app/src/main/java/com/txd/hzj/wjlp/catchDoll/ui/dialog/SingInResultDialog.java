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
 * <br>功能描述：签到结果展示弹窗
 */
public class SingInResultDialog extends BaseDialog {

    public SingInResultDialog(@NonNull Context context) {
        super(context);
    }

    public SingInResultDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String contentTypeStr;
        private String showTitleStr;
        private String moneyStr;
        private String btnStr;
        private boolean winning; // 是否中奖
        private OnClickListener onBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentTypeStr(String contentTypeStr) {
            this.contentTypeStr = contentTypeStr;
            return this;
        }

        public Builder setShowTitleStr(String showTitleStr) {
            this.showTitleStr = showTitleStr;
            return this;
        }

        public Builder setMoneyStr(String moneyStr) {
            this.moneyStr = moneyStr;
            return this;
        }

        public Builder setWinning(boolean winning) {
            this.winning = winning;
            return this;
        }

        public Builder setOnBtnClickListener(String btnStr, OnClickListener onBtnClickListener) {
            this.btnStr = btnStr;
            this.onBtnClickListener = onBtnClickListener;
            return this;
        }

        public SingInResultDialog create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final SingInResultDialog singInResultDialog = new SingInResultDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_sign_in_success, null);

            TextView dialogSingIn_resultAfter_tv = view.findViewById(R.id.dialogSingIn_resultAfter_tv);
            TextView dialogSingIn_resultBefore_tv = view.findViewById(R.id.dialogSingIn_resultBefore_tv);
            TextView dialogSingIn_contentType_tv = view.findViewById(R.id.dialogSingIn_contentType_tv);
            TextView dialogSingIn_money_tv = view.findViewById(R.id.dialogSingIn_money_tv);
            TextView dialogSingIn_iknow_tv = view.findViewById(R.id.dialogSingIn_iknow_tv);

            dialogSingIn_resultAfter_tv.setText(showTitleStr);
            dialogSingIn_resultBefore_tv.setText(showTitleStr);
            dialogSingIn_contentType_tv.setText(contentTypeStr);
            dialogSingIn_money_tv.setText(new StringBuffer().append("+").append(moneyStr));
            // 如果左侧的按钮文字不为null则显示按钮
            if (btnStr != null) {
                // 如果字符串不为空字符串，则设置指定的字符串，否则直接显示原始字符串
                if (!btnStr.isEmpty()) {
                    dialogSingIn_iknow_tv.setText(btnStr);
                }
                // 如果点击事件监听不为空，则直接设置其点击的监听事件
                if (onBtnClickListener != null) {
                    dialogSingIn_iknow_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBtnClickListener.onClick(singInResultDialog, DialogInterface.BUTTON_NEGATIVE);
                            if (singInResultDialog.isShowing()) {
                                singInResultDialog.dismiss();
                            }
                        }
                    });
                } else { // 否则，如果为null则直接隐藏该按钮
                    dialogSingIn_iknow_tv.setVisibility(View.GONE);
                }
            }

            int screenWidth = Config.getScreenWidth((Activity) context);
            int screenHeight = Config.getScreenHeight((Activity) context);
            Window window = singInResultDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
//            lp.height = screenHeight / 3 * 2;
            window.setAttributes(lp);

            singInResultDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            singInResultDialog.setCancelable(true);
            return singInResultDialog;

        }

    }
}
