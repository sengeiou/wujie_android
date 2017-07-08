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
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：14:40
 * 描述：
 * ===============Txunda===============
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
        return 10;
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
            switch (i) {
                case 0:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_xlg);
                    vh.all_classify_title_tv.setText("限量购");
                    break;
                case 1:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_pqq);
                    vh.all_classify_title_tv.setText("票券区");
                    break;
                case 2:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_ptq_l);
                    vh.all_classify_title_tv.setText("拼团购");
                    break;
                case 3:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_ztj);
                    vh.all_classify_title_tv.setText("主题街");
                    break;
                case 4:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_wjyg);
                    vh.all_classify_title_tv.setText("无界预购");
                    break;
                case 5:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_jkg);
                    vh.all_classify_title_tv.setText("进口馆");
                    break;
                case 6:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_jph);
                    vh.all_classify_title_tv.setText("竞拍汇");
                    break;
                case 7:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_qcg);
                    vh.all_classify_title_tv.setText("汽车购");
                    break;
                case 8:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_fcg);
                    vh.all_classify_title_tv.setText("房产购");
                    break;
                case 9:
                    vh.all_classify_logo_iv.setImageResource(R.drawable.icon_temp_yydb);
                    vh.all_classify_title_tv.setText("一元夺宝");
                    break;
            }

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
