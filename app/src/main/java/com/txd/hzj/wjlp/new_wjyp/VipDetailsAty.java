package com.txd.hzj.wjlp.new_wjyp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.http.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VipDetailsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    private String data;
    private Map<String, String> map;
    private List<Map<String, String>> list_pic = new ArrayList<>();
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv1)
    TextView tv1;
    @ViewInject(R.id.pay)
    LinearLayout pay;
    @ViewInject(R.id.tv2)
    TextView tv2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @OnClick({R.id.pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay:
                if (map.get("sale_status").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("data", data);
                    bundle.putString("order_id", "");
                    startActivity(VipPayAty.class, bundle);
                } else {
                    showToast("此会员级别禁止申请");
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_vipdetails;
    }

    @Override
    protected void initialized() {
        data = getIntent().getStringExtra("data");
        map = JSONUtils.parseKeyAndValueToMap(data);
        list_pic = JSONUtils.parseKeyAndValueToMapList(map.get("abs_url"));
        titlt_conter_tv.setText(map.get("rank_name"));
        String Y = "会员年费¥" + map.get("money") + "/" + (map.get("prescription").equals("0") ? "永久" : "年");//年or永久
        tv_price.setText(Y);
//        big_gift

        if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("1")&&map.get("score_status").equals("0")){
        tv1.setText("已拥有更高级别会员卡");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("1")&&map.get("score_status").equals("1")){
            tv1.setText("续费");
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("1")&&map.get("score_status").equals("2")){
            tv1.setText("立即开通");
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("1")&&map.get("score_status").equals("4")){
            tv1.setText("永久有效");
            pay.setEnabled(false);
            tv1.setTextColor(Color.BLACK);
            pay.setBackgroundColor(Color.GRAY);
            tv2.setVisibility(View.INVISIBLE);
        }else if (map.get("sale_status").equals("1")){
            pay.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("2")&&map.get("score_status").equals("0")){
            tv2.setVisibility(View.INVISIBLE);
            tv1.setText("已拥有更高级别会员卡");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("2")&&map.get("score_status").equals("1")){
            tv1.setText("续费");
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("2")&&map.get("score_status").equals("2")){
            tv1.setText("立即开通");
            tv2.setVisibility(View.INVISIBLE);
        }else if(map.get("sale_status").equals("0")&&map.get("big_gift").equals("2")&&map.get("score_status").equals("4")){
            tv1.setText("永久有效");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        }else if (map.get("sale_status").equals("1")&&map.get("big_gift").equals("2")) {
            pay.setVisibility(View.INVISIBLE);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        }

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(final int position, ImageView imageView) {
                Glide.with(VipDetailsAty.this).load(list_pic.get(position).get("url"))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.icon_200)
                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (!TextUtils.isEmpty(list_pic.get(position).get("merchant_id")) && !list_pic.get(position).get("merchant_id").equals("0")) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("mell_id", list_pic.get(position).get("merchant_id"));
//                            startActivity(MellInfoAty.class, bundle);
//                        } else if (!TextUtils.isEmpty(list_pic.get(position).get("goods_id")) && !list_pic.get(position).get("goods_id").equals("0")) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("ticket_buy_id", list_pic.get(position).get("goods_id"));
//                            bundle.putInt("from", 1);
//                            startActivity(TicketGoodsDetialsAty.class, bundle);
//                        } else {
//                            forShowAds(list_pic.get(position).get("desc"), list_pic.get(position).get("href"));
//                        }
                    }
                });
            }
        };
        online_carvouse_view.setPageCount(list_pic.size());
        online_carvouse_view.setImageListener(imageListener);
        for (Map<String, String> map: list_pic) {
            L.e("wang", "===>>>>>>>map:" + map.toString());
        }
    }

    @Override
    protected void requestData() {

    }

}
