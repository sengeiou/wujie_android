package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.GlideUtils;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.bean.MellInfoList;
import com.txd.hzj.wjlp.mellOnLine.MellListAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：11:17
 * 描述：
 * ===============Txunda===============
 */

public class MellListAdapter extends BaseAdapter {

    private Context context;
    private List<MellInfoList> mells;

    private boolean showSelect = false;

    private int size = 0;

    private int selectNum = 0;

    public MellListAdapter(Context context, List<MellInfoList> mells) {
        this.context = context;
        this.mells = mells;
        size = ToolKit.dip2px(context, 100);
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    private MellViewHolder mvh;

    @Override
    public int getCount() {
        return mells.size();
    }

    @Override
    public MellInfoList getItem(int i) {
        return mells.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_mell_lv, viewGroup, false);
            mvh = new MellViewHolder();
            ViewUtils.inject(mvh, view);
            view.setTag(mvh);
        } else {
            mvh = (MellViewHolder) view.getTag();
        }
        if (mells.size() > 0) {
            final MellInfoList mellInfoList = getItem(i);

            Glide.with(context).load(mellInfoList.getMerInfo().getLogo())
                    .override(size, size).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(mvh.mell_logo_iv);

            mvh.mell_name_tv.setText(mellInfoList.getMerInfo().getMerchant_name());

            mvh.textView7.setText(mellInfoList.getMerInfo().getMerchant_desc());

            mvh.mell_score_tv.setText(mellInfoList.getMerInfo().getScore());

            mvh.into_mell_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MellInfoAty.class);
                    intent.putExtra("merchant_id", mellInfoList.getMerInfo().getMerchant_id());
                    context.startActivity(intent);
                }
            });

            if (showSelect) {
                mvh.operation_mell_iv.setVisibility(View.VISIBLE);
                if (mellInfoList.isSelect()) {
                    mvh.operation_mell_iv.setImageResource(R.drawable.icon_cart_goods_selected);
                } else {
                    mvh.operation_mell_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
                }
            } else {
                mvh.operation_mell_iv.setVisibility(View.GONE);
            }

            mvh.mell_prodect_gv.setAdapter(new MellProdectAdapter(mellInfoList.getGoodsList()));

            mvh.operation_mell_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mellInfoList.isSelect()) {
                        mellInfoList.setSelect(false);
                        mvh.operation_mell_iv.setImageResource(R.drawable.icon_cart_goods_selected);
                    } else {
                        mellInfoList.setSelect(true);
                        mvh.operation_mell_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
                    }
                    selectNum = 0;
                    for (MellInfoList mell : mells) {
                        if (mell.isSelect()) {
                            selectNum++;
                        }
                    }
                    if (forSelectNum != null) {
                        forSelectNum.selectNum(selectNum);
                    }
                }
            });

        }

        return view;
    }

    private class MellViewHolder {

        /**
         * 店铺头像
         */
        @ViewInject(R.id.mell_logo_iv)
        private ImageView mell_logo_iv;

        /**
         * 名称
         */
        @ViewInject(R.id.mell_name_tv)
        private TextView mell_name_tv;

        /**
         * 店铺简介
         */
        @ViewInject(R.id.textView7)
        private TextView textView7;

        /**
         * 评分
         */
        @ViewInject(R.id.mell_score_tv)
        private TextView mell_score_tv;

        /**
         * 进店逛逛
         */
        @ViewInject(R.id.into_mell_tv)
        private TextView into_mell_tv;

        @ViewInject(R.id.mell_prodect_gv)
        private GridViewForScrollView mell_prodect_gv;

        /**
         * 选中，非选中
         */
        @ViewInject(R.id.operation_mell_iv)
        private ImageView operation_mell_iv;
    }


    private class MellProdectAdapter extends BaseAdapter {
        private MPViewHolder mpvh;

        private List<MellInfoList.GoodsList> prodect;

        public MellProdectAdapter(List<MellInfoList.GoodsList> prodect) {
            this.prodect = prodect;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return prodect.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_mell_prodect_gv, viewGroup, false);
                mpvh = new MPViewHolder();
                ViewUtils.inject(mpvh, view);
                view.setTag(mpvh);
            } else {
                mpvh = (MPViewHolder) view.getTag();
            }
            ChangeTextViewStyle.getInstance().forMellProdect(context, mpvh.mell_prodect_price_tv, "￥12.00");
            return view;
        }

        class MPViewHolder {

            @ViewInject(R.id.mell_prodect_price_tv)
            private TextView mell_prodect_price_tv;

        }
    }

    public interface ForSelectNum {
        void selectNum(int num);
    }

    private ForSelectNum forSelectNum;

    public void setForSelectNum(ForSelectNum forSelectNum) {
        this.forSelectNum = forSelectNum;
    }
}
