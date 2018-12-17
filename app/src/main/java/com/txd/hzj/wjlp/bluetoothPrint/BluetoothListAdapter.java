package com.txd.hzj.wjlp.bluetoothPrint;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：蓝牙列表适配器Adapter
 */
public class BluetoothListAdapter extends BaseAdapter {

    private Context context; // 上下文
    private List<BluetoothDevice> list; // 蓝牙设备列表

    public BluetoothListAdapter(Context context, List<BluetoothDevice> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_blurtooth, null);
            holder.item_blueName_tv = view.findViewById(R.id.item_blueName_tv);
            holder.item_blueAddress_tv = view.findViewById(R.id.item_blueAddress_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BluetoothDevice bluetoothDevice = list.get(i);

        String nameStr = bluetoothDevice.getName();
        String addressStr = bluetoothDevice.getAddress();
        holder.item_blueName_tv.setText((nameStr == null || nameStr.isEmpty()) ? addressStr : nameStr);
        holder.item_blueAddress_tv.setText(addressStr);

        return view;
    }

    class ViewHolder {
        TextView item_blueName_tv;
        TextView item_blueAddress_tv;
    }

}
