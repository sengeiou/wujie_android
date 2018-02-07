package com.txd.hzj.wjlp.txunda_lh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * by Txunda_LH on 2018/1/30.
 */

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @OnClick({R.id.pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay:
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                startActivity(VipPayAty.class, bundle);
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
        String Y = "会员年费¥" + map.get("money") + "/" +( map.get("prescription").equals("0")?"永久":"年");//年or永久
        tv_price.setText(Y);
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
    }

    @Override
    protected void requestData() {

    }
}
