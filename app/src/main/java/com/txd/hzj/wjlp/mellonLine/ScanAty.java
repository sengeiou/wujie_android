package com.txd.hzj.wjlp.mellonLine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.util.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.melloffLine.PaymentAty;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：上午 9:39
 * 描述：扫一扫
 */
public class ScanAty extends BaseAty implements QRCodeView.Delegate {


    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.zx_view)
    private QRCodeView mQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("扫一扫");
        mQR.setResultHandler(this);
        try {
            mQR.startSpot();
        } catch (Exception e) {
            showToast("相机启动失败，请退出后重新打开扫描");
            L.e("ScanAty Exception:" + e.toString());
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_scan;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    /**
     * 扫描二维码方法大全（已知）
     * <p>
     * mQR.startCamera();               开启预览，但是并未开始识别
     * mQR.stopCamera();                停止预览，并且隐藏扫描框
     * mQR.startSpot();                 开始识别二维码
     * mQR.stopSpot();                  停止识别
     * mQR.startSpotAndShowRect();      开始识别并显示扫描框
     * mQR.stopSpotAndHiddenRect();     停止识别并隐藏扫描框
     * mQR.showScanRect();              显示扫描框
     * mQR.hiddenScanRect();            隐藏扫描框
     * mQR.openFlashlight();            开启闪光灯
     * mQR.closeFlashlight();           关闭闪光灯
     * <p>
     * mQR.startSpotDelay(ms)           延迟ms毫秒后开始识别
     */
    @Override
    public void onScanQRCodeSuccess(String result) {

        L.e(result);
        //震动
        vibrate();
        //停止预览
        mQR.stopCamera();

        dataProcessing(result); // 处理数据

    }

    /**
     * 扫码成功之后数据的处理
     *
     * @param result 扫码成功返回字符串
     */
    private void dataProcessing(String result) {

        boolean isJsonStr = false;

        try {
            new JSONObject(result);
            isJsonStr = true;
        } catch (JSONException e) {
            isJsonStr = false;
        }

        if (isJsonStr) { // 如果回传的是JSON字符串并且字符串正确
            try {
                JSONObject jsonObject = new JSONObject(result);
                switch (jsonObject.getInt("type")) {
                    case 1: // 登录
                        if (!Config.isLogin()) {
                            toLogin(); // 如果未登录，则去登陆
                            return;
                        }
                        JSONObject data = jsonObject.getJSONObject("data");
                        String sid = data.getString("sid");

                        showToast("扫码成功，正在登陆网站端。。。");
                        // 实例化网络请求接口并添加请求参数
                        RegisterPst registerPst = new RegisterPst(this);
                        registerPst.qr_login(sid);
                        break;
                    case 2: // 邀请码注册
                        break;
                    case 3: // 下载
                        break;
                }
            } catch (JSONException e) {
                showToast("二维码异常，请刷新网站端界面。");
                showDialog(e.toString());
                L.e("扫码Json字符串异常：" + e.toString());
            }
        } else { // 否则的话按照正常字符串处理
            if (result.contains("User/mentorship/invite_code")) { // 扫描商家拜师码
                if (!Config.isLogin()) { // 如果未登录则先去登录
                    toLogin();
                } else {
                    String token = PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "token", ""); // 获取当前账号的Token
                    Bundle bundle = new Bundle();
                    bundle.putInt("from", 4);
                    result = result.contains("User/mentorship/invite_code") ? result + "/token/" + token : result; // 如果是拜师码，则添加当前登录人的Token
                    bundle.putString("url", result);
                    startActivity(WebViewAty.class, bundle);
                }
            } else if (result.contains("pay_money/pm_id")) { // 线下收款码
                Bundle bundle = new Bundle();
                bundle.putString("url", result);
                startActivity(WebViewAty.class, bundle);
            } else if (result.contains("OfflineStore/confirmation") && result.contains("stage_merchant_id")) { // 线下扫码付款
                String stage_merchant_id = "";
                String[] split = result.split("/");
                for (int i = 0; i < split.length; i++) {
                    if (!StringUtils.isEmpty(split[i]) && split[i].equals("stage_merchant_id")) {
                        stage_merchant_id = split[i + 1];
                        break;
                    }
                }
                if (!StringUtils.isEmpty(stage_merchant_id)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("stage_merchant_id", stage_merchant_id);
                    startActivity(PaymentAty.class, bundle);
                } else {
                    showToast("二维码错误，未获取到商家编号");
                }
            } else { // 非以上情况的直接跳转到网页
                Bundle bundle = new Bundle();
                bundle.putString("url", result);
                startActivity(WebViewAty.class, bundle);
            }
        }

        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showErrorTip("打开相机出错！请检查是否开启权限！");
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //启动相机
        mQR.startCamera();
    }

    @Override
    protected void onStop() {
        mQR.stopCamera();
        super.onStop();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("qr_login")) {
            L.e(jsonStr);
        }
    }
}
