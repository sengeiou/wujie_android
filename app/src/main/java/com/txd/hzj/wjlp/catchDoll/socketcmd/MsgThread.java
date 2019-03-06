package com.txd.hzj.wjlp.catchDoll.socketcmd;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public class MsgThread extends Thread {

    // 发送消息的队列
    private Queue<byte[]> sendMsgQuene = new LinkedList<>();
    private SockAPP sockAPP;

    private boolean shouldStopNow;

    public MsgThread(SockAPP sockAPP) {
        this.sockAPP = sockAPP;
        shouldStopNow = false;
    }

    public synchronized void putMsg(byte[] msg) {
        sendMsgQuene.offer(msg);
        // 唤醒线程
        if (sendMsgQuene.size() != 0) {
            notify();
        }
    }

    /**
     * 停止
     */
    public void StopNow() {
        shouldStopNow = true;
        interrupt();
    }

    @Override
    public void run() {
        super.run();
        synchronized (this) {
            while (!shouldStopNow) {
                // 当队列里的消息发送完毕后，线程等待
                while (sendMsgQuene.size() > 0) {
                    byte[] msg = sendMsgQuene.poll();
                    if (sockAPP != null) {
                        sockAPP.sendMsg(msg);
                    }
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e("MsgThread", "发送线程退出");
        }
    }
}
