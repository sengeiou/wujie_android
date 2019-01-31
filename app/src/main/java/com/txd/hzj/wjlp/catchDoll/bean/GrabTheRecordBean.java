package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：抓娃娃抓中记录
 */
public class GrabTheRecordBean {

    private String headUrl; // 头像
    private String userName; // 用户名称
    private long time; // 时间戳
    private String content; // 内容
    private String roomNumber; // 房间号
    private String videoUrl; // 视频回放地址

    public GrabTheRecordBean() {
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "GrabTheRecordBean{" +
                "headUrl='" + headUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
