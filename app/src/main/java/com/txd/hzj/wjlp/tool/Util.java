package com.txd.hzj.wjlp.tool;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.txd.hzj.wjlp.DemoApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：工具类
 */
public class Util {

    private static Toast toast;

    /**
     * UI提示Toast方法
     *
     * @param context 上下文
     * @param msg     提示消息
     */
    public static void showToast(Context context, String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 非UI提示Toast方法
     *
     * @param context
     * @param msg
     */
    public static void showToastThread(final Context context, final String msg) {
        DemoApplication.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    /**
     * 旋转
     *
     * @param view  要旋转的View
     * @param start 起始角度
     * @param end   终止角度
     */
    public static void rotate(View view, float start, float end) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", start, end);
        anim.setDuration(100);
        anim.start();
    }

    /**
     * 获取字符串的像素长度
     *
     * @param str 字符串
     * @return 长度
     */
    public static int getStringPixLength(String str) {
        int pixLength = 0;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Pinyin.isChinese(c)) {
                pixLength += 24;
            } else {
                if (String.valueOf(c).matches("[^\\x00-\\xff]")) {
                    pixLength += 24; // 全角
                } else {
                    pixLength += 12; // 半角
                }
            }
        }
        return pixLength;
    }

    /**
     * 获取当前时间
     *
     * @return 格式化后的时间字符串
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm");
    }

    /**
     * 获取当前时间
     *
     * @return 格式化后的时间字符串
     */
    public static String getCurrentTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    /**
     * 判断是否存在关键字
     * @param map
     * @param key
     * @param defaultStr
     * @return
     */
    public static String judgexistkey(Map map,Object key,String defaultStr){
        if (map.containsKey(key)){
            return String.valueOf(map.get(key));
        }
        return defaultStr;
    }

}
