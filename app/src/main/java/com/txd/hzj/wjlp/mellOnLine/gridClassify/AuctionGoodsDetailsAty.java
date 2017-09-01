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
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.dialog.AuctionSingUpDialog;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Integer> image;

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

    private List<String> auctionInfo;

    private AuctionInfoAdapter auctionInfoAdapter;

    /**
     * 去报名
     */
    @ViewInject(R.id.sing_up_tv)
    private TextView sing_up_tv;



    private AuctionSingUpDialog singUpDialog;

    private EditText auction_price_ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("拍品详情");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();
        forText();
        auction_info_lv.setAdapter(auctionInfoAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_goods_details;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        auctionInfo = new ArrayList<>();
        auctionInfoAdapter = new AuctionInfoAdapter();
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.sing_up_tv})
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sing_up_tv://报名
                singUpDialog = new AuctionSingUpDialog(this, new AuctionSingUpDialog.SignUpClick() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.submit_auction_price_tv:
                                L.e("=====金额=====",auction_price_ev.getText().toString());
                                singUpDialog.dismiss();
                                break;
                        }
                    }
                });
                singUpDialog.show();
                auction_price_ev = singUpDialog.findViewById(R.id.auction_price_ev);
                MoneyUtils.setPricePoint(auction_price_ev);
                break;
        }
    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setPageCount(image.size());
        online_carvouse_view.setImageListener(imageListener);
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(image.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    private void forText() {
        // 分红权
        ChangeTextViewStyle.getInstance().forTextColor(this, auction_profit_num_tv,
                "积分10.23", 3, Color.parseColor("#FD8214"));

        ChangeTextViewStyle.getInstance().forAuctionPrice(this, auction_price_tv, "当前价 ￥8000.00");

        // 去报名
        ChangeTextViewStyle.getInstance().forSingUpWhite(this, sing_up_tv, "去报名\n(保证金金额￥500)");

        String on_lookers = "围观 633 次";
        ChangeTextViewStyle.getInstance().forTextColor(this, on_lookers_tv, on_lookers, 3, 6,
                ContextCompat.getColor(this, R.color.app_text_color));
        String be_sign_up = "报名 0 人";
        ChangeTextViewStyle.getInstance().forTextColor(this, be_sign_up_tv, be_sign_up, 3, 4,
                ContextCompat.getColor(this, R.color.app_text_color));
        String set_remind = "设置提醒 15 人";
        ChangeTextViewStyle.getInstance().forTextColor(this, set_remind_tv, set_remind, 5, 8,
                ContextCompat.getColor(this, R.color.app_text_color));

        ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_1, "起拍价     ￥8000", 8,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_2, "加价幅度 ￥500", 5,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_3, "开拍时间 06月15日 09:00", 5,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, left_tv_4, "保留价     无", 8,
                ContextCompat.getColor(this, R.color.gray_text_color));

        ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_1, "保证金     ￥500", 8,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_2, "拍卖佣金 无", 5,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_3, "结束时间 06月15日 21:00", 5,
                ContextCompat.getColor(this, R.color.gray_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, righr_tv_4, "延时周期 5分/次", 5,
                ContextCompat.getColor(this, R.color.gray_text_color));
        // 竞拍详情(商家名称，评分)
        ChangeTextViewStyle.getInstance().forAuctionNameAndGrade(this, mell_name_and_grade_tv, "ZARA\n评分4.5分");
        // 出价id
        ChangeTextViewStyle.getInstance().forTextColor(this, user_auction_id_tv, "您的出价ID: 321860", 8,
                ContextCompat.getColor(this, R.color.app_text_color));
    }

    private class AuctionInfoAdapter extends BaseAdapter {
        private AuctionVh auctionVh;

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return auctionInfo.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
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
