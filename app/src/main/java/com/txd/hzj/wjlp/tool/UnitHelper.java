package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.util.TypedValue;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/29 20:29
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class UnitHelper {
    public static int dip2px(Context context, int dividerHeight) {
        int dip = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dividerHeight,
                context.getResources().getDisplayMetrics());
        return dip;
    }
}
