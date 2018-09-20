package com.txd.hzj.wjlp.distribution.shopaty;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 10:08
 * 功能描述：店铺管理首页，跳转相关管理页面
 * 联系方式：无
 */
public class ShopMain extends BaseAty implements OnClickListener {

    private TextView titleName;
    private LinearLayout shop_person_title_manage;
    private LinearLayout shopUpGoods;
    private LinearLayout shopGoodsManage;
    private LinearLayout shopMoney;
    private LinearLayout shopSetUp;
    private LinearLayout shopPersonManage;
    private LinearLayout shopOrderManage;
    private LinearLayout shop_give;
    private TextView money_tv;
    private TextView visitor;
    private TextView orderNum;
    private ShopExhibitPst mExhibitPst;
    private String mShop_id;
    private DecimalFormat mFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("has_shop") && "2".equals(extras.getString("has_shop"))){
            showTanchuang();
        }
    }

    private void showTanchuang() {
        final MikyouCommonDialog dialog=new MikyouCommonDialog(this,"您的店铺已过期，请重新申请","温馨提示","确定","",false);
        dialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
            @Override
            public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                if (btnType==MikyouCommonDialog.OK){
                    finish();
                }
            }
        });
        dialog.showDialog();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dbt_shop_manage_main;
    }

    @Override
    protected void initialized() {
        titleName = findViewById(R.id.titlt_conter_tv);
        /*shop_person_title_manage=findViewById(R.id.shop_person_title_manage);
        shop_person_title_manage.setVisibility(View.GONE);*/
        //小店上货
        shopUpGoods = findViewById(R.id.shop_up_goods);
        //商品管理
        shopGoodsManage = findViewById(R.id.shop_goods_manage);
        //小店营销
        shopMoney = findViewById(R.id.shop_money);
        //店铺设置
        shopSetUp = findViewById(R.id.shop_set_up);
        //顾客管理
        shopPersonManage = findViewById(R.id.shop_person_manage);
        //订单管理
        shopOrderManage = findViewById(R.id.shop_order_magage);
        //代金券
        shop_give=findViewById(R.id.shop_give);
        //付款金额
        money_tv=findViewById(R.id.money_tv);
        //访客量
        visitor = findViewById(R.id.shop_visitor);
        //订单量
        orderNum = findViewById(R.id.shop_order);

        //注册点击事件
        shopUpGoods.setOnClickListener(this);
        shopGoodsManage.setOnClickListener(this);
        shopMoney.setOnClickListener(this);
        shopSetUp.setOnClickListener(this);
        shopPersonManage.setOnClickListener(this);
        shopOrderManage.setOnClickListener(this);
        shop_give.setOnClickListener(this);
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(this,"shop_id")){
            mShop_id = PreferencesUtils.getString(this, "shop_id");
        }

        mFormat = new DecimalFormat("0.00");
    }

    @Override
    protected void requestData() {
        titleName.setText("店铺管理");
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            mExhibitPst.shops(mShop_id);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("shops")) {
            Log.i("获取信息", jsonStr.toString());
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if ("200".equals(JSONUtils.getMapValue(map, "code"))) {

                String data = JSONUtils.getMapValue(map, "data");
                Map<String, String> mapData = JSONUtils.parseKeyAndValueToMap(data);

                //付款金额
                String pay_money = JSONUtils.getMapValue(mapData, "pay_money");
                //付款订单数
                String pay_orders = JSONUtils.getMapValue(mapData, "pay_orders");
                //访问量
                String visit_nums = JSONUtils.getMapValue(mapData, "visit_nums");

                money_tv.setText(mFormat.format(Double.parseDouble(pay_money)));
                orderNum.setText(pay_orders);
                visitor.setText(visit_nums);
            }

        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_up_goods:
                startActivity(ShopExhibit.class,null);
                break;
            case R.id.shop_goods_manage:
                startActivity(ShopGoodsManage.class,null);
                break;
            case R.id.shop_money:
                startActivity(ShopRevenue.class,null);
                break;
            case R.id.shop_set_up:
                //店铺设置
                startActivity(ShopSetUp.class, null);
                break;
            case R.id.shop_person_manage:
                //顾客管理
                startActivity(ShopPersonManage.class, null);
                break;
            case R.id.shop_order_magage:
                //订单管理
                startActivity(ShopOrderManage.class, null);
                break;
            case R.id.shop_give:
                //赠送代金券
                startActivity(ShopGiveYellowVoucher.class,null);
                break;
        }
    }




}
