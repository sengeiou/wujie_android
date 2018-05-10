package com.txd.hzj.wjlp.new_wjyp;

import com.txd.hzj.wjlp.bean.commodity.PicturesBean;

import java.util.List;

/**
 * 评论实体类
 *
 */

public class BeanComment {
    /**
     * user_id : 27
     * exterior : 5
     * space : 4
     * controllability : 5
     * consumption : 5
     * label_str : ,2,3,5,
     * pictures : 10928,10929,10930,10931,10932
     * content : 跟他们一起去玩啊
     * create_time : 2017-12-01 14:26
     * comment_star : 4.8
     * pictures_arr : [{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b14490.png"},{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b182be.png"},{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b1bdf5.png"},{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b1fb64.png"},{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b22d05.png"}]
     * label_arr : [{"label":"外观漂亮"},{"label":"动力十足"},{"label":"耐力好"}]
     * nickname : GYM
     * head_pic : http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png
     */

    private String user_id;
    private String exterior;
    private String space;
    private String controllability;
    private String consumption;
    private String label_str;
    private String pictures;
    private String content;
    private String create_time;
    private String comment_star;
    private String nickname;
    private String head_pic;
    private List<PicturesBean> pictures_arr;
    private List<LabelArrBean> label_arr;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getControllability() {
        return controllability;
    }

    public void setControllability(String controllability) {
        this.controllability = controllability;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getLabel_str() {
        return label_str;
    }

    public void setLabel_str(String label_str) {
        this.label_str = label_str;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getComment_star() {
        return comment_star;
    }

    public void setComment_star(String comment_star) {
        this.comment_star = comment_star;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public List<PicturesBean> getPictures_arr() {
        return pictures_arr;
    }

    public void setPictures_arr(List<PicturesBean> pictures_arr) {
        this.pictures_arr = pictures_arr;
    }

    public List<LabelArrBean> getLabel_arr() {
        return label_arr;
    }

    public void setLabel_arr(List<LabelArrBean> label_arr) {
        this.label_arr = label_arr;
    }

    public static class PicturesArrBean {
        /**
         * path : http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a20f61b14490.png
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class LabelArrBean {
        /**
         * label : 外观漂亮
         */

        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
