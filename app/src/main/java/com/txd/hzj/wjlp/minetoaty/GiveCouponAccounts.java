package com.txd.hzj.wjlp.minetoAty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GiveCouponBean;
import com.txd.hzj.wjlp.http.user.UserPst;

import io.reactivex.annotations.Nullable;

/**
 * 创建者：Qyl
 * 创建时间：2018/5/18 0018 14:46
 * 功能描述：此类为蓝色代金券赠送页面
 * 联系方式：烧纸
 */
public class GiveCouponAccounts extends BaseAty implements View.OnClickListener {

    private TextView tvName;
    private TextView moneyOk;
    private EditText payPwds;
    private EditText moneyNumber;
    private EditText userName;
    private TextView balance;
    private UserPst userPst;
    private String nums;
    private ImageView titleBack;
    private String mMerchant_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPst = new UserPst(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.give_coupon_accounts;
    }

    @Override
    protected void initialized() {
        //可用余额
        String blue_voucher = getIntent().getExtras().getString("blue_voucher");
        mMerchant_id = getIntent().getExtras().getString("merchant_id");
        balance = findViewById(R.id.give_can_use_money_tv);
        balance.setText(blue_voucher);
        //title
        titleBack =  findViewById(R.id.title_be_back_iv);
        tvName =  findViewById(R.id.titlt_conter_tv);
        //确定
        moneyOk = findViewById(R.id.give_change_money_tv);
        //支付密码
        payPwds =  findViewById(R.id.give_pay_pwd_ev);
        //赠送金额
        moneyNumber = findViewById(R.id.give_tr_acc_money_et);
        //收卷方
        userName =  findViewById(R.id.give_opposite_side_ev);

        //注册点击事件
        moneyOk.setOnClickListener(this);
        titleBack.setOnClickListener(this);
    }

    @Override
    protected void requestData() {
        tvName.setText("赠送");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.give_change_money_tv:
                String money = moneyNumber.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String pwd = payPwds.getText().toString().trim();
                if (money != null && user != null && pwd != null) {
                    userPst.giveCoupon(money, user, pwd,mMerchant_id);
                }
                break;
            case R.id.title_be_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.i("蓝色代金券", jsonStr);
        GiveCouponBean gson = GsonUtil.GsonToBean(jsonStr, GiveCouponBean.class);
        if ("1".equals(gson.getCode())) {
            showToast("赠送成功");
        }
        nums = gson.getNums();
        if (nums != null) {
            balance.setText(nums);
        }
        Intent intent = new Intent();
        if (nums != null && nums != "") {
            intent.putExtra("info", nums);
            //把结果返回
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
