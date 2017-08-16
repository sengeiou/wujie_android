package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/11 0011
 * 时间：上午 11:41
 * 描述：推荐列表
 * ===============Txunda===============
 */
public class MellIntoListAty extends BaseAty {

    /**
     * 推荐列表
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.mell_into_lv)
    private ListView mell_into_lv;
    private MellIntoAdapter mellInto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐列表");
        mell_into_lv.setAdapter(mellInto);
        mell_into_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(MellIntoInfoAty.class, null);
            }
        });
    }

    @Override
    @OnClick({R.id.to_mell_into_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_mell_into_tv:
                startActivity(MerchantWillMoveIntoAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_into_list_hzj;
    }

    @Override
    protected void initialized() {
        mellInto = new MellIntoAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class MellIntoAdapter extends BaseAdapter {
        private MIVH mivh;

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
                view = LayoutInflater.from(MellIntoListAty.this).inflate(R.layout.item_mell_into_lv, null);
                mivh = new MIVH();
                ViewUtils.inject(mivh, view);
                view.setTag(mivh);
            } else {
                mivh = (MIVH) view.getTag();
            }
            return view;
        }

        class MIVH {

        }
    }

}
