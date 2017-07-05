package com.ants.theantsgo.payByThirdParty;

import com.ants.theantsgo.tools.ConstantUtils;
import com.ants.theantsgo.util.L;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:39
 * 描述：微信支付(适用于支付签名在服务器端完成的情况)
 * <p>
 * 注意：微信支付在碎片中似乎无法调起，建议使用背景为
 * 半透明的Activity(即弹窗样式的Activity)来吊取微信支付，
 * 支付成功后返回一个值进行调取接口查询是否支付成功
 * ===============Txunda===============
 */
public class WeChatPay {
    private String url = ConstantUtils.URL;
    // "appid": "wxd285375667976137",
    private String appid = "";
    // "mch_id": "1406791102"
    private String partnerid = "";
    // "prepay_id": "wx20170425135509a82a1dd60e0573220247",
    private String prepayid = "";
    // "nonce_str": "H8G8uHsxBONkELm7",
    private String noncestr = "";
    // "time_stamp": "1493099710",
    private String timestamp = "";
    // "package": "Sign=WXPay",
    private String package_Str = "";
    // "sign": "3D3E184820A216899F4CBF3DB83CFCDD",
    private String sign = "";

    public WeChatPay(String appid, String partnerid, String prepayid, String noncestr, String timestamp, String
            package_Str, String sign) {
        this.appid = appid;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.package_Str = package_Str;
        this.sign = sign;
        L.e("appid:" + appid, "\npartnerid:" + partnerid + "\nprepayid:" + prepayid + "\nnoncestr:"
                + noncestr + "\ntimestamp:" + timestamp + "\npackage_Str:" + package_Str + "\nsign" + sign);
    }

    /**
     * 获取PayReq对象
     *
     * @return PayReq
     */
    public void getPayReq(IWXAPI iwxapi) {
        PayReq req = new PayReq();
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.packageValue = package_Str;
        req.sign = sign;
        iwxapi.registerApp(appid);
        iwxapi.sendReq(req);
    }
}
