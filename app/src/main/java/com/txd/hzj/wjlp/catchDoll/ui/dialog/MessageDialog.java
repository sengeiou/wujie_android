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
public class MessageDialog extends BaseDialog {

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    public MessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context; // 上下文
        private String titleStr; // 标题
        private String messageStr; // 提示消息
        private String positiveBtnStr; // 积极（确认）按钮
        private OnClickListener onPositiveBtnClickListener; // 积极按钮点击事件
        private String negativeBtnStr; // 消极（取消）按钮
        private OnClickListener onNegativeBtnClickListener; // 消极按钮点击事件

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置标题
         *
         * @param titleStr 显示的标题信息
         */
        public Builder setTitle(String titleStr) {
            this.titleStr = titleStr;
            return this;
        }

        /**
         * 设置展示消息
         *
         * @param msg 展示的消息
         */
        public Builder setMessage(String msg) {
            this.messageStr = msg;
            return this;
        }

        /**
         * 设置积极按钮文字及点击监听
         *
         * @param positiveBtnStr             显示按钮文字
         * @param onPositiveBtnClickListener 点击监听
         */
        public Builder setOnPositiveBtnClickListener(String positiveBtnStr, OnClickListener onPositiveBtnClickListener) {
            this.positiveBtnStr = positiveBtnStr;
            this.onPositiveBtnClickListener = onPositiveBtnClickListener;
            return this;
        }

        /**
         * 设置消极按钮文字及点击监听
         *
         * @param negativeBtnStr             显示按钮文字
         * @param onNegativeBtnClickListener 点击监听
         */
        public Builder setOnNegativeBtnClickListener(String negativeBtnStr, OnClickListener onNegativeBtnClickListener) {
            this.negativeBtnStr = negativeBtnStr;
            this.onNegativeBtnClickListener = onNegativeBtnClickListener;
            return this;
        }

        public MessageDialog create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final MessageDialog messageDialog = new MessageDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_message, null);

            TextView title_tv = view.findViewById(R.id.messageDialog_title_tv);
            TextView message_tv = view.findViewById(R.id.messageDialog_message_tv);
            TextView positive_tv = view.findViewById(R.id.messageDialog_positive_tv);
            View btnLine_tv = view.findViewById(R.id.messageDialog_btnLine_view);
            TextView negative_tv = view.findViewById(R.id.messageDialog_negative_tv);

            title_tv.setVisibility((titleStr == null || "".equals(titleStr)) ? View.GONE : View.VISIBLE);
            if (title_tv.getVisibility() == View.VISIBLE) {
                title_tv.setText(titleStr);
            }

            message_tv.setText(messageStr);

            // 积极按钮点击事件以及设置按钮文字
            if ((positiveBtnStr != null) && !("".equals(positiveBtnStr))) {
                positive_tv.setText(positiveBtnStr);
                positive_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPositiveBtnClickListener != null) {
                            onPositiveBtnClickListener.onClick(messageDialog, DialogInterface.BUTTON_POSITIVE);
                        }
                        if (messageDialog.isShowing()) {
                            messageDialog.dismiss();
                        }
                    }
                });
            } else {
                positive_tv.setVisibility(View.GONE);
            }

            // 消极按钮点击事件以及设置按钮文字
            if ((negativeBtnStr != null) && !("".equals(negativeBtnStr))) {
                negative_tv.setText(negativeBtnStr);
                negative_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onNegativeBtnClickListener != null) {
                            onNegativeBtnClickListener.onClick(messageDialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                        if (messageDialog.isShowing()) {
                            messageDialog.dismiss();
                        }
                    }
                });
            } else {
                negative_tv.setVisibility(View.GONE);
            }

            if (positive_tv.getVisibility() == View.GONE || negative_tv.getVisibility() == View.GONE) {
                btnLine_tv.setVisibility(View.GONE);
            }

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = messageDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            window.setAttributes(lp);

            messageDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            messageDialog.setCancelable(true);
            return messageDialog;

        }

    }
}
