package com.txd.hzj.wjlp.catchDoll.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.txd.hzj.wjlp.catchDoll.view.BarrageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：工具类
 */
public class Util {

    /**
     * 将时间戳转换为时间字符串
     *
     * @param millis  毫秒时间戳
     * @param pattern 指定格式的字符串
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * dip转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 屏幕的宽
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 屏幕的高
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 判断当天是否是首次启动
     *
     * @return true:当天首次启动APP false:当天已启动过
     */
    public static boolean isTodayFastStart(Context context) {
        String keyStr = "startTime";

        String lastDate = SharedPreferencesUtils.getString(context, keyStr); // 获取上次启动日期
        String currentDate = millis2String(System.currentTimeMillis(), "yyyyMMdd"); // 获取当天日期

        if (lastDate == null || lastDate.isEmpty()) { // 上次启动日期为空，则返回是当天首次启动
            SharedPreferencesUtils.putString(context, keyStr, currentDate);
            return true;
        }
        if (!lastDate.equals(currentDate)) { // 如果上次启动日期和本次启动日期不一致，则返回是当天首次启动
            SharedPreferencesUtils.putString(context, keyStr, currentDate);
            return true;
        }

        return false; // 否则直接返回false
    }

    /**
     * 判断当天是否是首次启动
     *
     * @return true:首次启动APP false:已经启动过
     */
    public static boolean isFastStart(Context context) {
        String keyStr = "fastStart";
        // 设置默认值为true,如果未启动过则没有此key，返回为默认值
        boolean fastStart = SharedPreferencesUtils.getBoolean(context, keyStr, true);
        if (fastStart) { // 如果是首次启动，则将首选项的值改为false
            SharedPreferencesUtils.putBoolean(context, keyStr, false);
        }
        return fastStart;
    }

    /**
     * 生成0到(maxNumber-1)以内的随机数（不包括指定数字）
     *
     * @param maxNumber 最大数
     * @return 生成的数字
     */
    public static int getRandomNumber(int maxNumber) {
        return new Random().nextInt(maxNumber);
    }

    /**
     * 毫秒转换成时分秒
     *
     * @param millisecond 毫秒
     * @param showHour    是否显示小时
     * @return
     */
    public static String millisecond2Time(long millisecond, boolean showHour) {
        int countSecond = (int) (millisecond / 1000); // 将毫秒转换成秒
        int hour = countSecond / 3600; // 获取小时数 =秒数/3600
        int minute = (countSecond - hour * 3600) / 60; // 获取分钟数 =剩余秒数/60 剩余秒数 = 总秒数-小时数*3600
        int second = countSecond - hour * 3600 - minute * 60; // 获取秒数
        String timeStr;
        if (showHour) {
            timeStr = new StringBuffer().append(hour <= 9 ? "0" : "").append(hour).append(":").append(minute <= 9 ? "0" : "").append(minute).append(":").append(second <= 9 ? "0" : "").append(second).toString();
        } else {
            timeStr = new StringBuffer().append(minute <= 9 ? "0" : "").append(minute).append(":").append(second <= 9 ? "0" : "").append(second).toString();
        }
        return timeStr;
    }

    /**
     * 添加展示弹幕
     *
     * @param context     上下文
     * @param barrageView 要添加弹幕的目标View
     * @param msgList         要展示的消息集合
     */
    public static void showBarrage(final Context context, final BarrageView barrageView, final List<String> msgList) {
        if (barrageView == null || msgList == null || msgList.size() < 1) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final String msgStr : msgList) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            barrageView.addBarrageItemView(context, msgStr);
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
