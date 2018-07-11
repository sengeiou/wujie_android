package com.ants.theantsgo.base;

import java.io.Serializable;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/5 0005
 * 时间：09:12
 * 描述：所有Mode的基类
 */

public class BaseMode implements Serializable {
    public String code;
    public String message;

    public BaseMode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getFlag() {
        return code;
    }

    public void setFlag(String flag) {
        this.code = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
