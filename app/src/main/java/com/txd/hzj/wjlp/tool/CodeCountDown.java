package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.login.RegisterGetCodeAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：13:24
 * 描述：找回密码，注册获取验证码
 * ===============Txunda===============
 */
public class CodeCountDown extends CountDownTimer {
    private Context context;
    private TextView tv;

    public CodeCountDown(long millisInFuture, long countDownInterval, Context context, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.tv = tv;
    }

    @Override
    public void onTick(long l) {
        long residue = l / 1000;// 剩余时间
        String time = String.valueOf(residue);
        int len = time.length();
        tv.setEnabled(false);
        ChangeTextViewStyle.getInstance().forTextColor(context, tv, time + "秒后 重新获取", 0, len,
                ContextCompat.getColor(context, R.color.colorAccent));

    }

    @Override
    public void onFinish() {
        tv.setText("重新获取");
        tv.setEnabled(true);
    }
}
