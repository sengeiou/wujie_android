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

import java.math.BigDecimal;
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

    private int group_size = 0;

    public AllGvLvAdapter(Context context, List<AllGoodsBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.inflater = LayoutInflater.from(context);
        size1 = ToolKit.dip2px(context, 36);
        size2 = ToolKit.dip2px(context, 23);
//        if (8 == type) {
//            pic_size = Settings.displayWidth;
//            pic_size2 = Settings.displayWidth / 2;
//            group_size = ToolKit.dip2px(context, 40);
//        } else {
        pic_size = ToolKit.dip2px(context, 180);
        pic_size2 = pic_size;
        group_size = ToolKit.dip2px(context, 40);
//        }
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
                    view = inflater.inflate(R.layout.item_purchase_gv, viewGroup, false);
                    break;
                case 1:// 票券区
                    view = inflater.inflate(R.layout.item_ticket_gv, viewGroup, false);
                    break;
                case 3:// 进口馆
                    view = inflater.inflate(R.layout.item_import_gv, viewGroup, false);
                    break;
                case 4:// 竞拍汇
                    view = inflater.inflate(R.layout.item_auction_gv, viewGroup, false);
                    break;
                case 5:// 一元夺宝
                    view = inflater.inflate(R.layout.item_good_luck_gv, viewGroup, false);
                    break;
                case 6:// 汽车购
                    view = inflater.inflate(R.layout.item_car_gv, viewGroup, false);
                    break;
                case 7:// 房产购
                    view = inflater.inflate(R.layout.item_house_gv, viewGroup, false);
                    break;
                case 8:// 拼好货
                    view = inflater.inflate(R.layout.item_group_shopping_lv, viewGroup, false);
                    break;
            }
            vh = new ViewHolder();
            ViewUtils.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        // 原价删除线
        if (type < 5) {
            vh.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        switch (type) {
            case 0:// 限量购
            case 2:// 无界预购
                long now = System.currentTimeMillis() / 1000;
                long end;
                try {
                    end = Long.parseLong(allGoodsBean.getEnd_time());
                } catch (NumberFormatException e) {
                    end = 0;
                }
                vh.home_count_down_view.setTag("countDown" + i);
                vh.home_count_down_view.start((end - now) * 1000);
                if (type == 2) {
                    vh.goods_num_already_tv.setText("已抢购" + allGoodsBean.getSell_num() + "件");
                    vh.peice_tv.setText("￥" + allGoodsBean.getDeposit());
                } else {
                    vh.peice_tv.setText("￥" + allGoodsBean.getLimit_price());
                }

                int max;
                try {
                    max = Integer.parseInt(allGoodsBean.getSuccess_max_num());
                } catch (NumberFormatException e) {
                    max = 0;
                }
                int sell_num;
                try {
                    sell_num = Integer.parseInt(allGoodsBean.getSell_num());
                } catch (NumberFormatException e) {
                    sell_num = 0;
                }

                // 设置进度
                vh.cpb_progresbar2.setMaxProgress(max);
                vh.cpb_progresbar2.setCurProgress(sell_num);

                double d = sell_num * 100.0f / max;
                if (sell_num >= max) {
                    d = 100f;
                }
                String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                vh.preferential_type_tv.setText(str + "%");

                vh.sold_num_tv.setVisibility(View.GONE);
                Glide.with(context).load(allGoodsBean.getCountry_logo())
//                        .override(size1, size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .dontAnimate()
//                        .fitCenter()
                        .into(vh.item_country_logo_tv);

                vh.item_goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.older_price_tv.setText("￥" + allGoodsBean.getMarket_price());
                vh.get_integral_tv.setText(allGoodsBean.getIntegral());

                /*是否可以使用代金券使用多少优惠*/
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }

                break;
            case 1:// 票券区
                // 价格
                vh.peice_tv.setText("￥" + allGoodsBean.getShop_price());
                // 商品图片
                Glide.with(context).load(allGoodsBean.getCountry_logo())
//                        .override(size1, size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .dontAnimate()
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.country_logo_iv);

                vh.item_goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.older_price_tv.setText("￥" + allGoodsBean.getMarket_price());
                vh.get_integral_tv.setText(allGoodsBean.getIntegral());
                /* 是否可以使用代金券* 使用多少优惠*/
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }
                break;
            case 3:// 进口馆
                // 价格
                vh.peice_tv.setText("￥" + allGoodsBean.getShop_price());
                vh.sold_num_tv.setText("已售" + allGoodsBean.getSell_num() + "件");

                vh.item_goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.older_price_tv.setText("￥" + allGoodsBean.getMarket_price());
                vh.get_integral_tv.setText(allGoodsBean.getIntegral());
                /*
                * 是否可以使用代金券
                * 使用多少优惠
                */
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }
                // 国旗
                Glide.with(context).load(allGoodsBean.getCountry_logo())
