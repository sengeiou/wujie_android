package com.txd.hzj.wjlp.catchDoll.bean;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录对象
 */
public class GameRecordingBean implements Serializable{
    /**
     * id : 4
     * mode : 1
     * update_time : 1548050393
     * name : 抓手机
     * "roomid":"2",
     * room_pic : http://test.wujiemall.com/Uploads/Catcher/2019-01-19/5c42e35bc07b0.jpg
     * video_url :
     */

    private String id;
    private String mode;
    private String update_time;
    private String name;
    private String roomid;
    private String room_pic;
    private String video_url;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom_pic() {
        return room_pic;
    }

    public void setRoom_pic(String room_pic) {
        this.room_pic = room_pic;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }


}
