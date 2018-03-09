package com.txd.hzj.wjlp.bean;

/**
 * Created by Administrator on 2018/2/24.
 */

public class City1 {
    String region_id,region_name,letter;

    public City1(String region_id, String region_name, String letter) {
        this.region_id = region_id;
        this.region_name = region_name;
        this.letter = letter;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getRegion_id() {
        return region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public String getLetter() {
        return letter;
    }

    @Override
    public String toString() {
        return "City1{" +
                "region_id='" + region_id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }
}
