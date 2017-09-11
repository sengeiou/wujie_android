package com.txd.hzj.wjlp.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ants.theantsgo.R;
import com.ants.theantsgo.tools.ConstantUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends BaseAty implements IWXAPIEventHandler {

    private static final String TAG = "微信支付回调Activity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, ConstantUtils.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_base;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent("antGo.wxPay");
            intent.putExtra("errCode", resp.errCode);
            sendBroadcast(intent);
            finish();

        }
    }
}