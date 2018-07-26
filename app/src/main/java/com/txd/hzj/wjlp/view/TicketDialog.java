package com.txd.hzj.wjlp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/17 16:52
 * 功能描述：拼单购列表页弹窗
 * 联系方式：
 */
public class TicketDialog extends Dialog {
    private Context mContext;
    private TextView mRules_tv;
    private TextView mTextView;
    private CountDownTimer mCountDownTimer;
    private List<String> mGroup_buy_rule;
    private  ChangeShowStatus mChangeShowStatus;

    public TicketDialog(@NonNull Context context) {
        this(context, R.style.Ticket_Dialog);
//        mGroup_buy_rule=group_buy_rule;
    }

    public TicketDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

        init();
    }

    public void setData(List<String> group_buy_rule){
        mGroup_buy_rule=group_buy_rule;
        if (mGroup_buy_rule!=null){
            StringBuilder builder=new StringBuilder();
            for (int i = 0; i < mGroup_buy_rule.size(); i++) {
                if (i!=0 && i!=mGroup_buy_rule.size()-1){
                    builder.append("<br>");
                    builder.append(mGroup_buy_rule.get(i));
                }
            }

            mRules_tv.setText(Html.fromHtml(builder.toString()));
        }
    }

    public void setChangeShowStatus(ChangeShowStatus changeShowStatus){
        mChangeShowStatus=changeShowStatus;
    }
    public interface ChangeShowStatus{
            void changeStatus();
    }


    private void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.ticket_dialog_layout, null);
        setContentView(view);
        mTextView = view.findViewById(R.id.sure_tv);
        mRules_tv=view.findViewById(R.id.rules_tv);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) mRules_tv.getLayoutParams();
        int left=Settings.displayWidth/10;
        int top=Settings.displayHeight/10;
        layoutParams.setMargins(left,top,left, (int) (top*1.5));
        mRules_tv.setPadding(left/2,0,left/2,0);

        mRules_tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        countDownTimer();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeShowStatus.changeStatus();
                dismiss();
            }
        });

    }

    private void countDownTimer() {
        mCountDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished/1000!=0) {
                    mTextView.setText( millisUntilFinished / 1000 + "s");
                    mTextView.setClickable(false);
                }

            }

            @Override
            public void onFinish() {
                mTextView.setText("");
                mTextView.setBackgroundResource(R.drawable.wrong);
                mTextView.setClickable(true);
            }
        };
        mCountDownTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height= (int) (Settings.displayHeight*0.9);
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }

}
