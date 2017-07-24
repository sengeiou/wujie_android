package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.RadiusBackgroundSpan;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：14:20
 * 描述：设置文本样式
 * ===============Txunda===============
 */

public class TextUtils {

    /**
     * 用在显示标题的时候在标题的开头加上标签
     *
     * @param context  上下文
     * @param textView 需要显示的view
     * @param tip      提示文字 标签
     * @param result   标题
     * @param color    背景颜色
     * @param radius   圆角半径
     */
    public static void titleTipUtils(Context context, TextView textView, String tip, String result, int color, int
            radius) {

        SpannableStringBuilder builder = new SpannableStringBuilder(tip);
        //构造文字背景圆角
        RadiusBackgroundSpan span = new RadiusBackgroundSpan(color, radius);
        builder.setSpan(span, 0, tip.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //构造标签文字大小
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(ToolKit.sp2px(context, 14));
        builder.setSpan(spanSize, 0, tip.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //构造title文字大小
        AbsoluteSizeSpan spanSizeLast = new AbsoluteSizeSpan(ToolKit.sp2px(context, 14));
        builder.setSpan(spanSizeLast, tip.length(), builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(builder);
        sb.append(result);

        textView.setText(sb);
    }

}
