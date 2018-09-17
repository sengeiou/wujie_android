package com.txd.hzj.wjlp.mellonLine.gridClassify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：17:28
 * 描述：评论图片适配器
 *
 */

public class CommentPicAdapter extends BaseAdapter {

    private Context context;
    private int size;
    private LayoutInflater inflater;
    private List<PicturesBean> pic;
    private PicVh pvh;

    public CommentPicAdapter(Context context, List<PicturesBean> pic) {
        this.context = context;
        this.pic = pic;
        size = ToolKit.dip2px(context, 88);
    }

    @Override
    public int getCount() {
        return pic.size();
    }

    @Override
    public PicturesBean getItem(int i) {
        return pic.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PicturesBean pictures = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_evaluste_pic_gv, viewGroup,
                    false);
            pvh = new PicVh();
            ViewUtils.inject(pvh, view);
            view.setTag(pvh);
        } else {
            pvh = (PicVh) view.getTag();
        }

        Glide.with(context)
                .load(pictures.getPath())
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .override(size, size)
                .into(pvh.comm_pic_iv);
        return view;
    }
    class PicVh {

        /**
         * 评论图片
         */
        @ViewInject(R.id.comm_pic_iv)
        private ImageView comm_pic_iv;
    }
}
