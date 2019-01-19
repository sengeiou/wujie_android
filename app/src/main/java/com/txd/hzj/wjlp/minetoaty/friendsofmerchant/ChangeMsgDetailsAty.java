package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 14:18
 * 功能描述：互换消息详情
 */
public class ChangeMsgDetailsAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.otherLayout)
    private LinearLayout otherLayout;

    @ViewInject(R.id.otherSexTv)
    private TextView otherSexTv;

    @ViewInject(R.id.otherAgeTv)
    private TextView otherAgeTv;

    @ViewInject(R.id.otherGradeTv)
    private TextView otherGradeTv;

    @ViewInject(R.id.otherAddressTv)
    private TextView otherAddressTv;

    @ViewInject(R.id.otherAgreeTv)
    private TextView otherAgreeTv;

    @ViewInject(R.id.otherRefuseTv)
    private TextView otherRefuseTv;


    @ViewInject(R.id.ownLayout)
    private LinearLayout ownLayout;

    @ViewInject(R.id.ownSexTv)
    private TextView ownSexTv;

    @ViewInject(R.id.ownAgeTv)
    private TextView ownAgeTv;

    @ViewInject(R.id.ownGradeTv)
    private TextView ownGradeTv;

    @ViewInject(R.id.ownAddressTv)
    private TextView ownAddressTv;

    @ViewInject(R.id.ownAgreeTv)
    private TextView ownAgreeTv;

    @ViewInject(R.id.ownRefuseTv)
    private TextView ownRefuseTv;

    @ViewInject(R.id.changeSuccessTv)
    private TextView changeSuccessTv;
    private String mCid;
    private String mSta_mid;

    private String age;
    private String sex;
    private String member_coding;
    private String city_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_msg_details;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("详情");
        mCid = getIntent().getStringExtra("cid");
        mSta_mid = getIntent().getStringExtra("sta_mid");
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        exchange_info(mSta_mid, mCid, this);
    }

    void exchange_info(String sta_mid, String cid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/exchange_info", params, baseView);
    }

    void exchange_log(String sta_mid, String status,String c_id, String sex,String age, String member_coding,String city_id,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("status", status);
        params.addBodyParameter("c_id", c_id);
        if (!TextUtils.isEmpty(sex)){
            params.addBodyParameter("sex", sex);
        }
        if (!TextUtils.isEmpty(age)){
            params.addBodyParameter("age", age);
        }
        if (!TextUtils.isEmpty(member_coding)){
            params.addBodyParameter("member_coding", member_coding);
        }
        if (!TextUtils.isEmpty(city_id)){
            params.addBodyParameter("city_id", city_id);
        }
        apiTool2.postApi(Config.BASE_URL + "OsManager/exchange_log", params, baseView);
    }

    void exchange_refuse_log(String sta_mid, String status,String c_id,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("status", status);
        params.addBodyParameter("c_id", c_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/exchange_refuse_log", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("exchange_info")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ArrayList<Map<String, String>> uConditionList = JSONUtils.parseKeyAndValueToMapList(data.get("u_condition"));
            ArrayList<Map<String, String>> bConditionList = JSONUtils.parseKeyAndValueToMapList(data.get("b_condition"));
            double type = Double.parseDouble(data.get("type"));
            double status = Double.parseDouble(data.get("status"));
        /*  type == 1时 我发出的互换请求
            status = 0  请等待对方审核
            status = 1  成功交换
            status = 2  同意和拒绝按钮
            status = 3  对方已拒绝
            status = 5  已拒绝
        */
        /*  type == 2时 发给我的互换请求
            status = 0  同意和拒绝按钮
            status = 1  成功交换
            status = 3  已拒绝
            status = 5  对方已拒绝
         */

            if (uConditionList != null) {
                Map<String, String> uCondition = uConditionList.get(0);
                ownLayout.setVisibility(View.VISIBLE);
                ownSexTv.setText(uCondition.get("sex"));
                ownAgeTv.setText(uCondition.get("age"));
                ownGradeTv.setText(uCondition.get("member_coding"));
                ownAddressTv.setText(uCondition.get("city_id"));
            } else {
                ownLayout.setVisibility(View.GONE);
            }

            if (bConditionList != null) {
                Map<String, String> bCondition = bConditionList.get(0);
                otherLayout.setVisibility(View.VISIBLE);
                otherSexTv.setText(bCondition.get("sex"));
                otherAgeTv.setText(bCondition.get("age"));
                otherGradeTv.setText(bCondition.get("member_coding"));
                otherAddressTv.setText(bCondition.get("city_id"));
            } else {
                otherLayout.setVisibility(View.GONE);
            }

            if (type == 1) {
                if (status == 0) {
                    otherAgreeTv.setVisibility(View.GONE);
                    otherRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("请等待对方审核");
                } else if (status == 1) {
                    otherAgreeTv.setVisibility(View.GONE);
                    otherRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("成功交换");
                } else if (status == 2) {
                    otherAgreeTv.setVisibility(View.VISIBLE);
                    otherRefuseTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setVisibility(View.GONE);
                } else if (status == 3) {
                    otherAgreeTv.setVisibility(View.GONE);
                    otherRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("对方已拒绝");
                } else if (status == 5) {
                    otherAgreeTv.setVisibility(View.GONE);
                   otherRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("已拒绝");
                }
            } else if (type == 2) {
                if (status == 0) {
                    ownAgreeTv.setVisibility(View.VISIBLE);
                    ownRefuseTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setVisibility(View.GONE);
                } else if (status == 1) {
                    ownAgreeTv.setVisibility(View.GONE);
                    ownRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("成功交换");
                } else if (status == 3) {
                    ownAgreeTv.setVisibility(View.GONE);
                    ownRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("已拒绝");
                } else if (status == 5) {
                    ownAgreeTv.setVisibility(View.GONE);
                    ownRefuseTv.setVisibility(View.GONE);
                    changeSuccessTv.setVisibility(View.VISIBLE);
                    changeSuccessTv.setText("对方已拒绝");
                }
            }
            return;
        }
        if (requestUrl.endsWith("exchange_log") || requestUrl.endsWith("exchange_refuse_log")){
            showToast(map.get("message"));
            if (Integer.parseInt(map.get("code"))==1){
                exchange_info(mSta_mid, mCid, this);
            }
            return;
        }
    }

    @Override
    @OnClick({R.id.otherAgreeTv, R.id.otherRefuseTv, R.id.ownAgreeTv, R.id.ownRefuseTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otherAgreeTv:
                exchange_log(mSta_mid,"1",mCid,sex,age,member_coding,city_id,this);
                break;
            case R.id.otherRefuseTv:
                exchange_refuse_log(mSta_mid,"5",mCid,this);
                break;
            case R.id.ownAgreeTv:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid", mSta_mid);
                bundle.putString("c_id",mCid);
                startActivity(SetExchangeConditionsAty.class,bundle);
                break;
            case R.id.ownRefuseTv:
                exchange_refuse_log(mSta_mid,"3",mCid,this);
                break;
        }
    }
}
