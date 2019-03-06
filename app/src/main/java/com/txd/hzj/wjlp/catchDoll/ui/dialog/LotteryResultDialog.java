package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;
import com.txd.hzj.wjlp.catchDoll.view.StrokeTextView;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：转盘抽奖展示Dialog
 */
public class LotteryResultDialog extends BaseDialog {

    public LotteryResultDialog(@NonNull Context context) {
        super(context);
    }

    public LotteryResultDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String resultStr; // 抽奖结果

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setResultStr(String resultStr) {
            this.resultStr = resultStr;
            return this;
        }

        public LotteryResultDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final LotteryResultDialog lotteryResultDialog = new LotteryResultDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_lottery_result, null);

            StrokeTextView show_stv = view.findViewById(R.id.lotteryResult_show_stv);
            StrokeTextView result_stv = view.findViewById(R.id.lotteryResult_result_stv);
            ImageView receive_imgv = view.findViewById(R.id.lotteryResult_receive_imgv);

            result_stv.setText(new StringBuffer().append("抽中了").append(resultStr));
            show_stv.setStrokeWidth(10);
            result_stv.setStrokeWidth(8);

            receive_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lotteryResultDialog.isShowing()) {
                        lotteryResultDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = lotteryResultDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            window.setAttributes(lp);

            lotteryResultDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lotteryResultDialog.setCancelable(true);
            return lotteryResultDialog;
        }
    }

}
