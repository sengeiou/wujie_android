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
    /**
     * 型号名称
     */
    private String style_name;
    /**
     * 车型未选中图标
     */
    private String style_img;
    /**
     * 车型选中图片
     */
    private String true_style_img;
    /**
     * 型号id
     */
    private String style_id;
    /**
     * 品牌id
     */
    private String brand_id;
    /**
     * 品牌名称
     */
    private String brand_name;

    /**
     * 品牌logo
     */
    private String brand_logo;
    /**
     * 选中logo
     */
    private String true_brand_logo;

    public CarBean(boolean selecet, String style_name, String style_img, String true_style_img, String style_id,
                   String brand_id, String brand_name, String brand_logo, String true_brand_logo) {
        this.selecet = selecet;
        this.style_name = style_name;
        this.style_img = style_img;
        this.true_style_img = true_style_img;
        this.style_id = style_id;
        this.brand_id = brand_id;
        this.brand_name = brand_name;
        this.brand_logo = brand_logo;
        this.true_brand_logo = true_brand_logo;
    }

    public boolean isSelecet() {
        return selecet;
    }

    public void setSelecet(boolean selecet) {
        this.selecet = selecet;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getStyle_img() {
        return style_img;
    }

    public void setStyle_img(String style_img) {
        this.style_img = style_img;
    }

    public String getTrue_style_img() {
        return true_style_img;
    }

    public void setTrue_style_img(String true_style_img) {
        this.true_style_img = true_style_img;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getTrue_brand_logo() {
        return true_brand_logo;
    }

    public void setTrue_brand_logo(String true_brand_logo) {
        this.true_brand_logo = true_brand_logo;
    }

    @Override
    public String toString() {
        return "CarBean{" +
                "selecet=" + selecet +
                ", style_name='" + style_name + '\'' +
                ", style_img='" + style_img + '\'' +
                ", true_style_img='" + true_style_img + '\'' +
                ", style_id='" + style_id + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", brand_logo='" + brand_logo + '\'' +
                ", true_brand_logo='" + true_brand_logo + '\'' +
                '}';
    }
}
