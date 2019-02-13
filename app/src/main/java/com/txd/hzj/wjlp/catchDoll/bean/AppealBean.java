package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：申诉对象
 */
public class AppealBean {

    private int id; // 申诉编号
    private String causeStr; // 申诉原因
    private boolean isChecked; // 是否被选中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauseStr() {
        return causeStr;
    }

    public void setCauseStr(String causeStr) {
        this.causeStr = causeStr;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
