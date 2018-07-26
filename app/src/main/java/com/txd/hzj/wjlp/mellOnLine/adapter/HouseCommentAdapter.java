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
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：09:56
 * 描述：
 */

public class HouseCommentAdapter extends BaseAdapter {

    private HCVH hcvh;
    private Context context;
    private LayoutInflater inflater;

    public HouseCommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_house_comment_lv, null);
            hcvh = new HCVH();
            ViewUtils.inject(hcvh, view);
            view.setTag(hcvh);
        } else {
            hcvh = (HCVH) view.getTag();
        }

        hcvh.huose_comment_pic.setVisibility(View.VISIBLE);
        hcvh.huose_comment_pic.setAdapter(new PICAdapter(context));

        return view;
    }

    class HCVH {
        @ViewInject(R.id.huose_comment_pic)
        private GridViewForScrollView huose_comment_pic;
    }
    private class PICAdapter extends BaseAdapter {
        private List<String> pic;
        private Context context;
        private LayoutInflater inflater;
        private PicVh pvh;

        public PICAdapter(Context context) {
//            this.pic = pic;
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
