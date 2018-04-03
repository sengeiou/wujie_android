package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class User {
    private static String url = Config.BASE_URL + "User/";

    /**
     * 我的积分
     *
     * @param baseView
     */
    public static void myIntegral(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "myIntegral", new RequestParams(), baseView);
    }

    /**
     * 积分兑换余额
     *
     * @param baseView
     * @param integral 要兑换的积分
     */
    public static void changeIntegral(BaseView baseView, String integral) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("integral", integral);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "changeIntegral", params, baseView);
    }

    /**
     * 验证支付密码
     *
     * @param baseView
     * @param payPwd   待验证的密码
     */
    public static void verificationPayPwd(BaseView baseView, String payPwd) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("PayPwd", payPwd);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "verificationPayPwd", params, baseView);
    }

    /**
     * 个人中心
     *
     * @param baseView
     */
    public static void userCenter(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCenter", new RequestParams(), baseView);
    }

    /**
     * 积分自动兑换
     *
     * @param baseView
     * @param status   自动兑换状态 0关闭 1开启
     */
    public static void changeIntegralStatus(BaseView baseView, String status) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("status", status);
        apiTool2.postApi(url + "changeIntegralStatus", params, baseView);
    }

    /**
     * 获取个人资料
     *
     * @param baseView
     */
    public static void userInfo(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userInfo", new RequestParams(), baseView);
    }

    /**
     * 获取积分兑换金额详细信息
     * @param baseView
     * @param integral
     */
    public static void autoChange(BaseView baseView, String integral){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("integral", integral);
        apiTool2.postApi(url + "autoChange", params, baseView);
    }

}
