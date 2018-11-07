package com.txd.hzj.wjlp.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：APP 更新弹窗提示
 */
public class AppUpgradeDialog extends Dialog {

    public AppUpgradeDialog(@NonNull Context context) {
        super(context);
    }

    public AppUpgradeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String versionName;
        private String content;
        private String leftStr;
        private String rightStr;
        private OnClickListener onLeftClickListener;
        private OnClickListener onRightClickListener;
        private boolean cancelable = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setVersionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOnLeftClickListener(String leftBtnStr, OnClickListener onLeftClickListener) {
            this.leftStr = leftBtnStr;
            this.onLeftClickListener = onLeftClickListener;
            return this;
        }

        public Builder setOnRightClickListener(String rightBtnStr, OnClickListener onRightClickListener) {
            this.rightStr = rightBtnStr;
            this.onRightClickListener = onRightClickListener;
            return this;
        }

        public AppUpgradeDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final AppUpgradeDialog appUpgradeDialog = new AppUpgradeDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.view_app_upgrade_dialog, null);
            TextView versionNameTv = view.findViewById(R.id.upgradeDialog_versionName_tv);
            TextView contentTv = view.findViewById(R.id.upgradeDialog_content_tv);
            TextView closeTv = view.findViewById(R.id.upgradeDialog_close_tv);
            TextView upgradeTv = view.findViewById(R.id.upgradeDialog_upgrade_tv);

            versionNameTv.setText((versionName == null || versionName.isEmpty()) ? "1.0.0" : versionName);
            contentTv.setText((content == null || content.isEmpty()) ? "暂无修改太大的BUG" : content);
            // 如果左侧的按钮文字不为null则显示按钮
            if (leftStr != null) {
                // 如果字符串不为空字符串，则设置指定的字符串，否则直接显示原始字符串
                if (!leftStr.isEmpty()) {
                    closeTv.setText(leftStr);
                }
                // 如果点击事件监听不为空，则直接设置其点击的监听事件
                if (onLeftClickListener != null) {
                    closeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onLeftClickListener.onClick(appUpgradeDialog, DialogInterface.BUTTON_NEGATIVE);
                            if (appUpgradeDialog.isShowing()) {
                                appUpgradeDialog.dismiss();
                            }
                        }
                    });
                } else { // 否则，如果为null则直接隐藏该按钮
                    closeTv.setVisibility(View.GONE);
                }
            }
            if (rightStr != null) {
                if (!rightStr.isEmpty()) {
                    upgradeTv.setText(rightStr);
                }
                if (onRightClickListener != null) {
                    upgradeTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRightClickListener.onClick(appUpgradeDialog, DialogInterface.BUTTON_POSITIVE);
                            if (appUpgradeDialog.isShowing()) {
                                appUpgradeDialog.dismiss();
                            }
                        }
                    });
                } else {
                    upgradeTv.setVisibility(View.GONE);
                }
            }

            int screenWidth = Config.getScreenWidth((Activity) context);
            int screenHeight = Config.getScreenHeight((Activity) context);
            Window window = appUpgradeDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            lp.height = screenHeight / 3 * 2;
            window.setAttributes(lp);

            appUpgradeDialog.addContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            appUpgradeDialog.setCancelable(cancelable);
            return appUpgradeDialog;
        }

    }

}
