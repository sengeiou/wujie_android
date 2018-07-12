package com.txd.hzj.wjlp.minetoAty.coupon.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:52
 * 描述：会员卡碎片
 */
public class ClubCardFgt extends BaseFgt {


    @ViewInject(R.id.valid_ticket_lv)
    private ListViewForScrollView valid_ticket_lv;
    @ViewInject(R.id.un_valid_ticket_lv)
    private ListViewForScrollView un_valid_ticket_lv;

    @ViewInject(R.id.show_delete_iv)
    private ImageView show_delete_iv;

    @ViewInject(R.id.card_status_tv)
    private TextView card_status_tv;

    private ClubCartAdapter clubCartAdapter;
    private ClubCartAdapter clubCartAdapter1;


    public ClubCardFgt() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show_delete_iv.setVisibility(View.GONE);
        card_status_tv.setText("未激活会员卡");

//        valid_ticket_lv.setAdapter(clubCartAdapter);
//        un_valid_ticket_lv.setAdapter(clubCartAdapter1);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_club_card;
    }

    @Override
    protected void initialized() {
        clubCartAdapter = new ClubCartAdapter(0);
        clubCartAdapter1 = new ClubCartAdapter(1);

//        valid_ticket_lv.setVisibility(View.GONE);
//        un_valid_ticket_lv.setVisibility(View.GONE);

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {
    }

    private class ClubCartAdapter extends BaseAdapter {

        private CCVH ccvh;
        private int type = 0;

        public ClubCartAdapter(int type) {
            this.type = type;
        }

        @Override
        public int getCount() {
            return 3;
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
            if (null == view) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_club_card_lv, null);
                ccvh = new CCVH();
                ViewUtils.inject(ccvh, view);
                view.setTag(ccvh);
            } else {
                ccvh = (CCVH) view.getTag();
            }

            if (0 == type) {
                int pos = i % 3;
                int imageId = getResources().getIdentifier("icon_club_card_bg_"+pos, "drawable",
                        getActivity().getPackageName());
                ccvh.item_club_card_layout.setBackgroundResource(imageId);
            } else {
                ccvh.item_club_card_layout.setBackgroundResource(R.drawable.icon_club_card_bg_g_hzj);
            }

            return view;
        }

        class CCVH {
            @ViewInject(R.id.item_club_card_layout)
            private LinearLayout item_club_card_layout;
        }
    }

}
