package com.ants.theantsgo.birthdayForChinese;
/*
* NEW一个Bazi类 传入时间可以得出生辰八字getbazi();
*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * 作者：DUKE_HwangZj整理，原作者刘座山
 * 日期：2017/9/28 0028
 * 时间：下午 4:05
 * 描述：361186482@qq.com(原作者邮箱)
 *
 */
public class Bazi {
    public static final String tg[] = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    public static final String dz[] = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    final static SolarTerm st = new SolarTerm();
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.CHINA);
    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    int year = 0, yue = 0;

    public Bazi(Date d0) {
        date = d0;
    }

    /**
     * 获取农历年 需要另加1900
     */
    private int getyear() throws Exception {

        Date lichun = st.JQtest(date.getYear() - 3)[21];
        long lctime = lichun.getTime();
        long nowtime = date.getTime();
        if (nowtime >= lctime) {
            year = date.getYear();
        } else {
            year = date.getYear() - 1;
        }
        return year;
    }

    /**
     * 取节气
     */
    static Date[] getjq(int year) throws Exception {
        Date d0[] = new Date[24];
        for (int i = 0; i < 24; i++) {

            if (i < 3) {
                d0[i] = st.JQtest(year - 1)[i + 21];
            } else {
                d0[i] = st.JQtest(year)[i - 3];
            }
        }
        return d0;
    }

    /**
     * 获取农历月份 0~11
     */
    private int getyue() throws Exception {
        int yue = 0;
        int year = getyear() + 1900;
        Date d0[] = getjq(year);
        long nowtime = date.getTime();
        for (int i = 0; i < 24; i++) {
            long jqtime = d0[i].getTime();
            if (nowtime > jqtime) {
                yue = i / 2;
            } else
                break;
        }
        return yue;
    }

    /**
     * 求年天干数
     */
    private int getyeartg() throws Exception {
        return (getyear() + 6) % 10;
    }

    /**
     * 求年地支数
     */
    private int getyeardz() throws Exception {
        return getyear() % 12;
    }

    /**
     * 获取年干支
     */
    private String getyeargz() throws Exception {
        String bz;
        bz = tg[getyeartg()] + dz[getyeardz()];
        return bz;
    }

    /**
     * 求月天干数
     */
    private int getyuetg() throws Exception {
        return (getyear() * 12 + getyue() + 4) % 10;
    }

    /**
     * 求月地支数
     */
    private int getyuedz() throws Exception {
        return (getyear() * 12 + getyue() + 2) % 12;
    }

    /**
     * 获取月八字
     */
    private String getyuegz() throws Exception {
        String gz;
        gz = tg[getyuetg()] + dz[getyuedz()];
        return gz;
    }

    /**
     * 日期距离1900天数
     */
    private int tian2(Date date) throws Exception {
        Date date2 = df.parse("1900-1-1 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 求日天干数
     */
    private int getritg() throws Exception {
        int shi = date.getHours();
        if (shi == 23) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        }
        return tian2(date) % 10;
    }

    /**
     * 求日地支数
     */
    private int getridz() throws Exception {
        int shi = date.getHours();
        if (shi == 23) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        }
        return (tian2(date) + 10) % 12;
    }

    /**
     * 获取日八字
     */
    private String getrigz() throws Exception {
        String gz;
        gz = tg[getritg()] + dz[getridz()];
        return gz;
    }

    /**
     * 求时间天干数
     */
    private int getshitg() throws Exception {
        int shi = date.getHours();
        shi = (shi + 1) / 2;
        return (shi + tian2(date) * 12) % 10;
    }

    /**
     * 求时间地支数
     */
    private int getshidz() throws Exception {
        int shi = date.getHours();
        shi = (shi + 1) / 2;
        return shi % 12;
    }

    /**
     * 获取时间八字
     */
    private String getshigz() throws Exception {
        String gz;
        gz = tg[getshitg()] + dz[getshidz()];
        return gz;
    }

    /**
     * 获取完整八字
     */
    public String getbazi() throws Exception {
        return getyeargz() + "年" + getyuegz() + "月" + getrigz() + "日" + getshigz() + "时";
    }

    /**
     * 获取八字代数
     */
    public int[] getbazishu() throws Exception {
        int bzs[] = new int[8];
        bzs[0] = getyeartg();
        bzs[1] = getyeardz();
        bzs[2] = getyuetg();
        bzs[3] = getyuedz();
        bzs[4] = getritg();
        bzs[5] = getridz();
        bzs[6] = getshitg();
        bzs[7] = getshidz();
        return bzs;
    }
}
