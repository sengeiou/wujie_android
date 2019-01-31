package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录对象
 */
public class GameRecordingBean {

    private String headerUrl; // 头像地址
    private String name; // 名称
    private long time; // 时间
    private int type; // 抓取成败状态

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GameRecordingBean{" +
                "headerUrl='" + headerUrl + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
