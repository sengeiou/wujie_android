package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class HomeBannerViewHolder implements MZViewHolder<String> {

    ImageView itemHomeBannerPage_show_imgv;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_page, null);
        itemHomeBannerPage_show_imgv = view.findViewById(R.id.itemHomeBannerPage_show_imgv);
        return view;
    }

    @Override
    public void onBind(Context context, int i, String imageUrl) {
        // 数据绑定
        GlideUtils.loadUrlImg(context, imageUrl, itemHomeBannerPage_show_imgv);
//        itemHomeBannerPage_show_imgv.setImageResource(data);
    }

}
