package com.txd.hzj.wjlp.melloffLine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodsEvaluateAty;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.view.RatingBar;

import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：上午 10:07
 * 描述：线下商家详情
 *
 */
public class OffLineMellInfoAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.center_tv)
    private TextView center_tv;

    private int type = 0;

    @ViewInject(R.id.other_info_layout)
    private LinearLayout other_info_layout;

    private MerchantPst merchantPst;
    private String merchant_id = "";
    private String is_collect = "";

    /*@ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;*/

    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;
    private UserCollectPst collectPst;

    @ViewInject(R.id.total_tv)
    private TextView total_tv;
    @ViewInject(R.id.score_tv)
    private TextView score_tv;
    @ViewInject(R.id.nick_name_tv)
    private TextView nick_name_tv;
    @ViewInject(R.id.create_time_tv)
    private TextView create_time_tv;
    @ViewInject(R.id.other_info_tv)
    private TextView other_info_tv;

    @ViewInject(R.id.use_head_iv)
    private ShapedImageView use_head_iv;

    private int size = 0;
    private String merchant_name;

    @ViewInject(R.id.mell_score_rating_bar)
    private RatingBar mell_score_rating_bar;
    private Bundle bundle;
    private String goods_id;

    private String share_img;
    private String share_url;
    private String share_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式
        showStatusBar(R.id.mell_info_title_layout);
        if (0 == type) {
            center_tv.setText("好收成超市");
            other_info_layout.setBackgroundColor(Color.parseColor("#83CA9D"));
        } else {
            center_tv.setText(merchant_name);
            other_info_layout.setBackgroundColor(Color.parseColor("#2E94FF"));
        }
    }

    @Override
    @OnClick({R.id.check_all_comment_tv, R.id.mell_reported_tv, R.id.mell_aptitude_tv,R.id.off_line_mell_share_tv,
            R.id.goods_title_collect_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_all_comment_tv:
                bundle = new Bundle();
                bundle.putInt("from", 2);
                bundle.putString("mid", merchant_id);
                bundle.putString("goods_id", "");
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            case R.id.mell_reported_tv:// 举报商家
                bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("merchant_id", merchant_id);
                startActivity(MellReportedAty.class, bundle);
                break;
            case R.id.mell_aptitude_tv:// 商家资质
                bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("merchant_id", merchant_id);
                startActivity(MellAptitudeAty.class, bundle);
                break;
            case R.id.goods_title_collect_layout://收藏,取消收藏
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                if (is_collect.equals("0")) {
                    collectPst.addCollect("2", merchant_id);
                    break;
                }
                collectPst.delOneCollect("2", merchant_id);
                break;
            case R.id.off_line_mell_share_tv:

//                http://www.wujiemall.com/Wap/Merchant/merInfo/merchant_id/4.html
//                店铺详情
                share_url = Config.OFFICIAL_WEB + "Wap/Merchant/merInfo/merchant_id/"+merchant_id+ ".html";
                /**
                 * 1 商品 2商家 3书院 4红包 5其他(个人中心)
                 */
                toShare("无界优品", share_img, share_url, share_content, merchant_id, "2");
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_off_line_mell_info;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 0);
        merchantPst = new MerchantPst(this);
        collectPst = new UserCollectPst(this);
        merchant_id = getIntent().getStringExtra("merchant_id");
        merchant_name = getIntent().getStringExtra("merchant_name");
        size = ToolKit.dip2px(this, 60);
    }

    @Override
    protected void requestData() {
        if (1 == type) {
            merchantPst.merInfo(merchant_id);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        L.e(jsonStr);

        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("merInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            is_collect = data.get("is_collect");

            share_img = data.get("share_img");
            share_url = data.get("share_url");
            share_content = data.get("share_content");


            if ("0".equals(is_collect)) {
                goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
                goods_title_collect_tv.setText("收藏");
            } else {
                goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
                goods_title_collect_tv.setText("已收藏");
            }

            Map<String, String> mer_comment = JSONUtils.parseKeyAndValueToMap(data.get("mer_comment"));
            total_tv.setText(mer_comment.get("total"));
            score_tv.setText(mer_comment.get("score"));

            if (ToolKit.isList(mer_comment, "one_comment")) {
                Map<String, String> one_comment = JSONUtils.parseKeyAndValueToMap(mer_comment.get("one_comment"));

                goods_id = one_comment.get("goods_id");

                Glide.with(this).load(one_comment.get("head_pic"))
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(use_head_iv);

                float rat;
                try {
                    rat = Float.parseFloat(one_comment.get("all_star"));
                } catch (NumberFormatException e) {
                    rat = 0f;
                }
                mell_score_rating_bar.setStar(rat);

                nick_name_tv.setText(one_comment.get("nickname"));

                create_time_tv.setText(one_comment.get("create_time"));

                Map<String, String> mer_info = JSONUtils.parseKeyAndValueToMap(data.get("mer_info"));
                other_info_tv.setText("商品数量：" + mer_info.get("goods_total")
                        + "件\n月销单量：" + mer_info.get("goods_month_num")
                        + "件\n关注人数：" + mer_info.get("view_num")
                        + "人\n营业时间：" + mer_info.get("open_time") + "\n门店地址：" + mer_info.get("address") +
                        "\n门店电话：" + mer_info.get("phone"));

            }

            return;
        }
        if (requestUrl.contains("addCollect")) {// 添加收藏
            showRightTip("收藏成功");
            is_collect = "1";
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            goods_title_collect_tv.setText("已收藏");
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            goods_title_collect_tv.setText("收藏");
        }
    }
}
