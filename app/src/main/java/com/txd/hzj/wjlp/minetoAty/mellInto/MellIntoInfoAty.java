package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseMode;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/16 0016
 * 时间：下午 3:16
 * 描述：推荐详情
 * ===============Txunda===============
 */
public class MellIntoInfoAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.pic_for_mell_into_gv)
    private GridViewForScrollView pic_for_mell_into_gv;

    private PicAdapter padapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐详情");
        pic_for_mell_into_gv.setAdapter(padapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_into_info_hzj;
    }

    @Override
    protected void initialized() {
        padapter = new PicAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class PicAdapter extends BaseAdapter {

        private PicVh pvh;

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
                view = LayoutInflater.from(MellIntoInfoAty.this).inflate(R.layout.item_pic_for_mell_into_gc, null);
                pvh = new PicVh();
                ViewUtils.inject(pvh, view);
                view.setTag(pvh);
            } else
                pvh = (PicVh) view.getTag();

            return view;
        }

        private class PicVh {

        }
    }

}
