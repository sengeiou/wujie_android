package com.txd.hzj.wjlp.view.keyboard;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/23 023 上午 10:03
 * 功能描述：
 */
class KeyModel {

    private int code;
    private String lable;

    public KeyModel(int code, String lable) {
        this.code = code;
        this.lable = lable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
