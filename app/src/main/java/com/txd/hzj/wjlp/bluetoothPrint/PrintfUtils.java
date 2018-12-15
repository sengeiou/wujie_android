package com.txd.hzj.wjlp.bluetoothPrint;

import android.content.Context;

import com.android.print.sdk.PrinterInstance;
import com.ants.theantsgo.gson.GsonUtil;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.tool.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：蓝牙打输出印工具类
 */
public class PrintfUtils {

    private static Context context;
    public static PrinterInstance printer;
    public final static int WIDTH_PIXEL = 384; // 像素长度

    static {
        context = DemoApplication.getApplication().getApplicationContext();
        printer = BluetoothUtils.printer;
    }

    /**
     * 打印数据
     *
     * @param dataJson 要打印的数据，Json格式
     */
    public static void printf_50MM(final JSONObject dataJson) {

        DemoApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                Util.showToastThread(context, "正在打印.....请稍后");

                // 打印时间
                try {
                    PrintfDemo printfDemo = GsonUtil.GsonToBean(dataJson.toString(), PrintfDemo.class);

                    printfLine(); // 打印分割线 --------------------------------
                    printfText(Util.getCurrentTime("yyyy年MM月dd日 HH:mm") + "_voodoo"); // 打印当前时间
                    printfLine(); // 打印分割线 --------------------------------
                    // 店铺名称还有已支付或者到付啥的
                    for (PrintfDemo.Q1Bean q1 : printfDemo.getQ1()) {
                        printCenteredAlign(q1.getDesc());
                    }
                    printfLine(); // 打印分割线 --------------------------------
                    // 下单时间以及备注了什么的
                    for (PrintfDemo.Q2Bean q2 : printfDemo.getQ2()) {
                        printfText(q2.getDesc());
                    }
                    printfLineWithContent(" 菜品 "); // 打印分割线 ----------- 菜品 ------------
                    for (PrintfDemo.GinfoBean ginfoBean : printfDemo.getGinfo()) {

                        printfText(ginfoBean.getGoods_name()); // 打印菜品名称
                        printfGinfoNumPrice(Integer.parseInt(ginfoBean.getGoods_num()), Float.valueOf(ginfoBean.getShop_price()));

                        printfEnter();
                    }
                    printfLineWithContent(" 其他费用 "); // 打印分割线 ----------- 其他费用 ------------
                    // 其他费用模块以及优惠
                    for (PrintfDemo.Q3Bean q3 : printfDemo.getQ3()) {
                        printfText(q3.getDesc());
                    }
                    printfRightAlign(printfDemo.getTotal_price()); // 右对齐的总价格
                    printfLine(); // 打印分割线 --------------------------------

                    printfBoldText(printfDemo.getReceiver_address()); // 地址
                    printfBoldText(printfDemo.getReceiver()); // 电话
                    printfBoldText(printfDemo.getReceiver_phone()); // 联系人

                    printfLineWithContent(" #" + printfDemo.getNum() + "完 ");

                    printfEnter(3); // 内容打印完成换四行好将单子撕下

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    // ==================================== 打印具体设置和操作 ====================================

    /**
     * 打印带内容的分割线
     *
     * @param content 显示的内容
     */
    private static void printfLineWithContent(String content) throws UnsupportedEncodingException {

        int stringPixLength = Util.getStringPixLength(content); // 获取打印文字像素长度
        int emptyPixelLength = (WIDTH_PIXEL - stringPixLength) / 2; // 计算空出像素的长度
        int spacesPixLength = Util.getStringPixLength("="); // 获取单个字符的像素长度
        int spacesNumber = emptyPixelLength / spacesPixLength; // 添加空格的数量

        // 添加---
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < spacesNumber; i++) {
            stringBuffer.append("-");
        }
        stringBuffer.append(content);
        for (int i = 0; i < spacesNumber; i++) {
            stringBuffer.append("-");
        }
        stringBuffer.append("\n");

        printer.sendByteData(getGbk(stringBuffer.toString()));
    }

    /**
     * 打印商品购买数量以及总价格
     *
     * @param number 购买数量
     * @param price  单价
     */
    private static void printfGinfoNumPrice(int number, float price) throws UnsupportedEncodingException {

        String numberStr = "x" + String.valueOf(number);
        String priceStr = new BigDecimal(price * number).setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 计算总价格结果保留两位小数

        StringBuffer stringBuffer = new StringBuffer();

        // 计算出前面添加空格的数量放置空格和要打印的文字
        int beforeSpacesNumber = ((WIDTH_PIXEL - Util.getStringPixLength(numberStr)) / 2) / Util.getStringPixLength(" ");
        for (int i = 0; i < beforeSpacesNumber; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(numberStr);

        // 计算出数量和价格之间的空格数并添加空格
        int afterSpacesNumber = (WIDTH_PIXEL - Util.getStringPixLength(stringBuffer.toString()) - Util.getStringPixLength(priceStr)) / Util.getStringPixLength(" ");
        for (int i = 0; i < afterSpacesNumber; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(priceStr);
        printfText(stringBuffer.toString());

    }

    /**
     * 打印分割线，注意不能太长，否则会自己换出一行进行打印，如果分割线短则可以添加两个线
     *
     * @throws UnsupportedEncodingException
     */
    private static void printfLine() throws UnsupportedEncodingException {
        printfText("--------------------------------");
    }

    /**
     * 打印换行
     *
     * @param lineNum 需要换行的行数
     */
    private static void printfEnter(int lineNum) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < lineNum; i++) {
            line.append(" \n");
        }
        printer.sendByteData(line.toString().getBytes());
    }

    /**
     * 换一行
     */
    private static void printfEnter() {
        printfEnter(1);
    }

    /**
     * 打印正常左对齐文字
     *
     * @param content 要打印的字符串
     */
    private static void printfText(String content) throws UnsupportedEncodingException {
        printer.sendByteData(getGbk(content + (content.contains("\n") ? "" : "\n")));
    }

    /**
     * 打印右对齐文字
     *
     * @param content 要打印的文字
     * @throws UnsupportedEncodingException
     */
    private static void printfRightAlign(String content) throws UnsupportedEncodingException {

        content = content + (content.contains("\n") ? "" : "\n");

        int stringPixLength = Util.getStringPixLength(content); // 获取打印文字像素长度
        int emptyPixelLength = WIDTH_PIXEL - stringPixLength; // 计算空出像素的长度
        int spacesPixLength = Util.getStringPixLength(" "); // 获取单个空格的像素长度
        int spacesNumber = emptyPixelLength / spacesPixLength; // 添加空格的数量

        // 添加空格
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < spacesNumber; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(content);

        printer.sendByteData(getGbk(stringBuffer.toString()));
    }

    /**
     * 打印居中文字
     *
     * @param content 要打印的文字
     * @throws UnsupportedEncodingException
     */
    private static void printCenteredAlign(String content) throws UnsupportedEncodingException {

        content = content + (content.contains("\n") ? "" : "\n");

        int stringPixLength = Util.getStringPixLength(content); // 获取打印文字像素长度
        int emptyPixelLength = (WIDTH_PIXEL - stringPixLength) / 2; // 计算空出像素的长度【居中将空像素平分】
        int spacesPixLength = Util.getStringPixLength(" "); // 获取单个空格的像素长度
        int spacesNumber = emptyPixelLength / spacesPixLength; // 添加空格的数量

        // 添加空格
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < spacesNumber; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(content);

        printfBoldText(stringBuffer.toString());
    }

    /**
     * 打印加粗模式的文字
     *
     * @param content 要打印的字符串
     */
    private static void printfBoldText(String content) throws UnsupportedEncodingException {
        byte[] boldOn = {27, 69, 1};
        printer.sendByteData(boldOn); // 加粗
        byte[] txtSizeBytes = {0x1b, 0x21, 20}; // 20表示字体大小
        printer.sendByteData(txtSizeBytes); // 设置字体大小

        printfText(content + (content.contains("\n") ? "" : "\n"));

        byte[] boldOff = {27, 69, 0};
        printer.sendByteData(boldOff); // 取消加粗
        byte[] txtSizeBytes1 = {0x1b, 0x21, 0};
        printer.sendByteData(txtSizeBytes1); // 还原字体大小

        printer.init();
    }

    /**
     * 将字符串编码转换为GBK
     *
     * @param content 要转换编码的内容
     * @return 转换后的Byte数组
     */
    public static byte[] getGbk(String content) throws UnsupportedEncodingException {
        return content.getBytes("GBK");
    }

}
