package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：下午 4:27
 * 描述：分享次数
 * ===============Txunda===============
 */
public class ShareTimesAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.share_times_lv)
    private ListView share_times_lv;

    private ShapeAdapter shapeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分享次数");
        share_times_lv.setAdapter(shapeAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_times;
    }

    @Override
    protected void initialized() {
        shapeAdapter = new ShapeAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class ShapeAdapter extends BaseAdapter {

        private TimesVH timesVh;

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
                view = LayoutInflater.from(ShareTimesAty.this).inflate(R.layout.item_shape_time_lv, null);
                timesVh = new TimesVH();
                ViewUtils.inject(timesVh, view);
                view.setTag(timesVh);
            } else {
                timesVh = (TimesVH) view.getTag();
            }

            return view;
        }

        private class TimesVH {

        }
    }

}
