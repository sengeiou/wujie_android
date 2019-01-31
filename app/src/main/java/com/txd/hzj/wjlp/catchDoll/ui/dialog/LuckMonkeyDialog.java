package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;
import com.txd.hzj.wjlp.catchDoll.bean.SignInPrizeBean;
import com.txd.hzj.wjlp.catchDoll.view.nineLotteryView.LuckyMonkeyPanelView;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class LuckMonkeyDialog extends BaseDialog {

    public LuckMonkeyDialog(@NonNull Context context) {
        super(context);
    }

    public LuckMonkeyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        List<SignInPrizeBean> list;
        LuckyMonkeyPanelView.PanelStateListener onPanelStateListener;
        public static OnStartClickListener onStartClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setData(List<SignInPrizeBean> list) {
            this.list = list;
            return this;
        }

        public Builder setOnPanelStateListener(LuckyMonkeyPanelView.PanelStateListener onPanelStateListener) {
            this.onPanelStateListener = onPanelStateListener;
            return this;
        }

        public Builder setOnStartClickListener(OnStartClickListener onStartClickListener) {
            this.onStartClickListener = onStartClickListener;
            return this;
        }

        public LuckMonkeyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final LuckMonkeyDialog luckMonkeyDialog = new LuckMonkeyDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_luck_monkey, null);

            final LuckyMonkeyPanelView panel_lmpv = view.findViewById(R.id.dialogLuckMonkey_panel_lmpv);
            ImageView signIn_imgv = view.findViewById(R.id.dialogLuckMonkey_signIn_imgv);

            signIn_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStartClickListener != null) {
                        onStartClickListener.onStartClick(panel_lmpv);
                    }
                }
            });
            panel_lmpv.setItemPrize(list);
            panel_lmpv.setPanelStateListener(new LuckyMonkeyPanelView.PanelStateListener() {
                @Override
                public void onPanelStateStart() {
                    if (onPanelStateListener != null) {
                        onPanelStateListener.onPanelStateStart();
                    }
                }

                @Override
                public void onPanelStateStop(int position) {
                    if (onPanelStateListener != null) {
                        onPanelStateListener.onPanelStateStop(position);
                    }
                    // 如果结果不是再来一次，则直接关闭Dialog
                    if (list.get(position).getType() != 2) {
                        luckMonkeyDialog.dismiss();
                    }
                }
            });

//            int screenWidth = Config.getScreenWidth((Activity) context);
//            Window window = luckMonkeyDialog.getWindow(); // 这部分是设置dialog宽高
//            window.getDecorView().setPadding(0, 0, 0, 0);
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.width = screenWidth / 6 * 5;
//            window.setAttributes(lp);

            luckMonkeyDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            luckMonkeyDialog.setCancelable(true);

            return luckMonkeyDialog;

        }

    }

    public interface OnStartClickListener {
        void onStartClick(LuckyMonkeyPanelView luckyMonkeyPanelView);
    }

}
