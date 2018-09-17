package com.txd.hzj.wjlp.melloffLine.fgt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.SortBean;

import java.util.List;


public class ClassifyDetailAdapter extends RvAdapter<SortBean> {

    public ClassifyDetailAdapter(Context context, List<SortBean> list, RvListener listener) {
        super(context, list, listener);
    }


    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : (viewType == 1?R.layout.item_classify_detail:R.layout.item_coupon_layout);
    }

    @Override
    public int getItemViewType(int position) {

        // 标题返回1，商品返回1，优惠券返回2
        return list.get(position).isTitle() ? 0 : (list.get(position).isCoupon() ? 2 : 1);
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    public class ClassifyHolder extends RvHolder<SortBean> {
        TextView tvCity;
        ImageView avatar;
        TextView tvTitle;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvCity = (TextView) itemView.findViewById(R.id.tvCity);
                    avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
                    break;
            }

        }

        @Override
        public void bindHolder(SortBean sortBean, int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText("测试数据" + sortBean.getTag());
                    break;
                case 1:
                    tvCity.setText(sortBean.getName());
//                    avatar.setImageResource(R.mipmap.ic_launcher);
                    break;
            }

        }
    }
}