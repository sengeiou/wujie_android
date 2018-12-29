package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/29 9:41
 * 功能描述：海报详情页
 */
public class PosterAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.goods_title_tv)
    public TextView goods_title_tv;
    @ViewInject(R.id.goods_brief_tv)
    public TextView goods_brief_tv;
    @ViewInject(R.id.goods_price_tv)
    public TextView goods_price_tv;
    @ViewInject(R.id.goods_num_tv)
    public TextView goods_num_tv;
    @ViewInject(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private String mType;
    private String mId;
    private ArrayList<String> mImages;
    private String mGoods_name;
    private String mIntegral;
    private String mDiscount;
    private String mShop_price;
    private String mMarket_price;
    private String mShop_id;
    private String mGoods_brief;
    private String mSell_num;

    private MyAdpter mMyAdpter;
    private String mIs_active;

    /**
     * @param activity
     * @param type         1、普通商品(进口管、主题街) 2、拼单购 3、积分商城 4、分销（普通商品） 5、分销（399商品）
     * @param id           普通商品是goods_id，当是拼单购(group_buy_id)、积分商城(integral_buy_id)、分销(gid或者id)
     * @param images       组合海报商品图片 http开头的图片地址
     * @param goods_name   商品名称
     * @param integral     所返最多积分
     * @param discount     最大用券比例
     * @param shop_price   售价
     * @param market_price 市场价
     * @param shop_id      分销的shop_id
     * @param goods_brief  商品简介
     * @param sell_num       已售数量
     */
    public static void getInstance(Activity activity, String type, String id, ArrayList<String> images, String goods_name, String integral, String discount, String shop_price, String market_price, String shop_id, String goods_brief, String sell_num,String is_active) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("id", id);
        bundle.putStringArrayList("images", images);
        bundle.putString("goods_name", goods_name);
        bundle.putString("integral", integral);
        bundle.putString("discount", discount);
        bundle.putString("shop_price", shop_price);
        bundle.putString("market_price", market_price);
        bundle.putString("shop_id", shop_id);
        bundle.putString("goods_brief", goods_brief);
        bundle.putString("sell_num", sell_num);
        bundle.putString("is_active", is_active);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(activity, PosterAty.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.poster_layout;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("推广素材制作");
        Bundle extras = getIntent().getExtras();
        mType = extras.getString("type");
        mId = extras.getString("id");
        mImages = extras.getStringArrayList("images");
        mGoods_name = extras.getString("goods_name");
        mIntegral = extras.getString("integral");
        mDiscount = extras.getString("discount");
        mShop_price = extras.getString("shop_price");
        mMarket_price = extras.getString("market_price");
        mShop_id = extras.getString("shop_id");
        mGoods_brief = extras.getString("goods_brief");
        mSell_num = extras.getString("sell_num");
        mIs_active = extras.getString("is_active");
        goods_title_tv.setText(mGoods_name);
        goods_brief_tv.setText(mGoods_brief);
        goods_price_tv.setText("￥" + mShop_price);
        goods_num_tv.setText(mSell_num + "件");
    }

    @Override
    protected void requestData() {
        mRecyclerView.setFocusable(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mMyAdpter = new MyAdpter(mImages);
        mRecyclerView.setAdapter(mMyAdpter);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.endsWith("index.php/Api/Index/poster")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (null != data) {
                String img = data.get("img");
                setDialog(img);
            }

        }
    }

    private void setDialog(String url) {
        PosterDialog posterDialog=new PosterDialog(PosterAty.this);
        posterDialog.setUrl(url);
        posterDialog.show();
    }

    /**
     * 一键生成海报按钮
     */
    public void toPoster(View view) {
        postPoster(mType, mId, mImages.get(mMyAdpter.getSelectedId()), mGoods_name, mIntegral, mDiscount, mShop_price, mMarket_price, mShop_id, mIs_active,this);
    }

    private void postPoster(String type, String id, String image, String goods_name, String integral, String discount, String shop_price, String market_price, String shop_id, String is_active,BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", type);
        requestParams.addBodyParameter("id", id);
        requestParams.addBodyParameter("goods_img", image);
        requestParams.addBodyParameter("goods_name", goods_name);
        requestParams.addBodyParameter("integral", integral);
        requestParams.addBodyParameter("discount", discount);
        requestParams.addBodyParameter("shop_price", shop_price);
        requestParams.addBodyParameter("market_price", market_price);
        requestParams.addBodyParameter("shop_id", shop_id);
        requestParams.addBodyParameter("is_active", is_active);
        String invite_code = PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "invite_code", "");
        requestParams.addBodyParameter("invite_code", invite_code);
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Index/poster", requestParams, baseView);
    }

    public static class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder> {
        private ArrayList<String> data;
        private Context mContext;
        private int selectedId = 0;

        public MyAdpter(ArrayList<String> data) {
            this.data = data;
        }

        public int getSelectedId() {
            return selectedId;
        }

        @NonNull
        @Override
        public MyAdpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_poster_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            Glide.with(mContext).load(data.get(position)).into(holder.banner_img);
            if (selectedId == position) {
                holder.state_img.setSelected(true);
            } else {
                holder.state_img.setSelected(false);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedId = holder.getLayoutPosition();
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size() > 0 ? data.size() : 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.banner_img)
            public ImageView banner_img;
            @ViewInject(R.id.state_img)
            public ImageView state_img;

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
