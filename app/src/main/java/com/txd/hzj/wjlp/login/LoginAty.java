package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mob.tools.utils.UIHandler;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 7:10
 * 描述：登录，注册(50-1 登录，50-5 注册)
 * ===============Txunda===============
 */
public class LoginAty extends BaseAty implements Handler.Callback, PlatformActionListener {
    /**
     * 登录
     */
    @ViewInject(R.id.to_login_tv)
    private TextView to_login_tv;
    /**
     * 注册
     */
    @ViewInject(R.id.to_register_tv)
    private TextView to_register_tv;

    /**
     * 手机号
     */
    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;
    /**
     * 密码
     */
    @ViewInject(R.id.pwd_ev)
    private EditText pwd_ev;

    /**
     * 登录，注册第一步
     */
    @ViewInject(R.id.to_login_or_register_tv)
    private TextView to_login_or_register_tv;

    /**
     * 三方登录提示
     */
    @ViewInject(R.id.use_trilateral_lin_layout)
    private LinearLayout use_trilateral_lin_layout;
    private int type = 0;

    /**
     * 三方登录方式
     * 1.微信
     * 2.微博
     * 3.QQ
     */
    private String loginType = "";

    @ViewInject(R.id.for_third_layout)
    private LinearLayout for_third_layout;

    /**
     * 服务条款
     */
    @ViewInject(R.id.terms_of_service_tv)
    private TextView terms_of_service_tv;
    private Bundle bundle;

    private RegisterPst registerPst;
    private String phone = "";
    /**
     * 跳转方式
     * 0.推出跳转到登录页，登陆成功，跳转到首页
     * 1.跳转到登录页，直接返回
     */
    private int skip_type = 0;
    private String password = "";
    /**
     * 三方登录openId
     */
    private String openid = "";
    /**
     * 昵称
     */
    private String nick = "";
    /**
     * 头像路径
     */
    private String head_pic = "";


