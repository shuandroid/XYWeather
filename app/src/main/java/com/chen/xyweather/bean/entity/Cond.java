package com.chen.xyweather.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chen on 17-3-23.
 *  daily forecast 中的天气状况
 */
public class Cond implements Parcelable {

    public String code_d;
    public String code_n;
    public String txt_d;
    public String txt_n;

    public Cond() {


    }

    protected Cond(Parcel in) {
        code_d = in.readString();
        code_n = in.readString();
        txt_d = in.readString();
        txt_n = in.readString();
    }

    public static final Creator<Cond> CREATOR = new Creator<Cond>() {
        @Override
        public Cond createFromParcel(Parcel in) {
            return new Cond(in);
        }

        @Override
        public Cond[] newArray(int size) {
            return new Cond[size];
        }
    };

    public String getCode_d() {
        return code_d;
    }

    public String getCode_n() {
        return code_n;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public void setCode_d(String code_d) {
        this.code_d = code_d;
    }

    public void setCode_n(String code_n) {
        this.code_n = code_n;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code_d);
        dest.writeString(code_n);
        dest.writeString(txt_d);
        dest.writeString(txt_n);
    }
}
