package com.txd.hzj.wjlp.http.register;

import android.text.TextUtils;
import android.util.Log;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tools.RegexUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.StringUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.huanxin.db.DemoDBManager;

import java.io.File;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/17 0017
 * 时间：10:27
 * 描述：注册
 *
 */

public class RegisterPst extends BasePresenter {

    private Register register;

    public RegisterPst(BaseView baseView) {
        super(baseView);
        register = new Register();
    }

    /**
     * 检查手机号
     *
     * @param phone 手机号
     */
    public void checkPhone(String phone) {
        if (!RegexUtils.checkPhone(phone)) {
            baseView.onErrorTip("请检查手机号");
            return;
        }
        baseView.showDialog();
        register.registerOne(phone, baseView);
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     */
    public void getVerify(String phone, String type) {
        baseView.showDialog();
        register.sendVerify(phone, type, baseView);
    }

    /**
     * 检查验证码
     *
     * @param phone  手机号
     * @param verify 验证码
     */
    public void checkVerify(String phone, String verify) {
        if (StringUtils.isBlank(verify)) {// 检查验证码是否为空
            baseView.onErrorTip("请输入验证码");
            return;
        }
        baseView.showDialog();
        register.checkVerify(phone, "activate", verify, baseView);
    }

    /**
     * 检查验证码
     *
     * @param phone  手机号
     * @param verify 验证码
     */
    public void checkVerify(String phone, String type, String verify) {
        if (StringUtils.isBlank(verify)) {// 检查验证码是否为空
            baseView.onErrorTip("请输入验证码");
            return;
        }
        baseView.showDialog();
        register.checkVerify(phone, type, verify, baseView);
    }

    /**
     * 注册
     *
     * @param phone           手机号
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    public void register(String phone, String password, String confirmPassword) {
        int len = password.length();
        int len2 = confirmPassword.length();
        if (StringUtils.isBlank(password)) {
            baseView.onErrorTip("请输入密码");
            return;
        }
        if (StringUtils.isBlank(confirmPassword)) {
            baseView.onErrorTip("请确认密码");
            return;
        }
        if (len < 6 || len > 14) {
            baseView.onErrorTip("密码长度为6—14位");
            return;
        }
        if (len2 < 6 || len2 > 14) {
            baseView.onErrorTip("密码长度为6—14位");
            return;
        }
        if (!password.equals(confirmPassword)) {
            baseView.onErrorTip("请核对密码");
            return;
        }
        baseView.showDialog();
        register.register(phone, password, confirmPassword, baseView);
    }

    /**
     * 登录
     *
     * @param phone    手机号
     * @param password 密码
     */
    public void login(String phone, String password) {
        if (!RegexUtils.checkPhone(phone)) {
            baseView.onErrorTip("请检查手机号");
            return;
        }
        if (StringUtils.isBlank(password)) {
            baseView.onErrorTip("请输入密码");
            return;
        }
        baseView.showDialog();
        register.login(phone, password, baseView);
    }

    /**
     * 注册
     *
     * @param phone           手机号
     * @param password        密码
     * @param confirmPassword 确认密码
     */
    public void resetPassword(String phone, String verify, String password, String confirmPassword) {
        int len = password.length();
        int len2 = confirmPassword.length();
        if (StringUtils.isBlank(password)) {
            baseView.onErrorTip("请输入密码");
            return;
        }
        if (StringUtils.isBlank(confirmPassword)) {
            baseView.onErrorTip("请确认密码");
            return;
        }
        if (len < 6 || len > 14) {
            baseView.onErrorTip("密码长度为6—14位");
            return;
        }
        if (len2 < 6 || len2 > 14) {
            baseView.onErrorTip("密码长度为6—14位");
            return;
        }
        if (!password.equals(confirmPassword)) {
            baseView.onErrorTip("请核对密码");
            return;
        }
        if (StringUtils.isBlank("verify")) {
            baseView.onErrorTip("请输入验证码");
            return;
        }
        baseView.showDialog();
        register.resetPassword(phone, verify, password, confirmPassword, baseView);
    }

    public void toLogin(String currentUsername, String currentPassword) {
        if (TextUtils.isEmpty(currentUsername)) {
            baseView.onErrorTip("账号出错，请联系客服");
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            baseView.onErrorTip("账号出错，请联系客服");
            return;
        }

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        L.e("=====环信登录=====", "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                L.e("=====环信登录成功=====", "login: onSuccess");
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

            }

            @Override
            public void onProgress(int progress, String status) {
                L.e("=====环信登录=====", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                L.e("=====环信登录失败=====", "login: onError: " + code);
            }
        });
    }

    // 三方登陆
    public void otherLogin(String openid, String type, File head_pic, String nickname) {
        register.otherLogin(openid, type, head_pic, nickname, baseView);
    }

    // 三方登陆绑定手机号
    public void otherLoginBind(String bind_id, String phone, String verify, String invite_code) {
        baseView.showDialog();
        register.otherLoginBind(bind_id, phone, verify, invite_code, baseView);
    }

    /**
     * 扫码登录
     *
     * @param sid 扫描到的sid
     */
    public void qr_login(String sid) {
        register.qr_login(sid, baseView);
    }

}
