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
 * 时间：17:00
 * 描述：我的推荐
 * ===============Txunda===============
 */

public class RecommendSuccessAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.share_times_lv)
    private ListView share_times_lv;

    private ReSuccessAdapter reSuccessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐成功");
        share_times_lv.setAdapter(reSuccessAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_times;
    }

    @Override
    protected void initialized() {
        reSuccessAdapter = new ReSuccessAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class ReSuccessAdapter extends BaseAdapter {

        private RSVH rsvh;

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
                view = LayoutInflater.from(RecommendSuccessAty.this).inflate(R.layout.item_recomment_success_lv, null);
                rsvh = new RSVH();
                ViewUtils.inject(rsvh, view);
                view.setTag(rsvh);
            } else {
                rsvh = (RSVH) view.getTag();
            }
            return view;
        }

        private class RSVH {

        }

    }

}
