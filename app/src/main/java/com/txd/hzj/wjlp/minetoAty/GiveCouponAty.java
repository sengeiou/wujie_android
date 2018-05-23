package com.txd.hzj.wjlp.minetoAty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;

/**
 * 创建者：Qyl
 * 创建时间：2018/5/18 0018 14:11
 * 功能描述：此类为赠送蓝色代金券详情页面
 * 联系方式：无
 */
public class GiveCouponAty extends BaseAty implements View.OnClickListener {

    private ImageView back;
    private TextView tvName;
    private TextView layout_top_tv;
    private TextView number;
    private TextView ablance;
    private TextView giveCoupon;
    private String blue_voucher;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_give_coupon;
    }

    @Override
    protected void initialized() {

        Bundle extras = getIntent().getExtras();
         blue_voucher = extras.getString("blue_voucher");
        /**
         * title
         * */
        back = (ImageView) findViewById(R.id.title_be_back_iv);
        tvName = (TextView) findViewById(R.id.titlt_conter_tv);
        /**
         * 余额
         * */
        layout_top_tv = (TextView) findViewById(R.id.layout_top_tv);
        number = (TextView) findViewById(R.id.layout_bottom_tv);

         // 余额明细
        ablance = (TextView) findViewById(R.id.give_details_tv);
        //转账
        giveCoupon = (TextView) findViewById(R.id.give_accounts_tv);
        /**
         * 注册点击事件
         * */
        back.setOnClickListener(this);
        ablance.setOnClickListener(this);
        giveCoupon.setOnClickListener(this);
    }

    @Override
    protected void requestData() {
        tvName.setText("蓝色代金券");
        layout_top_tv.setText("我的蓝色代金券");
        if (blue_voucher!=null){
            number.setText(blue_voucher);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.title_be_back_iv:
                finish();
                break;
            case R.id.give_details_tv:
                Bundle bundles = new Bundle();
                bundles.putInt("from", 7);
                startActivity(ParticularsUsedByTricketAty.class,bundles);
                break;
            case R.id.give_accounts_tv:
                Intent intent = new Intent();
                intent.setClass(GiveCouponAty.this,GiveCouponAccounts.class);
               Bundle bundle = new Bundle();
                bundle.putString("blue_voucher",number.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent,1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000&&resultCode==RESULT_OK){
            if (data!=null){
                String info = (String) data.getSerializableExtra("info");
                if (info!=null){
                    number.setText(info);
                }
            }
        }
    }
}
