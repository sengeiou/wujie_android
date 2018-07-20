package com.txd.hzj.wjlp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.txd.hzj.wjlp.R;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/17 16:52
 * 功能描述：拼单购列表页弹窗
 * 联系方式：
 */
public class TicketDialog extends Dialog {
    private Context mContext;
    private TextView mTextView;
    private CountDownTimer mCountDownTimer;

    public TicketDialog(@NonNull Context context) {
        this(context, R.style.Ticket_Dialog);

    }

    public TicketDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    private void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.ticket_dialog_layout, null);
        setContentView(view);
        mTextView = view.findViewById(R.id.sure_tv);
        countDownTimer();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void countDownTimer() {
        mCountDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished/1000!=0) {
                    mTextView.setText("确定" + millisUntilFinished / 1000 + "s");
                    mTextView.setClickable(false);
                }else {
                    mTextView.setText("确定");
                    mTextView.setClickable(true);
                }

            }

            @Override
            public void onFinish() {
                mTextView.setText("确定");
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
        attributes.width = (int) (Settings.displayWidth*0.8);
        attributes.height= (int) (Settings.displayHeight*0.6);
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }

}