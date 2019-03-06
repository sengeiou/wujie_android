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
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：娃娃商品详情兑换银两成功弹窗
 */
public class RedemptionResultDialog extends BaseDialog {

    public RedemptionResultDialog(@NonNull Context context) {
        super(context);
    }

    public RedemptionResultDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String moneyStr;
        private OnClickListener onBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMoneyStr(String moneyStr) {
            this.moneyStr = moneyStr;
            return this;
        }

        public Builder setOnBtnClickListener(String btnStr, OnClickListener onBtnClickListener) {
            this.onBtnClickListener = onBtnClickListener;
            return this;
        }

        public RedemptionResultDialog create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final RedemptionResultDialog redemptionResultDialog = new RedemptionResultDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_redmption_result, null);

            TextView money_tv = view.findViewById(R.id.dialogRedmption_money_tv);
            ImageView btn_imgv = view.findViewById(R.id.dialogRedmption_btn_imgv);

            money_tv.setText((moneyStr == null || "".equals(moneyStr)) ? "0" : moneyStr);

            btn_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBtnClickListener != null) {
                        onBtnClickListener.onClick(redemptionResultDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                    if (redemptionResultDialog.isShowing()) {
                        redemptionResultDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = redemptionResultDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            window.setAttributes(lp);

            redemptionResultDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            redemptionResultDialog.setCancelable(true);
            return redemptionResultDialog;

        }
    }

}
