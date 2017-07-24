package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：11:30
 * 描述：收藏，书院
 * ===============Txunda===============
 */

public class WjBooksAdapter extends BaseAdapter {

    private Context context;
    private List<String> books;

    private BVH bvh;

    public WjBooksAdapter(Context context, List<String> books) {
        this.context = context;
        this.books = books;
    }

    private boolean canEdit = false;

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return books.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collect_books_lv, null);
            bvh = new BVH();
            ViewUtils.inject(bvh, view);
            view.setTag(bvh);
        } else {
            bvh = (BVH) view.getTag();
        }
        return view;
    }

    class BVH {

    }
}
