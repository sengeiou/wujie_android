package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：17:17
 * 描述：线上商城页面的GridView和ListView的适配器
 * ===============Txunda===============
 */

public class AllGvLvAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private ViewHolder vh;
    /**
     * 标识
     * 0.限量购
     * 1.票券区
     * 2.无界预购
     * 3.进口馆
     * 4.竞拍汇
     * 5.一元夺宝
     * 6.汽车购
     * 7.房产购
     * 8.拼团购
     */
    private int type = 0;

    public AllGvLvAdapter(Context context, List<String> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
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
            switch (type) {
                case 0:// 限量购
                case 2:// 无界预购
                    view = inflater.inflate(R.layout.item_purchase_gv, null);
                    break;
                case 1:// 票券区
                    view = inflater.inflate(R.layout.item_ticket_gv, null);
                    break;
                case 3:// 进口馆
                    view = inflater.inflate(R.layout.item_import_gv, null);
                    break;
                case 4:// 竞拍汇
                    view = inflater.inflate(R.layout.item_auction_gv, null);
                    break;
                case 5:// 一元夺宝
                    view = inflater.inflate(R.layout.item_good_luck_gv, null);
                    break;
                case 6:// 汽车购
                    view = inflater.inflate(R.layout.item_car_gv, null);
                    break;
                case 7:// 房产购
                    view = inflater.inflate(R.layout.item_house_gv, null);
                    break;
                case 8:// 拼好货
                    view = inflater.inflate(R.layout.item_group_shopping_lv, null);
                    break;
            }
            vh = new ViewHolder();
            ViewUtils.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        if (0 == type || 2 == type || 4 == type) {
            vh.home_count_down_view.setTag("text");
            vh.home_count_down_view.start(3600000);
        }
        if (0 == type || 2 == type || 5 == type) {
            // 设置进度
            vh.cpb_progresbar2.setMaxProgress(100);
            vh.cpb_progresbar2.setCurProgress(50);
        }

        if(type == 2){
            vh.goods_num_already_tv.setText("已预购100件");
            vh.sold_num_tv.setVisibility(View.GONE);
        }

        if (type < 5) {
            vh.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        return view;
    }

    class ViewHolder {
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;
        /**
         * 倒计时(type=0,2)
         */
        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;
        /**
         * 进度条(type=0,2,5)
         */
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;

        @ViewInject(R.id.sold_num_tv)
        private TextView sold_num_tv;
        /**
         * 已抢购
         * 已预购
         */
        @ViewInject(R.id.goods_num_already_tv)
        private TextView goods_num_already_tv;

    }
}
