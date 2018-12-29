package com.txd.hzj.wjlp.view;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：完善个人资料弹窗
 */
public class ImprovePersonalInfoDialog extends Dialog {

    public ImprovePersonalInfoDialog(@NonNull Context context) {
        super(context);
    }

    public ImprovePersonalInfoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String returnTicketStr;
        private String numberStr;
        private String text1Str;
        private String text2Str;
        private String btnStr;
        private OnClickListener onGoImproveBtnClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setReturnTicketStr(String returnTicketStr) {
            this.returnTicketStr = returnTicketStr;
            return this;
        }

        public Builder setNumberStr(String numberStr) {
            this.numberStr = numberStr;
            return this;
        }

        public Builder setText1Str(String text1Str) {
            this.text1Str = text1Str;
            return this;
        }

        public Builder setText2Str(String text2Str) {
            this.text2Str = text2Str;
            return this;
        }

        public Builder setOnGoImproveBtnClickListener(String btnStr, OnClickListener onGoImproveBtnClickListener) {
            this.btnStr = btnStr;
            this.onGoImproveBtnClickListener = onGoImproveBtnClickListener;
            return this;
        }

        public ImprovePersonalInfoDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ImprovePersonalInfoDialog improvePersonalInfoDialog = new ImprovePersonalInfoDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.view_improve_personal_info_dialog, null);
            TextView returnTicketTv = view.findViewById(R.id.viewImpPersInfoDlg_returnTicket_tv);
            TextView numberTv = view.findViewById(R.id.viewImpPersInfoDlg_number_tv);
            TextView showText1Tv = view.findViewById(R.id.viewImpPersInfoDlg_showText1_tv);
            TextView showText2Tv = view.findViewById(R.id.viewImpPersInfoDlg_showText2_tv);
            TextView goImproveTv = view.findViewById(R.id.viewImpPersInfoDlg_goImprove_tv);
            ImageView closeImgv = view.findViewById(R.id.viewImpPersInfoDlg_close_imgv);

            returnTicketTv.setText(returnTicketStr == null ? "" : returnTicketStr);
            numberTv.setText(numberStr == null ? "0" : numberStr);
            showText1Tv.setText(text1Str == null ? "" : text1Str);
            showText2Tv.setText(text2Str == null ? "" : text2Str);

            if (onGoImproveBtnClickListener != null) {
                if (btnStr != null && !btnStr.isEmpty()) {
                    goImproveTv.setText(btnStr);
                }
                goImproveTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onGoImproveBtnClickListener.onClick(improvePersonalInfoDialog, DialogInterface.BUTTON_NEGATIVE);
                        if (improvePersonalInfoDialog.isShowing()) {
                            improvePersonalInfoDialog.dismiss();
                        }
                    }
                });
            }

            closeImgv.setOnClickListener(new View.OnClickListener() { // 直接关闭弹窗
                @Override
                public void onClick(View v) {
                    if (improvePersonalInfoDialog.isShowing()) {
                        improvePersonalInfoDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            int screenHeight = Config.getScreenHeight((Activity) context);
            Window window = improvePersonalInfoDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            lp.height = screenHeight / 3 * 2;
            window.setAttributes(lp);

            improvePersonalInfoDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            improvePersonalInfoDialog.setCancelable(false); // 点击外侧不可关闭该窗口

            return improvePersonalInfoDialog;
        }


    }
}
