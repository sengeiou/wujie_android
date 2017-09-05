package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.AllGoodsBean;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
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
    private List<AllGoodsBean> list;
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

    private int size1 = 0;
    private int size2 = 0;

    private int pic_size = 0;
    private int pic_size2 = 0;
    private LinearLayout.LayoutParams params;

    public AllGvLvAdapter(Context context, List<AllGoodsBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
        size1 = ToolKit.dip2px(context, 40);
        size2 = ToolKit.dip2px(context, 32);
        pic_size = Settings.displayWidth;
        pic_size2 = Settings.displayWidth / 2;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AllGoodsBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AllGoodsBean allGoodsBean = getItem(i);
        if (view == null) {
            switch (type) {
                case 0:// 限量购
                case 2:// 无界预购
                    view = inflater.inflate(R.layout.item_purchase_gv, viewGroup,false);
                    break;
                case 1:// 票券区
                    view = inflater.inflate(R.layout.item_ticket_gv, viewGroup,false);
                    break;
                case 3:// 进口馆
                    view = inflater.inflate(R.layout.item_import_gv, viewGroup,false);
                    break;
                case 4:// 竞拍汇
                    view = inflater.inflate(R.layout.item_auction_gv, viewGroup,false);
                    break;
                case 5:// 一元夺宝
                    view = inflater.inflate(R.layout.item_good_luck_gv, viewGroup,false);
                    break;
                case 6:// 汽车购
                    view = inflater.inflate(R.layout.item_car_gv, viewGroup,false);
                    break;
                case 7:// 房产购
                    view = inflater.inflate(R.layout.item_house_gv, viewGroup,false);
                    break;
                case 8:// 拼好货
                    view = inflater.inflate(R.layout.item_group_shopping_lv, viewGroup,false);
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

        if (type == 0 || type == 2 || type == 4) {
            vh.sold_num_tv.setVisibility(View.GONE);
        }

        if (type == 2) {
            vh.goods_num_already_tv.setText("已预购100件");
        }
        if (type < 5) {
            vh.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        switch (type) {
            case 8:// 拼团购
                params = new LinearLayout.LayoutParams(pic_size,pic_size2);
                vh.goods_pic_iv.setLayoutParams(params);
                Glide.with(context).load(allGoodsBean.getGoods_img())
                        .override(pic_size, pic_size2)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.goods_pic_iv);
                List<AllGoodsBean.AppendPersonBean> append_person = allGoodsBean.getAppend_person();
                if (!ListUtils.isEmpty(append_person)) {
                    if (allGoodsBean.getAppend_person().size() >= 2) {
                        vh.sec_head_iv.setVisibility(View.VISIBLE);
                        Glide.with(context).load(append_person.get(0).getHead_pic())
                                .override(size1, size2)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.goods_pic_iv);
                        Glide.with(context).load(append_person.get(1).getHead_pic())
                                .override(pic_size, pic_size2)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.goods_pic_iv);
                    } else {
                        vh.sec_head_iv.setVisibility(View.GONE);
                        Glide.with(context).load(append_person.get(0).getHead_pic())
                                .override(size1, size2)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.goods_pic_iv);
                    }

                } else {
                    vh.frist_head_iv.setVisibility(View.GONE);
                    vh.sec_head_iv.setVisibility(View.GONE);
                }
                vh.goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.goods_price_tv.setText(allGoodsBean.getGroup_price());
                vh.group_totla_tv.setText("已团" + allGoodsBean.getTotal() + "件");
                vh.group_integral_tv.setText(allGoodsBean.getIntegral());
                break;
        }

        Glide.with(context).load(allGoodsBean.getCountry_logo())
                .override(size1, size2)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(vh.logo_for_country_iv);

        if (allGoodsBean.getIntegral().equals("0")) {
            vh.use_coupon_tv.setText("不可使用优惠券");
            vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            vh.use_coupon_tv.setText("可使用" + allGoodsBean.getIntegral() + "%优惠券");
            vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);

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

        /**
         * 已抢购
         * 已预购
         */
        @ViewInject(R.id.goods_num_already_tv)
        private TextView goods_num_already_tv;

        /**
         * type = 1,2
         * 已售xxx件
         */
        @ViewInject(R.id.sold_num_tv)
        private TextView sold_num_tv;

        /**
         * 国家国旗
         */
        @ViewInject(R.id.logo_for_country_iv)
        private ImageView logo_for_country_iv;
        /**
         * 是否使用优惠券，是否能使用优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;
        /**
         * 商品图片
         */
        @ViewInject(R.id.goods_pic_iv)
        private ImageView goods_pic_iv;
        /**
         * 商品名称
         */
        @ViewInject(R.id.goods_name_tv)
        private TextView goods_name_tv;
        /**
         * 商品价格
         */
        @ViewInject(R.id.goods_price_tv)
        private TextView goods_price_tv;
        /**
         * 已团件数
         */
        @ViewInject(R.id.group_totla_tv)
        private TextView group_totla_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.group_integral_tv)
        private TextView group_integral_tv;
        /**
         * 第一个头像
         */
        @ViewInject(R.id.frist_head_iv)
        private ShapedImageView frist_head_iv;
        /**
         * 第二个头像
         */
        @ViewInject(R.id.sec_head_iv)
        private ShapedImageView sec_head_iv;
    }
}
