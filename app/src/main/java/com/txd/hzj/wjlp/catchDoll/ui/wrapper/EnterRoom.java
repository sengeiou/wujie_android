package com.txd.hzj.wjlp.catchDoll.ui.wrapper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.socketcmd.SockAPP;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GameRoomActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/4 14:20
 * 功能描述：
 */
public class EnterRoom {
    private SockAPP sendThread;
    private String inRoomNumber; // 点击房间的房间号
    private String inRoomMac; // 点击房间的Mac地址
    private BaseAty mActivity;
    private String playerUrl1, playerUrl2;


    public EnterRoom(String inRoomNumber, String inRoomMac,BaseAty activity) {
        this.inRoomNumber = inRoomNumber;
        this.inRoomMac = inRoomMac;
        mActivity = activity;
        sendThread = new SockAPP();
        sendThread.StartWokring(handler, Constant.SERVER_IP, Constant.SERVER_PORT);
        Constant.SOCK_APP = sendThread;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SOCKET_LINE_SUCCESS:
                    L.i("Socket连接成功....");
                    String amac = inRoomNumber; // 房间号 id
                    String mac = inRoomMac; // 机器MAC地址
                    //发送进设备命令-并切换界面
                    JSONObject cmdJson = new JSONObject();
                    try {
                        //                      cmd:{"cmd":"enter_room","mac":mac} // 进入房间
                        cmdJson.put("cmd", "enter_room");
                        cmdJson.put("mac", mac);

                        String jsoncmd = cmdJson.toString();
                        byte msg_content[] = new byte[3 + jsoncmd.length()];
                        msg_content[0] = (byte) 0xda;
                        msg_content[1] = (byte) (jsoncmd.length() / 256);
                        msg_content[2] = (byte) (jsoncmd.length() % 256);
                        System.arraycopy(jsoncmd.getBytes(), 0, msg_content, 3, jsoncmd.getBytes().length);
                        if (sendThread != null) {
                            sendThread.SendOut(msg_content);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    playerUrl1 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + mac + "_1";
                    playerUrl2 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + mac + "_2";

                    mActivity.removeProgressDialog(); // 移除进入房间时候展示的等待界面

                    Bundle bundle = new Bundle();
                    bundle.putString("inRoomNumber", inRoomNumber);
                    bundle.putString(Constant.VIDEO_LIVE_1_URL_KEY, playerUrl1);
                    bundle.putString(Constant.VIDEO_LIVE_2_URL_KEY, playerUrl2);
                    Intent intent = new Intent(mActivity,GameRoomActivity.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                    break;
                case Constant.SOCKET_LINE_FAIL:
                    mActivity.removeProgressDialog(); // 移除进入房间时候展示的等待界面
                    mActivity.showToast("连接失败，暂时无法进入房间。。。。");
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
