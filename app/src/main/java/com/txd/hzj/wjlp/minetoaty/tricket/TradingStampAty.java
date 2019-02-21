package com.txd.hzj.wjlp.minetoaty.tricket;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea.GiveAwayModel;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/11 17:02
 * 功能描述：
 */
public class TradingStampAty extends BaseAty implements View.OnClickListener{

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.total_price_tv)
    public TextView total_price_tv;
    @ViewInject(R.id.change_tv)
    public TextView change_tv;
    @ViewInject(R.id.tips_tv)
    public TextView tips_tv;
    @ViewInject(R.id.tips_tv2)
    public TextView tips_tv2;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    //刷新
    @ViewInject(R.id.super_refresh)
    private SuperSwipeRefreshLayout refreshLayout;
    private int p = 1;
    private MyAdpter mAdpter;

    //刷新头
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    //加载
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private ArrayList<Map<String, String>> list = new ArrayList<>();
    private String mResult="";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trading_stamp;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("我的赠品券");
        refreshLayout.setHeaderView(createHeaderView());// add headerView
        refreshLayout.setFooterView(createFooterView());
        refreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        refreshLayout.setTargetScrollWithLayout(true);
        refreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                requestData();
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        refreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                requestData();
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setClickable(true);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(refreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    @Override
    protected void requestData() {
        GiveAwayModel.postGiftGoodsVouchersGiftVoucherIndex(p, this);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_tv:
                GiveAwayModel.postGiftGoodsVouchersChangeMoney(TradingStampAty.this);
                setClickable(false);
                break;
        }
    }

    private void setClickable(boolean isClick){
        if (isClick){
            change_tv.setOnClickListener(this);
        }else {
            change_tv.setOnClickListener(null);
        }
    }
    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        refreshComplete();
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.endsWith("Api/GiftGoodsVouchers/giftVoucherIndex")) {
            if (data.containsKey("gift")) {
                Map<String, String> giftData = JSONUtils.parseKeyAndValueToMap(data.get("gift"));
                String gift_num = giftData.containsKey("gift_num") ? giftData.get("gift_num") : "0";
                total_price_tv.setText(gift_num);

                String sum_money = giftData.containsKey("sum_money") ? giftData.get("sum_money") : "";
                String exchange_money = giftData.containsKey("exchange_money") ? giftData.get("exchange_money") : "";
                String exchange_voucher = giftData.containsKey("exchange_voucher") ? giftData.get("exchange_voucher") : "";
                // "exchanged": 0    //0:未兑换 1：已兑换
                String exchanged = giftData.containsKey("exchanged") ? giftData.get("exchanged") : "";
                if (Double.parseDouble(exchanged) == 0 && Double.parseDouble(gift_num)!=0 && Double.parseDouble(sum_money)!=0) {
                    change_tv.setTextColor(Color.WHITE);
                    change_tv.setBackgroundResource(R.drawable.icon_gift_zhuanhuan);
                    setClickable(true);
                    if (Double.parseDouble(exchange_money) == 0 && Double.parseDouble(exchange_voucher) != 0) {
                        mResult = "今日福利：可将" + sum_money + "赠品券转换成" +  exchange_voucher + "积分";
                    } else if (Double.parseDouble(exchange_money) != 0 && Double.parseDouble(exchange_voucher) == 0) {
                        mResult = "今日福利：可将" + sum_money + "赠品券转换成" + exchange_money + "余额";
                    } else if (Double.parseDouble(exchange_money) != 0 && Double.parseDouble(exchange_voucher) != 0) {
                        mResult = "今日福利：可将" + sum_money + "赠品券转换成" + exchange_money + "余额+" + exchange_voucher + "积分";
                    }

                    tips_tv.setText(mResult);
                    ViewTreeObserver viewTreeObserver = tips_tv.getViewTreeObserver();
                    viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            Layout layout = tips_tv.getLayout();
                            int lineEnd = layout.getLineEnd(0);
                            String substring = "";
                            String substring2 = "";
                            if (mResult.length() > lineEnd) {
                                substring = mResult.substring(lineEnd, mResult.length());
                                substring = "\u3000\u3000\u3000\u3000\u3000" + substring;
                                substring2 = mResult.substring(0, lineEnd);
                                tips_tv2.setVisibility(View.VISIBLE);
                                tips_tv2.setText(substring);
                                tips_tv.setText(substring2);
                            } else {
                                tips_tv.setText(mResult);
                            }

                            tips_tv.getViewTreeObserver().removeOnPreDrawListener(
                                    this);
                            return false;
                        }
                    });

                } else {
                    change_tv.setTextColor(ContextCompat.getColor(TradingStampAty.this,R.color.bg_color));
                    setClickable(false);
                    change_tv.setBackgroundResource(R.drawable.icon_gift_zhuanhuan_grey);
                }


            }
            if (data.containsKey("giftlist")) {
                final ArrayList<Map<String, String>> giftlist = JSONUtils.parseKeyAndValueToMapList(data.get("giftlist"));
                if (p == 1) {
                    list.clear();
                }
                if (null != giftlist) {
                    list.addAll(giftlist);
                    if (mAdpter == null) {
                        mAdpter = new MyAdpter(list);
                        recyclerView.setAdapter(mAdpter);
                        mAdpter.setOnItemClickListener(new MyAdpter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Bundle bundle = new Bundle();
                                bundle.putString("TradingStampAtyId", list.get(position).get("id"));
                                startActivity(TradingStampDetailsAty.class, bundle);
                            }
                        });
                    } else {
                        mAdpter.notifyDataSetChanged();
                    }

                }
            }

            return;
        }

        if (requestUrl.endsWith("Api/GiftGoodsVouchers/changeMoney")) {
            setClickable(true);
            if ("200".equals(map.get("code"))) {
                showToast(map.get("message"));
                GiveAwayModel.postGiftGoodsVouchersGiftVoucherIndex(p, this);
            }
            return;
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refreshComplete();
        setClickable(true);
    }

    private void refreshComplete() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            refreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            refreshLayout.setLoadMore(false);
            progressBar.setVisibility(View.GONE);
        }

    }


    public static class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder> {
        private ArrayList<Map<String, String>> giftlist;
        private OnItemClickListener mOnItemClickListener;

        public MyAdpter(ArrayList<Map<String, String>> giftlist) {
            this.giftlist = giftlist;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_stamp_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            final Map<String, String> map = giftlist.get(position);
            if (map.containsKey("use_money") && map.containsKey("money")) {
                float price = Float.parseFloat(map.get("money")) - Float.parseFloat(map.get("use_money"));
                holder.price_tv.setText("¥" + price);
            }
            holder.face_tv.setText("赠品券面值¥" + (map.containsKey("money") ? map.get("money") : ""));
            holder.get_way_tv.setText("获取途径：" + (map.containsKey("source_status") ? map.get("source_status") : ""));
            holder.get_time_tv.setText("获取时间：" + (map.containsKey("create_time") ? map.get("create_time") : ""));

            if (map.containsKey("type")) {
                int type = Integer.parseInt(map.get("type"));
                switch (type) {
                    case 0:
                        holder.state_img.setImageResource(R.drawable.fx_icon_chuji);
                        break;
                    case 1:
                        holder.state_img.setImageResource(R.drawable.fx_icon_zhongji);
                        break;
                    case 2:
                        holder.state_img.setImageResource(R.drawable.fx_icon_gaoji);
                        break;
                }

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return giftlist.size() > 0 ? giftlist.size() : 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.price_tv)
            private TextView price_tv;
            @ViewInject(R.id.face_tv)
            private TextView face_tv;
            @ViewInject(R.id.get_way_tv)
            private TextView get_way_tv;
            @ViewInject(R.id.get_time_tv)
            private TextView get_time_tv;
            @ViewInject(R.id.state_img)
            private ImageView state_img;

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }


}
