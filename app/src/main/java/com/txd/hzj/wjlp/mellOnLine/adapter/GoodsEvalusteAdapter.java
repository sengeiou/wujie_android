package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：17:56
 * 描述：
 * ===============Txunda===============
 */

public class GoodsEvalusteAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private List<String> list2;
    private LayoutInflater inflater;
    private GEVH gevh;

    public GoodsEvalusteAdapter(Context context, List<String> list, List<String> list2) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.aty_estimate_layout, viewGroup, false);
            gevh = new GEVH();
            ViewUtils.inject(gevh, view);
            view.setTag(gevh);
        } else {
            gevh = (GEVH) view.getTag();
        }
        gevh.estimate_pic.setVisibility(View.VISIBLE);
        gevh.estimate_pic.setAdapter(new PICAdapter(list2, context));
        return view;
    }

    class GEVH {
        @ViewInject(R.id.estimate_pic)
        private GridViewForScrollView estimate_pic;
    }

    private class PICAdapter extends BaseAdapter {
        private List<String> pic;
        private Context context;
        private LayoutInflater inflater;
        private PicVh pvh;

        public PICAdapter(List<String> pic, Context context) {
            this.pic = pic;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return pic.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.item_evaluste_pic_gv, viewGroup, false);
                pvh = new PicVh();
                ViewUtils.inject(pvh, view);
                view.setTag(pvh);
            } else {
                pvh = (PicVh) view.getTag();
            }
            return view;
        }

        class PicVh {

        }
    }

}
