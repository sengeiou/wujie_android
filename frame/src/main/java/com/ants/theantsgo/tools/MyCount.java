package com.ants.theantsgo.tools;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.ants.theantsgo.R;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：上午 11:31
 * 描述：获取验证码倒计时倒计时
 */
public class MyCount extends CountDownTimer {

    private TextView textView;

    public MyCount(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long residue = millisUntilFinished / 1000;// 剩余时间
        textView.setText("重新获取" + "(" + residue + ")");
        textView.setTextColor(Color.parseColor("#545454"));
        textView.setBackgroundResource(R.drawable.get_code_shape);
        textView.setEnabled(false);
    }

    @Override
    public void onFinish() {
        textView.setText("获取验证码");
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.shape_style2);
        textView.setEnabled(true);
    }
}
