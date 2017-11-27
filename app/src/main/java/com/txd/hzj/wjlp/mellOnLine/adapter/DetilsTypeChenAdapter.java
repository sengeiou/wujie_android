package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HouseTypeDetailsHzjAty;
import com.txd.hzj.wjlp.txunda_lh.RoundTransformation;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购Adapter(10-1汽车购)
 * ===============Txunda===============
 */

public class DetilsTypeChenAdapter extends RecyclerView.Adapter<DetilsTypeChenAdapter.ViewHolder> {

    private Context context;

    private List<Map<String, String>> list;

    private int size = 0;
    private int size1 = 0;
    private int size2 = 0;

    public DetilsTypeChenAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        size = ToolKit.dip2px(context, 180);
        size1 = ToolKit.dip2px(context, 32);
        size2 = ToolKit.dip2px(context, 24);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_type_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Map<String, String> map = list.get(position);

        int total;
        try {
            total = Integer.parseInt(map.get("total"));
        } catch (NumberFormatException e) {
            total = 100;
        }

        int sell_num;
        try {
            sell_num = Integer.parseInt(map.get("sell_num"));
        } catch (NumberFormatException e) {
            sell_num = 0;
        }

        holder.house_type_cpb.setMaxProgress(total);
        holder.house_type_cpb.setCurProgress(sell_num);

        Glide.with(context).load(map.get("house_style_img"))
//                .override(size, size)
//                .placeholder(R.drawable.ic_default)
//                .error(R.drawable.ic_default)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.house_style_pic_iv);

        Glide.with(context).load(map.get("country_logo"))
//                .override(size1, size2)
//                .placeholder(R.drawable.ic_default)
//                .error(R.drawable.ic_default)
                .bitmapTransform(new RoundTransformation(context,3))
                .dontAnimate()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.logo_for_country_iv);

        holder.style_name_tv.setText(map.get("style_name"));

        holder.style_developer_tv.setText(map.get("developer"));

        holder.style_pre_money_tv.setText("￥" + map.get("pre_money"));

        holder.textView2.setText("可    抵：￥" + map.get("true_pre_money") + "\n房全款：￥" + map.get("all_price"));

        holder.one_price_tv.setText(map.get("one_price") + "元/平");

        holder.use_coupon_tv.setText("可使用" + map.get("ticket_discount") + "%代金券");

        /**
         * 点击事件
         *
         * 跳转到详情页
         */
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HouseTypeDetailsHzjAty.class);
                intent.putExtra("style_id", map.get("style_id"));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        @ViewInject(R.id.house_type_cpb)
        private CustomProgressBar house_type_cpb;
        /**
         * 户型图
         */
        @ViewInject(R.id.house_style_pic_iv)
        private ImageView house_style_pic_iv;
        /**
         * 户型名称
         */
        @ViewInject(R.id.style_name_tv)
        private TextView style_name_tv;
        /**
         * 户型开发商
         */
        @ViewInject(R.id.style_developer_tv)
        private TextView style_developer_tv;
        /**
         * 户型代金券
         */
        @ViewInject(R.id.style_pre_money_tv)
        private TextView style_pre_money_tv;
        /**
         * 户型其他信息
         */
        @ViewInject(R.id.textView2)
        private TextView textView2;
        /**
         * 户型积分
         */
        @ViewInject(R.id.style_integral_tv)
        private TextView style_integral_tv;
        /**
         * 国旗
         */
        @ViewInject(R.id.logo_for_country_iv)
        private ImageView logo_for_country_iv;
        /**
         * 优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;
        /**
         * 优惠券
         */
        @ViewInject(R.id.one_price_tv)
        private TextView one_price_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
