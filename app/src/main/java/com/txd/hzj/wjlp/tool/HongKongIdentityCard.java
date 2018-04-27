package com.txd.hzj.wjlp.tool;

import com.ants.theantsgo.util.L;

import java.util.List;

/**
 * 开发者：wjj
 * 创建时间：2018-04-27 15:25
 * 功能描述：验证香港证件号
 */
public class HongKongIdentityCard {

    private static String idCardNumStr;
    private static String[] header = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private static Object headerNumber;
    private static int checkCode;

    /**
     * 验证香港身份证件是否合法
     *
     * @param idCardStr 身份证号
     * @return true 合法， false 不合法
     */
    public static boolean validateHKCard(String idCardStr) {
        String card = idCardStr.replaceAll("^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$", "");
        Integer sum = 0;
        if (card.length() == 9) {
            sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9 + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
            card = card.substring(1, 9);
        } else {
            sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
        }
        String mid = card.substring(1, 7);
        String end = card.substring(card.length() - 2, card.length() - 1);
        char[] chars = mid.toCharArray();
        Integer iflag = 7;
        for (char c : chars) {
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        if (end.toUpperCase().equals("A")) {
            sum = sum + 10;
        } else {
            sum = sum + Integer.valueOf(end);
        }
        return (sum % 11 == 0) ? true : false;
    }
}
