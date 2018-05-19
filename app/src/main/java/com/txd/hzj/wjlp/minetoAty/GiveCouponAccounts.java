package com.txd.hzj.wjlp.minetoAty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.CateIndex;
import com.txd.hzj.wjlp.bean.GiveCouponBean;
import com.txd.hzj.wjlp.http.user.UserPst;

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
        balance = (TextView) findViewById(R.id.give_can_use_money_tv);
        balance.setText(blue_voucher);
        //title
        titleBack = (ImageView) findViewById(R.id.title_be_back_iv);
        tvName = (TextView) findViewById(R.id.titlt_conter_tv);
        //确定
        moneyOk = (TextView) findViewById(R.id.give_change_money_tv);
        //支付密码
        payPwds = (EditText) findViewById(R.id.give_pay_pwd_ev);
        //赠送金额
        moneyNumber = (EditText) findViewById(R.id.give_tr_acc_money_et);
        //收卷方
        userName = (EditText) findViewById(R.id.give_opposite_side_ev);

        //注册点击事件
        moneyOk.setOnClickListener(this);
        titleBack.setOnClickListener(this);
    }

    @Override
    protected void requestData() {
        tvName.setText("赠送蓝色代金券");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.give_change_money_tv:
                String money = moneyNumber.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String pwd = payPwds.getText().toString().trim();
                if (money!=null&&user!=null&&pwd!=null){
                    userPst.giveCoupon(money,user,pwd);
                }
                break;
            case R.id.title_be_back_iv:
                Intent intent = new Intent();
                if (nums!=null&&nums!=""){
                    intent.putExtra("info",nums);
                    //把结果返回
                    setResult(RESULT_OK,intent);
                    finish();
                }
                finish();
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.i("蓝色代金券",jsonStr);
        GiveCouponBean gson = GsonUtil.GsonToBean(jsonStr, GiveCouponBean.class);
        if (gson.getCode().equals("1")){
            showToast("赠送成功");
        }
        nums = gson.getNums();
        if (nums!=null){
            balance.setText(nums);
        }
    }
}