    // 三方登陆
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    /**
     * 邀请码
     */
    private String invite_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.longin_title_layout);
        toChangeTextViewStatus(0);
        for_third_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_color));


        ChangeTextViewStyle.getInstance().forTextColor(this, terms_of_service_tv, "继续表示已经阅读并同意《服务条款》", 11,
                ContextCompat.getColor(this, R.color.theme_color));
    }

    @Override
    @OnClick({R.id.to_login_tv, R.id.to_register_tv, R.id.forget_pwd_tv, R.id.to_login_or_register_tv,
            R.id.share_to_wachar, R.id.share_to_qq, R.id.share_to_sine, R.id.terms_of_service_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_login_tv:// 登录(标题栏)
                type = 0;
                toChangeTextViewStatus(0);
                pwd_ev.setVisibility(View.VISIBLE);
                to_login_or_register_tv.setText("登录");
                use_trilateral_lin_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.to_register_tv:// 注册(标题栏)
                type = 1;
                toChangeTextViewStatus(1);
                pwd_ev.setVisibility(View.GONE);
                to_login_or_register_tv.setText("下一步");
                use_trilateral_lin_layout.setVisibility(View.GONE);
                break;
            case R.id.forget_pwd_tv:// 忘记密码
                startActivity(FindPwgBackHzjAty.class, null);
                break;
            case R.id.to_login_or_register_tv:// 登录，注册
                phone = phone_ev.getText().toString();
                if (0 == type) {//登录
                    password = pwd_ev.getText().toString();
                    registerPst.login(phone, password);
                } else {// 注册下一步
                    registerPst.checkPhone(phone);
                }
                hideKeyBoard();
                break;
            case R.id.share_to_wachar:// 微信
                loginType = "1";
                showDialog();
                authorize(new Wechat(this));
                break;
            case R.id.share_to_qq:// QQ
                loginType = "3";
                showDialog();
                Platform qq = ShareSDK.getPlatform(QZone.NAME);
                authorize(qq);
                break;
            case R.id.share_to_sine:// 新浪微博
                showDialog();
                loginType = "2";
                Platform wb = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(wb);
                break;
            case R.id.terms_of_service_tv:// 服务条款
                bundle = new Bundle();
                bundle.putInt("from", 3);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
        }
    }

    private void toChangeTextViewStatus(int i) {
        to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_unselect);
        to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_unselect);

        if (0 == i) {
            to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_selected);

            phone_ev.setText(PreferencesUtils.getString(this, "phone"));
            pwd_ev.setText(PreferencesUtils.getString(this, "pwd"));
        } else {
            to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_select);

            phone_ev.setText("");
        }
    }

    public void beBack(View view) {
        startActivity(MainAty.class, null);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_login;
    }

    @Override
    protected void initialized() {
        skip_type = getIntent().getIntExtra("type", 1);
        registerPst = new RegisterPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        if (requestUrl.contains("registerOne")) {// 注册第一步
//            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
//            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");
                if(code.equals("0")){
                    showToast(message);
                }else {
                    bundle = new Bundle();
                    bundle.putString("phone", phone);
                    startActivity(RegisterGetCodeAty.class, bundle);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return;
        }
        if (requestUrl.contains("Register/login")) {
            showToast("登录成功");
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            application.setUserInfo(data);
            Config.setLoginState(true);
            PreferencesUtils.putString(this, "phone", phone);
            PreferencesUtils.putString(this, "pwd", password);
            PreferencesUtils.putString(this, "token", data.get("token"));
            // 友盟统计
            MobclickAgent.onProfileSignIn(data.get("user_id"));

            // 极光设置Tag或者别名
            JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
            JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());

            // 环信登录
            registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
//            if (0 == skip_type) {
                AppManager.getInstance().killAllActivity();
                startActivity(MainAty.class, null);
//            }
            finish();
            return;
        }
        if (requestUrl.contains("Register/otherLogin")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (data.get("is_bind_phone").equals("0")) {
                showRightTip("请绑定手机号");
                bundle = new Bundle();
                bundle.putString("invite_code", invite_code);
                bundle.putInt("skip_type", skip_type);
                bundle.putString("bind_id", data.get("bind_id"));
                startActivity(BindAccountAty.class, bundle);
                finish();
            } else {
                showRightTip("登录成功");
                application.setUserInfo(data);
                Config.setLoginState(true);
                PreferencesUtils.putString(this, "token", data.get("token"));
                // 友盟统计
                MobclickAgent.onProfileSignIn(data.get("user_id"));
                // 极光设置Tag或者别名
                JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
                JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());
                // 环信登录
                registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
                if (0 == skip_type) {
                    startActivity(MainAty.class, null);
                    AppManager.getInstance().killAllActivity();
                }
                finish();
            }
        }
    }



    private void authorize(Platform plat) {
        // 判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            if (loginType.equals("1")) {
                openid = plat.getDb().get("unionid");
            } else {
                openid = plat.getDb().getUserId();
            }
            nick = plat.getDb().getUserName();
            if (openid != null && nick != null) {
                head_pic = plat.getDb().getUserIcon();
                getHeadPicAndLogin(head_pic);
                return;
            }
            // 三方登陆
            return;
        }
        // 授权监听

        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权，(即true不使用客户端登录，false有客户端则使用客户端登录，没有则使用web网页登录)
        plat.SSOSetting(false);
        // 获取用户资料
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (Platform.ACTION_USER_INFOR == i) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            if (loginType.equals("1")) {
                openid = platform.getDb().get("unionid");
            } else {
                openid = platform.getDb().getUserId();
            }
            nick = platform.getDb().getUserName();
            head_pic = platform.getDb().getUserIcon();
            getHeadPicAndLogin(head_pic);
            // 三方登陆
            L.e("=====openid=====", openid);
            L.e("=====nick=====", nick);
            L.e("=====pic=====", head_pic);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        removeDialog();
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
            L.e("=====授权失败=====", throwable.toString());
            L.e("=====授权失败=====", String.valueOf(i));
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        removeDialog();
        L.e(platform.getName(), "=====取消=====");
    }

    /**
     * 获取头像并登陆
     *
     * @param url 图片路径
     */
    private void getHeadPicAndLogin(String url) {
        HttpUtils utils = new HttpUtils();
        utils.download(url,
                Environment.getExternalStorageDirectory() + "/Txunda/img_head/head.png", new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        File head = new File(Environment.getExternalStorageDirectory() +
                                "/Txunda/img_head/head.png");
                        registerPst.otherLogin(openid, loginType, head, nick);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                String text = getString(com.ants.theantsgo.R.string.userid_found);
                showRightTip(text);
            }
            break;
            case MSG_LOGIN: {
                String text = getString(com.ants.theantsgo.R.string.logining, msg.obj);
                showRightTip(text);
            }
            break;
            case MSG_AUTH_CANCEL: {
                String text = getString(com.ants.theantsgo.R.string.auth_cancel);
                showErrorTip(text);
            }
            break;
            case MSG_AUTH_ERROR: {
                String text = getString(com.ants.theantsgo.R.string.auth_error);
                showErrorTip(text);
            }
            break;
            case MSG_AUTH_COMPLETE: {
                String text = getString(com.ants.theantsgo.R.string.auth_complete);
                showRightTip(text);
            }
            break;
        }
        return false;
    }
}
