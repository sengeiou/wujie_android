package com.txd.hzj.wjlp.bluetoothPrint;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.PreferencesUtils;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.Util;
import com.txd.hzj.wjlp.view.ScrollListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class SearchBluetoothAty extends BaseAty implements BluetoothUtils.BluetoothChangLister, BluetoothUtils.ConnectSuccess {

    @ViewInject(R.id.connectBluetooth_goback_imgv)
    public ImageView connectBluetooth_goback_imgv;
    @ViewInject(R.id.connectBluetooth_title_tv)
    public TextView connectBluetooth_title_tv;
    @ViewInject(R.id.connectBluetooth_right_tv)
    public TextView connectBluetooth_right_tv;
    @ViewInject(R.id.connectBluetooth_change_tv)
    public TextView connectBluetooth_change_tv; // 修改蓝牙名称
    @ViewInject(R.id.connectBluetooth_name_tv)
    public TextView connectBluetooth_name_tv; // 蓝牙名称
    @ViewInject(R.id.connectBluetooth_address_tv)
    public TextView connectBluetooth_address_tv; // 蓝牙地址
    @ViewInject(R.id.connectBluetooth_alreadyPaired_llayout)
    public LinearLayout connectBluetooth_alreadyPaired_llayout; // 已配对标题
    @ViewInject(R.id.connectBluetooth_alreadyPaired_imgv)
    public ImageView connectBluetooth_alreadyPaired_imgv; // 已配对列表三角
    @ViewInject(R.id.connectBluetooth_alreadyPaired_slv)
    public ScrollListView connectBluetooth_alreadyPaired_slv; // 已配对列表
    @ViewInject(R.id.connectBluetooth_nearby_llayout)
    public LinearLayout connectBluetooth_nearby_llayout; // 附近列表标题
    @ViewInject(R.id.connectBluetooth_nearby_imgv)
    public ImageView connectBluetooth_nearby_imgv; // 附近蓝牙列表三角
    @ViewInject(R.id.connectBluetooth_nearby_slv)
    public ScrollListView connectBluetooth_nearby_slv; // 附近列表

    private Set<BluetoothDevice> nearbyBluetoothSet;
    private List<BluetoothDevice> nearbyBluetoothDevices;
    private List<BluetoothDevice> readyBluetoothDevices;
    private BluetoothListAdapter nearbyListAdapter;
    private BluetoothListAdapter readyListAdapter;

    private Context context;
    private boolean isSearching = false; // 是否正在搜索
    private boolean readyListIsOpen = false; // 已配对的列表是否打开
    private boolean nearbyListIsOpen = false; // 附近蓝牙列表是否打开
    private BluetoothUtils bluetoothUtils;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_bluetooth;
    }

    @Override
    protected void initialized() {
        connectBluetooth_title_tv.setText("连接蓝牙打印机");
        connectBluetooth_right_tv.setText("搜索");
        connectBluetooth_right_tv.setVisibility(View.VISIBLE);
        context = this;
        nearbyBluetoothDevices = new ArrayList<>();
        readyBluetoothDevices = new ArrayList<>();
        nearbyListAdapter = new BluetoothListAdapter(context, nearbyBluetoothDevices);
        readyListAdapter = new BluetoothListAdapter(context, readyBluetoothDevices);

        bluetoothUtils = BluetoothUtils.getInstance(this);
        bluetoothUtils.setBluetoothChangLister(this);
        bluetoothUtils.setOnConnectSuccess(this);
        // 实例化蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 如果蓝牙没有开启则先打开蓝牙
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }

        starSearchBlue(); // 进来展示的时候直接搜索

        // 设置已连接的蓝牙名称以及地址
        connectBluetooth_name_tv.setText("名称：" + (bluetoothUtils.isConnect() ? PreferencesUtils.getBluetoothName(context) : "无"));
        connectBluetooth_address_tv.setText("地址：" + (bluetoothUtils.isConnect() ? PreferencesUtils.getBluetoothAddress(context) : "无"));

        // 设置已配对蓝牙列表
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice devices : bondedDevices) {
            readyBluetoothDevices.add(devices);
        }
        readyListAdapter.notifyDataSetChanged();
        if (readyBluetoothDevices.size() > 0 && !readyListIsOpen) {
            openList(connectBluetooth_alreadyPaired_slv, connectBluetooth_alreadyPaired_imgv);
        }
        // 设置两个列表的适配器和列表点击事件
        connectBluetooth_alreadyPaired_slv.setAdapter(readyListAdapter);
        connectBluetooth_alreadyPaired_slv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice bluetoothDevice = nearbyBluetoothDevices.get(i);
                connectBluetooth(bluetoothDevice);
            }
        });
        connectBluetooth_nearby_slv.setAdapter(nearbyListAdapter);
        connectBluetooth_nearby_slv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice bluetoothDevice = nearbyBluetoothDevices.get(i);
                connectBluetooth(bluetoothDevice);
            }
        });
    }

    /**
     * 连接蓝牙
     *
     * @param device 蓝牙设备
     */
    private void connectBluetooth(final BluetoothDevice device) {
        if (isSearching) {
            stopSearchBlue();
        }
        DemoApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                bluetoothUtils.openPrinter(device);
            }
        });
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.connectBluetooth_goback_imgv, R.id.connectBluetooth_right_tv, R.id.connectBluetooth_change_tv, R.id.connectBluetooth_alreadyPaired_llayout, R.id.connectBluetooth_nearby_llayout})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connectBluetooth_goback_imgv:
                finish();
                break;
            case R.id.connectBluetooth_right_tv: // 搜索停止
                if (isSearching) {
                    stopSearchBlue();
                } else {
                    starSearchBlue();
                }
                break;
            case R.id.connectBluetooth_change_tv: // 修改蓝牙名称
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View inputView = LayoutInflater.from(context).inflate(R.layout.view_change_bluetooth_name, null);
                final EditText changeName_input_et = inputView.findViewById(R.id.changeName_input_et);
                final TextView changeName_change_tv = inputView.findViewById(R.id.changeName_change_tv);
                final TextView changeName_cancel_tv = inputView.findViewById(R.id.changeName_cancel_tv);
                changeName_input_et.setText(PreferencesUtils.getBluetoothName(context));
                builder.setView(inputView);
                final AlertDialog alertDialog = builder.create();
                changeName_change_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newName = changeName_input_et.getText().toString().trim();
                        if (newName.isEmpty()) {
                            Util.showToast(context, "请输入名称");
                        } else {
                            bluetoothUtils.changeBlueName(newName);
                        }
                        alertDialog.dismiss();
                    }
                });
                changeName_cancel_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
            case R.id.connectBluetooth_alreadyPaired_llayout: // 已配对列表标题点击
                if (readyListIsOpen) {
                    closeList(connectBluetooth_alreadyPaired_slv, connectBluetooth_alreadyPaired_imgv);
                } else {
                    openList(connectBluetooth_alreadyPaired_slv, connectBluetooth_alreadyPaired_imgv);
                }
                break;
            case R.id.connectBluetooth_nearby_llayout: // 附近的蓝牙列表点击
                if (nearbyListIsOpen) {
                    closeList(connectBluetooth_nearby_slv, connectBluetooth_nearby_imgv);
                } else {
                    openList(connectBluetooth_nearby_slv, connectBluetooth_nearby_imgv);
                }
                break;
        }
    }



    /**
     * 搜索蓝牙打印机
     */
    private void starSearchBlue() {
        bluetoothUtils.search(response);
    }

    /**
     * 停止搜索蓝牙设备
     */
    private void stopSearchBlue() {
        bluetoothUtils.stopSearch();
    }

    /**
     * 搜索蓝牙响应事件
     */
    SearchResponse response = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            // 开始搜索
            connectBluetooth_right_tv.setText("停止");
            isSearching = true;
            Util.showToast(context, "正在搜索附近的打印机设备....");
            nearbyBluetoothSet = new HashSet<>();
            nearbyBluetoothDevices.clear();
            nearbyListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onDeviceFounded(SearchResult device) {
            // 搜索到设备
            BluetoothDevice bluetoothDevice = device.device;
            if (bluetoothDevice.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.IMAGING) {
                boolean add = nearbyBluetoothSet.add(bluetoothDevice);
                if (add) {
                    nearbyBluetoothDevices.add(bluetoothDevice);
                    nearbyListAdapter.notifyDataSetChanged();
                    if (!nearbyListIsOpen) {
                        openList(connectBluetooth_nearby_slv, connectBluetooth_nearby_imgv);
                    }
                }
            }
        }

        @Override
        public void onSearchStopped() {
            // 停止搜索
            connectBluetooth_right_tv.setText("搜索");
            isSearching = false;
            Util.showToast(context, "搜索完成");
            nearbyBluetoothSet.clear();
            nearbyBluetoothSet = null;
        }

        @Override
        public void onSearchCanceled() {
            // 搜索取消
            connectBluetooth_right_tv.setText("搜索");
            isSearching = false;
            Util.showToast(context, "搜索取消....");
            nearbyBluetoothSet.clear();
            nearbyBluetoothSet = null;
        }
    };

    /**
     * 打开显示列表
     *
     * @param listView  列表View
     * @param imageView 要旋转的图片
     */
    private void openList(ScrollListView listView, ImageView imageView) {
        listView.setVisibility(View.VISIBLE);
        Util.rotate(imageView, 0, 90);
        switch (listView.getId()) {
            case R.id.connectBluetooth_alreadyPaired_slv:
                readyListIsOpen = true;
                break;
            case R.id.connectBluetooth_nearby_slv:
                nearbyListIsOpen = true;
                break;
        }
    }

    /**
     * 关闭显示列表
     *
     * @param listView  列表View
     * @param imageView 要旋转的图片
     */
    private void closeList(ScrollListView listView, ImageView imageView) {
        listView.setVisibility(View.GONE);
        Util.rotate(imageView, 90, 0);
        switch (listView.getId()) {
            case R.id.connectBluetooth_alreadyPaired_slv:
                readyListIsOpen = false;
                break;
            case R.id.connectBluetooth_nearby_slv:
                nearbyListIsOpen = false;
                break;
        }
    }

    /**
     * 蓝牙连接状态改变
     *
     * @param name    变化后名称
     * @param address 变化后的地址
     */
    @Override
    public void change(String name, String address) {
        connectBluetooth_name_tv.setText("名称：" + name);
        connectBluetooth_address_tv.setText("地址：" + address);
    }

    /**
     * 蓝牙连接成功
     *
     * @param name    连接的蓝牙名称
     * @param address 连接的蓝牙MAC地址
     */
    @Override
    public void success(String name, String address) {
        finish();
    }

    /**
     * 界面关闭时
     */
    @Override
    protected void onDestroy() {
        stopSearchBlue();
        super.onDestroy();
    }
}
