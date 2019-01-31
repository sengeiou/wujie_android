package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：消息对象
 */
public class MessageBean {

    private String headUrl; // 头像
    private String content; // 消息内容

    public MessageBean() {
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "headUrl=" + headUrl +
                ", content='" + content + '\'' +
                '}';
    }
}
