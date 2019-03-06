package com.txd.hzj.wjlp.catchDoll.socketcmd;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.txd.hzj.wjlp.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

//连接应用服务器的类。包含接收 心跳 和重连
public class SockAPP {
    private static final String TAG = "SockAPP";

    public String hostName = "192.168.0.4";
    public int port = 7771;

    private Socket socket = null;
    private Handler handler; // 接收回传消息的Handler
    Thread thSocket = null;
    MsgThread msgThread = null;
    private boolean ShouldStopNow = false;

    private int inRoomErrorCount = 0; // 进入房间时失败重试次数

    public SockAPP() {
    }

    public void SetHandler(Handler handler) {
        this.handler = handler;
    }

    // 停止消息和线程
    public void StopNow() {
        ShouldStopNow = true;
        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
            }
        }
        if (thSocket != null) {
            thSocket.interrupt();
            thSocket = null;
        }
        if (msgThread != null) {
            msgThread.StopNow();
            msgThread.interrupt();
            msgThread = null;
        }
    }

    /**
     * 连接
     *
     * @param handler
     * @param strHost
     * @param dport
     */
    public void StartWokring(Handler handler, String strHost, int dport) {
        this.handler = handler;
        hostName = strHost;
        port = dport;

        ShouldStopNow = false; // 停止设置为false

        // 启动断线重连和消息接收线程
        thSocket = new Thread(new ReceiveWatchDog());
        thSocket.start();

        msgThread = new MsgThread(this);
        msgThread.start();
    }

    /**
     * 设置新的服务器地址
     *
     * @param strHost
     * @param dport
     */
    public void ApplyNewServer(String strHost, int dport) {
        if (hostName.equals(strHost) && port == dport) {
            return;
        }
        hostName = strHost;
        port = dport;
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
        }
    }

    // 断线重连以及消息接收
    class ReceiveWatchDog implements Runnable {
        @Override
        public void run() {
            while (!ShouldStopNow) {
                if (hostName.equals("") || port == 0) { // 参数不正确，空循环
                    try {
                        Thread.sleep(1000);
                        continue;
                    } catch (InterruptedException ea) {
                        continue;
                    }
                } else {
                    try {
                        Log.e(TAG, "try connect====IP:" + hostName + "Port:" + Integer.toString(port));
                        ShouldStopNow = false;
                        // 获取地址
                        InetAddress addr = InetAddress.getByName(hostName);
                        String domainName = addr.getHostName();//获得主机名
                        String ServerIP = addr.getHostAddress();//获得IP地址

                        // 连接Socket
                        socket = new Socket(ServerIP, port);
                        Log.i(TAG, "run: " + socket.isConnected());
                        socket.setKeepAlive(true);

                        // 设置提示连接成功之后调用handler返回what=0
                        Message message1 = Message.obtain(); // 将IP地址保存给娃娃机
                        message1.what = Constant.SOCKET_LINE_SUCCESS;
                        message1.obj = ServerIP;
                        if (handler != null) {
                            handler.sendMessage(message1);
                        }
                    } catch (IOException e) {
                        inRoomErrorCount++; // 计数器加一，如果大于10，则停止线程
                        if (inRoomErrorCount > 6) {
                            StopNow();
                            if (handler != null) { // 如果handler不为空，则直接发送失败
                                Message message = new Message();
                                message.what = Constant.SOCKET_LINE_FAIL;
                                handler.sendMessage(message);
                            }
                        }
                        Log.e(TAG, "Connect excetion..retry after 3s");
                        Log.e(TAG, e.toString());
                        try {
                            Thread.sleep(1000);
                            continue;
                        } catch (InterruptedException ea) {
                            continue;
                        }
                    }
                }

                // 获取Socket中的数据并发送给handler
                while (socket != null && socket.isConnected() && !ShouldStopNow) {
                    try {
                        // 读取Sock中的消息数据
                        InputStream reader = socket.getInputStream();

                        byte head[] = new byte[3];
                        int recv_len = reader.read(head, 0, 3);
                        if (recv_len <= 0) {
                            Log.e(TAG, "接收到的数据长度小于0。断开连接");
                            if (socket != null) {
                                socket.close();
                                socket = null;
                            }
                            break;
                        }
                        // 接收并验证数据的完整性
                        int headInt = (head[0] & 0xff);
                        if (headInt == 0xda && recv_len == 3) {
                            int data_len = (head[1] & 0xff) * 256 + head[2] & 0xff;
                            byte data_body[] = new byte[data_len];

                            recv_len = reader.read(data_body, 0, data_len);
                            if (recv_len < data_len) {
                                Log.e(TAG, "接收不完整，继续接收");
                                int total_recv_ren = recv_len;
                                int left_data_len = data_len - recv_len;
                                while (total_recv_ren < data_len) {
                                    recv_len = reader.read(data_body, total_recv_ren, left_data_len);
                                    if (recv_len <= 0) {
                                        Log.e(TAG, "接收到的数据长度小于0。断开连接");
                                        if (socket != null) {
                                            socket.close();
                                            socket = null;
                                        }
                                        break;
                                    }
                                    total_recv_ren += recv_len;
                                    left_data_len -= recv_len;
                                }
                            }

                            // 接收到Socket回传的完整数据发到handler
                            Message message = Message.obtain();
                            message.what = 10;
                            message.arg1 = data_len;
                            message.obj = data_body;
                            if (handler != null) {
                                handler.sendMessage(message);
                            }
                        }
                    } catch (IOException ea) {
                        try { // 产生异常断开连接
                            if (socket != null) {
                                socket.close();
                                socket = null;
                            }
                        } catch (IOException aa) {
                            break;
                        }
                    }
                }
            }
            Log.e(TAG, "接收线程退出.");
        }
    }

    public void SendOut(byte[] msg) {
        if (msgThread != null) {
            msgThread.putMsg(msg);
        }
    }

    /**
     * Socket发送消息
     *
     * @param msg byte数组消息
     */
    void sendMsg(byte[] msg) {
        if (socket == null) {
            Log.e(TAG, "socket是空,不发送");
            return;
        }
        try {
            if (socket != null && socket.isConnected()) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(msg);
                outputStream.flush();
            } else {
                Log.e(TAG, "socket没有连接.不发送");
            }
        } catch (IOException e) {
        }
    }

    /**
     * byte数组转String
     *
     * @param buffer byte数组
     * @return
     */
    public static final String bytesToHexString(byte[] buffer) {
        StringBuffer stringBuffer = new StringBuffer(buffer.length);
        String temp;
        for (int i = 0; i < buffer.length; ++i) {
            temp = Integer.toHexString(0xff & buffer[i]);
            if (temp.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }
}
