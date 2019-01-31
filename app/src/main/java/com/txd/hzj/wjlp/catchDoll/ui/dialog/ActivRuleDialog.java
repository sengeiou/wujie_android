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
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class ActivRuleDialog extends BaseDialog {

    public ActivRuleDialog(@NonNull Context context) {
        super(context);
    }

    public ActivRuleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String content;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public ActivRuleDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ActivRuleDialog activRuleDialog = new ActivRuleDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_activ_rule, null);

            TextView dialogActivRule_content_tv = view.findViewById(R.id.dialogActivRule_content_tv);
            ImageView dialogActivRule_close_imgv = view.findViewById(R.id.dialogActivRule_close_imgv);

//            dialogActivRule_content_tv.setText(Html.fromHtml(content));
            dialogActivRule_content_tv.setText(content);
            dialogActivRule_close_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activRuleDialog.isShowing()) {
                        activRuleDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = activRuleDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 4 * 3;
            window.setAttributes(lp);

            activRuleDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            activRuleDialog.setCancelable(true);
            return activRuleDialog;
        }
    }
}
