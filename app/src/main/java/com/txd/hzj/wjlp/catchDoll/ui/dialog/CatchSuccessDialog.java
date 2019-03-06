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
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：抓取成功弹窗
 */
public class CatchSuccessDialog extends BaseDialog {

    public CatchSuccessDialog(@NonNull Context context) {
        super(context);
    }

    public CatchSuccessDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String imageUrl;
        int countdownTime;
        String oneTimeStr;
        OnClickListener onOneTimeBtnClickListener;
        String helpFriendStr;
        OnClickListener onHelpFriendBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setCountdownTime(int countdownTime) {
            this.countdownTime = countdownTime;
            return this;
        }

        public Builder setOnHelpFriendBtnClickListener(String helpFriendStr, OnClickListener onHelpFriendBtnClickListener) {
            this.helpFriendStr = helpFriendStr;
            this.onHelpFriendBtnClickListener = onHelpFriendBtnClickListener;
            return this;
        }

        public Builder setOnOneTimeBtnClickListener(String oneTimeStr, OnClickListener onOneTimeBtnClickListener) {
            this.oneTimeStr = oneTimeStr;
            this.onOneTimeBtnClickListener = onOneTimeBtnClickListener;
            return this;
        }

        public CatchSuccessDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CatchSuccessDialog catchSuccessDialog = new CatchSuccessDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_catch_success, null);

            ImageView showImgv = view.findViewById(R.id.dialogCatchSuccess_show_imgv);
            TextView friendHelpTv = view.findViewById(R.id.dialogCatchSuccess_friendHelp_tv);
            final TextView oneTimeBtnTv = view.findViewById(R.id.dialogCatchSuccess_oneTimeBtn_tv);
            ImageView closeImgv = view.findViewById(R.id.dialogCatchSuccess_close_imgv);

            GlideUtils.loadUrlImg(context, imageUrl, showImgv); // 设置图片

            friendHelpTv.setText(helpFriendStr.isEmpty() ? "好友助力" : helpFriendStr);
            if (onHelpFriendBtnClickListener != null) {
                friendHelpTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHelpFriendBtnClickListener.onClick(catchSuccessDialog, DialogInterface.BUTTON_NEGATIVE);
                        if (catchSuccessDialog.isShowing()) {
                            catchSuccessDialog.dismiss();
                        }
                    }
                });
            }

            oneTimeBtnTv.setText(oneTimeStr.isEmpty() ? "再来一局" : oneTimeStr);
            if (onOneTimeBtnClickListener != null) {
                oneTimeBtnTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOneTimeBtnClickListener.onClick(catchSuccessDialog, DialogInterface.BUTTON_POSITIVE);
                        if (catchSuccessDialog.isShowing()) {
                            catchSuccessDialog.dismiss();
                        }
                    }
                });
            }

            // 关闭按钮点击事件
            closeImgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (catchSuccessDialog.isShowing()) {
                        catchSuccessDialog.dismiss();
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
//                    oneTimeBtnTv.setText(oneTimeStr);
//                    oneTimeBtnTv.setEnabled(true);
                    oneTimeBtnTv.setVisibility(View.GONE);
                }
            }.start();

            // Dialog关闭监听，如果关闭了并且倒计时不为空，则清空倒计时
            catchSuccessDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = catchSuccessDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 8 * 7;
            window.setAttributes(lp);

            catchSuccessDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            catchSuccessDialog.setCancelable(false);

            return catchSuccessDialog;
        }

    }
}
