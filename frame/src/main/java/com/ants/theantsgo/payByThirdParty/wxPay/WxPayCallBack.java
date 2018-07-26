package com.ants.theantsgo.payByThirdParty.wxPay;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:44
 * 描述：微信支付回调
 */
public interface WxPayCallBack {
    /**
     * 成功
     */
    void onPaySuccess();

    /**
     * 失败
     */
    void onPayFailure();
}
