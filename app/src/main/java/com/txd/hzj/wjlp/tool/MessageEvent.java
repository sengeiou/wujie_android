package com.txd.hzj.wjlp.tool;

/**
 * Created by Txd_lienchao on 2018/3/14 0014 下午 3:23.
 * 功能描述:事件总线
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class MessageEvent {
    private String message;
    private int code;
    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
