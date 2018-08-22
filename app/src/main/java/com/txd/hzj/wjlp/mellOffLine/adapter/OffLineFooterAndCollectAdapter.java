package com.txd.hzj.wjlp.mellOffLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.footPoint.OffLineCollectBean;
import com.txd.hzj.wjlp.bean.footPoint.OfflineFootBean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/22 11:16
 * 功能描述：线下店铺我的收藏、我的足迹适配器
 */
public class OffLineFooterAndCollectAdapter extends BaseAdapter {
    private final int size;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private boolean showSelect=false;
    private List<Boolean> selectList;
    private int selectNum;
    //收藏列表
    private List<OffLineCollectBean.DataBean> collectList;

    //足迹列表
    private List<OfflineFootBean.DataBean> footerList;
    /**
     * 数据类型
     * 0.足迹
     * 1.收藏
     */
    private int dataType = 0;

    public OffLineFooterAndCollectAdapter(Context context, List<OffLineCollectBean.DataBean> dataBeanList,int dataType) {
        mContext = context;
        size = ToolKit.dip2px(context, 100);
        this.collectList=dataBeanList;
        this.dataType=dataType;
    }
    public OffLineFooterAndCollectAdapter(Context context, List<OfflineFootBean.DataBean> footerList) {
        mContext = context;
        size = ToolKit.dip2px(context, 100);
        this.footerList=footerList;
        this.dataType=0;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    public void setShowSelect(boolean b) {
        this.showSelect = b;
    }

    public void setSelectItem(List<Boolean> booleanList) {
        this.selectList = booleanList;
    }

    public List<Boolean> getSelectList() {
        return selectList;
    }


    public interface  OnItemClickListener{
        void itemClick(int position);
    }

    public interface ForSelectNum {
        void selectNum(int num);
    }

    private ForSelectNum forSelectNum;

    public void setForSelectNum(ForSelectNum forSelectNum) {
        this.forSelectNum = forSelectNum;
    }

    @Override
    public int getCount() {
        return 1==dataType?(collectList.size()>0?collectList.size():0):(footerList.size()>0?footerList.size():0);
    }

    @Override
    public Object getItem(int position) {
        if (1==dataType){
            return collectList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        OffLineFooterAndCollectAdapter.ViewHolder viewHolder;
        if (view==null){
            viewHolder=new OffLineFooterAndCollectAdapter.ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.off_line_list_item,parent,false);
            ViewUtils.inject(viewHolder, view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (OffLineFooterAndCollectAdapter.ViewHolder) view.getTag();
        }
        if (showSelect) {
            viewHolder.offline_img.setVisibility(View.VISIBLE);
            if (selectList!=null && selectList.get(position)) {
                viewHolder.offline_img.setImageResource(R.drawable.icon_collect_mells_selected);
            } else {
                viewHolder.offline_img.setImageResource(R.drawable.icon_collect_mells_unselect);
            }
        } else {
            viewHolder.offline_img.setVisibility(View.GONE);
        }

        if (0==dataType){
            final OfflineFootBean.DataBean dataBean = footerList.get(position);
            Glide.with(mContext).load(dataBean.getLogo())
                    .override(size, size).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(viewHolder.mell_logo_iv);

            viewHolder.mell_name_tv.setText(dataBean.getMerchant_name());
            viewHolder.textView7.setText(dataBean.getMerchant_desc());
            String star = dataBean.getScore();
            viewHolder.shop_evaluate_star_level.setRating(android.text.TextUtils.isEmpty(star) ? 4 : Float.valueOf(star));
            viewHolder.achievement_tv.setText("|月售" + dataBean.getMonths_order() + "单");
        }else if (1==dataType) {
            final OffLineCollectBean.DataBean dataBean = collectList.get(position);
            Glide.with(mContext).load(dataBean.getLogo())
                    .override(size, size).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(viewHolder.mell_logo_iv);

            viewHolder.mell_name_tv.setText(dataBean.getMerchant_name());
            viewHolder.textView7.setText(dataBean.getMerchant_desc());
            String star = dataBean.getScore();
            viewHolder.shop_evaluate_star_level.setRating(android.text.TextUtils.isEmpty(star) ? 4 : Float.valueOf(star));
            viewHolder.achievement_tv.setText("|月售" + dataBean.getMonths_order() + "单");
        }



        //点击了进店逛逛
        viewHolder.into_mell_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null!=mOnItemClickListener){
                    mOnItemClickListener.itemClick(position);
                }

            }
        });

        viewHolder.offline_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=selectList){
                    if (selectList.get(position)) {
                        selectList.set(position,false);
                    }else{
                        selectList.set(position,true);
                    }
                    selectNum = 0;
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i)){
                            selectNum+=1;
                        }
                    }
                    notifyDataSetChanged();
                    if (forSelectNum != null) {
                        forSelectNum.selectNum(selectNum);
                    }
                }

            }
        });

        return view;
    }

    class ViewHolder{
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
        @ViewInject(R.id.shop_evaluate_star_level)
        private RatingBar shop_evaluate_star_level;

        /**
         * 业绩
         */
        @ViewInject(R.id.achievement_tv)
        private TextView achievement_tv;

        /**
         * 进店逛逛
         */
        @ViewInject(R.id.into_mell_tv)
        private TextView into_mell_tv;

        @ViewInject(R.id.offline_img)
        private ImageView offline_img;
    }
}
