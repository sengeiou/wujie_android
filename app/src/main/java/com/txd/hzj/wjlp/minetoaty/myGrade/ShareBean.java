package com.txd.hzj.wjlp.minetoAty.myGrade;

/**
 * by Txunda_LH on 2018/2/26.
 */

public class ShareBean {
    String num ;
    String nickname;
    String head_pic;

    public ShareBean(String num, String nickname, String head_pic) {
        this.num = num;
        this.nickname = nickname;
        this.head_pic = head_pic;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getNum() {
        return num;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "num='" + num + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
