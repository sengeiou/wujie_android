package com.txd.hzj.wjlp.catchDoll.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.txd.hzj.wjlp.catchDoll.util.Util;


/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：基类的Dialog
 */
public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private BaseDialog baseDialog;
        private View view; // 要显示的View
        private boolean cancelable; // 点击弹窗外侧是否可以关闭弹窗

        public Builder(Context context) {
            this.context = context;
        }

        public void setBaseDialog(BaseDialog baseDialog) {
            this.baseDialog = baseDialog;
        }

        /**
         * 设置显示的View布局
         *
         * @param view
         */
        public void setView(View view) {
            this.view = view;
        }

        /**
         * 设置点击窗口外侧是否可关闭弹窗
         *
         * @param cancelable
         */
        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        public BaseDialog create() {

            int screenWidth = Util.getScreenWidth((Activity) context); // 获取屏幕宽
            int screenHeight = Util.getScreenHeight((Activity) context); // 获取屏幕高

            Window window = baseDialog.getWindow(); // 获取Dialog的宽高
            window.getDecorView().setPadding(0, 0, 0, 0);

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = screenWidth / 4 * 3;
//            params.height = screenHeight / 3 * 2;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params); // 设置Dialog宽高

            baseDialog.addContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            baseDialog.setCancelable(cancelable);
            return baseDialog;
        }

    }

}