//                        .override(size1, size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .dontAnimate()
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.country_logo_iv);
                break;

            case 4://竞拍汇

                // 国旗
                Glide.with(context).load(allGoodsBean.getCountry_logo())
//                        .override(size1, size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .fitCenter()
//                        .dontAnimate()
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.logo_for_country_iv);

                long now4 = System.currentTimeMillis() / 1000;
                long end4;
                try {
                    end4 = Long.parseLong(allGoodsBean.getEnd_time());
                } catch (NumberFormatException e) {
                    end4 = 0;
                }
                vh.home_count_down_view.setTag("countDown" + i);
                vh.home_count_down_view.start(end4 - now4);

                vh.sold_num_tv.setVisibility(View.GONE);

                vh.item_goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.older_price_tv.setText("￥" + allGoodsBean.getMarket_price());
                vh.get_integral_tv.setText(allGoodsBean.getIntegral());
                /*
                * 是否可以使用代金券
                * 使用多少优惠
                */
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }
                break;
            case 5:   // 一元夺宝
                long now_time = System.currentTimeMillis() / 1000;
                long end_time;
                try {
                    end_time = Long.parseLong(allGoodsBean.getEnd_time());
                } catch (NumberFormatException e) {
                    end_time = 0;
                }
                vh.home_count_down_view.setTag("countDown" + i);
                vh.home_count_down_view.start(end_time - now_time);

                // 国旗
                Glide.with(context).load(allGoodsBean.getCountry_logo())
//                        .override(size1, size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .dontAnimate()
//                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.logo_for_country_iv);
                vh.one_buy_goods_name_tv.setText(allGoodsBean.getGoods_name());

                int max5;
                try {
                    max5 = Integer.parseInt(allGoodsBean.getPerson_num());
                } catch (NumberFormatException e) {
                    max5 = 100;
                }
                int pro;
                try {
                    pro = Integer.parseInt(allGoodsBean.getAdd_num());
                } catch (NumberFormatException e) {
                    pro = 0;
                }

                // 设置进度
                vh.cpb_progresbar2.setMaxProgress(max5);
                vh.cpb_progresbar2.setCurProgress(pro);
                // 剩余...人
                vh.add_num_tv.setText(allGoodsBean.getDiff_num());

                vh.goods_integral_tv.setText(allGoodsBean.getIntegral());

                /*
         * 是否可以使用代金券
         * 使用多少优惠
         */
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }
                break;


            case 6:// 汽车购
                // 距离
                vh.distance_tv.setText(allGoodsBean.getDistance());

                Glide.with(context).load(allGoodsBean.getCar_img())
                        .override(pic_size, pic_size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.car_pic_iv);
                // 汽车名称
                vh.car_name_tv.setText(allGoodsBean.getCar_name());
                // 代金券
                vh.chit_tv.setText(allGoodsBean.getPre_money());
                vh.car_other_info_tv.setText("可    抵：¥" + allGoodsBean.getTrue_pre_money() + "车款\n车全价：¥" +
                        allGoodsBean.getAll_price());
                // 积分
                vh.car_integral_tv.setText(allGoodsBean.getIntegral());
                // 国旗
                Glide.with(context).load(allGoodsBean.getBrand_logo())
                        .override(size1, size1)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.car_logo_for_country_iv);


                if (allGoodsBean.getTicket_discount().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }


                break;
            case 7:// 房产购

                // 距离
                vh.distance_tv.setText(allGoodsBean.getDistance());

                Glide.with(context).load(allGoodsBean.getHouse_img())
                        .override(pic_size, pic_size2)
