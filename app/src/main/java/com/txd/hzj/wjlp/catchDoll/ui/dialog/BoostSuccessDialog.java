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
 * <br>功能描述：
 */
public class BoostSuccessDialog extends BaseDialog {

    public BoostSuccessDialog(@NonNull Context context) {
        super(context);
    }

    public BoostSuccessDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String money; // 助力银两
        private String content; // 显示内容
        private OnClickListener goLotteryClickListener; // 点击抽奖按钮事件

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMoney(String money) {
            this.money = money;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setGoLotteryClickListener(OnClickListener goLotteryClickListener) {
            this.goLotteryClickListener = goLotteryClickListener;
            return this;
        }

        public BoostSuccessDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final BoostSuccessDialog boostSuccessDialog = new BoostSuccessDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_boost_success, null);

            TextView money_tv = view.findViewById(R.id.dialogBoostSuccess_money_tv);
            TextView content_tv = view.findViewById(R.id.dialogBoostSuccess_content_tv);
            ImageView goLottery_imgv = view.findViewById(R.id.dialogBoostSuccess_goLottery_imgv);
            ImageView close_imgv = view.findViewById(R.id.dialogBoostSuccess_close_imgv);

            money_tv.setText("+" + money + "银两");
            content_tv.setText(content);
            goLottery_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boostSuccessDialog.isShowing()) {
                        boostSuccessDialog.dismiss();
                    }
                    if (goLotteryClickListener != null) {
                        goLotteryClickListener.onClick(boostSuccessDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });

            close_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boostSuccessDialog.isShowing()) {
                        boostSuccessDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = boostSuccessDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 5 * 4;
            window.setAttributes(lp);

            boostSuccessDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            boostSuccessDialog.setCancelable(true);
            return boostSuccessDialog;
        }
    }

}
