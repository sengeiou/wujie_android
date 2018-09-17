package com.txd.hzj.wjlp.new_wjyp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.mine.TwoNineEightZeroBean;
import com.txd.hzj.wjlp.http.Goods;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VipDetailsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    //    private String data;
    //    private Map<String, String> map;
    private List<Map<String, String>> list_pic = new ArrayList<>();
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv1)
    TextView tv1;
    @ViewInject(R.id.pay)
    LinearLayout pay;
    @ViewInject(R.id.tv2)
    TextView tv2;

    //2980专区列表
    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private int allHeight;
    private String sale_status;
    private String rank_name;
    private String money;
    private String prescription;
    private String big_gift;
    private String score_status;
    private String abs_url;
    private String member_coding;
    private String pay_money;
    //"是否存在延时会员卡功能" 1 存在  0不存在
    private int reward_status = 0;
    //个人中心请求状态  1代表成功 0代表失败
    private int userCenterCode = 1;
    private int p = 1;

    private List<TwoNineEightZeroBean.DataBean.ListBean> mVipList;
    private VipAdapter mVipAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @OnClick({R.id.pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay:
                if (sale_status.equals("0")) {
                    if (reward_status == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("sale_status", sale_status);
                        bundle.putString("rank_name", rank_name);
                        bundle.putString("money", money);
                        bundle.putString("prescription", prescription);
                        bundle.putString("big_gift", big_gift);
                        bundle.putString("score_status", score_status);
                        bundle.putString("abs_url", abs_url);
                        bundle.putString("member_coding", member_coding);
                        bundle.putString("pay_money", money);
                        //                    bundle.putString("data", data);
                        bundle.putString("order_id", "");
                        startActivity(VipPayAty.class, bundle);
                    } else if (reward_status == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("userCenterCode", userCenterCode);
                        startActivity(FreeRenew.class, bundle);
                    }
                    finish();
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
        mVipList = new ArrayList<>();
        sale_status = getIntent().getStringExtra("sale_status");
        rank_name = getIntent().getStringExtra("rank_name");
        money = getIntent().getStringExtra("money");
        prescription = getIntent().getStringExtra("prescription");
        big_gift = getIntent().getStringExtra("big_gift");
        score_status = getIntent().getStringExtra("score_status");
        abs_url = getIntent().getStringExtra("abs_url");
        member_coding = getIntent().getStringExtra("member_coding");
        reward_status = getIntent().getIntExtra("rewardStatus", 0);
        userCenterCode = getIntent().getIntExtra("userCenterCode", 1);

        //        data = getIntent().getStringExtra("data");
        //        map = JSONUtils.parseKeyAndValueToMap(data); // TODO 报空指针

        list_pic = JSONUtils.parseKeyAndValueToMapList(abs_url);
        titlt_conter_tv.setText(rank_name);
        String Y = "会员年费¥" + money + "/" + (prescription.equals("0") ? "永久" : "年");//年or永久
        tv_price.setText(Y);
        //        big_gift

        if (sale_status.equals("0") && big_gift.equals("1") && score_status.equals("0")) {
            tv1.setText("已拥有更高级别会员卡");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("1") && score_status.equals("1")) {
            if (reward_status == 0) {
                tv1.setText("续费");
            } else if (reward_status == 1) {
                tv1.setText("免费续约");
                tv_price.setText("点击领取延期一年优享会员");
            }
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("1") && score_status.equals("2")) {
            tv1.setText("立即开通");
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("1") && score_status.equals("4")) {
            tv1.setText("永久有效");
            pay.setEnabled(false);
            tv1.setTextColor(Color.BLACK);
            pay.setBackgroundColor(Color.GRAY);
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("1")) {
            pay.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("2") && score_status.equals("0")) {
            tv2.setVisibility(View.INVISIBLE);
            tv1.setText("已拥有更高级别会员卡");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
        } else if (sale_status.equals("0") && big_gift.equals("2") && score_status.equals("1")) {
            if (reward_status == 0) {
                tv1.setText("续费");
            } else if (reward_status == 1) {
                tv1.setText("免费续约");
                tv_price.setText("点击领取延期一年优享会员");
            }
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("2") && score_status.equals("2")) {
            tv1.setText("立即开通");
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("0") && big_gift.equals("2") && score_status.equals("4")) {
            tv1.setText("永久有效");
            pay.setEnabled(false);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        } else if (sale_status.equals("1") && big_gift.equals("2")) {
            pay.setVisibility(View.INVISIBLE);
            pay.setBackgroundColor(Color.GRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setVisibility(View.INVISIBLE);
        }

        /**
         * 设置轮播图高度
         */
        //轮播图高度
        allHeight = Settings.displayWidth * 2 / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);


        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(final int position, ImageView imageView) {
                Glide.with(VipDetailsAty.this).load(list_pic.get(position).get("url"))
                        .error(R.mipmap.icon_200)
                        .placeholder(R.drawable.ic_default)
                        .dontAnimate()
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
        for (Map<String, String> map : list_pic) {
            L.e("wang", "===>>>>>>>map:" + map.toString());
        }
    }

    @Override
    protected void requestData() {
        Goods.twoNineEightZero(String.valueOf(p), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("twoNineEightZero")) {
            TwoNineEightZeroBean twoNineEightZeroBean = JSONObject.parseObject(jsonStr, TwoNineEightZeroBean.class);
            List<TwoNineEightZeroBean.DataBean.ListBean> list = twoNineEightZeroBean.getData().getList();
            mVipList.addAll(list);
            tv2.setVisibility(View.VISIBLE);
            mVipAdapter = new VipAdapter(this, mVipList);
            mVipAdapter.setOnItemClickListener(new VipAdapter.OnItemClickListener() {
                @Override
                public void click(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", mVipList.get(position).getGoods_id());
                    bundle.putInt("from", 13);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                }
            });
            mRecyclerView.setAdapter(mVipAdapter);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }


    private static class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {
        private Context mContext;
        private List<TwoNineEightZeroBean.DataBean.ListBean> mList;

        public VipAdapter(Context context, List<TwoNineEightZeroBean.DataBean.ListBean> list) {
            mContext = context;
            mList = list;
        }

        public interface OnItemClickListener {
            void click(int position);
        }

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.two_nine_eight_zero_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            TwoNineEightZeroBean.DataBean.ListBean listBean = mList.get(position);
            Glide.with(mContext).load(listBean.getGoods_img()).asBitmap().into(holder.goods_img);
            holder.goods_title_tv.setText(listBean.getGoods_name());
            holder.goods_price_tv.setText("￥" + listBean.getShop_price());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.click(holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size() > 0 ? mList.size() : 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView goods_img;
            TextView goods_title_tv;
            TextView goods_give_tv;
            TextView goods_price_tv;

            public ViewHolder(View itemView) {
                super(itemView);
                goods_img = itemView.findViewById(R.id.goods_img);
                goods_title_tv = itemView.findViewById(R.id.goods_name_tv);
                goods_give_tv = itemView.findViewById(R.id.give_tv);
                goods_price_tv = itemView.findViewById(R.id.goods_price_tv);
            }
        }
    }
}