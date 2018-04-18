package com.txd.hzj.wjlp.popAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：19:56
 * 描述：
 * ===============Txunda===============
 */

public class RedPackageAdapter extends BaseAdapter {

    private Context context;

    /**
     * 数据类型
     * 1.线上店铺，商家首页
     * 0.上市孵化
     */
    private int type = 0;

    /**
     * 圆角半径
     */
    private int size = 0;
    /**
     * 图片高度(根据type设置)
     */
    private int height = 0;
    /**
     * 图片宽度(根据type设置)
     */
    private int wight = 0;

    private List<Map<String, String>> list;
    private LinearLayout.LayoutParams layoutParams;
    private RPVH rpvh;

    public RedPackageAdapter(Context context, int type, List<Map<String, String>> list) {
        this.context = context;
        this.type = type;
        size = ToolKit.dip2px(context, 8);
        if (1 == type) {
//            wight = Settings.displayWidth - ToolKit.dip2px(context, 16);
//            height = (wight) / 4;
            wight = Settings.displayWidth;
            height = Settings.displayWidth * 300 / 842;

        } else {
            wight = Settings.displayWidth;
            height = (wight) * 2 / 3;
        }
        layoutParams = new LinearLayout.LayoutParams(wight, height);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Map<String, String> map = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_red_package_lv, viewGroup, false);
            rpvh = new RPVH();
            ViewUtils.inject(rpvh, view);
            view.setTag(rpvh);
        } else {
            rpvh = (RPVH) view.getTag();
        }
        String picUrl;
        rpvh.image_for_mell.setVisibility(View.GONE);
        if (0 == type) {
            rpvh.image_for_mell1.setShapeRadius(0);
            picUrl = map.get("face_img");
        } else {
            picUrl = map.get("bonus_face");
            rpvh.image_for_mell1.setShapeRadius(size);
        }
        rpvh.image_for_mell1.setLayoutParams(layoutParams);
        Glide.with(context).load(picUrl)
                .override(wight, height)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(rpvh.image_for_mell1);

        return view;
    }

    private class RPVH {

        @ViewInject(R.id.image_for_mell)
        private ImageView image_for_mell;
        @ViewInject(R.id.image_for_mell1)
        private ShapedImageView image_for_mell1;

    }
}
