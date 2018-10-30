package com.txd.hzj.wjlp.minetoaty.tricket;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
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
public class TradingStampAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.total_price_tv)
    public TextView total_price_tv;
    @ViewInject(R.id.tips_tv)
    public TextView tips_tv;
    @ViewInject(R.id.tips_tv2)
    public TextView tips_tv2;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private int p = 1;
    private MyAdpter mAdpter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trading_stamp;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("我的赠品券");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void requestData() {
        GiveAwayModel.postGiftGoodsVouchersGiftVoucherIndex(p, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.endsWith("Api/GiftGoodsVouchers/giftVoucherIndex")) {
            if (data.containsKey("gift")) {
                Map<String, String> giftData = JSONUtils.parseKeyAndValueToMap(data.get("gift"));
                String gift_num = giftData.containsKey("gift_num") ? giftData.get("gift_num") : "";
                String sum_money = giftData.containsKey("sum_money") ? giftData.get("sum_money") : "";
                String exchange_money = giftData.containsKey("exchange_money") ? giftData.get("exchange_money") : "";
                String exchange_voucher = giftData.containsKey("exchange_voucher") ? giftData.get("exchange_voucher") : "";
                total_price_tv.setText(gift_num);
                final String result="今日福利：可将" + sum_money + "赠品券转换成" + exchange_money + "余额+" + exchange_voucher + "积分";
                tips_tv.setText(result);
                ViewTreeObserver viewTreeObserver = tips_tv.getViewTreeObserver();
                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Layout layout = tips_tv.getLayout();
                        int lineEnd = layout.getLineEnd(0);
                        String substring="";
                        String substring2="";
                        if (result.length()>lineEnd){
                             substring= result.substring(lineEnd, result.length());
                            substring="\u3000\u3000\u3000\u3000\u3000"+substring;
                            substring2=result.substring(0,lineEnd);
                            tips_tv2.setVisibility(View.VISIBLE);
                            tips_tv2.setText(substring);
                            tips_tv.setText(substring2);
                        }else {
                            tips_tv.setText(result);
                        }

                        tips_tv.getViewTreeObserver().removeOnPreDrawListener(
                                this);
                        return false;
                    }
                });
            }
            if (data.containsKey("giftlist")) {
                final ArrayList<Map<String, String>> giftlist = JSONUtils.parseKeyAndValueToMapList(data.get("giftlist"));
                if (null!=giftlist) {
                    mAdpter = new MyAdpter(giftlist);
                    recyclerView.setAdapter(mAdpter);
                    mAdpter.setOnItemClickListener(new MyAdpter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle=new Bundle();
                            bundle.putString("TradingStampAtyId",giftlist.get(position).get("id"));
                            startActivity(TradingStampDetailsAty.class,bundle);
                        }
                    });
                }
            }

            return;
        }

        if (requestUrl.endsWith("Api/GiftGoodsVouchers/changeMoney")) {
            if ("200".equals(map.get("code"))){
                showToast(map.get("message"));
                GiveAwayModel.postGiftGoodsVouchersGiftVoucherIndex(p, this);
            }
            return;
        }
    }

    /**
     * 转换按钮
     */
    public void toChange(View view) {
        GiveAwayModel.postGiftGoodsVouchersChangeMoney(this);
    }


   public static class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder>{
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
           View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_stamp_item,parent,false);
           MyViewHolder holder=new MyViewHolder(view);
           ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            final Map<String, String> map = giftlist.get(position);
            if (map.containsKey("use_money") && map.containsKey("money")){
               float price=Float.parseFloat(map.get("money"))-Float.parseFloat(map.get("use_money"));
                holder.price_tv.setText("￥"+price);
            }
            holder.face_tv.setText("赠品券面值￥"+(map.containsKey("money")?map.get("money"):""));
            holder.get_way_tv.setText("获取途径："+(map.containsKey("source_status")?map.get("source_status"):""));
            holder.get_time_tv.setText("获取时间："+(map.containsKey("create_time")?map.get("create_time"):""));

            if (map.containsKey("type")){
                int type = Integer.parseInt(map.get("type"));
                switch (type){
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
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return giftlist.size()>0?giftlist.size():0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
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

        public interface OnItemClickListener{
           void onItemClick(int position);
        }
    }




}
