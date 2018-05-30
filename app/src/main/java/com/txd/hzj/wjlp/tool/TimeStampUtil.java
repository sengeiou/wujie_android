package com.txd.hzj.wjlp.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建人： Txd_Cjh
 * 创建时间： 2017/11/29 13:17
 * 功能描述：字符串时间戳相互转换
 * 联系方式：jiahuanchen@126.com
 */

public class TimeStampUtil {
    /**
     * 将字符串转换成时间戳
     *
     * @param user_time 年月
     * @return
     */
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 将字符串转换为时间戳
     *
     * @param user_time 年月日
     * @return
     */
    public static String getTimeTwo(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 将字符串转换为时间戳
     *
     * @param user_time 年月日
     * @return
     */
    public static String getTimeFour(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, str.length() >= 10 ? 10 : str.length());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 将字符串转换为时间戳
     *
     * @param user_time 年月日
     * @return
     */
    public static String getTimeThree(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月日
     * @return
     */
    public static String getStrTimeTwo(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月日
     * @return
     */
    public static String getStrTimeThree(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月日
     * @return
     */
    public static String getStrTimeFour(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月日
     * @return
     */
    public static String getStrTimeFive(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param cc_time 年月日
     * @return
     */
    public static String getStrTimeSix(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

}
