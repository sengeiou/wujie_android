package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 16:26
 * 描述：汽车购车型筛选bean(9-1汽车购)
 * ===============Txunda===============
 */
public class CarBean {

    private boolean selecet;
    private int unSelectIcon;
    private int selectIcon;
    private String carname;

    public CarBean(boolean selecet, int unSelectIcon, int selectIcon, String carname) {
        this.selecet = selecet;
        this.unSelectIcon = unSelectIcon;
        this.selectIcon = selectIcon;
        this.carname = carname;
    }

    public boolean isSelecet() {
        return selecet;
    }

    public void setSelecet(boolean selecet) {
        this.selecet = selecet;
    }

    public int getUnSelectIcon() {
        return unSelectIcon;
    }

    public void setUnSelectIcon(int unSelectIcon) {
        this.unSelectIcon = unSelectIcon;
    }

    public int getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(int selectIcon) {
        this.selectIcon = selectIcon;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }
}
