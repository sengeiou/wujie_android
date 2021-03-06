package com.txd.hzj.wjlp.minetoaty.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.OrderInfoUtil2_0;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.glide.GlideCacheUtil;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mob.tools.utils.UIHandler;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.UpdataApp;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.http.updataApp.UpdataPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.minetoaty.balance.BankInfoForReChargeAty;
import com.txd.hzj.wjlp.new_wjyp.Authentication_aty;
import com.txd.hzj.wjlp.tool.AppUpdate;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.annotations.NonNull;

import static com.txd.hzj.wjlp.login.LoginAty.APPID;
import static com.txd.hzj.wjlp.login.LoginAty.PID;
import static com.txd.hzj.wjlp.login.LoginAty.RSA2_PRIVATE;
import static com.txd.hzj.wjlp.login.LoginAty.RSA_PRIVATE;
import static com.txd.hzj.wjlp.login.LoginAty.TARGET_ID;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：下午 3:40
 * 描述：设置
 */
public class SetAty extends BaseAty implements Handler.Callback, PlatformActionListener ,AliPay.OnAuthInterface{
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MSG_AUTH_COMPLETE://授权成功

                    break;

            }
            startActivity(EditProfileAty.class, null);
            return false;
        }
    });
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private UserPst userPst;
    private String auth_status = "";
    private String comp_auth_status = "";

    /**
     * 缓存
     */
    @ViewInject(R.id.tv_data_number)
    private TextView tv_data_number;

    /**
     * 绑定手机
     */
    @ViewInject(R.id.user_bind_phone_tv)
    private TextView user_bind_phone_tv;
    /**
     * 是否有登录密码
     */
    private String is_password = "0";
    /**
     * 是否有支付密码
     */
    private String is_pay_password = "0";

    /**
     * 登录密码
     */
    @ViewInject(R.id.rel_editpassword)
    private TextView rel_editpassword;
    /**
     * 支付密码
     */
    @ViewInject(R.id.rel_editpaypassword)
    private TextView rel_editpaypassword;
    private Bundle bundle;
    private String phone = "";

    @ViewInject(R.id.tv_qq_bind)
    private TextView tv_qq_bind;
    @ViewInject(R.id.tv_wx_bind)
    private TextView tv_wx_bind;
    @ViewInject(R.id.tv_wb_bind)
    private TextView tv_wb_bind;
    @ViewInject(R.id.tv_ali_bind)
    private TextView tv_ali_bind;
    @ViewInject(R.id.set_upDate_tv)
    private TextView set_upDate_tv;

    /**
     * 我的银行卡
     */
    @ViewInject(R.id.rel_myBankCard)
    private TextView rel_myBankCard;

    private boolean isMyBankCardClick = false; // 我的银行卡点击

    private Map<String, String> qq_bind;
    private Map<String, String> wx_bind;
    private Map<String, String> weibo_bind;
    private Map<String,String> alipay_bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
    }

    @Override
    @OnClick({R.id.rel_editprofile, R.id.rel_editpassword, R.id.rel_editpaypassword, R.id.rel_realname,
            R.id.rel_bind_phone, R.id.sing_out_tv, R.id.clear_cach_layout
            , R.id.layout_wechat_bind, R.id.layout_qq_bind, R.id.layout_sina_bind,R.id.layout_alipay_bind, R.id.set_upDate_tv, R.id.rel_myBankCard})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rel_editprofile:// 个人资料
                startActivity(EditProfileAty.class, null);
                break;
            case R.id.rel_editpassword:// 修改登录密码
                bundle = new Bundle();
                bundle.putString("is_password", is_password);
                startActivity(EditLoginPasswordAty.class, bundle);
                break;
            case R.id.rel_editpaypassword:// 修改支付密码
                bundle = new Bundle();
                bundle.putString("is_pay_password", is_pay_password);
                bundle.putString("phone", phone);
                startActivity(EditPayPasswordAty.class, bundle);
                this.finish();
                break;
            case R.id.rel_realname:// 实名认证
                userPst.userInfo();
                showProgressDialog();
                break;
            case R.id.rel_myBankCard:
                isMyBankCardClick = true;
                User.userInfo(this); // 获取用户信息
                break;
            case R.id.rel_bind_phone: // 绑定手机号
                if (phone.equals("")) {
                    bundle = new Bundle();
                    bundle.putInt("from", 1);
                    startActivity(BindNewPhoneAty.class, bundle);
                } else {
                    bundle = new Bundle();
                    bundle.putString("phone", phone);
                    startActivity(BindPhoneAty.class, bundle);
                }
                break;
            case R.id.sing_out_tv: // 退出登录
                new MikyouCommonDialog(this, "确定要退出吗?", "提示", "取消", "确定", true).setOnDiaLogListener(new MikyouCommonDialog
                        .OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                            which) {
                        switch (btnType) {
                            case MikyouCommonDialog.NO:
                                Platform qq = ShareSDK.getPlatform(QZone.NAME);
                                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                                Platform sl = ShareSDK.getPlatform(SinaWeibo.NAME);
                                if (null != qq && qq.isAuthValid()) {
                                    qq.removeAccount(true);
                                }
                                if (null != wx && wx.isAuthValid()) {
                                    wx.removeAccount(true);
                                }
                                if (null != sl && sl.isAuthValid()) {
                                    sl.removeAccount(true);
                                }
                                Config.setLoginState(false);
                                PreferencesUtils.putString(SetAty.this, "token", "");
                                PreferencesUtils.putString(SetAty.this, "invite_code", "");
                                PreferencesUtils.putBoolean(SetAty.this, Config.PREF_KEY_LOGIN_STATE, false); // 将登录状态设置为未登录
                                // 友盟统计signout统计
                                MobclickAgent.onProfileSignOff();
                                // 删除极光推送之前设置好的Tag或Alias
                                JpushSetTagAndAlias.getInstance().delAlias(getApplicationContext());
                                JpushSetTagAndAlias.getInstance().delTag(getApplicationContext());

                                logout();
                                break;
                            case MikyouCommonDialog.OK:
                                break;
                        }
                    }
                }).showDialog();
                break;
            case R.id.clear_cach_layout:// 清除缓存
                new MikyouCommonDialog(this, "确定要清除缓存？", "提示", "取消", "确定", true).setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                            which) {
                        switch (btnType) {
                            case MikyouCommonDialog.NO:
                                showProgressDialog();
                                GlideCacheUtil.getInstance().clearImageAllCache(getApplicationContext());
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        removeProgressDialog();
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(task, 500);
                                tv_data_number.setText("0.00M");
                                break;
                            case MikyouCommonDialog.OK:
                                break;
                        }
                    }
                }).showDialog();
                break;
            case R.id.layout_wechat_bind: // 绑定微信
                loginType = "1";
                if (wx_bind.get("is_bind").equals("0")) {
                    showDialog();
                    authorize(new Wechat(this));
                } else {
                    new AlertDialog(this).builder().setTitle("提示").setMsg("是否取消绑定？").setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User.removeBind("1", SetAty.this);
                        }
                    }).show();


                }
                break;
            case R.id.layout_qq_bind: // 绑定qq
                if (qq_bind.get("is_bind").equals("0")) {
                    loginType = "3";
                    showDialog();
                    Platform qq = ShareSDK.getPlatform(QZone.NAME);
                    authorize(qq);
                } else {
                    new AlertDialog(this).builder().setTitle("提示").setMsg("是否取消绑定？").setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User.removeBind("3", SetAty.this);
                        }
                    }).show();

                }
                break;
            case R.id.layout_sina_bind: // 绑定新浪
                if (weibo_bind.get("is_bind").equals("0")) {
                    showDialog();
                    loginType = "2";
                    Platform wb = ShareSDK.getPlatform(SinaWeibo.NAME);
                    authorize(wb);
                } else {
                    new AlertDialog(this).builder().setTitle("提示").setMsg("是否取消绑定？").setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User.removeBind("2", SetAty.this);
                        }
                    }).show();
                }

                break;
                //支付宝绑定
            case R.id.layout_alipay_bind:

                if (alipay_bind.get("is_bind").equals("0")) {
                    showDialog();
                    loginType = "7";
                    String alipay_auth_code = PreferencesUtils.getString(SetAty.this, "alipay_auth_code");
                    if (TextUtils.isEmpty(alipay_auth_code)){
                        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
                        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
                        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
                        final String authInfo = info + "&" + sign;
                        AliPay aliPay=new AliPay(authInfo, this);
                        aliPay.setMessageWhat(AliPay.SDK_AUTH_FLAG);
                        aliPay.pay();
                    }else {
                        User.bindOther(alipay_auth_code, loginType, "", this);
                    }
                } else {
                    new AlertDialog(this).builder().setTitle("提示").setMsg("是否取消绑定？").setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User.removeBind("7", SetAty.this);
                        }
                    }).show();
                }
                break;
            case R.id.set_upDate_tv: // 检查更新
                UpdataPst updataPst = new UpdataPst(this);
                updataPst.toUpdata();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_set;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetSize();
        userPst.setting();
    }

    /**
     * 三方登录方式
     * 1.微信
     * 2.微博
     * 3.QQ
     * 7.支付宝
     */
    private String loginType = "";

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

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("Upgrade")) {
            UpdataApp updataApp = GsonUtil.GsonToBean(jsonStr, UpdataApp.class);
            AppUpdate.getInstance().showAppUpdateDialog(updataApp, this, false);
        }
        if (requestUrl.contains("bindOther")) {
            showToast("绑定成功！");
            userPst.setting();
            showProgressDialog();
            return;
        }
        if (requestUrl.contains("removeBind")) {
            showToast("解绑成功！");
            userPst.setting();
            showProgressDialog();
            return;
        }
        if (requestUrl.contains("userInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (data.get("code").equals("1")) {
                data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
                if (data.get("personal_data_status").equals("0")) {
                    showToast("请先完善个人资料");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.what = 0;
                            message.obj = 0;
                            handler.sendMessage(message);
                        }
                    }).start();
                    return;
                }
                if (isMyBankCardClick) { // 点击的我的银行卡
                    isMyBankCardClick = false;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isPlatform", false); // 选择个人银行卡
                    PreferencesUtils.putString(SetAty.this, "key1", "0");
                    startActivity(BankInfoForReChargeAty.class, bundle);
                } else { // 应该是去的实名认证
                    Bundle bb = new Bundle();
                    bb.putString("auth_status", auth_status);
                    bb.putString("comp_auth_status", comp_auth_status);
                    startActivity(Authentication_aty.class, bb);
                }
            }
            return;

        }

        // 请求更新时不返回下面这些字段，防止其报空指针异常，所以直接除去更新对这块的调用
        if (!requestUrl.contains("Upgrade")) {
            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");
            // 认证状态 0 未认证 1认证中 2 已认证 3被拒绝 "
            auth_status = data.get("auth_status");
            comp_auth_status = data.get("comp_auth_status");
            phone = data.get("phone");
            user_bind_phone_tv.setText(phone);
            is_password = data.get("is_password");

            if (is_password.equals("0")) {
                rel_editpassword.setText("设置登录密码");
            } else {
                rel_editpassword.setText("修改登录密码");
            }

            is_pay_password = data.get("is_pay_password");
            if (is_pay_password.equals("0")) {
                rel_editpaypassword.setText("设置支付密码");
            } else {
                rel_editpaypassword.setText("修改支付密码");
            }
            Map<String, String> m = JSONUtils.parseKeyAndValueToMap(jsonStr);
            m = JSONUtils.parseKeyAndValueToMap(m.get("data"));
            qq_bind = JSONUtils.parseKeyAndValueToMap(m.get("qq_bind"));
            setBindText(tv_qq_bind, qq_bind);
            wx_bind = JSONUtils.parseKeyAndValueToMap(m.get("wx_bind"));
            setBindText(tv_wx_bind, wx_bind);
            weibo_bind = JSONUtils.parseKeyAndValueToMap(m.get("weibo_bind"));
            setBindText(tv_wb_bind, weibo_bind);
            alipay_bind = JSONUtils.parseKeyAndValueToMap(m.get("alipay_bind"));
            setBindText(tv_ali_bind, alipay_bind);
        }

    }

    private void setBindText(TextView textView, Map<String, String> map) {
        if (map.get("is_bind").equals("0")) {
            textView.setText("未绑定");
            return;
        }
        Map<String, String> bind_info = JSONUtils.parseKeyAndValueToMap(map.get("bind_info"));
        textView.setText(TextUtils.isEmpty(bind_info.get("nickname")) ? "已绑定" : bind_info.get("nickname"));

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
                //head_pic = plat.getDb().getUserIcon();
                User.bindOther(openid, loginType, nick, this);
                //                userPst.bindOther(openid, loginType, nick);
                //getHeadPicAndLogin(head_pic);
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
                        File head = new File(Environment.getExternalStorageDirectory() + "/Txunda/img_head/head.png");
                        //   registerPst.otherLogin(openid, loginType, head, nick);
                        User.bindOther(openid, loginType, nick, SetAty.this);
                        //                        userPst.bindOther(openid, loginType, nick);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    private void logout() {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginoutToLogin();
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorTip("退出失败，请重新操作");
                    }
                });
            }
        });
    }

    /**
     * 获取缓存大小
     */
    public void SetSize() {
        tv_data_number.setText(GlideCacheUtil.getInstance().getCacheSize(getApplicationContext()));
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
            //            userPst.bindOther(openid, loginType, nick);
            getHeadPicAndLogin(head_pic);
            // 三方登陆
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        removeDialog();
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        removeDialog();
        L.e(platform.getName(), "=====取消=====");
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
                msg.obj = "正在登录";
                //                handler.sendMessage(msg);
            }
            break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppUpdate.INSTALL_APK_REQUESTCODE:
                //有注册权限且用户允许安装
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppUpdate.getInstance().install(SetAty.this);
                } else {
                    //将用户引导至安装未知应用界面。
                    //                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    //                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);

                    Uri packageURI = Uri.parse("package:" + getPackageName());
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    startActivityForResult(intent, 1000);

                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000: // App更新
                AppUpdate.getInstance().install(SetAty.this);
                break;
        }
    }


    @Override
    public void onSuccess(String auth_code) {
        PreferencesUtils.putString(SetAty.this,"alipay_auth_code",auth_code);
        User.bindOther(auth_code, "7", "", this);
    }

    @Override
    public void onFailure() {
        removeDialog();
    }

}
