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
    private String label;
    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, String label) {
        this.message = message;
        this.label = label;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
