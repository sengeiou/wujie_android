package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：17:34
 * 描述：修改TextView的字体样式
 * ===============Txunda===============
 */
public class ChangeTextViewStyle {

    private static ChangeTextViewStyle instance;

    private ChangeTextViewStyle() {
    }

    public static synchronized ChangeTextViewStyle getInstance() {
        if (instance == null) {
            instance = new ChangeTextViewStyle();
        }
        return instance;
    }

    /**
     * 修改商家商品价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forMellProdect(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        int end_posion;
        if (str.contains("￥")) {
            unit_position = str.indexOf("￥");
            end_posion = str.indexOf(".");
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_default_style), unit_position, unit_position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style2), unit_position + 1, end_posion, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_default_style), unit_position, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 修改商品详情价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forGoodsPrice(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position;
        int end_posion;
        if (str.contains("￥")) {
            unit_position = str.indexOf("￥");
            end_posion = str.indexOf(".");
            if (end_posion < 0) {
                end_posion = str.length();
            }
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style), unit_position + 1, end_posion + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 修改商品详情价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forGoodsPrice24(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position;
        int end_posion;
        if (str.contains("￥") && str.contains(".")) {
            unit_position = str.indexOf("￥");
            end_posion = str.indexOf(".");
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style), unit_position + 1, end_posion + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 修改字体颜色
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     * @param start   起始位置
     * @param color   颜色
     */
    public void forTextColor(Context context, TextView tv, String str, int start, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
        builder.setSpan(redSpan, start, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 修改字体颜色
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     * @param start   起始位置
     * @param color   颜色
     */
    public void forTextColor(Context context, TextView tv, String str, int start, int end, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
        builder.setSpan(redSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 修改字体颜色,上标(上标字体为默认字体的一半)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     * @param start   起始位置
     * @param color   颜色
     */
    public void forTextColorSub(Context context, TextView tv, String str, int start, int color) {
        SpannableString msp = new SpannableString(str);
        int len = str.length();
        msp.setSpan(new ForegroundColorSpan(color), start, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        msp.setSpan(new SuperscriptSpan(), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new RelativeSizeSpan(0.5f), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        tv.setText(msp);
    }

    /**
     * 修改字体颜色,上标(上标字体为默认字体的一半)
     *
     * @param tv  TextView
     * @param str 内容
     */
    public void forFeeStyle(TextView tv, String str) {
        SpannableString msp = new SpannableString(str);
        int len = str.length();
        msp.setSpan(new SuperscriptSpan(), len - 3, len - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new RelativeSizeSpan(0.5f), len - 3, len - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        tv.setText(msp);
    }

    /**
     * 修改商品详情商家描述(换行)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forGoodsLineFeed(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        unit_position = str.indexOf("\n");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style2), 0, unit_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 修改商品详情商家描述(换行)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forAuctionNameAndGrade(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        unit_position = str.indexOf("\n");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.auction_name_grage), 0, unit_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 拍品详情--去报名(换行)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forSingUpWhite(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        unit_position = str.indexOf("\n");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style_white), 0, unit_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 拍品详情--去报名(换行)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forCartPrice(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = str.indexOf("\n");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.cart_goods_price_style), unit_position, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 拍品详情--去报名(换行)
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forTabText(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = str.indexOf("\n");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.tab_text_style), 0, unit_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 拍品详情--当前价
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forAuctionPrice(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        unit_position = str.indexOf("￥");
        int len = str.length();
        styledText.setSpan(new TextAppearanceSpan(context, R.style.auction_price_style_18), unit_position, unit_position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.auction_price_style_24), unit_position + 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 订单列表价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forOrderPrice(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position;
        unit_position = str.indexOf("￥");
        int point_position;
        point_position = str.indexOf(".");
        styledText.setSpan(new TextAppearanceSpan(context, R.style.sign_in_tip_style), unit_position + 1, point_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 订单列表价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forOrderPrice2(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position;
        unit_position = str.indexOf("￥");
        int point_position;
        point_position = str.indexOf(".");
        int len = str.length();
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_for_member), unit_position, unit_position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_for_member2), unit_position + 1, point_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_for_member), point_position, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 会员等级
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public void forMemberGrade(Context context, TextView tv, String str, int pos) {
        SpannableString styledText = new SpannableString(str);
        int len = str.length();
        styledText.setSpan(new TextAppearanceSpan(context, R.style.style_for_member), pos, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }

}
