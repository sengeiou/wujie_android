package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
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
    /**
     * 修改商家商品价格样式
     *
     * @param context 上下文
     * @param tv      TextView
     * @param str     内容
     */
    public static void forMellProdect(Context context, TextView tv, String str) {
        SpannableString styledText = new SpannableString(str);
        int unit_position = 0;
        int end_posion = 0;
        if (str.contains("￥")) {
            L.e("=====有=====");
            unit_position = str.indexOf("￥");
            end_posion = str.indexOf(".");
            L.e(unit_position+"====="+end_posion);
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

}
