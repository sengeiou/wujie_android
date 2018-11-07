package com.txd.hzj.wjlp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.R;


/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:50
 * 描述：自定义弹窗
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 创建弹窗
     *
     * @author HwangZJ
     *         <p>
     *         2016年12月16日下午3:51:22
     */
    public static class Builder {
        private Context context;
        private boolean canceble;
        /**
         * 标题
         */
        private String title;
        /**
         * 提示信息
         */
        private String message;
        /**
         * 设置点击弹窗外侧是否关闭弹窗
         */
        private Boolean cancelable = true;
        /**
         * 左边按钮内容
         */
        private String positiveButtonText;
        /**
         * 右边按钮内容
         */
        private String negativeButtonText;
        /**
         * 中间区域显示的View
         */
        private View contentView;
        /**
         * 左边按钮的点击事件
         */
        private OnClickListener positiveButtonClickListener;
        /**
         * 右边按钮的点击事件
         */
        private OnClickListener negativeButtonClickListener;

        /**
         * 上下文
         *
         * @param context
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 提示内容
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 提示内容
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置点击外侧是否关闭
         *
         * @param cancelable false为不关闭，默认为True
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE); // 正按钮隐藏
                layout.findViewById(R.id.positiveAndNegativeButtonLine).setVisibility(View.GONE); // 隐藏按钮中间的线
                ((Button) layout.findViewById(R.id.negativeButton)).setBackgroundResource(R.drawable.btn_ok_onebtn); // 将负按钮设置为两个下圆角
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE); // 负按钮隐藏
                layout.findViewById(R.id.positiveAndNegativeButtonLine).setVisibility(View.GONE); // 隐藏按钮中间的线
                ((Button) layout.findViewById(R.id.positiveButton)).setBackgroundResource(R.drawable.btn_ok_onebtn); // 将正按钮设置为两个下圆角
            }
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            dialog.setCancelable(cancelable); // 设置点击弹窗外侧是否关闭弹窗，默认为true关闭
            return dialog;
        }
    }
}