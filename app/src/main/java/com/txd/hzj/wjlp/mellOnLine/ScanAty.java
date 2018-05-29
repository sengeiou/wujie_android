package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：上午 9:39
 * 描述：扫一扫
 * ===============Txunda===============
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
        mQR.startSpot();
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
        //  showRightTip(result);
        L.e(result);
        //震动
        vibrate();
        //停止预览
        mQR.stopCamera();

        // type:1登录，2邀请码注册 ，3下载
        if (result.contains("type")) {
            if (!Config.isLogin()) { // 如果未登录则先去登录
                toLogin();
            } else {
                // 已登录
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String sid = data.getString("sid");

                    // 实例化网络请求接口并添加请求参数
                    RegisterPst registerPst = new RegisterPst(this);
                    registerPst.qr_login(sid);

                } catch (JSONException e) {
                    L.e("扫码Json字符串异常：" + e.toString());
                }
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("from", 4);
            bundle.putString("href", result);
            startActivity(NoticeDetailsAty.class, bundle);
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
        if (requestUrl.contains("qr_login")){
            L.e(jsonStr);
        }
    }
}
