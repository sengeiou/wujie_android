package com.txd.hzj.wjlp.minetoAty.address.adapter;

import com.bigkoo.pickerview.adapter.WheelAdapter;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/22 0022
 * 时间：10:34
 * 描述：
 *
 */

public class AreaAdapter<T> implements WheelAdapter {

    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = 4;

    // items
    private List<T> items;
    // length
    private int length;

    public AreaAdapter(List<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    public AreaAdapter(List<T> items) {
        this(items, DEFAULT_LENGTH);
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }
}
