package com.txd.hzj.wjlp.mellOnLine.adapter;

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
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/13 0013
 * 时间：09:10
 * 描述：
 *
 */

public class MellGoodsAndAdsAdapter extends BaseAdapter {

    private int type;

    private Context context;

    private List<Map<String, String>> list;

    private LayoutInflater mInflayer;

    private int adsSize = 0;
    private int adsSize1 = 0;

    private int logo_size1 = 0;
    private int logo_size2 = 0;

    private int pic_size = 0;

    private int group_size = 0;

    public MellGoodsAndAdsAdapter(Context context, int type, List<Map<String, String>> list) {
        this.type = type;
        this.context = context;
        this.list = list;
        if (0 == type) {
            adsSize = Settings.displayWidth;
            adsSize1 = Settings.displayWidth;
        } else {
            logo_size1 = ToolKit.dip2px(context, 36);
            logo_size2 = ToolKit.dip2px(context, 23);
            pic_size = ToolKit.dip2px(context, 180);
            group_size = ToolKit.dip2px(context, 40);
        }
        mInflayer = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Map<String, String> map = getItem(i);
        MellGAVH holder;
        if (view == null) {
            switch (type) {
                case 0:// 店铺首页
                    view = mInflayer.inflate(R.layout.item_red_package_lv, parent, false);
                    break;
                case 1:// 全部商品,热销,最新上架
                    view = mInflayer.inflate(R.layout.item_mell_goods_rv, parent, false);
                    break;
                case 2:// 限量购
                case 4:// 无界预购
                    view = mInflayer.inflate(R.layout.item_purchase_gv, parent, false);
                    break;
                case 3:// 拼团购
                    view = mInflayer.inflate(R.layout.item_group_shopping_lv, parent, false);
                    break;
                case 5:// 竞拍汇
                    view = mInflayer.inflate(R.layout.item_auction_gv, parent, false);
                    break;
                case 6:// 一元夺宝(积分夺宝)
                    view = mInflayer.inflate(R.layout.item_good_luck_gv, parent, false);
                    break;
            }
            holder = new MellGAVH();
            ViewUtils.inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (MellGAVH) view.getTag();
        }
        switch (type) {
            case 0:// TODO==========店铺首页==========

                holder.image_for_mell1.setVisibility(View.GONE);

                ViewGroup.LayoutParams lp = holder.image_for_mell.getLayoutParams();
                lp.width = adsSize;
                lp.height = adsSize * 2 / 3;
                holder.image_for_mell.setLayoutParams(lp);
                holder.image_for_mell.setMaxWidth(adsSize);
                holder.image_for_mell.setMaxHeight(adsSize * 2 / 3);

                Glide.with(context).load(map.get("ads_pic"))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .dontAnimate()
                        .into(holder.image_for_mell);


                break;
            case 1:// TODO==========全部商品，热销，新上==========

                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .dontAnimate()
                        .into(holder.logo_for_country_iv);

                Glide.with(context).load(map.get("goods_img"))
//                        .override(pic_size, pic_size)sgds
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .centerCrop()
                        .into(holder.imageView);

                holder.mell_goods_name_tv.setText(map.get("goods_name"));
                // 现价
                holder.now_price_tv.setText("￥" + map.get("shop_price"));
                // 积分
                holder.integral_tv.setText(map.get("integral"));
                holder.sold_num_tv.setText("已售" + map.get("sell_num") + "件");
                break;
            case 3:// TODO==========拼团购==========
                // 商品名
                holder.goods_name_tv.setText(map.get("goods_name"));
                // 商品团购价格
                holder.goods_price_tv.setText(map.get("group_price"));
                // 商品积分
                holder.group_integral_tv.setText(map.get("integral"));

                holder.group_num.setText("(" + map.get("group_num")+ "人拼单价)");
                // 已团。。。。。件
                holder.group_already_tv.setText("已拼" + map.get("total") + "件");


                // 商品图片
                Glide.with(context).load(map.get("goods_img"))
                        .override(pic_size, pic_size)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(holder.goods_pic_iv);
                // 团购两人头像(最多两人)

                if (ToolKit.isList(map, "append_person")) {
                    List<Map<String, String>> append_person = JSONUtils.parseKeyAndValueToMapList(
                            map.get("append_person"));
                    if (!ListUtils.isEmpty(append_person)) {
                        if (append_person.size() >= 2) {
                            holder.sec_head_iv.setVisibility(View.VISIBLE);
                            Glide.with(context).load(append_person.get(0).get("head_pic"))
                                    .override(group_size, group_size)
                                    .placeholder(R.drawable.ic_default)
                                    .error(R.drawable.ic_default)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(holder.frist_head_iv);
                            Glide.with(context).load(append_person.get(1).get("head_pic"))
                                    .override(group_size, group_size)
                                    .placeholder(R.drawable.ic_default)
                                    .error(R.drawable.ic_default)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(holder.sec_head_iv);
                        } else {
                            holder.sec_head_iv.setVisibility(View.GONE);
                            Glide.with(context).load(append_person.get(0).get("head_pic"))
                                    .override(group_size, group_size)
                                    .placeholder(R.drawable.ic_default)
                                    .error(R.drawable.ic_default)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(holder.frist_head_iv);
                        }

                    } else {
                        holder.frist_head_iv.setVisibility(View.GONE);
                        holder.sec_head_iv.setVisibility(View.GONE);
                    }
                } else {
                    holder.frist_head_iv.setVisibility(View.GONE);
                    holder.sec_head_iv.setVisibility(View.GONE);
                }
                holder.group_already_tv.setText("已拼" + map.get("total") + "件");


//                1试用品拼单 2常规拼单",
                holder.showTyImg.setVisibility("1".equals(map.get("group_type")) ? View.VISIBLE : View.GONE);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(logo_size1, logo_size2);
                holder.logo_for_country_iv.setLayoutParams(params1);
                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .dontAnimate()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(holder.logo_for_country_iv);

   /*
                 * 是否可以使用代金券
                 * 使用多少优惠
                 */
                String logStr = "";
                try {
                    if (map.get("ticket_buy_discount").equals("0")) {
                        holder.use_coupon_tv.setText("不可使用代金券");
                        holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                        logStr = "不可使用代金券";
                    } else {
                        holder.use_coupon_tv.setText("最多可使用" + map.get("ticket_buy_discount") + "%代金券");
                        holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                        logStr = "最多可使用" + map.get("ticket_buy_discount") + "%代金券";
                    }
                } catch (Exception e) {
                    logStr = "首页代金券Catch.Exception:" + e.toString();
                } finally {
                    L.e(logStr);
                }



                break;
            case 2:// TODO==========限量购，无界预购
            case 4:
                if (2 == type) {
                    holder.sold_num_tv.setText("已抢购" + map.get("sell_num") + "件");
                    holder.goods_num_already_tv.setText("已抢购" + map.get("sell_num") + "件");
                    holder.peice_tv.setText("￥" + map.get("limit_price"));
                } else {
                    holder.sold_num_tv.setText("已预购" + map.get("sell_num") + "件");
                    holder.goods_num_already_tv.setText("已预购" + map.get("sell_num") + "件");
                    holder.peice_tv.setText("￥" + map.get("deposit"));
                }

                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .dontAnimate()
                        .into(holder.item_country_logo_tv);

                Glide.with(context).load(map.get("goods_img"))
                        .override(pic_size, pic_size)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(holder.goods_pic_iv);

                // 商品名称
                holder.item_goods_name_tv.setText(map.get("goods_name"));
                // 积分
                holder.get_integral_tv.setText(map.get("integral"));
                break;
            case 5:// TODO==========竞拍汇
                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .dontAnimate()
                        .into(holder.item_country_logo_tv);

                Glide.with(context).load(map.get("goods_img"))
                        .override(pic_size, pic_size)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(holder.goods_pic_iv);

                holder.peice_tv.setText("￥" + map.get("start_price"));
                // 商品名称
                holder.item_goods_name_tv.setText(map.get("goods_name"));
                // 积分
                holder.get_integral_tv.setText(map.get("integral"));

                break;
            case 6:// TODO==========一元夺宝
                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .dontAnimate()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(holder.logo_for_country_iv);

                Glide.with(context).load(map.get("goods_img"))
                        .override(pic_size, pic_size)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(holder.goods_pic_iv);

                holder.one_buy_goods_name_tv.setText(map.get("goods_name"));

                holder.goods_integral_tv.setText(map.get("integral"));

                /*
                 * 是否可以使用代金券
                 * 使用多少优惠
                 */
                if (map.containsKey("ticket_buy_discount") && !map.get("ticket_buy_discount").equals("0")) {
                    holder.use_coupon_tv.setText("最多可使用" + map.get("ticket_buy_discount") + "%代金券");
                    holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                } else {
                    holder.use_coupon_tv.setText("不可使用代金券");
                    holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                }
                break;
        }
        if (0 != type) {
            L.e("ticket" + map.get("ticket_buy_discount"));
            if (map.containsKey("ticket_buy_discount") && !map.get("ticket_buy_discount").equals("0")) {
                holder.use_coupon_tv.setText("最多可使用" + map.get("ticket_buy_discount") + "%代金券");
                holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
            } else {
                holder.use_coupon_tv.setText("不可使用代金券");
                holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
            }
        }
        // type == 2,4,5的时候，设置倒计时
        if (2 == type || 4 == type || 5 == type) {
            // 当前时间戳
            long now = System.currentTimeMillis();
            // 结束时间戳
            long end;
            try {
                end = Long.parseLong(map.get("end_time"));
            } catch (NumberFormatException e) {
                end = 0;
            }
            holder.home_count_down_view.setTag(type + "countDown" + i);
            holder.home_count_down_view.start(end - now);
        }
        // 设置进度(商品进度，参团进度)
        if (2 == type || 4 == type || 6 == type) {
            // 最大值(库存)
            int max;
            try {
                if (2 == type) {// 限量购库存
                    max = Integer.parseInt(map.get("limit_store"));
                } else if (4 == type) {
                    max = Integer.parseInt(map.get("pre_store"));
                } else {// 需参团人数
                    max = Integer.parseInt(map.get("person_num"));
                }
            } catch (NumberFormatException e) {
                max = 0;
            }
            // 进度(已参团人数，销量)
            int sell_num;
            try {
                if (6 == type) {// 已参团人数
                    sell_num = Integer.parseInt(map.get("add_num"));
                } else {// 销量
                    sell_num = Integer.parseInt(map.get("sell_num"));
                }
            } catch (NumberFormatException e) {
                sell_num = 0;
            }
            // 设置进度
            holder.cpb_progresbar2.setMaxProgress(max);
            holder.cpb_progresbar2.setCurProgress(sell_num);
            // 计算百分比
            if (6 != type) {// 不是一元夺宝
                double d = sell_num * 100.0f / max;
                // 判断销量是否比库存大，大则设置百分比为100
                if (sell_num >= max) {
                    d = 100f;
                }
                // 保留两位小数
                String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                holder.preferential_type_tv.setText(str + "%");
            } else {// 一元夺宝(剩余人数)
                holder.add_num_tv.setText(String.valueOf(max - sell_num));
            }
        }
        // 商家广告,拼团购,一元夺宝(积分夺宝)没有原价
        if (0 != type && 3 != type && 6 != type) {
            // 原价
            holder.older_price_tv.setText("￥" + map.get("market_price"));
            holder.older_price_tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return view;
    }

    private class MellGAVH {
        /**
         * 广告
         */
        @ViewInject(R.id.image_for_mell)
        private ImageView image_for_mell;

        @ViewInject(R.id.image_for_mell1)
        private ImageView image_for_mell1;

        /**
         * 原价
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;

        /**
         * 倒计时(type=2,4,5)
         */
        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;
        /**
         * 进度条(type=2,4)
         */
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;

        /**
         * 国旗
         */
        @ViewInject(R.id.logo_for_country_iv)
        private ImageView logo_for_country_iv;
        /**
         * 是否使用代金券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;
        /**
         * 商品图片
         */
        @ViewInject(R.id.imageView)
        private ImageView imageView;
        /**
         * 商品名称
         */
        @ViewInject(R.id.mell_goods_name_tv)
        private TextView mell_goods_name_tv;
        /**
         * 现价
         */
        @ViewInject(R.id.now_price_tv)
        private TextView now_price_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.integral_tv)
        private TextView integral_tv;
        /**
         * 国旗
         */
        @ViewInject(R.id.item_country_logo_tv)
        private ImageView item_country_logo_tv;
        /**
         * 商品图片
         */
        @ViewInject(R.id.goods_pic_iv)
        private ImageView goods_pic_iv;

        /**
         * 商品名称
         */
        @ViewInject(R.id.item_goods_name_tv)
        private TextView item_goods_name_tv;
        /**
         * 商品现价
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.get_integral_tv)
        private TextView get_integral_tv;

        /**
         * 已抢购
         */
        @ViewInject(R.id.sold_num_tv)
        private TextView sold_num_tv;
        /**
         * 已抢购
         */
        @ViewInject(R.id.goods_num_already_tv)
        private TextView goods_num_already_tv;
        /**
         * 进度
         */
        @ViewInject(R.id.preferential_type_tv)
        private TextView preferential_type_tv;
        /**
         * 剩余人数
         */
        @ViewInject(R.id.add_num_tv)
        private TextView add_num_tv;


        @ViewInject(R.id.goods_name_tv)
        private TextView goods_name_tv;
        @ViewInject(R.id.goods_price_tv)
        private TextView goods_price_tv;
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
        /**
         * 已团。。。件
         */
        @ViewInject(R.id.group_already_tv)
        private TextView group_already_tv;

        /**
         * 一元夺宝 商品名称
         */
        @ViewInject(R.id.one_buy_goods_name_tv)
        private TextView one_buy_goods_name_tv;
        /**
         * 一元夺宝 积分
         */
        @ViewInject(R.id.goods_integral_tv)
        private TextView goods_integral_tv;

        /**
         * 一元夺宝 积分
         */
        @ViewInject(R.id.group_num)
        private TextView group_num;

        @ViewInject(R.id.showTyImg)
        private ImageView showTyImg;
    }

}
