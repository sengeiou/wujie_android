package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.auction.AuctionPst;
import com.txd.hzj.wjlp.mellOnLine.dialog.AuctionSingUpDialog;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：下午 4:17
 * 描述：8-3拍品详情
 * ===============Txunda===============
 */
public class AuctionGoodsDetailsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private List<Map<String, String>> image;

    /**
     * 分红权
     */
    @ViewInject(R.id.auction_profit_num_tv)
    private TextView auction_profit_num_tv;

    /**
     * 当前价
     */
    @ViewInject(R.id.auction_price_tv)
    private TextView auction_price_tv;
    /**
     * 围观人数
     */
    @ViewInject(R.id.on_lookers_tv)
    private TextView on_lookers_tv;
    /**
     * 报名人数
     */
    @ViewInject(R.id.be_sign_up_tv)
    private TextView be_sign_up_tv;
    /**
     * 设置提醒人数
     */
    @ViewInject(R.id.set_remind_tv)
    private TextView set_remind_tv;

    /**
     * 起拍价
     */
    @ViewInject(R.id.left_tv_1)
    private TextView left_tv_1;
    /**
     * 加价幅度
     */
    @ViewInject(R.id.left_tv_2)
    private TextView left_tv_2;
    /**
     * 开拍时间
     */
    @ViewInject(R.id.left_tv_3)
    private TextView left_tv_3;
    /**
     * 保留价
     */
    @ViewInject(R.id.left_tv_4)
    private TextView left_tv_4;
    /**
     * 保证金
     */
    @ViewInject(R.id.righr_tv_1)
    private TextView righr_tv_1;
    /**
     * 拍卖佣金
     */
    @ViewInject(R.id.righr_tv_2)
    private TextView righr_tv_2;
    /**
     * 结束时间
     */
    @ViewInject(R.id.righr_tv_3)
    private TextView righr_tv_3;
    /**
     * 延时周期
     */
    @ViewInject(R.id.righr_tv_4)
    private TextView righr_tv_4;

    /**
     * 商家名称和评分
     */
    @ViewInject(R.id.mell_name_and_grade_tv)
    private TextView mell_name_and_grade_tv;

    /**
     * 出价id
     */
    @ViewInject(R.id.user_auction_id_tv)
    private TextView user_auction_id_tv;

    /**
     * 拍卖记录列表
     */
    @ViewInject(R.id.auction_info_lv)
    private ListViewForScrollView auction_info_lv;

    private List<Map<String, String>> auctionInfoList;

    private AuctionInfoAdapter auctionInfoAdapter;

    /**
     * 去报名
     */
    @ViewInject(R.id.sing_up_tv)
    private TextView sing_up_tv;
    /**
     * 竞拍出价(去报名)
     */
    private AuctionSingUpDialog singUpDialog;

    private EditText auction_price_ev;

    private AuctionPst auctionPst;

    private String auction_id = "";

    /**
     * 店铺logo
     */
    @ViewInject(R.id.auction_mell_logo_iv)
    private ImageView auction_mell_logo_iv;
    /**
     * 店铺logo大小
     */
    private int logoSize = 0;
    /**
     * 拍卖状态
     */
    @ViewInject(R.id.auction_status_tv)
    private TextView auction_status_tv;
    /**
     * 轮播图大小
     */
    private int bannerSize = 0;
    /**
     * 商品大小
     */
    @ViewInject(R.id.action_goods_name_tv)
    private TextView action_goods_name_tv;

    /**
     * 设置提醒
     */
    @ViewInject(R.id.remind_me_tv)
    private TextView remind_me_tv;
    /**
     * 是否提醒了
     * 1 提醒 0未提醒
     */
    private String is_remind = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("拍品详情");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_goods_details;
    }

    @Override
    protected void initialized() {
        auctionPst = new AuctionPst(this);
        auction_id = getIntent().getStringExtra("auction_id");
        image = new ArrayList<>();
        logoSize = ToolKit.dip2px(this, 50);
        bannerSize = Settings.displayWidth;
        auctionInfoList = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        auctionPst.auctionInfo(auction_id);
    }

    @Override
    @OnClick({R.id.sing_up_tv,R.id.remind_me_tv})
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sing_up_tv://报名
                singUpDialog = new AuctionSingUpDialog(this, new AuctionSingUpDialog.SignUpClick() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.submit_auction_price_tv:
                                L.e("=====金额=====", auction_price_ev.getText().toString());
                                singUpDialog.dismiss();
                                break;
                        }
                    }
                });
                singUpDialog.show();
                auction_price_ev = singUpDialog.findViewById(R.id.auction_price_ev);
                MoneyUtils.setPricePoint(auction_price_ev);
                break;
            case R.id.remind_me_tv:// 是否提醒
                if(is_remind.equals("1")){
                    showRightTip("您已设置提醒");
                    break;
                }
                auctionPst.remindMe(auction_id);
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("auctionInfo")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            // 商品图片轮播图
            if (ToolKit.isList(data, "goodsGallery")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goodsGallery"));
                forBanner();
            }
            // 商品其他信息
            Map<String, String> auctionInfo = JSONUtils.parseKeyAndValueToMap(data.get("auctionInfo"));
            action_goods_name_tv.setText(auctionInfo.get("goods_name"));
            // 积分
            ChangeTextViewStyle.getInstance().forTextColor(this, auction_profit_num_tv,
                    "积分" + auctionInfo.get("integral"), 2, Color.parseColor("#FD8214"));
            // 当前价
            ChangeTextViewStyle.getInstance().forAuctionPrice(this, auction_price_tv, "当前价 ￥" +
                    auctionInfo.get("now_price"));
            // 去报名(保证金额)
            ChangeTextViewStyle.getInstance().forSingUpWhite(this, sing_up_tv, "去报名\n(保证金金额￥" +
                    auctionInfo.get("base_money") + ")");
            // 围观人数
            String on_lookers = "围观 " + auctionInfo.get("click_num") + " 次";
            ChangeTextViewStyle.getInstance().forTextColor(this, on_lookers_tv, on_lookers, 3, 6,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 报名人数
            String be_sign_up = "报名 " + auctionInfo.get("apply_num") + " 人";
            ChangeTextViewStyle.getInstance().forTextColor(this, be_sign_up_tv, be_sign_up, 3, 4,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 设置提醒人数
            String set_remind = "设置提醒 " + auctionInfo.get("remind_num") + " 人";
            ChangeTextViewStyle.getInstance().forTextColor(this, set_remind_tv, set_remind, 5, 8,
                    ContextCompat.getColor(this, R.color.app_text_color));
            // 起拍价
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_1, "起拍价     ￥" +
                    auctionInfo.get("start_price"), 8, ContextCompat.getColor(this, R.color.gray_text_color));
            // 加价幅度
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_2, "加价幅度 ￥" +
                    auctionInfo.get("add_price"), 5, ContextCompat.getColor(this, R.color.gray_text_color));

            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_3, "开拍时间 " + auctionInfo.get("start_time"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_4, "保留价     " + auctionInfo.get
                            ("leave_price"), 8,
                    ContextCompat.getColor(this, R.color.gray_text_color));

            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_1, "保证金     ￥" + auctionInfo.get
                            ("base_money"), 8,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_2, "拍卖佣金 " + auctionInfo.get("commission"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_3, "结束时间 " + auctionInfo.get("end_time"), 5,
                    ContextCompat.getColor(this, R.color.gray_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_4, "延时周期 " + auctionInfo.get("delay_time")
                    + "分/次", 5, ContextCompat.getColor(this, R.color.gray_text_color));

            // 出价id
            ChangeTextViewStyle.getInstance().forTextColor(this, user_auction_id_tv, "您的出价ID: " + data.get("mybid"), 8,
                    ContextCompat.getColor(this, R.color.app_text_color));
            is_remind = auctionInfo.get("is_remind");
            if (is_remind.equals("1")) {
                remind_me_tv.setText("已提醒");
            } else {
                remind_me_tv.setText("提醒");
            }

            if (auctionInfo.get("is_running").equals("0")) {
                auction_status_tv.setText("正在进行 " + auctionInfo.get("end_true_time") + "结束");
            } else {
                auction_status_tv.setText("已结束");
            }

            Map<String, String> merchantInfo = JSONUtils.parseKeyAndValueToMap(data.get("merchantInfo"));
            // 竞拍详情(商家名称，评分)
            ChangeTextViewStyle.getInstance().forAuctionNameAndGrade(this, mell_name_and_grade_tv,
                    merchantInfo.get("merchant_name") + "\n评分" + merchantInfo.get("score") + "分");
            Glide.with(this).load(merchantInfo.get("logo"))
                    .override(logoSize, logoSize)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(auction_mell_logo_iv);
            if (ToolKit.isList(data, "auction_log")) {
                auctionInfoList = JSONUtils.parseKeyAndValueToMapList(data.get("auction_log"));
                auctionInfoAdapter = new AuctionInfoAdapter();
                auction_info_lv.setAdapter(auctionInfoAdapter);
            }
            return;
        }
        if(requestUrl.contains("remindMe")){
            is_remind = "1";
            remind_me_tv.setText("已提醒");
        }
    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(AuctionGoodsDetailsAty.this).load(image.get(position).get("path"))
                    .centerCrop()
                    .override(bannerSize, bannerSize)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };


    private class AuctionInfoAdapter extends BaseAdapter {
        private AuctionVh auctionVh;

        @Override
        public int getCount() {
            return auctionInfoList.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return auctionInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (null == view) {
                view = LayoutInflater.from(AuctionGoodsDetailsAty.this).inflate(R.layout.item_auction_info_lv, null);
                auctionVh = new AuctionVh();
                ViewUtils.inject(auctionVh, view);
                view.setTag(auctionVh);
            } else {
                auctionVh = (AuctionVh) view.getTag();
            }
            if (0 == i) {
                auctionVh.item_auction_name_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_id_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_price_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
                auctionVh.item_auction_time_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.colorAccent));
            } else {
                auctionVh.item_auction_name_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_id_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_price_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
                auctionVh.item_auction_time_tv.setTextColor(ContextCompat.getColor(AuctionGoodsDetailsAty.this,
                        R.color.gray_text_color));
            }
            auctionVh.item_auction_name_tv.setText(map.get("nickname"));
            auctionVh.item_auction_id_tv.setText(map.get("log_id"));
            auctionVh.item_auction_price_tv.setText(map.get("bid_price"));
            auctionVh.item_auction_time_tv.setText(map.get("bid_time"));

            return view;
        }

        class AuctionVh {
            /**
             * 出价人
             */
            @ViewInject(R.id.item_auction_name_tv)
            private TextView item_auction_name_tv;

            /**
             * 出价ID
             */
            @ViewInject(R.id.item_auction_id_tv)
            private TextView item_auction_id_tv;
            /**
             * 价格
             */
            @ViewInject(R.id.item_auction_price_tv)
            private TextView item_auction_price_tv;
            /**
             * 出价时间
             */
            @ViewInject(R.id.item_auction_time_tv)
            private TextView item_auction_time_tv;

        }
    }

}
