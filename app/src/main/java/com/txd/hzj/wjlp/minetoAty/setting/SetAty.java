package com.txd.hzj.wjlp.minetoAty.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.GlideCacheUtil;
import com.ants.theantsgo.util.L;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.RealnameAty;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：下午 3:40
 * 描述：设置
 * ===============Txunda===============
 */
public class SetAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private UserPst userPst;
    private String auth_status = "0";

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
    }

    @Override
    @OnClick({R.id.rel_editprofile, R.id.rel_editpassword, R.id.rel_editpaypassword, R.id.rel_realname,
            R.id.rel_bind_phone, R.id.sing_out_tv, R.id.clear_cach_layout})
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
                startActivity(EditPayPasswordAty.class, bundle);
                break;
            case R.id.rel_realname:// 实名认证
                if (auth_status.equals("2")) {
                    showRightTip("已认证成功");
                    break;
                }
                bundle = new Bundle();
                bundle.putString("auth_status", auth_status);
                startActivity(RealnameAty.class, bundle);
                break;
            case R.id.rel_bind_phone:// 绑定手机号
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
            case R.id.sing_out_tv:// 退出登录
                new MikyouCommonDialog(this, "确定要退出吗?", "提示", "确定", "取消").setOnDiaLogListener(new MikyouCommonDialog
                        .OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                            which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK:
                                Config.setLoginState(false);
                                logout();
                                break;
                            case MikyouCommonDialog.NO:
                                break;
                        }
                    }
                }).showDialog();
                break;
            case R.id.clear_cach_layout:// 清除缓存
                new MikyouCommonDialog(this, "提示", "确定要清除缓存？", "确定", "取消").setOnDiaLogListener(new MikyouCommonDialog
                        .OnDialogListener() {

                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                            which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK:
                                showProgressDialog();
                                GlideCacheUtil.getInstance().clearImageAllCache(getApplicationContext());
                                TimerTask task = new TimerTask() {
                                    public void run() {
                                        removeProgressDialog();
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(task, 500);
                                tv_data_number.setText("0.00M");
                                break;
                            case MikyouCommonDialog.NO:
                                break;
                        }
                    }
                }).showDialog();
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

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
        Map<String, String> data = (Map<String, String>) map.get("data");
        // 认证状态 0 未认证 1认证中 2 已认证
        auth_status = data.get("auth_status");
        phone = data.get("phone");
        user_bind_phone_tv.setText(phone);
        is_password = data.get("is_password");

        if (is_password.equals("0")) {
            rel_editpassword.setText("修改登录密码");
        } else {
            rel_editpassword.setText("设置登录密码");
        }

        is_pay_password = data.get("is_pay_password");
        if (is_pay_password.equals("0")) {
            rel_editpaypassword.setText("修改支付密码");
        } else {
            rel_editpaypassword.setText("设置支付密码");
        }


    }

    private void logout() {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                L.e("=====退出登录=====", "成功");
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
                L.e("=====退出登录=====", "退出中");
            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorTip("退出失败，请重新操作");
                    }
                });
                L.e("=====退出登录=====", "失败：" + code + "-----" + message);
            }
        });
    }

    /**
     * 获取缓存大小
     */
    public void SetSize() {
        tv_data_number.setText(GlideCacheUtil.getInstance().getCacheSize(getApplicationContext()));
    }
}
