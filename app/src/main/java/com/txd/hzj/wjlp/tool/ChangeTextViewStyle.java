package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_default_style), unit_position,
                    unit_position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style2), unit_position + 1,
                    end_posion, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            styledText.setSpan(new TextAppearanceSpan(context, R.style.text_default_style), unit_position, str.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style), unit_position + 1,
                    end_posion + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        if (str.contains("￥")) {
            unit_position = str.indexOf("￥");
            end_posion = str.indexOf(".");
            styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style), unit_position + 1,
                    end_posion + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        styledText.setSpan(new TextAppearanceSpan(context, R.style.goods_price_style2), 0,
                unit_position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }


}
