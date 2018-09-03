package com.txd.hzj.wjlp.minetoAty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Promoters;
import com.txd.hzj.wjlp.bean.mine.MineInfoBean;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mainFgt.MineFgt;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;
import com.txd.hzj.wjlp.new_wjyp.VipDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private UserPst userPst;
    private String type;
    private LinearLayout view;
    @ViewInject(R.id.iv_vip)
    private ImageView ima_view;
    private Context context;
    private Promoters mData;
    Map<String, String> map;
    ArrayList<Map<String, String>> list = new ArrayList<>();
    private List<MineInfoBean> shangjiamaList;

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
        if (extras != null) {
            type = extras.getString("TYPE");
            if (type.equals("0")) {
                blue_voucher = extras.getString("blue_voucher");
                if (extras.containsKey("shangjiamaList")) {
                    shangjiamaList = (List<MineInfoBean>) extras.getSerializable("shangjiamaList");
                }
            } else {
                view = findViewById(R.id.coupon_ll_vip);
                view.setVisibility(View.GONE);
                ima_view.setVisibility(View.VISIBLE);
                String url = extras.getString("url");
                Glide.with(this).load(url)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ima_view);
                User.userCard(this);
            }
        }


        /**
         * title
         * */
        back = findViewById(R.id.title_be_back_iv);

        tvName = findViewById(R.id.titlt_conter_tv);
        /**
         * 余额
         * */
        layout_top_tv = findViewById(R.id.layout_top_tv);
        number = findViewById(R.id.layout_bottom_tv);

        // 余额明细
        ablance = findViewById(R.id.give_details_tv);
        //转账
        giveCoupon = findViewById(R.id.give_accounts_tv);
        /**
         * 注册点击事件
         * */
        back.setOnClickListener(this);
        ima_view.setOnClickListener(this);
        ablance.setOnClickListener(this);
        giveCoupon.setOnClickListener(this);
    }


    @Override
    protected void requestData() {
        if (type.equals("0")) {
            tvName.setText("蓝色代金券");
        } else {
            tvName.setText("申请无界推广会员");
        }
        layout_top_tv.setText("蓝色代金券金额");
        if (blue_voucher != null) {
            number.setText(blue_voucher);
        }

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_be_back_iv:
                finish();
                break;
            case R.id.give_details_tv:
                Bundle bundles = new Bundle();
                bundles.putInt("from", 7);
                startActivity(ParticularsUsedByTricketAty.class, bundles);
                break;
            case R.id.give_accounts_tv:
                if (shangjiamaList != null && shangjiamaList.size() > 0) {
                    showTanchuang();
                }
                break;
            case R.id.iv_vip:
                try {
                    /**
                     * 跳转到购买优享会员页面
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString("sale_status", list.get(2).get("sale_status"));
                    bundle.putString("rank_name", list.get(2).get("rank_name"));
                    bundle.putString("money", list.get(2).get("money"));
                    bundle.putString("prescription", list.get(2).get("prescription"));
                    bundle.putString("big_gift", list.get(2).get("big_gift"));
                    bundle.putString("score_status", list.get(2).get("score_status"));
                    bundle.putString("abs_url", list.get(2).get("abs_url"));
                    bundle.putString("member_coding", list.get(2).get("member_coding"));
                    startActivity(VipDetailsAty.class, bundle);
                } catch (IndexOutOfBoundsException e) {
                    showToast("该会员卡信息存在异常");
                }
                break;
        }
    }

    private void showTanchuang() {
        final Dialog dialog = new Dialog(GiveCouponAty.this, R.style.Ticket_Dialog);
        View view = View.inflate(GiveCouponAty.this, R.layout.aty_registration_list, null);
        dialog.setContentView(view);
        TextView close_tv = view.findViewById(R.id.close_tv);
        TextView jump_tv=view.findViewById(R.id.jump_tv);
        jump_tv.setVisibility(View.GONE);
        ListView listView = view.findViewById(R.id.listview);
        MineFgt.MineInfoAdapter infoAdapter;
        close_tv.setText("赠送蓝色代金券：店铺选择");
        infoAdapter = new MineFgt.MineInfoAdapter(GiveCouponAty.this, shangjiamaList);
        listView.setAdapter(infoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("blue_voucher", number.getText().toString());
                bundle.putString("merchant_id",shangjiamaList.get(position).getStage_merchant_id());
                startActivityForResult(GiveCouponAccounts.class, bundle, 1000);
                dialog.dismiss();
            }
        });
        jump_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("blue_voucher", number.getText().toString());
                bundle.putString("merchant_id","");
                startActivityForResult(GiveCouponAccounts.class, bundle, 1000);
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (Settings.displayWidth * 0.8);
        attributes.height = Settings.displayHeight / 3;
        window.setAttributes(attributes);
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("userCard")) {
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (data != null) {
                String info = (String) data.getSerializableExtra("info");
                if (info != null) {
                    number.setText(info);
                }
            }
        }
    }
}
