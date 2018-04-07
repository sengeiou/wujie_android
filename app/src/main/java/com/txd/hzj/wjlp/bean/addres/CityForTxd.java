package com.txd.hzj.wjlp.bean.addres;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:24
 * 描述：
 * ===============Txunda===============
 */

public class CityForTxd implements IPickerViewData, Parcelable {
    @Override
    public String toString() {
        return "CityForTxd{" +
                "city_id='" + city_id + '\'' +
                ", cityname='" + cityname + '\'' +
                ", districts=" + districts +
                '}';
    }

    private String city_id;
    private String cityname;
    private List<DistrictsForTxd> districts;

    public CityForTxd(String city_id, String cityname, List<DistrictsForTxd> districts) {
        this.city_id = city_id;
        this.cityname = cityname;
        this.districts = districts;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<DistrictsForTxd> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsForTxd> districts) {
        this.districts = districts;
    }

    @Override
    public String getPickerViewText() {
        return getCityname();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city_id);
        dest.writeString(this.cityname);
        dest.writeList(this.districts);
    }

    protected CityForTxd(Parcel in) {
        this.city_id = in.readString();
        this.cityname = in.readString();
        this.districts = new ArrayList<DistrictsForTxd>();
        in.readList(this.districts, DistrictsForTxd.class.getClassLoader());
    }

    public static final Parcelable.Creator<CityForTxd> CREATOR = new Parcelable.Creator<CityForTxd>() {
        @Override
        public CityForTxd createFromParcel(Parcel source) {
            return new CityForTxd(source);
        }

        @Override
        public CityForTxd[] newArray(int size) {
            return new CityForTxd[size];
        }
    };
}
