package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：13:56
 * 描述：
 * ===============Txunda===============
 */

public class FeedbackType {


    /**
     * code : 1
     * message : 请求成功
     * data : {"user_id":"账号id","real_name":"姓名","feedback_type":[{"f_type_id":"意见反馈类型id","f_type_name":"问题类型"}]}
     * nums : 0
     */

    private String code;
    private String message;
    private DataBean data;
    private String nums;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * user_id : 账号id
         * real_name : 姓名
         * feedback_type : [{"f_type_id":"意见反馈类型id","f_type_name":"问题类型"}]
         */

        private String user_id;
        private String real_name;
        private List<FeedbackTypeBean> feedback_type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public List<FeedbackTypeBean> getFeedback_type() {
            return feedback_type;
        }

        public void setFeedback_type(List<FeedbackTypeBean> feedback_type) {
            this.feedback_type = feedback_type;
        }

        public static class FeedbackTypeBean {
            /**
             * f_type_id : 意见反馈类型id
             * f_type_name : 问题类型
             */

            private String f_type_id;
            private String f_type_name;

            public String getF_type_id() {
                return f_type_id;
            }

            public void setF_type_id(String f_type_id) {
                this.f_type_id = f_type_id;
            }

            public String getF_type_name() {
                return f_type_name;
            }

            public void setF_type_name(String f_type_name) {
                this.f_type_name = f_type_name;
            }
        }
    }
}
