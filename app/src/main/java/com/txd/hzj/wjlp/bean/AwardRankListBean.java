package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/12/25 15:02
 * 功能描述：
 */
public class AwardRankListBean {

    /**
     * code : 1
     * message : 获取成功
     * data : {"user_info":{"rank":"1","user_id":"100","user_name":"cold","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"},"rank_list":[{"rank":"1","user_id":"100","user_name":"cold","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"},{"rank":"2","user_id":"22","user_name":"Cyf","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"}]}
     * nums : 0
     */

    private int code;
    private String message;
    private DataBean data;
    private String nums;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * user_info : {"rank":"1","user_id":"100","user_name":"cold","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"}
         * rank_list : [{"rank":"1","user_id":"100","user_name":"cold","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"},{"rank":"2","user_id":"22","user_name":"Cyf","head_pic":"http://img.wujiemall.com/Public/Pc/img/default.png","user_count":"0"}]
         */

        private UserInfoBean user_info;
        private List<RankListBean> rank_list;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<RankListBean> getRank_list() {
            return rank_list;
        }

        public void setRank_list(List<RankListBean> rank_list) {
            this.rank_list = rank_list;
        }

        public static class UserInfoBean {
            /**
             * rank : 1
             * user_id : 100
             * user_name : cold
             * head_pic : http://img.wujiemall.com/Public/Pc/img/default.png
             * user_count : 0
             */

            private String rank;
            private String user_id;
            private String user_name;
            private String head_pic;
            private String user_count;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getUser_count() {
                return user_count;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }
        }

        public static class RankListBean {
            /**
             * rank : 1
             * user_id : 100
             * user_name : cold
             * head_pic : http://img.wujiemall.com/Public/Pc/img/default.png
             * user_count : 0
             */

            private String rank;
            private String user_id;
            private String user_name;
            private String head_pic;
            private String user_count;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getUser_count() {
                return user_count;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }
        }
    }
}
