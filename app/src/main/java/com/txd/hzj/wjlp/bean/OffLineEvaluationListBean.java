package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/2 16:12
 * 功能描述：线下店铺评论列表页的bean类
 */
public class OffLineEvaluationListBean {

    /**
     * c_id : 5
     * nickname : 无界新人1508
     * start_time : 2018-07-23 15:50:49
     * environment : 5
     * head_pic : http://img.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif
     * content : 店铺不错
     * picture : [{"path":"http://img.wujiemall.com/Uploads/Merchant/2018-07-31/5b6016ba3d478.jpg"}]
     */

    private String c_id;
    private String nickname;
    private String start_time;
    private String environment;
    private String head_pic;
    private String content;
    private List<PictureBean> picture;

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }

    public static class PictureBean {
        /**
         * path : http://img.wujiemall.com/Uploads/Merchant/2018-07-31/5b6016ba3d478.jpg
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
