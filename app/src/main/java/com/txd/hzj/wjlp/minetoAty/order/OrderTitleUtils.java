package com.txd.hzj.wjlp.minetoAty.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：11:51
 * 描述：
 * ===============Txunda===============
 */

public class OrderTitleUtils {

    // 定义私有构造方法（防止通过 new OrderTitleUtils()去实例化）
    private OrderTitleUtils() {
    }

    // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
    private static OrderTitleUtils instance;

    // 定义一个静态的方法（调用时再初始化OrderTitleUtils，使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
    public static synchronized OrderTitleUtils getInstance() {
        if (instance == null)
            instance = new OrderTitleUtils();
        return instance;
    }

    private String title1[] = {"全部", "待付款", "待发货", "待收货", "待评价"};
    private String title2[] = {"全部", "待付款", "待成团", "待发货", "待收货", "待评价"};
    private String title3[] = {"全部", "预购中", "待付尾款", "待发货", "待收货", "待评价"};
    private String title4[] = {"全部", "进行中", "已揭晓", "中奖记录"};
    private String title5[] = {"全部", "待付款", "办理手续中", "待评价"};

    public List<Map<String, String>> orderTitle1() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < title1.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title1[i]);
            map.put("type", String.valueOf(i));
            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> orderTitle2() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < title2.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title2[i]);
            map.put("type", String.valueOf(i));
            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> orderTitle3() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < title3.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title3[i]);
            map.put("type", String.valueOf(i));
            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> orderTitle4() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < title4.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title4[i]);
            map.put("type", String.valueOf(i));
            list.add(map);
        }
        return list;
    }
    public List<Map<String, String>> orderTitle5() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < title5.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", title5[i]);
            map.put("type", String.valueOf(i+1));
            list.add(map);
        }
        return list;
    }

}
