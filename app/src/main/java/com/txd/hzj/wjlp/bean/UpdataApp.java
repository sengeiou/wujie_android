package com.txd.hzj.wjlp.bean;

import com.ants.theantsgo.base.BaseMode;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/8 0008
 * 时间：09:12
 * 描述：
 * ===============Txunda===============
 */

public class UpdataApp {
    /**
     * code : 1
     * message : 请求成功
     * data : {"code":"1","uri":"http://ysw.txunda.com/index.php/Api/Upgrade/memberUpgrade","message":"用户端正式版",
     * "name":"V1.0"}
     * nums : 0
     */

    private String code;
    private String message;
    private DataBean data;
    private int nums;

    public UpdataApp(String code, String message, DataBean data, int nums) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.nums = nums;
    }

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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * code : 1
         * uri : http://ysw.txunda.com/index.php/Api/Upgrade/memberUpgrade
         * message : 用户端正式版
         * name : V1.0
         */

        private String code;
        private String uri;
        private String message;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "code='" + code + '\'' +
                    ", uri='" + uri + '\'' +
                    ", message='" + message + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpdataApp{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}