package com.ants.theantsgo.payByThirdParty.wxPay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:44
 * 描述：微信广播(根据状态来判断支付成功或者失败，进行回调)
 * ===============Txunda===============
 */
public class WxPayStatusReceiver extends BroadcastReceiver {
    private WxPayCallBack wxPayCallBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        int errCode = intent.getIntExtra("errCode", 5);
        if (0 == errCode) {
            wxPayCallBack.onPaySuccess();
        } else {
            wxPayCallBack.onPayFailure();
        }
    }

    public void setWxPayCallBack(WxPayCallBack wxPayCallBack) {
        this.wxPayCallBack = wxPayCallBack;
    }

}
