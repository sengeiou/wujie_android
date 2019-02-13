package com.txd.hzj.wjlp.bluetoothPrint;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.ants.theantsgo.util.PreferencesUtils;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.tool.Util;

import java.util.Set;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：蓝牙操作工具类
 */
public class BluetoothUtils {

    private static final String TAG = "BluetoothUtils";

    private static BluetoothUtils bluetoothUtils;
    private Context context;

    public static PrinterInstance printer; // 设备实例（此处为打印机实例）
    private boolean connecting = false; // 是否正在连接中
    public static boolean isHasPrinter = false; // 是否已有设备连接

    private ConnectSuccess connectSuccess; // 连接成功接口回调
    private BluetoothChangLister bluetoothChangLister; // 蓝牙连接改变监听

    private static BluetoothClient mClient;

    public static BluetoothUtils getInstance(Context context) {
        if (bluetoothUtils == null) {
            synchronized (BluetoothUtils.class) {
                if (bluetoothUtils == null) {
                    bluetoothUtils = new BluetoothUtils(context);
                }
            }
        }
        return bluetoothUtils;
    }

    public BluetoothUtils(Context context) {
        this.context = context;
        mClient = new BluetoothClient(context.getApplicationContext());
    }

    /**
     * 设置打印设备
     *
     * @param printer
     */
    public void setPrinter(PrinterInstance printer) {
        this.printer = printer;
    }

    /**
     * 连接设备
     */
    public void connection() {
        if (printer != null) {
            connecting = true;
            printer.openConnection();
        }
    }

    /**
     * 是否正在连接中
     *
     * @return
     */
    public boolean isConnecting() {
        return connecting;
    }

    /**
     * 是否已有设备连接
     *
     * @return
     */
    public boolean isConnect() {
        return isHasPrinter;
    }

    /**
     * 设置并连接设备
     *
     * @param bluetoothDevice 蓝牙设备
     */
    public void openPrinter(final BluetoothDevice bluetoothDevice) {
        DemoApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //连接保存
                PreferencesUtils.saveBluetoothName(context, bluetoothDevice.getName());
                PreferencesUtils.saveBluetoothAddress(context, bluetoothDevice.getAddress());
                setPrinter(new PrinterInstance(context, bluetoothDevice, mHandler)); // 设置连接设备
                connection();
            }
        });
    }

    /**
     * 断开连接设备
     */
    public void disConnect(final String msg) {
        isHasPrinter = false;
        DemoApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (printer != null) {
                    printer.closeConnection();
                    printer = null;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Util.showToastThread(context, msg);
                    }
                });
            }
        });
    }

    /**
     * 设置默认连接
     */
    public void defaultConnection() {
        String bluetoothAddress = PreferencesUtils.getBluetoothAddress(context);
        if (bluetoothAddress == null || bluetoothAddress.isEmpty()) {
            return;
        }
        // 获取已连接过的设备进行检索，如果有保存的地址则直接新建设备进行连接
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            if (bluetoothAddress.equals(device.getAddress())) {
                printer = new PrinterInstance(context, device, mHandler);
                printer.openConnection();
                return;
            }
        }
    }

//    /**
//     * 修改设备名称
//     *
//     * @param printerName 设备的名称
//     */
//    public void changeBlueName(final String printerName) {
//        DemoApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Util.showToastThread(context, "修改名称，请稍后....");
//                    // 进入空中AT指令
//                    String AT = "$OpenFscAtEngine$";
//                    printer.sendByteData(AT.getBytes());
//                    Thread.sleep(500);
//                    byte[] read = printer.read();
//                    if (read == null) {
//                        Util.showToastThread(context, "修改失败....");
//                    } else {
//                        String readString = new String(read);
//                        if (readString.contains("$OK,Opened$")) {
//                            printer.sendByteData(("AT+NAME=" + printerName + "\r\n").getBytes());
//                            Thread.sleep(500);
//                            byte[] isSuccess = printer.read();
//                            if (new String(isSuccess).contains("OK")) {
//                                Util.showToastThread(context, "修改成功....");
//                                PreferencesUtils.saveBluetoothName(context, printerName);
//                                if (bluetoothChangLister != null) {
//                                    ((Activity) context).runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            bluetoothChangLister.change(printerName, PreferencesUtils.getBluetoothAddress(context));
//                                        }
//                                    });
//                                }
//                            } else {
//                                Util.showToastThread(context, "修改失败....");
//                            }
//                        }
//                    }
//                } catch (InterruptedException e) {
////                  TODO  CatchHandler.getInstance().saveCatchInfo2File(e);
//                    Log.e(TAG, "run: Exception == >> " + e.toString());
//                }
//            }
//        });
//    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String bluetoothName = "未连接蓝牙";
            String bluetoothAddress = bluetoothName;

            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS: // 连接成功
                    isHasPrinter = true;
                    Util.showToast(context, "连接成功....");
                    bluetoothName = PreferencesUtils.getBluetoothName(context);
                    bluetoothAddress = PreferencesUtils.getBluetoothAddress(context);
                    if (connectSuccess != null) {
                        connectSuccess.success(bluetoothName, bluetoothAddress);
                    }
                    break;
                case PrinterConstants.Connect.FAILED: // 连接失败
                    disConnect("连接失败....");
                    if (bluetoothChangLister != null) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bluetoothChangLister.change("", PreferencesUtils.getBluetoothAddress(context));
                            }
                        });
                    }
                    break;
                case PrinterConstants.Connect.CLOSED: // 连接断开
                    disConnect("连接断开....");
                    if (bluetoothChangLister != null) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bluetoothChangLister.change("", PreferencesUtils.getBluetoothAddress(context));
                            }
                        });
                    }
                    break;
            }

            connecting = false; // 连接已结束
        }
    };

    /**
     * 搜索附近的蓝牙设备
     */
    public void search(SearchResponse response) {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        mClient.search(request, response);
    }

    /**
     * 停止搜索
     */
    public void stopSearch() {
        mClient.stopSearch();
    }

    // ========================= 接口 =========================

    /**
     * 【接口】连接成功
     */
    public interface ConnectSuccess {
        /**
         * 连接成功回调
         *
         * @param name    连接的蓝牙名称
         * @param address 连接的蓝牙MAC地址
         */
        void success(String name, String address);
    }

    /**
     * 设置接口
     *
     * @param connectSuccess 连接成功接口
     */
    public void setOnConnectSuccess(ConnectSuccess connectSuccess) {
        this.connectSuccess = connectSuccess;
    }

    /**
     * 【接口】蓝牙改变监听
     */
    public interface BluetoothChangLister {
        /**
         * 蓝牙变化
         *
         * @param name    变化后名称
         * @param address 变化后的地址
         */
        void change(String name, String address);
    }

    /**
     * 设置接口
     *
     * @param bluetoothChangLister 实现的接口监听
     */
    public void setBluetoothChangLister(BluetoothChangLister bluetoothChangLister) {
        this.bluetoothChangLister = bluetoothChangLister;
    }

}
