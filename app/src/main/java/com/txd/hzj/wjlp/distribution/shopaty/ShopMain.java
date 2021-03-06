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

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ToShareAty;

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
    private LinearLayout share_shop;
    private TextView money_tv;
    private TextView visitor;
    private TextView orderNum;
    private TextView level_tv;
    private ShopExhibitPst mExhibitPst;
    private String mShop_id;
    private DecimalFormat mFormat;
    private String shopName;
    private String shopDesc;
    private String shopUrl;
    private String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("has_shop") && "2".equals(extras.getString("has_shop"))) {
            showTanchuang();
        }
    }

    private void showTanchuang() {
        final MikyouCommonDialog dialog = new MikyouCommonDialog(this, "您的店铺已过期，请重新申请", "温馨提示", "确定", "", false);
        dialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
            @Override
            public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                if (btnType == MikyouCommonDialog.OK) {
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
        shop_give = findViewById(R.id.shop_give);
        //付款金额
        money_tv = findViewById(R.id.money_tv);
        //访客量
        visitor = findViewById(R.id.shop_visitor);
        //订单量
        orderNum = findViewById(R.id.shop_order);

        level_tv=findViewById(R.id.level_tv);

        share_shop = findViewById(R.id.share_shop);

        //注册点击事件
        shopUpGoods.setOnClickListener(this);
        shopGoodsManage.setOnClickListener(this);
        shopMoney.setOnClickListener(this);
        shopSetUp.setOnClickListener(this);
        shopPersonManage.setOnClickListener(this);
        shopOrderManage.setOnClickListener(this);
        shop_give.setOnClickListener(this);
        share_shop.setOnClickListener(this);
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(this, "shop_id")) {
            mShop_id = PreferencesUtils.getString(this, "shop_id");
        }

        mFormat = new DecimalFormat("0.00");
    }

    @Override
    protected void requestData() {
        titleName.setText("链客网店");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                //店主等级  [1 => '初级店铺', 2 =>'中级店铺', 3 => '高级店铺' ]
                String set_id = JSONUtils.getMapValue(mapData, "set_id");
                if ("1".equals(set_id)){
                    level_tv.setBackgroundResource(R.drawable.icon_shop_star);
                    level_tv.setText("初级店主");
                }else if ("2".equals(set_id)){
                    level_tv.setBackgroundResource(R.drawable.icon_shop_star);
                    level_tv.setText("中级店主");
                }else if ("3".equals(set_id)){
                    level_tv.setBackgroundResource(R.drawable.icon_shop_star);
                    level_tv.setText("高级店主");
                }else if ("4".equals(set_id)){
                    level_tv.setBackgroundResource(R.drawable.icon_shop_v);
                    level_tv.setText("高级店主+");
                }

                money_tv.setText(mFormat.format(Double.parseDouble(pay_money)));
                orderNum.setText(pay_orders);
                visitor.setText(visit_nums);

                //店铺名字
                shopName = JSONUtils.getMapValue(mapData, "shop_name");
                //店铺描述
                shopDesc = JSONUtils.getMapValue(mapData, "shop_desc");
                //店铺头像
                shopUrl = JSONUtils.getMapValue(mapData, "shop_url");


                user_id = JSONUtils.getMapValue(mapData, "user_id");
            }

        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_up_goods:
                startActivity(ShopExhibit.class, null);
                break;
            case R.id.shop_goods_manage:
                startActivity(ShopGoodsManage.class, null);
                break;
            case R.id.shop_money:
                startActivity(ShopRevenue.class, null);
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
                startActivity(ShopGiveYellowVoucher.class, null);
                break;
            case R.id.share_shop:
                //分享店铺
                Bundle bundle = new Bundle();
                bundle.putString("title", shopName);
                bundle.putString("pic", shopUrl);
                String shop_id_jiami = PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "shop_id_jiami");
                bundle.putString("url", Config.SHARE_URL + "Distribution/DistributionShop/shop/shop_id/" + shop_id_jiami + ".html");
                if (shopDesc==null || shopDesc.isEmpty() || shopDesc.equals("null")){
                    shopDesc="终于等到你，还好我没放弃。欢迎光临本小店~";
                }
                bundle.putString("context", shopDesc);
                bundle.putString("id", user_id);
                bundle.putString("Shapetype", "6");
                startActivity(ToShareAty.class, bundle);
                break;
        }
    }


}
