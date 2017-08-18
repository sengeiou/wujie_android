package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.minetoAty.BindPhoneAty;
import com.txd.hzj.wjlp.minetoAty.EditLoginPasswordAty;
import com.txd.hzj.wjlp.minetoAty.EditPayPasswordAty;
import com.txd.hzj.wjlp.minetoAty.EditProfileAty;
import com.txd.hzj.wjlp.minetoAty.RealnameAty;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
    }

    @Override
    @OnClick({R.id.rel_editprofile, R.id.rel_editpassword, R.id.rel_editpaypassword, R.id.rel_realname,
            R.id.rel_bind_phone, R.id.sing_out_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rel_editprofile:// 个人资料
                startActivity(EditProfileAty.class, null);
                break;
            case R.id.rel_editpassword:// 修改登录密码
                startActivity(EditLoginPasswordAty.class, null);
                break;
            case R.id.rel_editpaypassword:// 修改支付密码
                startActivity(EditPayPasswordAty.class, null);
                break;
            case R.id.rel_realname:// 实名认证
                startActivity(RealnameAty.class, null);
                break;
            case R.id.rel_bind_phone:// 绑定手机号
                startActivity(BindPhoneAty.class, null);
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
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_set;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

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

}
