package com.txd.hzj.wjlp.new_wjyp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/12.
 */

public class Invoice1 implements Serializable {
    String tax;
    String express_fee;
    String tax_pay;
    String invoice_type;
    String text1, text2, text3, text4, text5, text6;

    public Invoice1() {
    }

    public String getTax_pay() {
        return tax_pay;
    }

    public void setTax_pay(String tax_pay) {
        this.tax_pay = tax_pay;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getText4() {
        return text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText3() {
        return text3;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getTax() {
        return tax;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public String getInvoice_type() {
        return invoice_type;
    }


    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6;
    }

    protected Invoice1(Parcel in) {
        tax = in.readString();
        express_fee = in.readString();
        invoice_type = in.readString();
        text1 = in.readString();
        text2 = in.readString();
        text3 = in.readString();
        text4 = in.readString();
        text5 = in.readString();
        text6 = in.readString();
    }

//    public static final Creator<Invoice1> CREATOR = new Creator<Invoice1>() {
//        @Override
//        public Invoice1 createFromParcel(Parcel in) {
//            return new Invoice1(in);
//        }
//
//        @Override
//        public Invoice1[] newArray(int size) {
//            return new Invoice1[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(tax);
//        dest.writeString(express_fee);
//        dest.writeString(invoice_type);
//        dest.writeString(text1);
//        dest.writeString(text2);
//        dest.writeString(text3);
//        dest.writeString(text4);
//        dest.writeString(text5);
//        dest.writeString(text6);
//    }

    @Override
    public String toString() {
        return "Invoice1{" +
                "tax='" + tax + '\'' +
                ", express_fee='" + express_fee + '\'' +
                ", invoice_type='" + invoice_type + '\'' +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                ", text4='" + text4 + '\'' +
                ", text5='" + text5 + '\'' +
                ", text6='" + text6 + '\'' +
                '}';
    }
}
