package com.txd.hzj.wjlp.minetoaty.tricket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea.GiveAwayModel;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/25 14:11
 * 功能描述：明细详情页
 */
public  class TradingStampDetailsAty extends BaseAty{

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private String mId;
    private int p=1;
    private MyAdpter mAdpter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trading_stamp_details;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("明细");
        mId = getIntent().getExtras().getString("TradingStampAtyId");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void requestData() {
        GiveAwayModel.postGiftGoodsVouchersGetGiftVouchersInfo(mId,p,this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("Api/GiftGoodsVouchers/getGiftVouchersInfo")){
            ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (null!=data){
                mAdpter=new MyAdpter(data);
                recyclerView.setAdapter(mAdpter);
            }
        }
    }


    public static class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewHolder>{
        private ArrayList<Map<String, String>> data;
        private Context mContext;
        public MyAdpter(ArrayList<Map<String, String>> data) {
            this.data = data;
        }


        @NonNull
        @Override
        public MyAdpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext=parent.getContext();
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_stamp_details_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            final Map<String, String> map = data.get(position);
            holder.title_tv.setText((map.containsKey("reason")?map.get("reason"):""));
            if (map.containsKey("add_sub") && "0".equals(map.get("add_sub"))){
                holder.price_tv.setText("+"+(map.containsKey("money")?map.get("money"):""));
            }else if (map.containsKey("add_sub") && "1".equals(map.get("add_sub"))){
                holder.price_tv.setText("-"+(map.containsKey("money")?map.get("money"):""));
            }
            holder.time_tv.setText((map.containsKey("create_time")?map.get("create_time"):""));
            if (map.containsKey("img")){
                Glide.with(mContext).load(map.get("img")).into(holder.icon_img);
            }
        }

        @Override
        public int getItemCount() {
            return data.size()>0?data.size():0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            @ViewInject(R.id.icon_img)
            public ImageView icon_img;
            @ViewInject(R.id.title_tv)
            public TextView title_tv;
            @ViewInject(R.id.price_tv)
            public TextView price_tv;
            @ViewInject(R.id.time_tv)
            public TextView time_tv;
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
