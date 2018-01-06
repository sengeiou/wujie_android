package com.txd.hzj.wjlp.txunda_lh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.FindHouseByMapAty;
import com.txd.hzj.wjlp.txunda_lh.http.CarOrder;
import com.txd.hzj.wjlp.txunda_lh.http.HouseOrder;

import java.util.Map;

/**
 * by Txunda_LH on 2017/11/29.
 */

public class CarOrderInfo extends BaseAty {
    @ViewInject(R.id.imageview)
    private ImageView imageview;
    @ViewInject(R.id.tv_name)
    private TextView tv_title;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv1)
    private TextView tv1;
    @ViewInject(R.id.tv2)
    private TextView tv2;
    @ViewInject(R.id.shop_name)
    private TextView shop_name;
    @ViewInject(R.id.order_price)
    private TextView order_price;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;

    @ViewInject(R.id.address)
    private TextView address;

    @ViewInject(R.id.shop_mobile)
    private TextView shop_mobile;

    @ViewInject(R.id.contact_name)
    private TextView contact_name;

    @ViewInject(R.id.contact_mobile)
    private TextView contact_mobile;

    @ViewInject(R.id.order_sn)
    private TextView order_sn;

    @ViewInject(R.id.create_time)
    private TextView create_time;

    @ViewInject(R.id.pay_type)
    private TextView pay_type;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.submit)
    private TextView submit;
    private String lat = "";
    private String lng = "";
    @ViewInject(R.id.discount)
    private TextView discount;
    @ViewInject(R.id.layout_discount)
    LinearLayout layout_discount;
    @ViewInject(R.id.integral)
    private TextView integral;
    @ViewInject(R.id.layout_integral)
    private LinearLayout layout_integral;
    private String type = "";
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.ads)
    private TextView ads;
    @ViewInject(R.id.layout_tel)
    private LinearLayout layout_tel;
    @ViewInject(R.id.tv_btn_left)
    public TextView tv_btn_left;
    @ViewInject(R.id.tv_btn_right)
    public TextView tv_btn_right;
    private String status = "";
    private String order_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_order_info;
    }

    @Override
    protected void initialized() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type.equals("1")) {
            CarOrder.orderInfo(getIntent().getStringExtra("id"), this);
        } else {
            HouseOrder.orderInfo(getIntent().getStringExtra("id"), this);
        }
        showProgressDialog();
    }

    @Override
    protected void requestData() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("lng", lng);
                bundle.putString("lat", lat);
                startActivity(FindHouseByMapAty.class, bundle);
            }
        });

        tv_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("0")) {
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("order_id", order_id);
                    ApiTool2 apiTool2 = new ApiTool2();
                    apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/cancelOrder" : "HouseOrder/cancelOrder"), params, CarOrderInfo.this);

                }
            }
        });
        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("0")) {
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("order_id", order_id);
                    ApiTool2 apiTool2 = new ApiTool2();
                    apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/addOrder" : "HouseOrder/addOrder"), params, CarOrderInfo.this);
                } else if (status.equals("2")) {
                    Bundle b = new Bundle();
                    b.putString("id",order_id);
                    b.putString("type",type);
                    startActivity(aty_comment.class, b);
                } else {
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("order_id", order_id);
                    ApiTool2 apiTool2 = new ApiTool2();
                    apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/deleteOrder" : "HouseOrder/deleteOrder"), params, CarOrderInfo.this);
                }
            }
        });
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.contains("orderInfo")) {
            status = map.get("status");
            order_id = map.get("order_id");
            Glide.with(this).load(type.equals("1") ? map.get("car_img") : map.get("house_style_img")).into(imageview);
            tv_title.setText(type.equals("1") ? map.get("car_name") : map.get("style_name"));
            tv_price.setText("￥"+map.get("goods_price"));
            String s = "";
            if (type.equals("1")) {
                s = "车全款：￥" + map.get
                        ("all_price") + "\n可    抵：￥" + map.get("true_pre_money") + "车款";
            } else {
                s = "房全款：￥" + map.get
                        ("all_price") + "\n可    抵：￥" + map.get("true_pre_money") + "房款";
                tv2.setText(map.get("house_name") + "\t\t" + map.get("one_price") + "元/平");
            }
            tv1.setText(s);
            tv_num.setText("x" + map.get("num"));
            order_price.setText("¥" + map.get("order_price"));
            if (map.get("discount").equals("0.00")) {
                layout_discount.setVisibility(View.GONE);
            } else {
                discount.setText("¥-" + map.get("discount"));
            }
            if (map.get("integral").equals("0.00")) {
                layout_integral.setVisibility(View.GONE);
            } else {
                integral.setText(map.get("integral"));
            }
            lng = map.get("lng");
            lat = map.get("lat");
            switch (status) {
                case "0":
                    titlt_conter_tv.setText("待付款");
                    tv_btn_left.setVisibility(View.VISIBLE);
                    tv_btn_right.setVisibility(View.VISIBLE);
                    tv_btn_left.setText("取消订单");
                    tv_btn_right.setText("付款");
                    break;
                case "1":
                    titlt_conter_tv.setText("办理手续中");
                    tv_btn_left.setVisibility(View.GONE);
                    tv_btn_right.setVisibility(View.GONE);
                    break;
                case "2":
                    titlt_conter_tv.setText("待评价");
                    tv_btn_left.setVisibility(View.GONE);
                    tv_btn_right.setVisibility(View.VISIBLE);
                    tv_btn_right.setText("评价");
                    break;
                case "3":
//                holder.state.setText("卖家已发货");
//                holder.tv_btn_left.setText("查看物流");
//                holder.tv_btn_right.setText("确认收货");
                    break;
                case "4":
                    titlt_conter_tv.setText("已完成");
                    tv_btn_right.setText("删除");
                    tv_btn_left.setVisibility(View.GONE);
                    tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    titlt_conter_tv.setText("已取消");
                    tv_btn_right.setText("删除");
                    tv_btn_left.setVisibility(View.GONE);
                    tv_btn_right.setVisibility(View.VISIBLE);
                    break;
            }
            if (type.equals("2")) {
                name.setText("楼盘名称");
                ads.setText("楼盘地址");
                layout_tel.setVisibility(View.GONE);
            }

            shop_name.setText(type.equals("1") ? map.get("shop_name") : map.get("style_name"));
            address.setText(type.equals("1") ? map.get("address") : map.get("sell_address"));
            shop_mobile.setText(type.equals("1") ? map.get("shop_mobile") : "");
            shop_mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(type.equals("1") ? map.get("shop_mobile") : map.get("link_phone"));
                }
            });
            contact_name.setText(type.equals("1") ? map.get("contact_name") : map.get("link_man"));
            contact_mobile.setText(type.equals("1") ? map.get("contact_mobile") : map.get("link_phone"));
            contact_mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(type.equals("1") ? map.get("contact_mobile") : map.get("link_phone"));
                }
            });
            order_sn.setText(map.get("order_sn"));
            create_time.setText(map.get("create_time"));
            switch (map.get("pay_type")) {
                case "0":
                    pay_type.setText("待付款");
                    break;
                case "1":
                    pay_type.setText("支付宝");
                    break;
                case "2":
                    pay_type.setText("微信");
                    break;
                case "3":
                    pay_type.setText("余额");
                    break;
                case "4":
                    pay_type.setText("积分");
                    break;
            }
        }
        if (requestUrl.contains("cancelOrder")) {
            if (type.equals("1")) {
                CarOrder.orderInfo(order_id, this);
            } else {
                HouseOrder.orderInfo(order_id, this);
            }
            showProgressDialog();
        }
        if (requestUrl.contains("deleteOrder")) {
            finish();
            showToast("删除成功！");
        }
        if (requestUrl.contains("addOrder")) {
            Intent i = new Intent();
            i.setClass(this, aty_pay.class);
            i.putExtra("data", map.toString());
            i.putExtra("type", type);
            startActivity(i);
        }
    }
}
