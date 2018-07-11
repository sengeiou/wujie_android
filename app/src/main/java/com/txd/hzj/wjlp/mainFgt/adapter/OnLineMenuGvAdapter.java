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
 * 日期：2017/7/28 0028
 * 时间：13:53
 * 描述：
 */

public class OnLineMenuGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    private ViewHolder vh;

    private int curIndex = 0;
    private int pageSize;

    public OnLineMenuGvAdapter(Context context, List<String> list, int curIndex) {
        this.context = context;
        this.list = list;
        this.curIndex = curIndex;
        pageSize = 10;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() > (curIndex + 1) * pageSize ? pageSize : (list.size() - curIndex * pageSize);
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

        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        int pos = i + curIndex * pageSize;
        vh.all_classify_title_tv.setText(list.get(pos));
        setIcon(pos);
        return view;
    }

    /**
     * 设置图标
     *
     * @param i 小标
     */
    private void setIcon(int i) {
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
