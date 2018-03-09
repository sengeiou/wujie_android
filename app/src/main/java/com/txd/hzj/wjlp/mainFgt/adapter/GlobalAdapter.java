package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.TwoCateListBean;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/13 0013
 * 时间：14:02
 * 描述：全球馆适配器
 * ===============Txunda===============
 */

public class GlobalAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;
    private LayoutInflater inflater;

    private ViewHolder vh;

    private int curIndex = 0;
    private int pageSize;

    private int size = 0;

    public GlobalAdapter(Context context, List<Map<String, String>> list, int curIndex) {
        this.context = context;
        this.list = list;
        this.curIndex = curIndex;
        pageSize = 10;
        size = ToolKit.dip2px(context, 72);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() > (curIndex + 1) * pageSize ? pageSize : (list.size() - curIndex * pageSize);
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
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        int pos = i + curIndex * pageSize;
        Map<String, String> twoCateListBean = getItem(pos);
        if (view == null) {
            view = inflater.inflate(R.layout.item_gridview_classify, null);
            vh = new ViewHolder();
            ViewUtils.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
//       vh.all_classify_title_tv.setText(twoCateListBean.get("country_name"));
        vh.all_classify_title_tv.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(size,size);
        vh.all_classify_logo_iv.setLayoutParams(layoutParams);
        Glide.with(context).load(twoCateListBean.get("house_img"))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.drawable.ic_default)
//                .error(R.drawable.ic_default)
//                .override(size, size)
                .centerCrop()
                .into(vh.all_classify_logo_iv);

        return view;
    }

    class ViewHolder {
        /**
         * 分类logo
         */
        @ViewInject(R.id.all_classify_logo_iv)
        private ImageView all_classify_logo_iv;
        /**
         * 分类名称
         */
        @ViewInject(R.id.all_classify_title_tv)
        private TextView all_classify_title_tv;

    }
}
