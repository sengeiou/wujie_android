package com.txd.hzj.wjlp.bean.addres;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:25
 * 描述：
 * ===============Txunda===============
 */

public class DistrictsForTxd implements IPickerViewData, Parcelable {

    @Override
    public String toString() {
        return "DistrictsForTxd{" +
                "districtname='" + districtname + '\'' +
                ", district_id='" + district_id + '\'' +
                '}';
    }

    /**
     * districtName : 东城区
     * district_id : 500
     */

    private String districtname;
    private String district_id;

    public DistrictsForTxd(String districtname, String district_id) {
        this.districtname = districtname;
        this.district_id = district_id;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    @Override
    public String getPickerViewText() {
        return getDistrictname();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.districtname);
        dest.writeString(this.district_id);
    }

    protected DistrictsForTxd(Parcel in) {
        this.districtname = in.readString();
        this.district_id = in.readString();
    }

    public static final Parcelable.Creator<DistrictsForTxd> CREATOR = new Parcelable.Creator<DistrictsForTxd>() {
        @Override
        public DistrictsForTxd createFromParcel(Parcel source) {
            return new DistrictsForTxd(source);
        }

        @Override
        public DistrictsForTxd[] newArray(int size) {
            return new DistrictsForTxd[size];
        }
    };
}
