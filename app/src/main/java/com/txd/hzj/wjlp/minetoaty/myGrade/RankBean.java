package com.txd.hzj.wjlp.minetoaty.myGrade;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by OTKJ on 2018/5/4.
 */

public class RankBean implements Serializable {
    @Expose
    private String num;
    @Expose
    private String id;
    @Expose
    private String nickname;
    @Expose
    private String head_pic;
    @Expose
    private String parent_id;
    @Expose
    private String user_id;

    public String getHead_pic() {
        return head_pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic0() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
