package com.txd.hzj.wjlp.http.message;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/27 0027
 * 时间：09:48
 * 描述：用户消息模块
 * ===============Txunda===============
 */

public class UserMessagePst extends BasePresenter {
    private UserMessage userMessage;

    public UserMessagePst(BaseView baseView) {
        super(baseView);
        userMessage = new UserMessage();
    }

    // 消息中心
    public void newMsg(String account_json, int p, boolean show) {
        if (show)
            baseView.showDialog();
        userMessage.newMsg(account_json, p, baseView);
    }

    /**
     * 消息列表
     *
     * @param p    分页
     * @param type 消息类型
     * @param show 是否显示加载框
     */
    public void messageList(int p, int type, boolean show) {
        if (show)
            baseView.showDialog();
        if (0 == type) {
            userMessage.orderMsgList(p, baseView);
            return;
        }
        if (1 == type) {
            userMessage.msgList(p, baseView);
            return;
        }
        if (2 == type) {
            userMessage.announceList(p, baseView);
        }
    }
    // 公告内容
    public void announceInfo(String id) {
        baseView.showDialog();
        userMessage.announceInfo(id, baseView);
    }
}
