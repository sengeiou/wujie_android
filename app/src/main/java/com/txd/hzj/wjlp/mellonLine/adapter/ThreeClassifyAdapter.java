package com.txd.hzj.wjlp.mellonLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CateIndex;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：09:54
 * 描述：1-2分类，三级分类列表适配器
 */

public class ThreeClassifyAdapter extends BaseAdapter {

    private Context context;
    private List<CateIndex.Data.TwoCateBean.ThreeCateBean> data;
    private LayoutInflater inflater;
    private ThreeViewHolder tvh;
    private int size = 0;
    CateIndex.Data.TwoCateBean.ThreeCateBean threeCateBean;

    public ThreeClassifyAdapter(Context context, List<CateIndex.Data.TwoCateBean.ThreeCateBean> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        size = ToolKit.dip2px(context, 80);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CateIndex.Data.TwoCateBean.ThreeCateBean getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        threeCateBean = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_right_three_level_gv, viewGroup, false);
            tvh = new ThreeViewHolder();
            ViewUtils.inject(tvh, view);
            view.setTag(tvh);
        } else {
            tvh = (ThreeViewHolder) view.getTag();
        }
        tvh.third_cate_name_tv.setText(threeCateBean.getName());
        Glide.with(context).load(threeCateBean.getCate_img())
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(size, size)
                .into(tvh.third_cate_iv);
        return view;
    }

    private class ThreeViewHolder {
        /**
         * 图片
         */
        @ViewInject(R.id.third_cate_iv)
        private ImageView third_cate_iv;
        /**
         * 名称
         */
        @ViewInject(R.id.third_cate_name_tv)
        private TextView third_cate_name_tv;

    }
}
