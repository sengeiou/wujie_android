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
 * 创建者：zhangyunfei
 * 创建时间：2018/8/9 9:00
 * 功能描述：线下店铺
 */
public class OffLineMenuGvAdapter  extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    private OffLineMenuGvAdapter.ViewHolder vh;

    private int curIndex = 0;
    private int pageSize;

    public OffLineMenuGvAdapter(Context context, List<String> list, int curIndex) {
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
            vh = new OffLineMenuGvAdapter.ViewHolder();
            ViewUtils.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (OffLineMenuGvAdapter.ViewHolder) view.getTag();
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
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_selecteddelicacies);
                break;
            case 1:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_merchantmap);
                break;
            case 2:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_foodandbeverage);
                break;
            case 3:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_qualityshopping);
                break;
            case 4:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_entertainment);
                break;
            case 5:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_lifeservice);
                break;
            case 6:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_exquisitebeauty);
                break;
            case 7:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_learningandtraining);
                break;
            case 8:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_motherandchild);
                break;
            case 9:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_exerciseandfitness);
                break;
            case 10:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_getmarried);
                break;
            case 11:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_hotelaccommodation);
                break;
            case 12:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_automobileservice);
                break;
            case 13:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_petlife);
                break;
            case 14:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_wordofmouth);
                break;
            case 15:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_filmperformance);
                break;
            case 16:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_song);
                break;
            case 17:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_peripheralamusement);
                break;
            case 18:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_traveloverseas);
                break;
            case 19:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_medicalhealth);
                break;
            case 20:
                vh.all_classify_logo_iv.setImageResource(R.drawable.icon_traffictravel);
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
