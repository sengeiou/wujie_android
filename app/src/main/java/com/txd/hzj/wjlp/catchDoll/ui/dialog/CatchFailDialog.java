package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：抓取成功弹窗
 */
public class CatchFailDialog extends BaseDialog {

    public CatchFailDialog(@NonNull Context context) {
        super(context);
    }

    public CatchFailDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        int countdownTime;
        String oneTimeStr;
        OnClickListener onOneTimeBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCountdownTime(int countdownTime) {
            this.countdownTime = countdownTime;
            return this;
        }

        public Builder setOnOneTimeBtnClickListener(String oneTimeStr, OnClickListener onOneTimeBtnClickListener) {
            this.oneTimeStr = oneTimeStr;
            this.onOneTimeBtnClickListener = onOneTimeBtnClickListener;
            return this;
        }

        public CatchFailDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CatchFailDialog catchFailDialog = new CatchFailDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_catch_fail, null);

            TextView friendHelpTv = view.findViewById(R.id.dialogCatchSuccess_friendHelp_tv);
            final TextView oneTimeBtnTv = view.findViewById(R.id.dialogCatchSuccess_oneTimeBtn_tv);
            ImageView closeImgv = view.findViewById(R.id.dialogCatchSuccess_close_imgv);

            friendHelpTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (catchFailDialog.isShowing()) {
                        catchFailDialog.dismiss();
                    }
                }
            });

            oneTimeBtnTv.setText(oneTimeStr.isEmpty() ? "再来一局" : oneTimeStr);
            if (onOneTimeBtnClickListener != null) {
                oneTimeBtnTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOneTimeBtnClickListener.onClick(catchFailDialog, DialogInterface.BUTTON_POSITIVE);
                        if (catchFailDialog.isShowing()) {
                            catchFailDialog.dismiss();
                        }
                    }
                });
            }

            // 关闭按钮点击事件
            closeImgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (catchFailDialog.isShowing()) {
                        catchFailDialog.dismiss();
                    }
                }
            });

            // 设置倒计时以及按钮显示和可点击状态
            final CountDownTimer countDownTimer = new CountDownTimer((countdownTime + 1) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    oneTimeBtnTv.setText(new StringBuffer().append(oneTimeStr).append("(").append(millisUntilFinished / 1000).append("s)"));
                }

                @Override
                public void onFinish() {
                    oneTimeBtnTv.setText(oneTimeStr);
                    oneTimeBtnTv.setEnabled(true);
                }
            }.start();
            oneTimeBtnTv.setEnabled(false);

            // Dialog关闭监听，如果关闭了并且倒计时不为空，则清空倒计时
            catchFailDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = catchFailDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 5 * 4;
            window.setAttributes(lp);

            catchFailDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            catchFailDialog.setCancelable(false);

            return catchFailDialog;
        }

    }
}
