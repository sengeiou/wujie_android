package com.ants.theantsgo.payByThirdParty.aliPay;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:43
 * 描述：支付宝回调接口
 */
public interface AliPayCallBack {
    /**
     * 当支付成功时的回调函数
     */
    void onComplete();

    /**
     * 当支付失败时的回调函数（包括用户主动取消支付，或者系统返回的错误
     */
    void onFailure();

    /**
     * 当等待支付结果确认时的回调函�?<br/>
     * 在终交易是否成功以服务端异步通知为准（小概率状况）
     */
    void onProcessing();
}
