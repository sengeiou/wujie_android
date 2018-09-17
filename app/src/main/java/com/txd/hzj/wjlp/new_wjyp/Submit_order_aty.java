package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 提交房产和车辆的订单
 */

public class Submit_order_aty extends BaseAty {
    @ViewInject(R.id.imageview)
    private ImageView imageview;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv1)
    private TextView tv1;
    @ViewInject(R.id.tv2)
    private TextView tv2;
    @ViewInject(R.id.price)
    private TextView price;
    @ViewInject(R.id.im_jia)
    private ImageView im_jia;
    @ViewInject(R.id.im_jian)
    private ImageView im_jian;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.submit)
    private TextView submit;
    Intent intent;
    private int number = 1;
    private double money;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_sb_order;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("提交订单");
    }

    @Override
    protected void requestData() {
        intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("image")).into(imageview);
        tv_title.setText(intent.getStringExtra("title"));
        tv_price.setText("¥" + intent.getStringExtra("price"));
        money = Double.parseDouble(intent.getStringExtra("price"));
        tv1.setText(intent.getStringExtra("money"));
        tv2.setText(intent.getStringExtra("type").equals("1") ? "" : intent.getStringExtra("developer"));
        price.setText("¥" + intent.getStringExtra("price"));
        im_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                setPrice(number);

            }
        });
        im_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1) {
                    return;
                }
                number--;
                setPrice(number);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                ApiTool2 apiTool2 = new ApiTool2();
                params.addBodyParameter((intent.getStringExtra("type").equals("1") ? "car_id" : "style_id"), intent.getStringExtra("id"));
                params.addBodyParameter("num", String.valueOf(number));
                apiTool2.postApi(Config.BASE_URL + (intent.getStringExtra("type").equals("1") ? "CarOrder/addOrder" : "HouseOrder/addOrder"),
                        params, Submit_order_aty.this);
                showProgressDialog();
            }
        });
    }

    private void setPrice(int number) {
        DecimalFormat df = new DecimalFormat("#.00");
        double price = number * money;
        this.price.setText("¥" + df.format(price));
        tv_num.setText(String.valueOf(number));
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("CarOrder/addOrder")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Bundle bundle = new Bundle();
            bundle.putString("type", intent.getStringExtra("type"));
            bundle.putString("data", data.get("data"));
            startActivity(Pay_aty.class, bundle);
            finish();
        }
        if (requestUrl.contains("HouseOrder/addOrder")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Bundle bundle = new Bundle();
            bundle.putString("type", intent.getStringExtra("type"));
            bundle.putString("data", data.get("data"));
            startActivity(Pay_aty.class, bundle);
            finish();
        }
    }
}
