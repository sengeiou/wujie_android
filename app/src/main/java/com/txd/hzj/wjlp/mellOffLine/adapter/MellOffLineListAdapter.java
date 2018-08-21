package com.txd.hzj.wjlp.mellOffLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.bean.offline.OffLineListBean;
import com.txd.hzj.wjlp.mellOffLine.ShopMallDetailsAty;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/31 13:47
 * 功能描述：线下商城搜索的适配器
 */
public class MellOffLineListAdapter extends BaseAdapter {

    private final int size;
    private Context mContext;
    private List<OffLineListBean.DataBean> mList;

    public MellOffLineListAdapter(Context context, List<OffLineListBean.DataBean> list) {
        mContext = context;
        mList = list;
        size = ToolKit.dip2px(context, 100);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.off_line_list_item,parent,false);
            ViewUtils.inject(viewHolder, view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        final OffLineListBean.DataBean dataBean = mList.get(position);
        Glide.with(mContext).load(dataBean.getLogo())
                .override(size, size).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(viewHolder.mell_logo_iv);

        viewHolder.mell_name_tv.setText(dataBean.getMerchant_name());
        viewHolder.textView7.setText(dataBean.getMerchant_desc());
        String star = dataBean.getScore();
        viewHolder.shop_evaluate_star_level.setRating(android.text.TextUtils.isEmpty(star)?4:Float.valueOf(star));
        viewHolder.achievement_tv.setText("|月售"+dataBean.getMonths_order()+"单");

        //点击了进店逛逛
        viewHolder.into_mell_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShopMallDetailsAty.class);
                Bundle options = new Bundle();

                OffLineDataBean offLineDataBea=new OffLineDataBean();
                offLineDataBea.setS_id(dataBean.getMerchant_id());
                offLineDataBea.setMerchant_name(dataBean.getMerchant_name());
                offLineDataBea.setMerchant_desc(dataBean.getMerchant_desc());
                offLineDataBea.setLogo(dataBean.getLogo());
                offLineDataBea.setScore(dataBean.getScore());
                offLineDataBea.setMonths_order(dataBean.getMonths_order());
                offLineDataBea.setDistance("");
                Map<String, String> locInfo = DemoApplication.getInstance().getLocInfo();
                offLineDataBea.setLat(locInfo.containsKey("lat")?locInfo.get("lat"):"");
                offLineDataBea.setLng(locInfo.containsKey("lon")?locInfo.get("lon"):"");
                offLineDataBea.setProportion("");
                offLineDataBea.setShow(false);
                offLineDataBea.setTicket(null);
                offLineDataBea.setUser_id("");
                options.putSerializable("mellInfo", offLineDataBea);
                intent.putExtras(options);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder{
        /**
         * 店铺头像
         */
        @ViewInject(R.id.mell_logo_iv)
        private ImageView mell_logo_iv;

        /**
         * 名称
         */
        @ViewInject(R.id.mell_name_tv)
        private TextView mell_name_tv;

        /**
         * 店铺简介
         */
        @ViewInject(R.id.textView7)
        private TextView textView7;

        /**
         * 评分
         */
        @ViewInject(R.id.shop_evaluate_star_level)
        private RatingBar shop_evaluate_star_level;

        /**
         * 业绩
         */
        @ViewInject(R.id.achievement_tv)
        private TextView achievement_tv;

        /**
         * 进店逛逛
         */
        @ViewInject(R.id.into_mell_tv)
        private TextView into_mell_tv;
    }
}
