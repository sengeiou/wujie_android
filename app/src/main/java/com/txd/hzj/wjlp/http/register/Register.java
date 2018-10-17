package com.txd.hzj.wjlp.http.register;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/17 0017
 * 时间：10:18
 * 描述：登录注册模块
 *
 */

class Register {
    private String url = Config.BASE_URL + "Register/";

    /**
     * 发送短信验证码
     *
     * @param phone    手机号
     * @param type     类型短信类型:注册activate 忘记密码：retrieve 解绑旧手机号：mod_bind 绑定新手机号：re_bind 重置支付密码：re_pay_pwd
     * @param baseView 回调
     */
    void sendVerify(String phone, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "sendVerify", params, baseView);
    }

    /**
     * 注册第一步
     *
     * @param phone    手机号
     * @param baseView 回调
     */
    void registerOne(String phone, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        apiTool2.postApi(url + "registerOne", params, baseView);
    }

    /**
     * 验证短信验证码
     *
     * @param phone    手机号
     * @param type     类型
     * @param verify   验证码
     * @param baseView 回调
     */
    void checkVerify(String phone, String type, String verify, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", type);
        params.addBodyParameter("verify", verify);
        apiTool2.postApi(url + "checkVerify", params, baseView);
        LogUtils.i("短信验证码============" + verify + "=====================");
    }

    /**
     * 用户注册
     *
     * @param phone           手机号
     * @param password        密码
     * @param confirmPassword 确认密码
     * @param baseView        回调
     */
    void register(String phone, String password, String confirmPassword, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("confirmPassword", confirmPassword);
        apiTool2.postApi(url + "register", params, baseView);
    }

    /**
     * 登录
     *
     * @param phone    手机号·
     * @param password 密码
     * @param baseView 回调
     */
    void login(String phone, String password, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        apiTool2.postApi(url + "login", params, baseView);
    }

    /**
     * 忘记密码
     *
     * @param phone           手机号
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @param baseView        回调
     */
    void resetPassword(String phone, String verify, String newPassword, String confirmPassword, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("newPassword", newPassword);
        params.addBodyParameter("confirmPassword", confirmPassword);
        apiTool2.postApi(url + "resetPassword", params, baseView);
    }

    /**
     * 三方登陆
     *
     * @param openid   openid
     * @param type     登录类型 1 微信 2微博 3 QQ
     * @param head_pic 头像
     * @param nickname 昵称
     * @param baseView 回调
     */
    void otherLogin(String openid, String type, File head_pic, String nickname, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("openid", openid);
        params.addBodyParameter("type", type);
        if (head_pic!=null){
            params.addBodyParameter("head_pic", head_pic);
        }
        if (!TextUtils.isEmpty(nickname)){
            params.addBodyParameter("nickname", nickname);
        }

        apiTool2.postApi(url + "otherLogin", params, baseView);
    }

    /**
     * 三方登录绑定手机
     *
     * @param bind_id     绑定id
     * @param phone       手机号
     * @param verify      验证码
     * @param invite_code 邀请码
     * @param baseView    回调
     */
    void otherLoginBind(String bind_id, String phone, String verify, String invite_code, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bind_id", bind_id);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("invite_code", invite_code);
        apiTool2.postApi(url + "otherLoginBind", params, baseView);
    }

    /**
     * 扫码登录
     *
     * @param sidStr
     * @param baseView
     */
    void qr_login(String sidStr, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sid", sidStr);
        apiTool2.postApi(url + "qr_login", params, baseView);
    }

}
