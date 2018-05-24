package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.minetoAty.balance.RechargeOffLineAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderDetailsAty;
import com.txd.hzj.wjlp.minetoAty.order.VipCardDetailsAty;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUserCouponAty;

import java.util.List;

public class StickyExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    private Context context;
    private List<TricketDetailks> stickyExampleModels;

    /**
     * 1.购物券使用明细
     * 2.积分明细
     * 3.余额明细
     * 4.线下充值
     * 5.成长值明细
     * 7.蓝色代金券赠送明细
     * 8.蓝色代金券使用明细
     */
    private int type = 1;

    public StickyExampleAdapter(Context context, List<TricketDetailks> recyclerViewModels, int type) {
        this.context = context;
        this.stickyExampleModels = recyclerViewModels;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tricket_rv, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        ViewUtils.inject(recyclerViewHolder, view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewHolder) {
            // 获取ViewHolder
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            // 获取明细实体类
            final TricketDetailks stickyExampleModel = stickyExampleModels.get(position);
            L.e("wang", "=======>>>>>>>stickyExampleModel:" + stickyExampleModel);
            // 费吸顶文本标题
            recyclerViewHolder.tvName.setText(stickyExampleModel.getReason());
            // 交易记录时间
            recyclerViewHolder.tvGender.setText(stickyExampleModel.gender);
            if (position == 0) {// 第一条
                // 显示吸顶文本
                // 显示吸顶文本
                recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                // 设置吸顶文本内容
                recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                // 给itemView设置Tag
                recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                if (!TextUtils.equals(stickyExampleModel.sticky, stickyExampleModels.get(position - 1).sticky)) {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                    recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                    recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.GONE);
                    recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
            // 描述
            recyclerViewHolder.itemView.setContentDescription(stickyExampleModel.sticky);
            // 查看详情
            if (type == 4 || type == 3 || type == 1||type == 7){ // 4:线下充值明细，3:余额明细，1:代金券使用明细
                recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE);
            }
            // 查看详情点击事件
            recyclerViewHolder.check_details_for_balance_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type == 4) {
                        PreferencesUtils.putString(context, "idid", stickyExampleModel.log_id);
                        Intent intent = new Intent(context, RechargeOffLineAty.class);
                        intent.putExtra("act_id", stickyExampleModel.log_id);
                        context.startActivity(intent);
                    } else if (type == 3){
                        L.e("========stickyExampleModel.act_type==type == 3=========" + stickyExampleModel.act_type);
                        if (stickyExampleModel.act_type.equals("10")) { // 会员卡
                            Intent intent = new Intent(context, VipCardDetailsAty.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("order_id", stickyExampleModel.log_id);
                            bundle.putString("member_coding", stickyExampleModel.getMemberCoding());
                            L.e("10101010order_id:" + stickyExampleModel.log_id + "\tmember_coding:" + stickyExampleModel.getMemberCoding());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else if (stickyExampleModel.act_type.equals("3")){ // 订单详情界面
                            Intent intent = new Intent(context, OrderDetailsAty.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", stickyExampleModel.log_id);
                            bundle.putString("type", "0");
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    } else if (type == 1){
                        L.e("========stickyExampleModel.act_type==type == 1=========" + stickyExampleModel.act_type);
                        if (stickyExampleModel.act_type.equals("1")) { // 会员卡
                            Intent intent = new Intent(context, VipCardDetailsAty.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("order_id", stickyExampleModel.log_id);
                            bundle.putString("member_coding", stickyExampleModel.getMemberCoding());
                            L.e("1111order_id:" + stickyExampleModel.log_id + "\tmember_coding:" + stickyExampleModel.getMemberCoding());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else if (stickyExampleModel.act_type.equals("2")){ // 订单详情界面
                            Intent intent = new Intent(context, OrderDetailsAty.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", stickyExampleModel.getOrderId());
                            bundle.putString("type", "0");
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }else if (type==7){
                        Intent intent = new Intent(context, ParticularsUserCouponAty.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", stickyExampleModel.log_id);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
            // 查看线下充值详情----隐藏
//            recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.VISIBLE);
            recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.profession);

            if (1 == type) { // 购物券明细
                // 获得，消费积分(转出，消费) add_sub: 0加 1减
                if (stickyExampleModel.getAdd_sub().equals("0")) {
                    recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.profession);
                    recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE);
                } else {
                    recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.profession);
                    recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.VISIBLE);
                }

                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv); // ==================================================================================
            } else if (2 == type) { // 积分明细
                // 获得，消费积分 add_sub：0减 1加
                recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE); // 积分明细不显示查看详情
                if (stickyExampleModel.getAct_type().equals("1") || stickyExampleModel.getAct_type().equals("3")) {
                    recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.profession);
                } else {
                    recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.profession);
                }
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
            } else if (3 == type) { // 余额明细
                // 获得，消费积分(转出，消费) add_sub：1加 2减
                if (stickyExampleModel.getAdd_sub().equals("1")) {
                    recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.profession);
                    recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE);
                } else {
                    recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.profession);
                }
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
            } else if (4 == type) { // 线下充值
                recyclerViewHolder.tvName.setText(stickyExampleModel.getName());
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
//                recyclerViewHolder.t_details_logo_tv.setImageResource(R.drawable.icon_bal_log_1);
            } else if (5 == type) { // 成长值明细
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
            }else if(7 == type){ // 蓝色代金券赠送明细
                recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.profession);
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
            }else if(8 == type){ // 蓝色代金券使用明细
                recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.profession);
                Glide.with(context).load(stickyExampleModel.imgStr).into(recyclerViewHolder.t_details_logo_tv);
            }
        }
    }

    @Override
    public int getItemCount() {
        return stickyExampleModels == null ? 0 : stickyExampleModels.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.tv_sticky_header_view)
        TextView tvStickyHeader;
        @ViewInject(R.id.t_details_title_tv)
        TextView tvName;
        @ViewInject(R.id.t_details_time_tv)
        TextView tvGender;
        @ViewInject(R.id.check_details_for_balance_tv)
        TextView check_details_for_balance_tv;
        @ViewInject(R.id.t_details_logo_tv)
        ImageView t_details_logo_tv;
        @ViewInject(R.id.t_details_price_tv)
        TextView t_details_price_tv;
        RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
