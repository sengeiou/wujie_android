package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:31
 * 描述：
 * ===============Txunda===============
 */

public class GroupBean {
    /**
     * id : 团购ID
     * group_buy_id : 组团ID
     * start_time : 开始时间
     * status : 状态
     * group_num : 需参团人数
     * head_user : {"head_pic":"头像","nickname":"昵称"}
     * diff : 参团信息
     */

    private String id;
    private String group_buy_id;
    private String start_time;
    private String status;
    private String group_num;
    private HeadUserBean head_user;
    private String diff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_buy_id() {
        return group_buy_id;
    }

    public void setGroup_buy_id(String group_buy_id) {
        this.group_buy_id = group_buy_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public HeadUserBean getHead_user() {
        return head_user;
    }

    public void setHead_user(HeadUserBean head_user) {
        this.head_user = head_user;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public static class HeadUserBean {
        /**
         * head_pic : 头像
         * nickname : 昵称
         */

        private String head_pic;
        private String nickname;

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public String toString() {
            return "HeadUserBean{" +
                    "head_pic='" + head_pic + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "id='" + id + '\'' +
                ", group_buy_id='" + group_buy_id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", status='" + status + '\'' +
                ", group_num='" + group_num + '\'' +
                ", head_user=" + head_user +
                ", diff='" + diff + '\'' +
                '}';
    }
}