//                        .placeholder(R.drawable.ic_default)
//                        .error(R.drawable.ic_default)
//                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.house_pic_iv);

                // 名称
                vh.house_name_tv.setText(allGoodsBean.getHouse_name());
                vh.developer_tv.setText(allGoodsBean.getDeveloper());
                vh.min_to_max_price_tv.setText(allGoodsBean.getMin_price() + "-" + allGoodsBean.getMax_price());
                vh.house_now_num_tv.setText(allGoodsBean.getNow_num() + "套");


                break;
            case 8:// 拼团购
                vh.goods_name_tv.setText(allGoodsBean.getGoods_name());
                vh.goods_price_tv.setText(allGoodsBean.getGroup_price());
                vh.group_integral_tv.setText(allGoodsBean.getIntegral());
                vh.tv_shop_price.setText("单买价：¥" + allGoodsBean.getShop_price());
                vh.group_num.setText("(" + allGoodsBean.getGroup_num() + "人拼单价)");
                // 商品图片重置大小
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pic_size, pic_size2);
//                vh.goods_pic_iv.setLayoutParams(params);
                // 团购两人头像(最多两人)
                List<AllGoodsBean.AppendPersonBean> append_person = allGoodsBean.getAppend_person();
                if (!ListUtils.isEmpty(append_person)) {
                    if (allGoodsBean.getAppend_person().size() >= 2) {
                        vh.sec_head_iv.setVisibility(View.VISIBLE);
                        Glide.with(context).load(append_person.get(0).getHead_pic())
                                .override(group_size, group_size)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.frist_head_iv);
                        Glide.with(context).load(append_person.get(1).getHead_pic())
                                .override(group_size, group_size)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.sec_head_iv);
                    } else {
                        vh.frist_head_iv.setVisibility(View.GONE);
                        Glide.with(context).load(append_person.get(0).getHead_pic())
                                .override(group_size, group_size)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(vh.sec_head_iv);
                    }

                } else {
                    vh.frist_head_iv.setVisibility(View.GONE);
                    vh.sec_head_iv.setVisibility(View.GONE);
                }

                vh.group_totla_tv.setText("已    拼：" + allGoodsBean.getTotal() + "件");

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(size1, size2);
                vh.logo_for_country_iv.setLayoutParams(params1);
                Glide.with(context).load(allGoodsBean.getCountry_logo())
                        .override(size1, size2)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .dontAnimate()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(vh.logo_for_country_iv);

                /*
                 * 是否可以使用代金券
                 * 使用多少优惠
                 */
                if (allGoodsBean.getTicket_buy_id().equals("0")) {
                    vh.use_coupon_tv.setText("不可使用代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    vh.use_coupon_tv.setText("最多可使用" + allGoodsBean.getTicket_buy_discount() + "%代金券");
                    vh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                }

                break;
        }
        // 除掉汽车购，房产购
        if (6 != type && 7 != type) {
            Glide.with(context).load(allGoodsBean.getGoods_img())
                    .override(pic_size, pic_size2)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(vh.goods_pic_iv);
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
         * 国家国旗
         */
        @ViewInject(R.id.item_country_logo_tv)
        private ImageView item_country_logo_tv;
        /**
         * 是否使用代金券，是否能使用代金券
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
         * 商品名称1-7
         */
        @ViewInject(R.id.item_goods_name_tv)
        private TextView item_goods_name_tv;
        /**
         * 商品价格
         */
        @ViewInject(R.id.goods_price_tv)
        private TextView goods_price_tv;
        /**
         * 商品价格(1-7)
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;
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
         * 积分(1-7)
         */
        @ViewInject(R.id.get_integral_tv)
        private TextView get_integral_tv;
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

        /**
         * 售货进度
         */
        @ViewInject(R.id.preferential_type_tv)
        private TextView preferential_type_tv;

        /**
         * 票券区国旗
         */
        @ViewInject(R.id.country_logo_iv)
        private ImageView country_logo_iv;
        // TODO==========汽车购==========
        /**
         * 距离
         */
        @ViewInject(R.id.distance_tv)
        private TextView distance_tv;
        /**
         * 汽车购图片
         */
        @ViewInject(R.id.car_pic_iv)
        private ImageView car_pic_iv;
        /**
         * 汽车名称
         */
        @ViewInject(R.id.car_name_tv)
        private TextView car_name_tv;
        /**
         * 代金券面值
         */
        @ViewInject(R.id.chit_tv)
        private TextView chit_tv;
        /**
         * 其他信息
         */
        @ViewInject(R.id.car_other_info_tv)
        private TextView car_other_info_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.car_integral_tv)
        private TextView car_integral_tv;

        @ViewInject(R.id.car_logo_for_country_iv)
        private ImageView car_logo_for_country_iv;

        // TODO==========房产购==========
        /**
         * 房产图片
         */
        @ViewInject(R.id.house_pic_iv)
        private ImageView house_pic_iv;
        /**
         * 房产名称
         */
        @ViewInject(R.id.house_name_tv)
        private TextView house_name_tv;
        /**
         * 房产开发商
         */
        @ViewInject(R.id.developer_tv)
        private TextView developer_tv;
        /**
         * 房产价格
         */
        @ViewInject(R.id.min_to_max_price_tv)
        private TextView min_to_max_price_tv;
        /**
         * 在售房源
         */
        @ViewInject(R.id.house_now_num_tv)
        private TextView house_now_num_tv;

        // TODO==========一元夺宝=========

        @ViewInject(R.id.one_buy_goods_name_tv)
        private TextView one_buy_goods_name_tv;

        @ViewInject(R.id.add_num_tv)
        private TextView add_num_tv;
        @ViewInject(R.id.goods_integral_tv)
        private TextView goods_integral_tv;

        @ViewInject(R.id.group_num)
        private TextView group_num;
        @ViewInject(R.id.tv_shop_price)
        private TextView tv_shop_price;

    }
}
