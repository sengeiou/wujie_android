package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：14:40
 * 描述：
 */

public class GVClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    /**
     * 0，首页数据
     */
    private int type = 0;
    private LayoutInflater inflater;

    private ViewHolder vh;

    public GVClassifyAdapter(Context context, List<String> list, int type) {
        this.context = context;
        this.type = type;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (0 == type) {
            return list.size();
        } else {
            return 10;
        }
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
            view = inflater.inflate(R.layout.item_gridview_classify, null);
            vh = new ViewHolder();
            ViewUtils.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        if (0 == type) {
            vh.all_classify_title_tv.setText(list.get(i));
            switch (i) {
                case 0:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_xlg);
                    break;
                case 1:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_pqq);
                    break;
                case 2:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_ptq_l);
                    break;
                case 3:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_ztj);
                    break;
                case 4:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_wjyg);
                    break;
                case 5:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_jkg);
                    break;
                case 6:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_jph);
                    break;
                case 7:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_qcg);
                    break;
                case 8:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_fcg);
                    break;
                case 9:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_yydb);
                    break;
            }
        } else {
            vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_xlg);
        }

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
