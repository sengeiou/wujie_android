package com.ants.theantsgo.payByThirdParty;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.payByThirdParty.aliPay.PayResult;
import com.ants.theantsgo.util.L;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:38
 * 描述：支付宝支付(此类适用于支付签名在服务器段完成的情况)
 * ===============Txunda===============
 */
public class AliPay implements Runnable {

    private Thread thread;
    private String orderInfo;
    private AliPayCallBack aliPayCallBack;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /**
     * 够造函数
     *
     * @param orderInfo 这个参数是有服务器获取到的订单信息
     */
    public AliPay(String orderInfo, AliPayCallBack aliPayCallBack) {
        this.orderInfo = orderInfo;
        this.aliPayCallBack = aliPayCallBack;
        thread = new Thread(this);
    }

    /**
     * 吊起支付宝
     */
    public void pay() {
        thread.start();
    }

    @Override
    public void run() {
        PayTask alipay = new PayTask(AppManager.getInstance().getTopActivity());
        Map<String, String> result = alipay.payV2(orderInfo, true);
        L.e("=====msp=====", result.toString());
        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        aliPayCallBack.onComplete();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        if (TextUtils.equals(resultStatus, "8000")) { // 等待支付结果确认
                            aliPayCallBack.onProcessing();
                        } else { // 支付失败
                            aliPayCallBack.onFailure();
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") &&
//                            TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast
//                                        .LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//
//                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
